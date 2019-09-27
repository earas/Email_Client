package aras.controller;


import aras.EmailManager;
import aras.controller.services.LoginService;
import aras.model.EmailAccount;
import aras.view.MyViewFactory;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginWindowController extends BaseController {

    @FXML
    private PasswordField password;

    @FXML
    private Button login;

    @FXML
    private Label error;

    @FXML
    private TextField mailAdress;

    public LoginWindowController(EmailManager emailManager, MyViewFactory myViewFactory, String fxmlName) {
        super(emailManager, myViewFactory, fxmlName);
    }

    @FXML
    void loginPressed() {
        if(fieldAreValid()){
            EmailAccount emailAccount = new EmailAccount(mailAdress.getText(),password.getText());
            LoginService loginService =  new LoginService(emailAccount,emailManager);
            loginService.start();
            loginService.setOnSucceeded(event -> {
                EmailLoginResult emailLoginResult = loginService.getValue();
                switch (emailLoginResult) {
                    case SUCCESS:
                        System.out.println("login succesfull! "+emailAccount);
                        if(!myViewFactory.isMainViewInitialized()) {
                            myViewFactory.showMainWindow();
                        }
                        Stage stage = (Stage) error.getScene().getWindow();
                        myViewFactory.closeStage(stage);


                        return;
                    case FAILED_BY_UNEXPECTED_ERROR:
                        error.setText("unexpected error!");
                        return;
                    case FAILED_BY_CREDENTIALS:
                        error.setText("invalid credentials!");
                        return;
                        default:
                            return;
                }

            });

        }

       /* myViewFactory.showMainWindow();
        Stage stage = (Stage) error.getScene().getWindow();
        myViewFactory.closeStage(stage);*/

    }

    private boolean fieldAreValid() {
        if(mailAdress.getText().isEmpty()){
        error.setText("Fill email");

        return false;
        }

        if(password.getText().isEmpty()){
            error.setText("Fill password");
            return false;
        }
        return true;
    }

}
