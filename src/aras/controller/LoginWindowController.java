package aras.controller;


import aras.EmailManager;
import aras.view.MyViewFactory;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;

public class LoginWindowController extends BaseController {

    @FXML
    private PasswordField mail;

    @FXML
    private Button login;

    @FXML
    private Label error;

    public LoginWindowController(EmailManager emailManager, MyViewFactory myViewFactory, String fxmlName) {
        super(emailManager, myViewFactory, fxmlName);
    }

    @FXML
    void loginPressed() {

    }

}
