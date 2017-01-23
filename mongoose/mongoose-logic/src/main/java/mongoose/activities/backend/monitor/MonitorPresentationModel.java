package mongoose.activities.backend.monitor;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import naga.fxdata.displaydata.DisplayResultSet;

/**
 * @author Bruno Salmon
 */
public class MonitorPresentationModel {

    private final Property<DisplayResultSet> memoryDisplayResultSetProperty = new SimpleObjectProperty<>();
    Property<DisplayResultSet> memoryDisplayResultSetProperty() { return memoryDisplayResultSetProperty; }

    private final Property<DisplayResultSet> cpuDisplayResultSetProperty = new SimpleObjectProperty<>();
    Property<DisplayResultSet> cpuDisplayResultSetProperty() { return cpuDisplayResultSetProperty; }
}
