package naga.core.activity;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import naga.core.json.JsonObject;
import naga.core.orm.domainmodel.DataSourceModel;
import naga.core.routing.history.History;
import naga.core.spi.toolkit.GuiNode;

/**
 * @author Bruno Salmon
 */
class ActivityContextImpl implements ActivityContext {

    private final ActivityContext parentContext;
    private ActivityManager activityManager;
    private DataSourceModel dataSourceModel;
    private History history;
    private JsonObject params;

    public ActivityContextImpl(ActivityContext parentContext) {
        this.parentContext = parentContext;
    }

    @Override
    public ActivityManager getActivityManager() {
        return activityManager;
    }

    void setActivityManager(ActivityManager activityManager) {
        this.activityManager = activityManager;
    }

    void setDataSourceModel(DataSourceModel dataSourceModel) {
        this.dataSourceModel = dataSourceModel;
    }

    @Override
    public DataSourceModel getDataSourceModel() {
        return dataSourceModel != null || parentContext == null ? dataSourceModel : parentContext.getDataSourceModel();
    }

    public void setHistory(History history) {
        this.history = history;
    }

    @Override
    public History getHistory() {
        return history != null || parentContext == null ? history : parentContext.getHistory();
    }

    @Override
    public JsonObject getParams() {
        return params;
    }

    @Override
    public void setParams(JsonObject params) {
        this.params = params;
    }

    private Property<GuiNode> nodeProperty;
    @Override
    public Property<GuiNode> nodeProperty() {
        if (nodeProperty == null)
            nodeProperty = new SimpleObjectProperty<>();
        return nodeProperty;
    }

    private Property<GuiNode> mountNodeProperty;
    @Override
    public Property<GuiNode> mountNodeProperty() {
        if (mountNodeProperty == null)
            mountNodeProperty = new SimpleObjectProperty<>();
        return mountNodeProperty;
    }
}
