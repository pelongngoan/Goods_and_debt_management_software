package com.example.project_i.KhachHang;

import com.example.project_i.AutoCompleteComboBoxListener;

import com.example.project_i.KetNoi_Database.DBConnection;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import static com.example.project_i.KhachHang.NhaCungCap_Controller.*;


public class NhaCungCap_add_Controller implements Initializable {
    @FXML
    private TextField ten_TextField, ma_TextField, diaChi_TextField, sdt_TextField, nguoiDD_TextField, mst_TextField, tk_TextField, nganHang_TextField, noDau_TextField;
    @FXML
    private Button themMoi_Button, ghi_Button, thoat_Button;
    @FXML
    private ComboBox khuVuc_ComboBox;
    @FXML
    private AnchorPane themNCC_Form;
    @FXML
    private void setThemMoi_Button(){
        ten_TextField.clear();
        diaChi_TextField.clear();
        sdt_TextField.clear();
        nguoiDD_TextField.clear();
        mst_TextField.clear();
        tk_TextField.clear();
        nganHang_TextField.clear();
        noDau_TextField.clear();
        khuVuc_ComboBox.setDisable(false);
        ten_TextField.setDisable(false);
        diaChi_TextField.setDisable(false);
        sdt_TextField.setDisable(false);
        nguoiDD_TextField.setDisable(false);
        mst_TextField.setDisable(false);
        tk_TextField.setDisable(false);
        nganHang_TextField.setDisable(false);
        noDau_TextField.setDisable(false);
        ghi_Button.setDisable(false);
        themMoi_Button.setDisable(true);
        ma_TextField.setText("NCC_"+(DBConnection.getNCC_Data().get(DBConnection.getNCC_Data().size()-1).getMaNCC()+1));
    }
    @FXML
    private void setGhi_Button(){
        boolean exist = false;
        System.out.println(themNCC);
        if (themNCC==true) {
            for (NhaCungCap_Module nhaCungCapModule : DBConnection.getNCC_Data()) {
                if (nhaCungCapModule.getTenNCC() == ten_TextField.getText()) {
                    exist = true;
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Thông báo !");
                    alert.setContentText("Tên nhà cung cấp đã tồn tại!");
                    alert.showAndWait();
                    break;
                } else exist = false;
            }
            if (exist == false && !khuVuc_ComboBox.getEditor().getText().isEmpty()
                    && !ten_TextField.getText().isEmpty() && !diaChi_TextField.getText().isEmpty()) {
                try {
                    preparedStatement = conn.prepareStatement("danhMucNCC_add ?,?,?,?,?,?,?,?,?");
                    preparedStatement.setString(1, ten_TextField.getText());
                    preparedStatement.setString(2, khuVuc_ComboBox.getEditor().getText());
                    preparedStatement.setString(3, diaChi_TextField.getText());
                    preparedStatement.setString(4, nguoiDD_TextField.getText());
                    preparedStatement.setString(5, sdt_TextField.getText());
                    preparedStatement.setString(6, mst_TextField.getText());
                    preparedStatement.setString(7, tk_TextField.getText());
                    preparedStatement.setString(8, nganHang_TextField.getText());
                    preparedStatement.setString(9, noDau_TextField.getText());
                    preparedStatement.execute();

                    khuVuc_ComboBox.setDisable(true);
                    ten_TextField.setDisable(true);
                    diaChi_TextField.setDisable(true);
                    sdt_TextField.setDisable(true);
                    nguoiDD_TextField.setDisable(true);
                    mst_TextField.setDisable(true);
                    tk_TextField.setDisable(true);
                    nganHang_TextField.setDisable(true);
                    noDau_TextField.setDisable(true);
                    themMoi_Button.setDisable(false);
                    ghi_Button.setDisable(true);
                } catch (Exception e) {
                    throw new RuntimeException();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Thông báo !");
                alert.setContentText("Chưa nhập thông tin bắt buộc (*)");
                alert.showAndWait();
            }
        } else {
            if (ten_TextField.getText() == tenNCC){
                exist = false;
            } else {
                for (NhaCungCap_Module nhaCungCapModule : DBConnection.getNCC_Data()) {
                    if (nhaCungCapModule.getTenNCC() == ten_TextField.getText()) {
                        exist = true;
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Thông báo !");
                        alert.setContentText("Tên nhà cung cấp đã tồn tại!");
                        alert.showAndWait();
                        break;
                    }
                }
            }
            if (exist == false && !khuVuc_ComboBox.getEditor().getText().isEmpty()
                    && !ten_TextField.getText().isEmpty() && !diaChi_TextField.getText().isEmpty()) {
                try {
                    preparedStatement = conn.prepareStatement("danhMucNCC_update ?,?,?,?,?,?,?,?,?,?");
                    preparedStatement.setString(1, idNCC);
                    preparedStatement.setString(2, ten_TextField.getText());
                    preparedStatement.setString(3, khuVuc_ComboBox.getEditor().getText());
                    preparedStatement.setString(4, diaChi_TextField.getText());
                    preparedStatement.setString(5, nguoiDD_TextField.getText());
                    preparedStatement.setString(6, sdt_TextField.getText());
                    preparedStatement.setString(7, mst_TextField.getText());
                    preparedStatement.setString(8, tk_TextField.getText());
                    preparedStatement.setString(9, nganHang_TextField.getText());
                    preparedStatement.setString(10, noDau_TextField.getText());
                    preparedStatement.execute();

                    khuVuc_ComboBox.setDisable(true);
                    ten_TextField.setDisable(true);
                    diaChi_TextField.setDisable(true);
                    sdt_TextField.setDisable(true);
                    nguoiDD_TextField.setDisable(true);
                    mst_TextField.setDisable(true);
                    tk_TextField.setDisable(true);
                    nganHang_TextField.setDisable(true);
                    noDau_TextField.setDisable(true);
                    themMoi_Button.setDisable(false);
                    ghi_Button.setDisable(true);
                    loader();
                } catch (Exception e) {
                    throw new RuntimeException();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Thông báo !");
                alert.setContentText("Chưa nhập thông tin bắt buộc (*)");
                alert.showAndWait();
            }
        }

    }
    Parent root;
    Scene scene;
    Stage stage;

    void loader() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("nhaCungCap.fxml"));
        //root = loader.load();*/
        root = loader.load();
        NhaCungCap_Controller nhaCungCap_controller = loader.getController();
        nhaCungCap_controller.setQuanLyNCC_Table();
        stage = (Stage) themNCC_Form.getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.close();
    }
    @FXML
    private void setThoat_Button() throws IOException {
            loader();
    }
    public Connection conn = null ;
    public PreparedStatement preparedStatement = null;
    public ResultSet resultSet = null;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        conn = DBConnection.ConnectionDB();
        for (KhuVuc_Module khuVucModule:DBConnection.getKV_Data()){
            khuVuc_ComboBox.getItems().add(khuVucModule.getTenKV());


        }
        new AutoCompleteComboBoxListener<>(khuVuc_ComboBox);
        ma_TextField.setText("NCC_"+(Integer.valueOf(DBConnection.getNCC_Data().get(DBConnection.getNCC_Data().size()-1).getMaNCC())+1));
        if (themNCC == false) {
            themMoi_Button.setVisible(false);
            ma_TextField.setText("NCC_"+idNCC);
            ten_TextField.setText(tenNCC);
            khuVuc_ComboBox.getEditor().setText(khuVucNCC);
            diaChi_TextField.setText(diaChiNCC);
            nguoiDD_TextField.setText(nguoiDaiDienNCC);
            sdt_TextField.setText(sdtNCC);
            mst_TextField.setText(mstNCC);
            tk_TextField.setText(tkNCC);
            nganHang_TextField.setText(nganHangNCC);
            noDau_TextField.setText(noDauNCC);
        }
        themNCC_Form.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()){
                    case F5 -> setThemMoi_Button();
                    case F8 -> setGhi_Button();
                    case F12 -> {
                        try {
                            setThoat_Button();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
        });

    }
}
