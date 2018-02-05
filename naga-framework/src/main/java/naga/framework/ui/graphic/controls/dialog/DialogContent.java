package naga.framework.ui.graphic.controls.dialog;

import javafx.scene.control.Button;
import javafx.scene.layout.Region;
import naga.framework.ui.i18n.I18n;

/**
 * @author Bruno Salmon
 */
public class DialogContent implements DialogBuilder {

    private String title;
    private String headerText;
    private String contentText;
    private String okText = "Ok";
    private String cancelText = "Cancel";
    private I18n i18n;

    private Button okButton = new Button();
    private Button cancelButton = new Button();

    private DialogCallback dialogCallback;

    public static DialogContent createConfirmationDialog(String headerText, String contentText, I18n i18n) {
        return createConfirmationDialog("Confirmation", headerText, contentText, i18n);
    }

    public static DialogContent createConfirmationDialog(String title, String headerText, String contentText, I18n i18n) {
        return new DialogContent().setTitle(title).setHeaderText(headerText).setContentText(contentText).setI18n(i18n).setYesNo();
    }

    @Override
    public I18n getI18n() {
        return i18n;
    }

    @Override
    public void setDialogCallback(DialogCallback dialogCallback) {
        this.dialogCallback = dialogCallback;
    }

    @Override
    public DialogCallback getDialogCallback() {
        return dialogCallback;
    }

    public DialogContent setTitle(String title) {
        this.title = title;
        return this;
    }

    public DialogContent setHeaderText(String headerText) {
        this.headerText = headerText;
        return this;
    }

    public DialogContent setContentText(String contentText) {
        this.contentText = contentText;
        return this;
    }

    public DialogContent setI18n(I18n i18n) {
        this.i18n = i18n;
        return this;
    }

    public DialogContent setYesNo() {
        okText = "Yes";
        cancelText = "No";
        return this;
    }


    public Button getOkButton() {
        return okButton;
    }

    public DialogContent setOkButton(Button okButton) {
        this.okButton = okButton;
        return this;
    }

    public Button getCancelButton() {
        return cancelButton;
    }

    public DialogContent setCancelButton(Button cancelButton) {
        this.cancelButton = cancelButton;
        return this;
    }

    @Override
    public Region build() {
        return new GridPaneBuilder(i18n)
                .addTextRow(headerText)
                .addTextRow(contentText)
                .addButtons(okText, okButton, cancelText, cancelButton)
                .build();
    }
}