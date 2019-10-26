package aras.controller.services;

import aras.model.EmailTreeItem;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Store;
import javax.mail.event.MessageCountEvent;
import javax.mail.event.MessageCountListener;
import java.util.List;

public class FetchFolderService extends Service<Void> {

    private Store store;
    private EmailTreeItem<String> foldersRoot;
    private List<Folder> folderList;

    public FetchFolderService(Store store, EmailTreeItem<String> foldersRoot, List<Folder> folderList) {
        this.store = store;
        this.foldersRoot = foldersRoot;
        this.folderList = folderList;
    }

    @Override
    protected Task<Void> createTask() {
        return new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                fetchFolders();
                return null;
            }
        };
    }

    private void fetchFolders() throws MessagingException {
        Folder[] folders = store.getDefaultFolder().list();
        handleFolders(folders,foldersRoot);

    }

    private void handleFolders(Folder[] folders, EmailTreeItem<String> foldersRoot) throws MessagingException {
        for(Folder folder : folders){
            folderList.add(folder);
            EmailTreeItem<String> emailTreeItem = new EmailTreeItem<String>(folder.getName());
            foldersRoot.getChildren().add((emailTreeItem));
            foldersRoot.setExpanded(true);
            fetchMessagesonFolder(folder,emailTreeItem);
            addMessageListenerToFolder(folder,emailTreeItem);
            if(folder.getType() == Folder.HOLDS_FOLDERS){
                Folder[] subFolders = folder.list();
                handleFolders(subFolders,emailTreeItem);

            }
        }

    }

    private void addMessageListenerToFolder(Folder folder, EmailTreeItem<String> emailTreeItem) {
        folder.addMessageCountListener(new MessageCountListener() {
            @Override
            public void messagesAdded(MessageCountEvent messageCountEvent) {
               // System.out.println("message added event");
                for(int i = 0; i < messageCountEvent.getMessages().length; i++){
                    try {
                        Message message = folder.getMessage(folder.getMessageCount() - i);
                        emailTreeItem.addEmailToTop(message);
                    } catch (MessagingException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void messagesRemoved(MessageCountEvent messageCountEvent) {
                System.out.println("message deleted event"+ messageCountEvent);
            }
        });

    }

    private void fetchMessagesonFolder(Folder folder, EmailTreeItem<String> emailTreeItem) {
        Service fetchMessagesService = new Service() {
            @Override
            protected Task createTask() {
                return new Task() {
                    @Override
                    protected Object call() throws Exception {
                        if(folder.getType() != Folder.HOLDS_FOLDERS){
                            folder.open(Folder.READ_WRITE);
                            int folderSize = folder.getMessageCount();
                            for(int i = folderSize; i > 0; i--)
                            {
                                //System.out.println(folder.getMessage(i).getSubject());
                                emailTreeItem.addEmail(folder.getMessage(i));


                            }
                        }
                        return null;
                    }
                };
            }
        };

        fetchMessagesService.start();

    }
}
