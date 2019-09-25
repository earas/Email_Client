package aras.controller;

import aras.EmailManager;
import aras.view.MyViewFactory;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeView;
import javafx.scene.web.WebView;

public class MainWindowController extends BaseController {

    @FXML
    private TreeView<?> emailsTreeView;

    @FXML
    private TableView<?> emailsTableView;

    @FXML
    private WebView emailWebView;

    public MainWindowController(EmailManager emailManager, MyViewFactory myViewFactory, String fxmlName) {
        super(emailManager, myViewFactory, fxmlName);
    }

    @FXML
    void optionsAction() {
        myViewFactory.showOptionsWindow();

    }

    @FXML
    void addAccountAction() {
        myViewFactory.showLoginWindow();
    }

}
