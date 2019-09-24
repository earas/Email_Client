package aras.controller;

import aras.EmailManager;
import aras.view.MyViewFactory;

public abstract class BaseController {

    private EmailManager emailManager;
    private MyViewFactory myViewFactory;
    private String fxmlName;

    public BaseController(EmailManager emailManager, MyViewFactory myViewFactory, String fxmlName) {
        this.emailManager = emailManager;
        this.myViewFactory = myViewFactory;
        this.fxmlName = fxmlName;
    }
}
