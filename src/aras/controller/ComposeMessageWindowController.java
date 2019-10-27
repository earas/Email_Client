package aras.controller;
import aras.EmailManager;
import aras.controller.services.EmailSenderService;
import aras.model.EmailAccount;
import aras.view.MyViewFactory;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.web.HTMLEditor;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class ComposeMessageWindowController extends BaseController implements Initializable {

    @FXML
    private TextField recipientTextFIeld;

    @FXML
    private TextField subjectTextField;

    @FXML
    private HTMLEditor htmlEditor;

    @FXML
    private Label errorLabel;

    @FXML
    private ChoiceBox<EmailAccount> emailChoice;

    @FXML
    void sendButtonAction() {
       // System.out.println(htmlEditor.getHtmlText());
        EmailSenderService emailSenderService = new EmailSenderService(
                emailChoice.getValue(),
                subjectTextField.getText(),
                recipientTextFIeld.getText(),
                htmlEditor.getHtmlText()
        );
        emailSenderService.start();
        emailSenderService.setOnSucceeded(e ->{
            EmailSendingResult emailSendingResult = emailSenderService.getValue();
            switch (emailSendingResult){
                case SUCCESS:
                    Stage stage = (Stage) recipientTextFIeld.getScene().getWindow();
                    myViewFactory.closeStage(stage);
                    break;
                case FAILED_BY_PROVIDER:
                    errorLabel.setText("provider error");
                    break;
                case FAILED_BY_UNEXPECTED_ERROR:
                    errorLabel.setText("unexpected error");
                    break;
            }
        });
    }

    public ComposeMessageWindowController(EmailManager emailManager, MyViewFactory viewFactory, String fxmlName) {
        super(emailManager, viewFactory, fxmlName);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        emailChoice.setItems(emailManager.getEmailAccounts());
        emailChoice.setValue(emailManager.getEmailAccounts().get(0));
    }
}
