package aras.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;

import javax.mail.Flags;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

public class EmailTreeItem<String> extends TreeItem<String> {

    private String name;
    private ObservableList<EmailMessage> emailMessages;
    private int unreadMessageCount;


    public EmailTreeItem(String name) {
        super(name);
        this.name = name;
        this.emailMessages = FXCollections.observableArrayList();
    }

    public ObservableList<EmailMessage> getEmailMessages(){
        return emailMessages;
    }

public void addEmail(Message message) throws MessagingException {
        boolean IsRead = message.getFlags().contains(Flags.Flag.SEEN);
        EmailMessage emailMessage = new EmailMessage(
                message.getSubject(),
                message.getFrom()[0].toString(),
                message.getRecipients(MimeMessage.RecipientType.TO)[0].toString(),
                message.getSize(),
                message.getSentDate(),
                IsRead,
                message
        );
        emailMessages.add(emailMessage);
        if(!IsRead){
            incrementMessagesCount();
        }

}

private void incrementMessagesCount(){
        unreadMessageCount++;
        updateName();
}



private void updateName(){
        if(unreadMessageCount > 0){
            this.setValue((String)(name + "(" + unreadMessageCount + ")"));
        }else {
            this.setValue(name);
        }

}


    public void addEmailToTop() {
    }
}
