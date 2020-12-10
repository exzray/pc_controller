package com.akmal.layout;

import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;
import javafx.util.converter.NumberStringConverter;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

public class RootController implements Initializable {

    @FXML
    private TextField edit_address;

    @FXML
    private TextField edit_port;

    @FXML
    private TextField edit_mousex;

    @FXML
    private TextField edit_mousey;

    @FXML
    private TextField edit_dheight;

    @FXML
    private TextField edit_dwidth;

    @FXML
    private Button button;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String str_width = "" + Toolkit.getDefaultToolkit().getScreenSize().width;
        String str_height = "" + Toolkit.getDefaultToolkit().getScreenSize().height;

        edit_dwidth.setText(str_width);
        edit_dheight.setText(str_height);
    }

    public void observeMouseX(DoubleProperty dp) {
        StringConverter<Number> converter = new NumberStringConverter();
        Bindings.bindBidirectional(edit_mousex.textProperty(), dp, converter);
    }

    public void observeMouseY(DoubleProperty dp) {
        StringConverter<Number> converter = new NumberStringConverter();
        Bindings.bindBidirectional(edit_mousey.textProperty(), dp, converter);
    }


}
