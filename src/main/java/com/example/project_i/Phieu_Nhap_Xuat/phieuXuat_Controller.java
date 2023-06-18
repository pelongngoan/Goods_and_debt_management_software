package com.example.project_i.Phieu_Nhap_Xuat;

import com.example.project_i.KetNoi_Database.DBConnection;
import com.example.project_i.Phieu_Nhap_Xuat.ctPhieu_Nhap_Xuat_Module;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import javax.swing.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class phieuXuat_Controller /*implements Initializable*/ {
/*
    @FXML
    private TableColumn<ctPhieu_Nhap_Xuat_Module, String> donViTinh_Column;

    @FXML
    private TableColumn<ctPhieu_Nhap_Xuat_Module, Double> giaNhap_Column;

    @FXML
    private Button luuPhieu_Button;

    @FXML
    private TextField maHang;

    @FXML
    private TableColumn<ctPhieu_Nhap_Xuat_Module, Integer> maHang_Column;

    @FXML
    private TextField maKH;

    @FXML
    private TextField maPhieu;

    @FXML
    private DatePicker ngayPS_Field;

    @FXML
    private Button nhap_Button;

    @FXML
    private TableView<ctPhieu_Nhap_Xuat_Module> phieuNhap_Table;
    @FXML
    private TableColumn<ctPhieu_Nhap_Xuat_Module, Double> soLuong_Column;

    @FXML
    private Button taoPhieu_Button;

    @FXML
    private TableColumn<ctPhieu_Nhap_Xuat_Module, String> tenHang_Column;

    @FXML
    private TableColumn<ctPhieu_Nhap_Xuat_Module, Double> thanhTien_Column;
    public Connection conn = null ;
    public PreparedStatement preparedStatement = null;
    public ResultSet resultSet = null;
    ObservableList<ctPhieu_Nhap_Xuat_Module> phieuNhap_Data;
    public void setTaoPhieu_Button() {
        conn = DBConnection.ConnectionDB();
        String sql = "phieuXuat_add ?,?,?";
        try {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, Integer.parseInt(maPhieu.getText()));
            preparedStatement.setString(2, ngayPS_Field.getValue().toString());
            preparedStatement.setInt(3, Integer.parseInt(maKH.getText()));
            preparedStatement.execute();
            JOptionPane.showMessageDialog(null, "ADD");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    public void setNhap_Button(){
        conn = DBConnection.ConnectionDB();
        String sql = "ctPhieuNhap_add ?,?";
        try {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, Integer.parseInt(maPhieu.getText()));
            preparedStatement.setInt(2, Integer.parseInt(maHang.getText()));
            preparedStatement.execute();
            JOptionPane.showMessageDialog(null, "ADD");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        maHang.clear();
        setCellQuanLyHH_Table();
    }
    public void loadDatabase(){
        conn = DBConnection.ConnectionDB();
        String sql="phieuNhap_show";
        try {
            preparedStatement = conn.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                phieuNhapModules.add();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

       // phieuNhap_Table.setItems();
    }

    public void loadDataFromDatabase() {
        try {
            preparedStatement = conn.prepareStatement("phieuNhap_show ?");
            preparedStatement.setInt(1, Integer.parseInt(maPhieu.getText()));
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                phieuNhap_Data.add(new ctPhieu_Nhap_Xuat_Module(resultSet.getInt(1),resultSet.getString(2),resultSet.getString(3),resultSet.getDouble(4),resultSet.getDouble(5),resultSet.getDouble(6)));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        phieuNhap_Table.setItems(phieuNhap_Data);
    }
    public void setCellQuanLyHH_Table(){
        phieuNhap_Table.getItems().clear();
        maHang_Column.setCellValueFactory(new PropertyValueFactory<>("maHang"));
        tenHang_Column.setCellValueFactory(new PropertyValueFactory<>("tenHang"));
        donViTinh_Column.setCellValueFactory(new PropertyValueFactory<>("donViTinh"));
        soLuong_Column.setCellValueFactory(new PropertyValueFactory<>("soLuong"));
        giaNhap_Column.setCellValueFactory(new PropertyValueFactory<>("giaNhap"));
        thanhTien_Column.setCellValueFactory(new PropertyValueFactory<>("total"));
        loadDataFromDatabase();
    }
    int index = -1;
    public String curentID;
    @FXML
    void getSelected(MouseEvent event1){
        index = phieuNhap_Table.getSelectionModel().getSelectedIndex();
        if (index <= -1) {
            return ;
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        conn = DBConnection.ConnectionDB();
        phieuNhap_Data = FXCollections.observableArrayList();


    }
*/
}
