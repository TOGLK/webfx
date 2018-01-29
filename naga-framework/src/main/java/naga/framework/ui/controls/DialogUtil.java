package naga.framework.ui.controls;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import naga.framework.ui.layouts.LayoutUtil;
import naga.framework.ui.layouts.SceneUtil;
import naga.fx.properties.Properties;
import naga.fx.spi.Toolkit;
import naga.util.Booleans;
import naga.util.collection.Collections;
import naga.util.function.Consumer;

import java.util.ArrayList;
import java.util.List;

import static naga.framework.ui.layouts.LayoutUtil.createPadding;
import static naga.framework.ui.layouts.LayoutUtil.setMaxSizeToInfinite;
import static naga.framework.ui.layouts.LayoutUtil.setMaxSizeToPref;

/**
 * @author Bruno Salmon
 */
public class DialogUtil {

    private final static Property<Background> dialogBackgroundProperty = new SimpleObjectProperty<>();
    public static Property<Background> dialogBackgroundProperty() {
        return dialogBackgroundProperty;
    }

    private final static Property<Border> dialogBorderProperty = new SimpleObjectProperty<>();
    public static Property<Border> dialogBorderProperty() {
        return dialogBorderProperty;
    }


    public static DialogCallback showModalNodeInGoldLayout(Region modalNode, Pane parent) {
        return showModalNodeInGoldLayout(modalNode, parent, 0, 0);
    }

    public static DialogCallback showModalNodeInGoldLayout(DialogBuilder dialogBuilder, Pane parent) {
        return showModalNodeInGoldLayout(dialogBuilder, parent, 0, 0);
    }

    public static DialogCallback showModalNodeInGoldLayout(DialogBuilder dialogBuilder, Pane parent, double percentageWidth, double percentageHeight) {
        Region dialog = dialogBuilder.build();
        if (percentageWidth != 0)
            LayoutUtil.setPrefWidthToInfinite(dialog);
        if (percentageHeight != 0)
            LayoutUtil.setPrefHeightToInfinite(dialog);
        DialogCallback dialogCallback = showModalNodeInGoldLayout(dialog, parent, percentageWidth, percentageHeight);
        dialogBuilder.setDialogCallback(dialogCallback);
        return dialogCallback;
    }

    public static DialogCallback showModalNodeInGoldLayout(Region modalNode, Pane parent, double percentageWidth, double percentageHeight) {
        Insets padding = modalNode.getPadding();
        return showModalNode(LayoutUtil.createGoldLayout(decorate(modalNode), percentageWidth, percentageHeight), parent)
            .addCloseHook(() -> modalNode.setPadding(padding));
    }

    public static DialogCallback showModalNode(Region modalNode, Pane parent) {
        DialogCallback dialogCallback = createDialogCallback(setMaxSizeToInfinite(modalNode), parent);
        setUpModalNodeResizeRelocate(modalNode, parent, dialogCallback);
        return dialogCallback;
    }

    private static void setUpModalNodeResizeRelocate(Region modalNode, Pane parent, DialogCallback dialogCallback) {
        SceneUtil.onSceneReady(parent, scene ->
            dialogCallback.addCloseHook(
                Properties.runNowAndOnPropertiesChange(() -> {
                        Point2D parentSceneXY = parent.localToScene(0, 0);
                        double width = Math.min(parent.getWidth(), scene.getWidth() - parentSceneXY.getX());
                        double height = Math.min(parent.getHeight(), scene.getHeight() - parentSceneXY.getY());
                        modalNode.resizeRelocate(0, 0, width, height);
                    }, parent.widthProperty(), parent.heightProperty(), scene.widthProperty(), scene.heightProperty()
                )::unregister));
    }

    public static BorderPane decorate(Node content) {
        // Setting max width/height to pref width/height (otherwise the grid pane takes all space with cells in top left corner)
        if (content instanceof Region)
            setMaxSizeToPref(createPadding((Region) content));
        BorderPane decorator = new BorderPane(content);
        decorator.backgroundProperty().bind(dialogBackgroundProperty());
        decorator.borderProperty().bind(dialogBorderProperty());
        decorator.setMinHeight(0d);
        return decorator;
    }

    public static void showDialog(DialogContent dialogContent, Consumer<DialogCallback> okConsumer, Pane parent) {
        DialogCallback dialogCallback = showModalNodeInGoldLayout(dialogContent.build(), parent);
        dialogContent.getCancelButton().setOnAction(event -> dialogCallback.closeDialog());
        dialogContent.getOkButton().setOnAction(event -> okConsumer.accept(dialogCallback));
    }

