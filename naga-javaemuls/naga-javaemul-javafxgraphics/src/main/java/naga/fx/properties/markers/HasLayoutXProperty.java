package naga.fx.properties.markers;

import emul.javafx.beans.property.Property;

/**
 * @author Bruno Salmon
 */
public interface HasLayoutXProperty {

    Property<Double> layoutXProperty();
    default void setLayoutX(Double layoutX) { layoutXProperty().setValue(layoutX); }
    default Double getLayoutX() { return layoutXProperty().getValue(); }

}