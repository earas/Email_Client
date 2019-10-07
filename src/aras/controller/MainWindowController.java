package aras.controller;

import aras.EmailManager;
import aras.model.EmailMessage;
import aras.model.EmailTreeItem;
import aras.model.SizeInteger;
import aras.view.MyViewFactory;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.web.WebView;
import javafx.util.Callback;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

public class MainWindowController extends BaseController implements Initializable {

    @FXML
    private TreeView<String> emailsTreeView;

    @FXML
    private TableView<EmailMessage> emailsTableView;

    @FXML
    private WebView emailWebView;


    @FXML
    private TableColumn<EmailMessage, SizeInteger> sizeCol;

    @FXML
    private TableColumn<EmailMessage, String> senderCol;

    @FXML
    private TableColumn<EmailMessage, Date> dateCol;

    @FXML
    private TableColumn<EmailMessage, String> subjectCol;

    @FXML
    private TableColumn<EmailMessage, String> recipientCol;




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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        setUpEmailsTreeView();
        setUpEmailTableView();
        setUpFolderSelection();
        setUpBoldRows();

    }

    private void setUpBoldRows() {
        emailsTableView.setRowFactory(new Callback<TableView<EmailMessage>, TableRow<EmailMessage>>() {
            @Override
            public TableRow<EmailMessage> call(TableView<EmailMessage> emailMessageTableView) {
                return new TableRow<EmailMessage>()
                {
                    @Override
                    protected  void updateItem(EmailMessage item, boolean empty){
                        super.updateItem(item,empty);
                        if(item != null){
                            if(item.isRead()){
                                setStyle("");
                            }
                            else{
                                setStyle("-fx-font-weight: bold");
                            }
                        }
                    }

                };           }
        });

    }

    private void setUpFolderSelection() {
        emailsTreeView.setOnMouseClicked(e -> {
            EmailTreeItem<String> item = (EmailTreeItem<String>) emailsTreeView.getSelectionModel().getSelectedItem();
            if(item != null){
                emailsTableView.setItems(item.getEmailMessages());
            }

        });
    }

    private void setUpEmailTableView() {
        senderCol.setCellValueFactory((new PropertyValueFactory<EmailMessage, String>("Sender")));
        subjectCol.setCellValueFactory((new PropertyValueFactory<EmailMessage, String>("Subject")));
        recipientCol.setCellValueFactory((new PropertyValueFactory<EmailMessage, String>("Recipient")));
        sizeCol.setCellValueFactory((new PropertyValueFactory<EmailMessage, SizeInteger>("Size")));
        dateCol.setCellValueFactory((new PropertyValueFactory<EmailMessage, Date>("Date")));


    }

    private void setUpEmailsTreeView() {
        emailsTreeView.setRoot(emailManager.getFoldersRoot());
        emailsTreeView.setShowRoot(false);

    }
}