    public static DialogCallback showDropUpOrDownDialog(Region dialogNode, Region buttonNode, Pane parent, ObservableValue resizeProperty, boolean up) {
        DialogCallback dialogCallback = createDialogCallback(dialogNode, parent);
        setUpDropDownDialogResizeRelocate(dialogNode, buttonNode, parent, dialogCallback, resizeProperty, up);
        return dialogCallback;
    }

    private static DialogCallback createDialogCallback(Region dialogNode, Pane parent) {
        dialogNode.setManaged(false);
        parent.getChildren().add(dialogNode);
        return new DialogCallback() {
            private final List<Runnable> closeHooks = new ArrayList<>();
            private boolean closed;
            @Override
            public void closeDialog() {
                if (!closed)
                    Toolkit.get().scheduler().runInUiThread(() -> {
                        // Sequence note: we call the hooks before removing the dialog from the UI because some hooks
                        // may be interested in the UI state before closing, like in ButtonSelector where it decides to
                        // restore the focus to the button if the last focus is inside the dialog
                        for (Runnable closeHook: closeHooks)
                            closeHook.run();
                        // Now we can remove the dialog from the UI
                        parent.getChildren().remove(dialogNode); // May clean the scene focus owner if it was inside
                    });
                closed = true;
            }

            @Override
            public boolean isDialogClosed() {
                return closed;
            }

            @Override
            public void showException(Throwable e) {
                Toolkit.get().scheduler().runInUiThread(() -> AlertUtil.showExceptionAlert(e, parent.getScene().getWindow()));
            }

            @Override
            public DialogCallback addCloseHook(Runnable closeHook) {
                closeHooks.add(closeHook);
                return this;
            }
        };
    }

    private static void setUpDropDownDialogResizeRelocate(Region dialogNode, Region buttonNode, Pane parent, DialogCallback dialogCallback, ObservableValue resizeProperty, boolean up) {
        SceneUtil.onSceneReady(buttonNode, scene -> {
            List<ObservableValue> reactingProperties = Collections.listOf(
                    buttonNode.widthProperty(),
                    buttonNode.heightProperty(),
                    resizeProperty);
            for (ScrollPane scrollPane = LayoutUtil.findScrollPaneAncestor(buttonNode); scrollPane != null; scrollPane = LayoutUtil.findScrollPaneAncestor(scrollPane)) {
                reactingProperties.add(scrollPane.hvalueProperty());
                reactingProperties.add(scrollPane.vvalueProperty());
            }
            setDropDialogUp(dialogNode, up);
            Runnable positionUpdater = () -> {
                Point2D buttonSceneXY = buttonNode.localToScene(0, 0);
                Point2D parentSceneXY = parent.localToScene(0, 0);
                //Logger.log("Computing dialogPrefWidth...");
                double dialogPrefWidth = dialogNode.prefWidth(-1);
                //Logger.log("dialogPrefWidth = " + dialogPrefWidth);
                double width = LayoutUtil.boundedSize(dialogPrefWidth, buttonNode.getWidth(), scene.getWidth() - buttonSceneXY.getX());
                double height = dialogNode.prefHeight(width);
                double deltaY = isDropDialogUp(dialogNode) ? -height : buttonNode.getHeight();
                Region.layoutInArea(dialogNode, buttonSceneXY.getX() - parentSceneXY.getX(), buttonSceneXY.getY() - parentSceneXY.getY() + deltaY, width, height, -1, null, true, false, HPos.LEFT, VPos.TOP, true);
            };
            dialogNode.getProperties().put("positionUpdater", positionUpdater); // used by updateDropUpOrDownDialogPosition()
            dialogCallback
                    .addCloseHook(Properties.runNowAndOnPropertiesChange(positionUpdater, reactingProperties)::unregister)
                    .addCloseHook(() -> dialogNode.relocate(0, 0))
                    .addCloseHook(SceneUtil.runOnceFocusIsOutside(dialogNode, dialogCallback::closeDialog)::unregister);
        });
    }

    public static void setDropDialogUp(Region dialogNode, boolean up) {
        dialogNode.getProperties().put("up", up);
    }

    public static boolean isDropDialogUp(Region dialogNode) {
        return Booleans.isTrue(dialogNode.getProperties().get("up"));
    }

    public static void updateDropUpOrDownDialogPosition(Region dialogNode) {
        Object positionUpdater = dialogNode.getProperties().get("positionUpdater");
        if (positionUpdater instanceof Runnable)
           ((Runnable) positionUpdater).run();
    }

}
