package aras.controller;


import aras.EmailManager;
import aras.view.ColorTheme;
import aras.view.FontSize;
import aras.view.MyViewFactory;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Slider;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.net.URL;
import java.util.ResourceBundle;

public class OptionsWindowController extends BaseController implements Initializable {

    public OptionsWindowController(EmailManager emailManager, MyViewFactory myViewFactory, String fxmlName) {
        super(emailManager, myViewFactory, fxmlName);
    }

    @FXML
    private ChoiceBox<ColorTheme> themePicker;

    @FXML
    private Slider fontSizePicker;

    @FXML
    private Button appBtn;

    @FXML
    private Button cancelBtn;



    @FXML
    void cancelButtonAction(ActionEvent event) {
        Stage stage = (Stage) fontSizePicker.getScene().getWindow();
        myViewFactory.closeStage(stage);

    }

    @FXML
    void applyBtnAction(ActionEvent event) {
        myViewFactory.setColorTheme(themePicker.getValue());
        myViewFactory.setFontSize(FontSize.values()[(int)(fontSizePicker.getValue())]);
        myViewFactory.updateStyle();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setUpThemePicker();
        setUpSizePicker();
    }

    private void setUpSizePicker() {
        fontSizePicker.setMin(0);
        fontSizePicker.setMax(FontSize.values().length - 1);
        fontSizePicker.setValue(myViewFactory.getFontSize().ordinal());
        fontSizePicker.setMajorTickUnit(1);
        fontSizePicker.setMinorTickCount(0);
        fontSizePicker.setBlockIncrement(1);
        fontSizePicker.setSnapToTicks(true);
        fontSizePicker.setShowTickLabels(true);
        fontSizePicker.setShowTickMarks(true);
        fontSizePicker.setLabelFormatter(new StringConverter<Double>() {
            @Override
            public String toString(Double aDouble) {
                int i = aDouble.intValue();
                return FontSize.values()[i].toString();
            }

            @Override
            public Double fromString(String s) {
                return null;
            }
        });
        fontSizePicker.valueProperty().addListener((obs,oldVal,newVal) -> {
            fontSizePicker.setValue(newVal.intValue());
        });


    }

    private void setUpThemePicker() {
        themePicker.setItems(FXCollections.observableArrayList(ColorTheme.values()));
        themePicker.setValue(myViewFactory.getColorTheme());
    }
}
