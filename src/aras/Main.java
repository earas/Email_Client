package aras;

import aras.view.MyViewFactory;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        MyViewFactory viewFactory = new MyViewFactory(new EmailManager());
        viewFactory.showLoginWindow();
        //viewFactory.showOptionsWindow();
        //viewFactory.updateStyle();


    }


    public static void main(String[] args) {
        launch(args);
    }
}
