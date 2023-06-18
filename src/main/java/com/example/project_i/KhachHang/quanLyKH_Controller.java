package com.example.project_i.KhachHang;

import com.example.project_i.KetNoi_Database.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class quanLyKH_Controller implements Initializable {

    @FXML
    private TableColumn<KhachHang_Module, Double> debt_Column;

    @FXML
    private TableColumn<KhachHang_Module, String> diaChi_Column;

    @FXML
    private TableColumn<KhachHang_Module, Integer> maKH_Column;

    @FXML
    private TableView<KhachHang_Module> quanLyKH_Table;

    @FXML
    private TableColumn<KhachHang_Module, String> sdt_KH_Column;

    @FXML
    private TableColumn<KhachHang_Module, Integer> stt_KH_Column;

    @FXML
    private TableColumn<KhachHang_Module, String> tenKH_Column;

    public Connection conn = null ;
    public PreparedStatement preparedStatement = null;
    public ResultSet resultSet = null;
    ObservableList<KhachHang_Module> customers_Data;


    public void loadDataFromDatabase() {
        try {
            preparedStatement = conn.prepareStatement("SELECT * FROM danhMucKH");
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                customers_Data.add(new KhachHang_Module(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(5),resultSet.getString(4),resultSet.getString(6)));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        quanLyKH_Table.setItems(customers_Data);
    }
    public void setCellQuanLyKH_Table(){
        maKH_Column.setCellValueFactory(new PropertyValueFactory<>("maKH"));
        tenKH_Column.setCellValueFactory(new PropertyValueFactory<>("tenKh"));
        diaChi_Column.setCellValueFactory(new PropertyValueFactory<>("diaChi"));

        sdt_KH_Column.setCellValueFactory(new PropertyValueFactory<>("sdt"));

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        conn = DBConnection.ConnectionDB();
        customers_Data = FXCollections.observableArrayList();
        setCellQuanLyKH_Table();
        loadDataFromDatabase();
    }


}
