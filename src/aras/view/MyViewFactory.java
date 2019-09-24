package aras.view;

import aras.EmailManager;

import javax.swing.text.ViewFactory;

public class MyViewFactory {

    private EmailManager emailManager;

    public MyViewFactory(EmailManager emailManager){
        this.emailManager = emailManager;
    }

    public void showLoginWindow() {

    }

}
