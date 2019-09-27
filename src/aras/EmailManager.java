package aras;

import aras.controller.services.FetchFolderService;
import aras.model.EmailAccount;
import aras.model.EmailTreeItem;
import javafx.scene.control.TreeItem;

public class EmailManager {

    private EmailTreeItem<String> foldersRoot = new EmailTreeItem<String>("");

    public TreeItem<String> getFoldersRoot() {
        return foldersRoot;
    }

    public void addEmailAccount(EmailAccount emailAccount)
    {
        EmailTreeItem<String> treeItem = new EmailTreeItem<String>(emailAccount.getAddress());
       // treeItem.setExpanded(true);
        FetchFolderService fetchFolderService = new FetchFolderService(emailAccount.getStore(), treeItem);
        fetchFolderService.start();
        //treeItem.getChildren().add(new TreeItem<String>("Inbox"));
        foldersRoot.getChildren().add(treeItem);

    }

}
