package com.akmal.layout;

import com.akmal.system.ServerService;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.StringConverter;
import javafx.util.converter.NumberStringConverter;

import java.awt.*;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
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

    @FXML
    private Label label_status;

    @FXML
    private Rectangle status;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // server setting
        try {
            InetAddress address = InetAddress.getLocalHost();

            edit_address.setText(address.getHostAddress());
            edit_port.setText("8080");

        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        // system info
        String str_width = "" + Toolkit.getDefaultToolkit().getScreenSize().width;
        String str_height = "" + Toolkit.getDefaultToolkit().getScreenSize().height;

        edit_dwidth.setText(str_width);
        edit_dheight.setText(str_height);

        button.setOnAction(this::button);
    }

    public void observeMouseX(DoubleProperty dp) {
        StringConverter<Number> converter = new NumberStringConverter();
        Bindings.bindBidirectional(edit_mousex.textProperty(), dp, converter);
    }

    public void observeMouseY(DoubleProperty dp) {
        StringConverter<Number> converter = new NumberStringConverter();
        Bindings.bindBidirectional(edit_mousey.textProperty(), dp, converter);
    }

    public void observeServerRunning(BooleanProperty bp) {
        bp.addListener((observableValue, unknown, running) -> {

            if (running) {
                button.setText("STOP SERVER");
                label_status.setText("waiting for client");
                status.setFill(Color.DODGERBLUE);

            } else {
                button.setText("START SERVER");
                label_status.setText("");
                status.setFill(Color.RED);
            }

        });
    }

    private void button(ActionEvent actionEvent) {

        ServerService service = ServerService.getInstance();

        if (service.getServerStatus().get()) {
            service.stopServer();
        } else
            service.startServer();

    }
}
