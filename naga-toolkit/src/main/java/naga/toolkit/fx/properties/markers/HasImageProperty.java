package naga.toolkit.fx.properties.markers;


import javafx.beans.property.Property;
import naga.toolkit.fx.scene.image.Image;

/**
 * @author Bruno Salmon
 */
public interface HasImageProperty {

    Property<Image> imageProperty();
    default void setImage(Image image) { imageProperty().setValue(image); }
    default Image getImage() { return imageProperty().getValue(); }

}