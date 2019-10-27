package aras.controller;

import aras.EmailManager;
import aras.controller.services.MessageRendererService;
import aras.model.EmailMessage;
import aras.model.EmailTreeItem;
import aras.model.SizeInteger;
import aras.view.MyViewFactory;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.web.WebView;
import javafx.util.Callback;

import javax.mail.Message;
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

    private MessageRendererService messageRendererService;

    private MenuItem markUnreadMenuItem = new MenuItem("mark as unread");
    private MenuItem deleteMessageMenuItem = new MenuItem("delete message");


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

    @FXML
    void composeMessageAction(){
        myViewFactory.showComposeMessageWindow();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        setUpEmailsTreeView();
        setUpEmailTableView();
        setUpFolderSelection();
        setUpBoldRows();
        setUpMessageRendererService();
        setUpMessageSelection();
        setUpContextMenus();

    }

    private void setUpContextMenus() {
        emailsTableView.setContextMenu(new ContextMenu(markUnreadMenuItem,deleteMessageMenuItem));

        markUnreadMenuItem.setOnAction( event -> {
            emailManager.setUnRead();
        });
        deleteMessageMenuItem.setOnAction( actionEvent -> {
            emailManager.deleteSelectedMessage();
            emailWebView.getEngine().loadContent("");
        });


    }

    private void setUpMessageSelection() {
        emailsTableView.setOnMouseClicked(event ->{
            EmailMessage emailMessage = emailsTableView.getSelectionModel().getSelectedItem();
            if(emailMessage != null){
                emailManager.setSelectedMessage(emailMessage);
                if(!emailMessage.isRead()){
                    emailManager.setRead();
                }
                //emailManager.setSelectedMessage(emailMessage);
                messageRendererService.setEmailMessage(emailMessage);
                messageRendererService.restart();
                //restart because start method can be used only 1 times
            }

        });

    }

    private void setUpMessageRendererService() {
        messageRendererService = new MessageRendererService(emailWebView.getEngine());
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
                emailManager.setSelectedFolder(item);
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
       // emailsTableView.setContextMenu(new ContextMenu(markUnreadMenuItem,deleteMessageMenuItem));


    }

    private void setUpEmailsTreeView() {
        emailsTreeView.setRoot(emailManager.getFoldersRoot());
        emailsTreeView.setShowRoot(false);

    }
}
