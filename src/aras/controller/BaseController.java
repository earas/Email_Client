package aras.controller;

import aras.EmailManager;
import aras.view.MyViewFactory;

public abstract class BaseController {

    protected EmailManager emailManager;
    protected MyViewFactory myViewFactory;
    private String fxmlName;

    public BaseController(EmailManager emailManager, MyViewFactory myViewFactory, String fxmlName) {
        this.emailManager = emailManager;
        this.myViewFactory = myViewFactory;
        this.fxmlName = fxmlName;
    }

    public String getFxmlName(){
        return fxmlName;
    };
}
