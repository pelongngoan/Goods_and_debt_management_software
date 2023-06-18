package com.example.project_i.Login;

import com.example.project_i.FXML_Loader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    public TextField nameTexField;
    public TextField passwordTexField;
    public Pane pane;


    @FXML
    public void login(ActionEvent event) throws IOException {

        URL url = new File("src/main/resources/com/example/project_i/TrangChu/MenuBar.fxml").toURI().toURL();
        Parent root = FXMLLoader.load(url);
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Phần mềm quản lý hàng hóa && công nợ");
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


    }
}
