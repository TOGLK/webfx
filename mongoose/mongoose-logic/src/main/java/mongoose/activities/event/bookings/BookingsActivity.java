package mongoose.activities.event.bookings;

import mongoose.format.PriceFormatter;
import naga.core.spi.platform.Platform;
import naga.core.spi.toolkit.Toolkit;
import naga.core.spi.toolkit.nodes.BorderPane;
import naga.core.spi.toolkit.nodes.CheckBox;
import naga.core.spi.toolkit.nodes.SearchBox;
import naga.core.spi.toolkit.nodes.Table;
import naga.core.ui.displayresultset.DisplayColumn;
import naga.core.ui.presentation.PresentationActivity;
import naga.core.ui.presentation.ViewBuilder;
import naga.core.ui.rx.RxFilter;

/**
 * @author Bruno Salmon
 */
public class BookingsActivity extends PresentationActivity<BookingsViewModel, BookingsPresentationModel> {

    public static ViewBuilder<BookingsViewModel> viewBuilder;

    public BookingsActivity() {
        super(BookingsPresentationModel::new);
        setViewBuilder(viewBuilder);
    }

    protected BookingsViewModel buildView(Toolkit toolkit) {
        // Building the UI components
        SearchBox searchBox = toolkit.createNode(SearchBox.class);
        Table table = toolkit.createNode(Table.class);
        CheckBox limitCheckBox = toolkit.createNode(CheckBox.class);

        return new BookingsViewModel(toolkit.createNode(BorderPane.class)
                .setTop(searchBox)
                .setCenter(table)
                .setBottom(limitCheckBox)
                , searchBox, table, limitCheckBox);
    }

    protected void bindViewModelWithPresentationModel(BookingsViewModel vm, BookingsPresentationModel pm) {
        // Hard coded initialization
        SearchBox searchBox = vm.getSearchBox();
        CheckBox limitCheckBox = vm.getLimitCheckBox();
        searchBox.setPlaceholder("Enter first name to narrow the list");
        searchBox.requestFocus();
        limitCheckBox.setText("Limit to 100");

        // Initialization from the presentation model current state
        searchBox.setText(pm.searchTextProperty().getValue());
        limitCheckBox.setSelected(pm.limitProperty().getValue());

        // Binding the UI with the presentation model for further state changes
        // User inputs: the UI state changes are transferred in the presentation model
        pm.searchTextProperty().bind(searchBox.textProperty());
        pm.limitProperty().bind(limitCheckBox.selectedProperty());
        pm.bookingsDisplaySelectionProperty().bind(vm.getTable().displaySelectionProperty());
        // User outputs: the presentation model changes are transferred in the UI
        vm.getTable().displayResultSetProperty().bind(pm.bookingsDisplayResultSetProperty());
    }

    @Override
    protected void initializePresentationModel(BookingsPresentationModel pm) {
        pm.eventIdProperty().setValue(getParams().get("eventId"));
    }


    protected void bindPresentationModelWithLogic(BookingsPresentationModel pm) {
        // Loading the domain model and setting up the reactive filter
        RxFilter rxFilter = createRxFilter("{class: 'Document', where: '!cancelled', orderBy: 'ref desc'}")
                // Condition
                .combine(pm.eventIdProperty(), s -> "{where: 'event=" + s + "'}")
                // Search box condition
                .combine(pm.searchTextProperty(), s -> s == null ? null : "{where: 'lower(person_firstName) like `%" + s.toLowerCase() + "%`'}")
                // Limit condition
                .combine(pm.limitProperty(), "{limit: '100'}")
                .setDisplayColumns(
                        new DisplayColumn("Ref", "ref"),
                        new DisplayColumn("First name", "person_firstName"),
                        new DisplayColumn("Last name", "person_lastName"),
                        new DisplayColumn("Invoiced", "price_net", PriceFormatter.SINGLETON),
                        new DisplayColumn("Deposit", "price_deposit", PriceFormatter.SINGLETON),
                        new DisplayColumn("Balance", "price_balance", PriceFormatter.SINGLETON))
                .displayResultSetInto(pm.bookingsDisplayResultSetProperty());

        pm.bookingsDisplaySelectionProperty().addListener((observable, oldValue, newValue) -> {
            int selectedRow = newValue.getSelectedRow();
            Platform.log("Selected row: " + selectedRow);
            if (selectedRow >= 0)
                Platform.log("Selected entity: " + rxFilter.getCurrentEntityList().get(selectedRow));
        });
    }
}
