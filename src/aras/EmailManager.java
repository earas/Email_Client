package aras;

import aras.controller.services.FetchFolderService;
import aras.controller.services.FolderUpdaterService;
import aras.model.EmailAccount;
import aras.model.EmailTreeItem;
import javafx.scene.control.TreeItem;

import javax.mail.Folder;
import java.util.ArrayList;
import java.util.List;

public class EmailManager {

    private EmailTreeItem<String> foldersRoot = new EmailTreeItem<String>("");

    public TreeItem<String> getFoldersRoot() {
        return foldersRoot;
    }

    private FolderUpdaterService folderUpdateService;

    private List<Folder> folderList = new ArrayList<Folder>();

    public List<Folder> getFolderList(){
        return this.folderList;
    }

    public EmailManager(){
        folderUpdateService = new FolderUpdaterService(folderList);
        folderUpdateService.start();
    }


    public void addEmailAccount(EmailAccount emailAccount)
    {
        EmailTreeItem<String> treeItem = new EmailTreeItem<String>(emailAccount.getAddress());
       // treeItem.setExpanded(true);
        FetchFolderService fetchFolderService = new FetchFolderService(emailAccount.getStore(), treeItem, folderList);
        fetchFolderService.start();
        //treeItem.getChildren().add(new TreeItem<String>("Inbox"));
        foldersRoot.getChildren().add(treeItem);

    }

}
