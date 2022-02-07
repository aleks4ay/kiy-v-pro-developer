package org.aleks4ay.developer.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.aleks4ay.developer.dao.ConnectionPool;
import org.aleks4ay.developer.service.OtherService;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerSimpleSql implements Initializable {

    private final OtherService service = new OtherService(ConnectionPool.getInstance());

    @FXML private TextField sql;
    @FXML private Label result;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        result.setText("-");
    }


    public void goSql(ActionEvent event) {
        String id = ((Button)event.getSource()).getId();
        if (sql.getText() != null && !sql.getText().isEmpty()) {
            if (id.equalsIgnoreCase("select")) {
                result.setText(service.selectSql(sql.getText()));
            }
            if (id.equalsIgnoreCase("update")) {
                service.updateSql(sql.getText());
                result.setText("update...");
            }
        }
    }
}