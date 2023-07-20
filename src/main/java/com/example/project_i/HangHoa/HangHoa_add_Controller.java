package com.example.project_i.HangHoa;
import com.example.project_i.KetNoi_Database.DBConnection;
import com.example.project_i.TrangChu.MenuController;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.controlsfx.control.textfield.TextFields;
import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static com.example.project_i.HangHoa.quanLyHH_Controller.*;

public class HangHoa_add_Controller implements Initializable {

    @FXML
    private TextField donViTinh_TexField,giaBanLe_TexField,giaNhap_TexField
            ,giaXuat_TexField,nhomHH_TexField,tenHH_TexField,tonKho_TexField;
    @FXML
    private Button themMoi_Button,ghi_Button,thoat_Button;

    @FXML
    private AnchorPane themSP_Form;

    Stage stage;

    public Connection conn = null ;
    public PreparedStatement preparedStatement = null;
    public ResultSet resultSet = null;
    Parent root;
    Scene scene;
    void loader() throws IOException {
        stage = (Stage) themSP_Form.getScene().getWindow();
        stage.close();
    }
    void setDisable(){
        tenHH_TexField.setDisable(false);
        nhomHH_TexField.setDisable(false);
        donViTinh_TexField.setDisable(false);
        giaNhap_TexField.setDisable(false);
        giaXuat_TexField.setDisable(false);
        giaBanLe_TexField.setDisable(false);
        tonKho_TexField.setDisable(false);
        tenHH_TexField.clear();
        nhomHH_TexField.clear();
        donViTinh_TexField.clear();
        giaNhap_TexField.clear();
        giaXuat_TexField.clear();
        giaBanLe_TexField.clear();
        tonKho_TexField.clear();
    }
    void setNotDisable(){
        tenHH_TexField.setDisable(true);
        nhomHH_TexField.setDisable(true);
        donViTinh_TexField.setDisable(true);
        giaNhap_TexField.setDisable(true);
        giaXuat_TexField.setDisable(true);
        giaBanLe_TexField.setDisable(true);
        tonKho_TexField.setDisable(true);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        conn = DBConnection.ConnectionDB();
        System.out.println(chinhSua);
        List<String> tenList = new ArrayList<>();
        List<String> nhomList = new ArrayList<>();
        List<String> donViList = new ArrayList<>();
        try {
            preparedStatement = conn.prepareStatement("SELECT  DISTINCT phanLoai FROM danhMucHH");
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                nhomList.add(resultSet.getString(1));
                //tenList.add(resultSet.getString(3));
                //donViList.add(resultSet.getString(4));
            }
        }catch (SQLException e){

        }
        themMoi_Button.setOnAction(event -> {
            ghi_Button.setDisable(false);
            themMoi_Button.setDisable(true);
            setDisable();
        });
        TextFields.bindAutoCompletion(tenHH_TexField, tenList);
        TextFields.bindAutoCompletion(nhomHH_TexField, nhomList);
        TextFields.bindAutoCompletion(donViTinh_TexField, donViList);
        if (chinhSua == true){
                tenHH_TexField.setText(tenHang);
                nhomHH_TexField.setText(nhomSP);
                donViTinh_TexField.setText(donViTinh);
                giaNhap_TexField.setText(giaNhap);
                giaXuat_TexField.setText(giaXuat);
                giaBanLe_TexField.setText(giaBanLe);
                tonKho_TexField.setText(tonKho);
                themMoi_Button.setVisible(false);
        } else if (chinhSua == false){
            themMoi_Button.setDisable(true);
        }
        ghi_Button.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            boolean match = false;
            for (HangHoa_Module data :DBConnection.getHH_Data()){
                if (data.getTenHang().matches(tenHH_TexField.getText())){
                    match =true;
                    break;
                } else match = false;
            }
            if (match == false && chinhSua == false && !tenHH_TexField.getText().isEmpty() ) {
                try {
                    preparedStatement = conn.prepareStatement("danhMucHH_add ?,?,?,?,?,?,?");
                    preparedStatement.setString(1, tenHH_TexField.getText());
                    preparedStatement.setString(2, donViTinh_TexField.getText());
                    preparedStatement.setString(3, nhomHH_TexField.getText());
                    preparedStatement.setString(4, giaNhap_TexField.getText());
                    preparedStatement.setString(5, giaXuat_TexField.getText());
                    preparedStatement.setString(6, giaBanLe_TexField.getText());
                    preparedStatement.setString(7, tonKho_TexField.getText());
                    preparedStatement.execute();
                    alert.setTitle("Thông báo !");
                    alert.setContentText("Bạn đã thêm sản phẩm thành công !");
                    alert.showAndWait();
                    setNotDisable();
                    ghi_Button.setDisable(true);
                    themMoi_Button.setDisable(false);
                } catch (SQLException e) {

                }
            } else if ((match == false && chinhSua == true && !tenHH_TexField.getText().isEmpty()) || tenHH_TexField.getText().matches(tenHang)  ) {
                try {
                    String sql = "danhMucHH_update ?,?,?,?,?,?,?,?";
                    preparedStatement= conn.prepareStatement(sql);
                    preparedStatement.setInt(1, curentID);
                    preparedStatement.setString(2, tenHH_TexField.getText());
                    preparedStatement.setString(3, nhomHH_TexField.getText());
                    preparedStatement.setString(4, donViTinh_TexField.getText());
                    preparedStatement.setString(5, giaNhap_TexField.getText());
                    preparedStatement.setString(6, giaXuat_TexField.getText());
                    preparedStatement.setString(7, giaBanLe_TexField.getText());
                    preparedStatement.setString(8, tonKho_TexField.getText());
                    preparedStatement.execute();
                    alert.setTitle("Thông báo chỉnh sửa sản phẩm ");
                    alert.setContentText("Bạn đã chỉnh sửa thành công !");
                    alert.showAndWait();
                    thoat_Button.fire();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e);
                }

            } else if (tenHH_TexField.getText().isEmpty()) {
                alert.setTitle("Thông báo !");
                alert.setContentText("Tên sản phẩm không được bỏ trống");
                alert.showAndWait();
            } else {
                alert.setTitle("Thông báo !");
                alert.setContentText("Sản phẩm đã tồn tại!");
                alert.showAndWait();
            }

        });
        themSP_Form.setOnKeyPressed(event -> {
            switch (event.getCode()){
                case F5 -> themMoi_Button.fire();
                case F8 -> ghi_Button.fire();
                case F12 -> thoat_Button.fire();
            }
        });
        thoat_Button.setOnAction(event -> {
            //setDisable();
            try {
                loader();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
