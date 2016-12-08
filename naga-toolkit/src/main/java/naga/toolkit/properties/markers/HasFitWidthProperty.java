package naga.toolkit.properties.markers;

import javafx.beans.property.Property;

/**
 * @author Bruno Salmon
 */
public interface HasFitWidthProperty {

    Property<Double> fitWidthProperty();
    default void setFitWidth(Double fitWidth) { fitWidthProperty().setValue(fitWidth); }
    default Double getFitWidth() { return fitWidthProperty().getValue(); }

}
