package com.example.project_i.TrangChu;

import com.example.project_i.KetNoi_Database.DBConnection;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class thuChiAU_Controller implements Initializable {
    @FXML
    private Button them_Button,ghi_Button,thoat_Button;

    @FXML
    public ComboBox<String> loai_ComboBox;

    @FXML
    public TextField noiDung_TexField;

    @FXML
    private AnchorPane thuChiAU_AnchorPane;
    public boolean AU = true;
    Connection conn = null;
    PreparedStatement preparedStatement = null;
    public int id ;
    public String noiDung="";
    Alert alert = new Alert(Alert.AlertType.INFORMATION);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        alert.setTitle("Thông báo!");
        loai_ComboBox.getItems().addAll("Thu","Chi");
        ghi_Button.setOnAction(event->{
            boolean exist = false;
            for (ThuChi_Module data: DBConnection.getTC_Data()){
                if (data.getNoiDung().matches(noiDung_TexField.getText()) && !noiDung_TexField.getText().matches(noiDung)){
                    exist = true;
                    break;
                } else {
                    exist = false;
                }
            }
            if (exist == false && !noiDung_TexField.getText().isEmpty()) {
                try {
                    conn = DBConnection.ConnectionDB();
                    preparedStatement = conn.prepareStatement("danhMucThuChi_AU ?,?,?,?");
                    preparedStatement.setBoolean(1, AU);
                    preparedStatement.setInt(2, id);
                    preparedStatement.setString(3, loai_ComboBox.getEditor().getText());
                    preparedStatement.setString(4, noiDung_TexField.getText());
                    preparedStatement.execute();
                    them_Button.setDisable(false);
                    ghi_Button.setDisable(true);
                    noiDung_TexField.setDisable(true);
                    loai_ComboBox.setDisable(true);
                    them_Button.requestFocus();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            } else if (exist == true){
                alert.setContentText("Nội dung thu chi này đã tồn tại!");
                alert.showAndWait();
            } else {
                alert.setContentText("Vui lòng không để trống nội dung thu chi!");
                alert.showAndWait();
            }
        });
        them_Button.setOnAction(event -> {
            them_Button.setDisable(true);
            ghi_Button.setDisable(false);
            noiDung_TexField.clear();
            noiDung_TexField.setDisable(false);
            loai_ComboBox.setDisable(false);
            noiDung_TexField.requestFocus();
        });
        thoat_Button.setOnAction(event -> {
            Stage stage = (Stage) thoat_Button.getScene().getWindow();
            stage.close();
        });
        thuChiAU_AnchorPane.setOnKeyPressed(event -> {
            switch (event.getCode()){
                case F5 -> them_Button.fire();
                case F8 -> ghi_Button.fire();
                case F12 -> thoat_Button.fire();
            }
        });



    }
}
