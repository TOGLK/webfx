package naga.toolkit.drawing.shapes;

import javafx.collections.ObservableList;
import naga.toolkit.properties.markers.*;
import naga.toolkit.transform.Transform;

import java.util.Collection;

/**
 * @author Bruno Salmon
 */
public interface Drawable extends
        HasOnMouseClickedProperty,
        HasLayoutXProperty,
        HasLayoutYProperty,
        HasVisibleProperty,
        HasOpacityProperty,
        HasClipProperty {

    ObservableList<Transform> getTransforms();

    Collection<Transform> localToParentTransforms();

    default void relocate(double x, double y) {
        setLayoutX(x);
        setLayoutY(y);
    }
}
