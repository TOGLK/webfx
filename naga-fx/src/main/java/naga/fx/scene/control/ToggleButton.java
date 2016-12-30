package naga.fx.scene.control;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import naga.fx.properties.markers.HasSelectedProperty;

/**
 * @author Bruno Salmon
 */
public abstract class ToggleButton extends ButtonBase implements
        HasSelectedProperty {

    private final Property<Boolean> selectedProperty = new SimpleObjectProperty<>(false);
    @Override
    public Property<Boolean> selectedProperty() {
        return selectedProperty;
    }
}