package aras;

import aras.controller.services.FetchFolderService;
import aras.controller.services.FolderUpdaterService;
import aras.model.EmailAccount;
import aras.model.EmailMessage;
import aras.model.EmailTreeItem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;

import javax.mail.Flags;
import javax.mail.Folder;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EmailManager {

    private EmailTreeItem<String> foldersRoot = new EmailTreeItem<String>("");

    public TreeItem<String> getFoldersRoot() {
        return foldersRoot;
    }

    private FolderUpdaterService folderUpdateService;

    private List<Folder> folderList = new ArrayList<Folder>();

    private EmailMessage selectedMessage;

    private  EmailTreeItem<String> selectedFolder;

    public List<Folder> getFolderList(){
        return this.folderList;
    }

    private ObservableList<EmailAccount> emailAccounts = FXCollections.observableArrayList();

    public ObservableList<EmailAccount> getEmailAccounts(){
    return this.emailAccounts;
    }


    public EmailManager(){
        folderUpdateService = new FolderUpdaterService(folderList);
        folderUpdateService.start();
    }

    public EmailMessage getSelectedMessage() {
        return selectedMessage;
    }

    public void setSelectedMessage(EmailMessage selectedMessage) {
        this.selectedMessage = selectedMessage;
    }

    public EmailTreeItem<String> getSelectedFolder() {
        return selectedFolder;
    }

    public void setSelectedFolder(EmailTreeItem<String> selectedFolder) {
        this.selectedFolder = selectedFolder;
    }

    public void addEmailAccount(EmailAccount emailAccount)
    {
        emailAccounts.add(emailAccount);
        EmailTreeItem<String> treeItem = new EmailTreeItem<String>(emailAccount.getAddress());
       // treeItem.setExpanded(true);
        FetchFolderService fetchFolderService = new FetchFolderService(emailAccount.getStore(), treeItem, folderList);
        fetchFolderService.start();
        //treeItem.getChildren().add(new TreeItem<String>("Inbox"));
        foldersRoot.getChildren().add(treeItem);

    }

    public void setUnRead( ) {
        try{
            selectedMessage.setRead(false);
            selectedMessage.getMessage().setFlag(Flags.Flag.SEEN,false);
            selectedFolder.incrementMessagesCount();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteSelectedMessage( ) {
        try{
            selectedMessage.getMessage().setFlag(Flags.Flag.DELETED,true);
            selectedFolder.getEmailMessages().remove(selectedMessage);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }



    public void setRead( ) {
        try{
            selectedMessage.setRead(true);
            selectedMessage.getMessage().setFlag(Flags.Flag.SEEN,true);
            selectedFolder.decrementMessagesCount();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
