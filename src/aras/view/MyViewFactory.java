package aras.view;

import aras.EmailManager;
import aras.controller.BaseController;
import aras.controller.LoginWindowController;
import aras.controller.MainWindowController;
import aras.controller.OptionsWindowController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.swing.text.ViewFactory;
import java.io.IOException;
import java.util.ArrayList;

public class MyViewFactory {

    private EmailManager emailManager;
    private ColorTheme colorTheme = ColorTheme.DARK;
    private  FontSize fontSize = FontSize.MEDIUM;
    private ArrayList<Stage> activeStages;
    private boolean mainViewInitialized = false;

    public MyViewFactory(EmailManager emailManager){
        this.emailManager = emailManager;
        activeStages = new ArrayList<Stage>();

    }

    public ColorTheme getColorTheme() {
        return colorTheme;
    }

    public void setColorTheme(ColorTheme colorTheme) {
        this.colorTheme = colorTheme;
    }

    public FontSize getFontSize() {
        return fontSize;
    }

    public void setFontSize(FontSize fontSize) {
        this.fontSize = fontSize;
    }

    public boolean isMainViewInitialized(){
        return  mainViewInitialized;
    }

    public void showLoginWindow() {
        BaseController controller = new LoginWindowController(emailManager,this,"LoginWindow.fxml");
        initializeStage(controller);

    }

    public void showMainWindow(){
        BaseController controller = new MainWindowController(emailManager,this,"MainWindow.fxml");
        initializeStage(controller);
        mainViewInitialized = true;
    }

    public void showOptionsWindow(){
        BaseController controller = new OptionsWindowController(emailManager,this,"OptionsWindow.fxml");
        initializeStage(controller);

    }

    private void initializeStage(BaseController baseController){

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(baseController.getFxmlName()));
        fxmlLoader.setController(baseController);
        Parent parent;

        try{
            parent = fxmlLoader.load();
        } catch(IOException e){
            e.printStackTrace();
            return;
        }
        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
        activeStages.add(stage);
    }

    public void closeStage(Stage stageToClose){
        activeStages.remove(stageToClose);
        stageToClose.close();
    }

    public void updateStyle() {
        for (Stage stage : activeStages){
            Scene scene = stage.getScene();
            scene.getStylesheets().clear();
            scene.getStylesheets().add(getClass().getResource(ColorTheme.getCssPath(colorTheme)).toExternalForm());
            scene.getStylesheets().add(getClass().getResource(FontSize.getCssPath(fontSize)).toExternalForm());


        }


    }
}
