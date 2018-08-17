package mongoose.activities.bothends.generic;

import javafx.scene.control.Button;
import mongoose.activities.bothends.logic.ui.theme.Theme;
import naga.framework.ui.graphic.controls.button.ButtonFactoryMixin;
import naga.framework.ui.graphic.controls.button.ButtonBuilder;
import naga.framework.ui.graphic.materialdesign.MaterialFactoryMixin;

/**
 * @author Bruno Salmon
 */
public interface MongooseButtonFactoryMixin extends ButtonFactoryMixin, MaterialFactoryMixin {

    @Override
    default Button styleButton(Button button) {
        button.textFillProperty().bind(Theme.mainTextFillProperty());
        return button;
    }

    default Button newBookButton() {
        return newBookButtonBuilder().build();
    }

    default ButtonBuilder newBookButtonBuilder() {
        return newColorButtonBuilder("Book>>", "#7fd504", "#2a8236");
    }

    default Button newSoldoutButton() {
        return newSoldoutButtonBuilder().build();
    }

    default ButtonBuilder newSoldoutButtonBuilder() {
        return newColorButtonBuilder("Soldout", "#e92c04", "#853416");
    }

}