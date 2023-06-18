package com.example.project_i.HangHoa;

import com.example.project_i.KetNoi_Database.DBConnection;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
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
import java.util.Optional;
import java.util.ResourceBundle;

import static com.example.project_i.HangHoa.quanLyHH_Controller.*;

public class HangHoa_add_Controller implements Initializable {

    @FXML
    private TextField donViTinh_TexField;

    @FXML
    private Button ghi_Button;

    @FXML
    private TextField giaBanLe_TexField;

    @FXML
    private TextField giaNhap_TexField;

    @FXML
    private TextField giaXuat_TexField;

    @FXML
    private TextField maHH_TexField;

    @FXML
    private TextField nhomHH_TexField;

    @FXML
    private TextField tenHH_TexField;

    @FXML
    private Button themMoi_Button;

    @FXML
    private AnchorPane themSP_Form;

    @FXML
    private Button thoat_Button;

    @FXML
    private TextField tonKho_TexField;
    Stage stage;

    public Connection conn = null ;
    public PreparedStatement preparedStatement = null;
    public ResultSet resultSet = null;
    @FXML
    void setThemMoi_Button(){
        maHH_TexField.setDisable(false);
        tenHH_TexField.setDisable(false);
        nhomHH_TexField.setDisable(false);
        donViTinh_TexField.setDisable(false);
        giaNhap_TexField.setDisable(false);
        giaXuat_TexField.setDisable(false);
        giaBanLe_TexField.setDisable(false);
        tonKho_TexField.setDisable(false);
        maHH_TexField.clear();
        tenHH_TexField.clear();
        nhomHH_TexField.clear();
        donViTinh_TexField.clear();
        giaNhap_TexField.clear();
        giaXuat_TexField.clear();
        giaBanLe_TexField.clear();
        tonKho_TexField.clear();
        themMoi_Button.setDisable(true);
        ghi_Button.setDisable(false);
    }
    @FXML
    void setGhi_Button() throws SQLException {
        if (chinhSua == 0){
            setChinhSuaSP();
        } else if (chinhSua == 1){
            setThemMoiSP();
        }
    }
    void setThemMoiSP() {
            int a = 0;
            int b = 0;
            try {
                preparedStatement = conn.prepareStatement("SELECT CAST(CASE WHEN COUNT(*) > 0 THEN 1 ELSE 0 END AS BIT) as ma FROM danhMucHH WHERE maHang = ?");
                preparedStatement.setString(1, maHH_TexField.getText());
                resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    a = resultSet.getInt(1);
                }
                System.out.println(a);
                if (a == 1) {
                    System.out.println("And This");
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Thông báo trùng lặp mã hàng !");
                    alert.setContentText("Mã hàng bị trùng với mã đã tồn tại, xin hãy nhập lại !");
                    alert.showAndWait();
                } else if (a == 0) {
                    try {
                        preparedStatement = conn.prepareStatement("SELECT CAST(CASE WHEN COUNT(*) > 0 THEN 1 ELSE 0 END AS BIT) as ma FROM danhMucHH WHERE tenHang = ?");
                        preparedStatement.setString(1, tenHH_TexField.getText());
                        resultSet = preparedStatement.executeQuery();
                        while (resultSet.next()) {
                            b = resultSet.getInt(1);
                        }
                        System.out.println(b);
                        if (b == 0) {
                            preparedStatement = conn.prepareStatement("danhMucHH_add ?,?,?,?,?,?,?,?");
                            preparedStatement.setString(1, maHH_TexField.getText());
                            preparedStatement.setString(2, tenHH_TexField.getText());
                            preparedStatement.setString(3, donViTinh_TexField.getText());
                            preparedStatement.setString(4, nhomHH_TexField.getText());
                            preparedStatement.setString(5, giaNhap_TexField.getText());
                            preparedStatement.setString(6, giaXuat_TexField.getText());
                            preparedStatement.setString(7, giaBanLe_TexField.getText());
                            preparedStatement.setString(8, tonKho_TexField.getText());
                            preparedStatement.execute();

                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Thông báo thêm sản phẩm thành công");
                            alert.setContentText("Bạn đã thêm sản phẩm thành công !");
                            alert.showAndWait();
                            maHH_TexField.setDisable(true);
                            tenHH_TexField.setDisable(true);
                            nhomHH_TexField.setDisable(true);
                            donViTinh_TexField.setDisable(true);
                            giaNhap_TexField.setDisable(true);
                            giaXuat_TexField.setDisable(true);
                            giaBanLe_TexField.setDisable(true);
                            tonKho_TexField.setDisable(true);
                            ghi_Button.setDisable(true);
                            themMoi_Button.setDisable(false);

                        } else if (b == 1) {
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setTitle("Thông báo trùng lặp tên hàng !");
                            alert.setContentText("Tên hàng hóa bị trùng bạn có muốn tiếp tục không ?");
                            Optional<ButtonType> rs = alert.showAndWait();
                            if (rs.get() == ButtonType.OK) {
                                preparedStatement = conn.prepareStatement("danhMucHH_add ?,?,?,?,?,?,?,?");
                                preparedStatement.setString(1, maHH_TexField.getText());
                                preparedStatement.setString(2, tenHH_TexField.getText());
                                preparedStatement.setString(3, donViTinh_TexField.getText());
                                preparedStatement.setString(4, nhomHH_TexField.getText());
                                preparedStatement.setString(5, giaNhap_TexField.getText());
                                preparedStatement.setString(6, giaXuat_TexField.getText());
                                preparedStatement.setString(7, giaBanLe_TexField.getText());
                                preparedStatement.setString(8, tonKho_TexField.getText());
                                preparedStatement.execute();
                                Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                                alert2.setTitle("Thông báo thêm sản phẩm thành công");
                                alert2.setContentText("Bạn đã thêm sản phẩm thành công !");
                                alert2.showAndWait();
                                maHH_TexField.setDisable(true);
                                tenHH_TexField.setDisable(true);
                                nhomHH_TexField.setDisable(true);
                                donViTinh_TexField.setDisable(true);
                                giaNhap_TexField.setDisable(true);
                                giaXuat_TexField.setDisable(true);
                                giaBanLe_TexField.setDisable(true);
                                tonKho_TexField.setDisable(true);
                                ghi_Button.setDisable(true);
                                themMoi_Button.setDisable(false);
                            }
                        }
                    } catch (Exception e) {
                        return;
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    @FXML
    void setChinhSuaSP(){
        try {
            String sql = "danhMucHH_update ?,?,?,?,?,?,?,?,?";
            preparedStatement= conn.prepareStatement(sql);
            preparedStatement.setString(1, maHH_TexField.getText());
            preparedStatement.setString(2, tenHH_TexField.getText());
            preparedStatement.setString(3, nhomHH_TexField.getText());
            preparedStatement.setString(4, donViTinh_TexField.getText());
            preparedStatement.setString(5, giaNhap_TexField.getText());
            preparedStatement.setString(6, giaXuat_TexField.getText());
            preparedStatement.setString(7, giaBanLe_TexField.getText());
            preparedStatement.setString(8, tonKho_TexField.getText());
            preparedStatement.setString(9, maHang );
            preparedStatement.execute();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Thông báo chỉnh sửa sản phẩm ");
            alert.setContentText("Bạn đã chỉnh sửa thành công !");
            alert.showAndWait();
            setThoat_Button();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    Parent root;
    Scene scene;
    void loader() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("quanLyHH_Form.fxml"));
        root = loader.load();
        quanLyHH_Controller quanLyHHController = loader.getController();
        quanLyHHController.setCellQuanLyHH_Table();
        stage = (Stage) themSP_Form.getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.close();
    }
    @FXML
    void setThoat_Button() throws IOException {

        maHH_TexField.clear();
        tenHH_TexField.clear();
        nhomHH_TexField.clear();
        donViTinh_TexField.clear();
        giaNhap_TexField.clear();
        giaXuat_TexField.clear();
        giaBanLe_TexField.clear();
        tonKho_TexField.clear();
        loader();

    }

    @FXML
    public void setThemSP_Form(KeyEvent event) throws SQLException, IOException {
        if (event.getCode() == KeyCode.F5 ){
            setThemMoi_Button();
        } else if (event.getCode() == KeyCode.F8) {
            setGhi_Button();
        } else if (event.getCode() == KeyCode.F12){
            setThoat_Button();
        }
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
        TextFields.bindAutoCompletion(tenHH_TexField, tenList);
        TextFields.bindAutoCompletion(nhomHH_TexField, nhomList);
        TextFields.bindAutoCompletion(donViTinh_TexField, donViList);
        if (chinhSua == 0){
                maHH_TexField.setText(maHang);
                tenHH_TexField.setText(tenHang);
                nhomHH_TexField.setText(nhomSP);
                donViTinh_TexField.setText(donViTinh);
                giaNhap_TexField.setText(giaNhap);
                giaXuat_TexField.setText(giaXuat);
                giaBanLe_TexField.setText(giaBanLe);
                tonKho_TexField.setText(tonKho);
            themMoi_Button.setVisible(false);
        } else if (chinhSua == 1){
            themMoi_Button.setDisable(true);
        }
    }
}
