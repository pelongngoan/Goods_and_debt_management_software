package com.example.project_i.TrangChu;

import com.example.project_i.KetNoi_Database.DBConnection;
import com.example.project_i.Phieu_Nhap_Xuat.ctPhieu_Nhap_Xuat_Module;
import com.example.project_i.Phieu_Nhap_Xuat.phieu_Nhap_Xuat_Module;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class timKiemThuChi_Controller implements Initializable {

    @FXML
    private TableView<ThuChi_Module> ctPhieu_Table;

    @FXML
    private DatePicker denNgay_DatePicker;

    @FXML
    private CheckBox loaiHD_CheckBox;

    @FXML
    private ComboBox<String> loaiHD_ComboBox;

    @FXML
    private CheckBox maHD_CheckBox;

    @FXML
    private TextField maHD_TextField;

    @FXML
    private CheckBox ngayPS_CheckBox;

    @FXML
    private TableColumn<ThuChi_Module, LocalDate> ngayPS_Column;

    @FXML
    private TableColumn<ThuChi_Module, String> nguoiDD_Column;

    @FXML
    private TableColumn<ThuChi_Module, String> noiDung_Column;

    @FXML
    private TableColumn<ThuChi_Module, String> soPH_Column;

    @FXML
    private TableColumn<ThuChi_Module, Float> soTien_Column;

    @FXML
    private TableColumn<ThuChi_Module, Integer> stt_Column;

    @FXML
    private CheckBox tenKH_CheckBox;

    @FXML
    private TextField tenKH_TextField;

    @FXML
    private Button thoat_Button;

    @FXML
    private AnchorPane timKiem_AnchorPane;

    @FXML
    private Button timKiem_Button;

    @FXML
    private CheckBox timTatCa_CheckBox;

    @FXML
    private DatePicker tuNgay_DatePicker;
    PreparedStatement preparedStatement = null;
    Connection conn = null;
    ResultSet resultSet = null;
    ObservableList<ThuChi_Module> phieu_Data = FXCollections.observableArrayList();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loaiHD_ComboBox.getItems().addAll("PHTHU", "PHCHI");
        conn = DBConnection.ConnectionDB();
        ngayPS_Column.setCellValueFactory(new PropertyValueFactory<>("ngayPS"));
        soPH_Column.setCellValueFactory(new PropertyValueFactory<>("loai"));
        noiDung_Column.setCellValueFactory(new PropertyValueFactory<>("noiDung"));
        soTien_Column.setCellValueFactory(new PropertyValueFactory<>("soTien"));
        nguoiDD_Column.setCellValueFactory(new PropertyValueFactory<>("nguoiDD"));
        timKiem_Button.setOnAction(event -> {
            phieu_Data.clear();
            if (!tuNgay_DatePicker.getEditor().getText().isEmpty() && !denNgay_DatePicker.getEditor().getText().isEmpty()) {
                try {
                    preparedStatement = conn.prepareStatement("danhMucThuChi_get_few ?,?");
                    preparedStatement.setDate(1, Date.valueOf(tuNgay_DatePicker.getValue()));
                    preparedStatement.setDate(2, Date.valueOf(denNgay_DatePicker.getValue()));
                    resultSet = preparedStatement.executeQuery();
                    while (resultSet.next()) {
                        phieu_Data.add(new ThuChi_Module(resultSet.getString(2), resultSet.getString(4), resultSet.getDate(1).toLocalDate(), resultSet.getFloat(5), resultSet.getString(6)));
                    }
                    ctPhieu_Table.setItems(phieu_Data);
                } catch (SQLException e) {

                }
            } else {
                try {
                    preparedStatement = conn.prepareStatement("danhMucThuChi_get_all");
                    resultSet = preparedStatement.executeQuery();
                    while (resultSet.next()) {
                        phieu_Data.add(new ThuChi_Module(resultSet.getString(2), resultSet.getString(4), resultSet.getDate(1).toLocalDate(), resultSet.getFloat(5), resultSet.getString(6)));
                    }
                    ctPhieu_Table.setItems(phieu_Data);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        loaiHD_ComboBox.getEditor().textProperty().addListener((observable, oldValue, newValue) -> {
            FilteredList<ThuChi_Module> filteredData = new FilteredList<>(phieu_Data, b -> true);
            filteredData.setPredicate(needed -> {
                if (loaiHD_ComboBox.getEditor().getText().isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = loaiHD_ComboBox.getEditor().getText().toLowerCase();
                if (needed.getLoai().split("_")[0].toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else {
                    return false;
                }
            });
            SortedList<ThuChi_Module> sortedData = new SortedList<>(filteredData);
            sortedData.comparatorProperty().bind(ctPhieu_Table.comparatorProperty());
            ctPhieu_Table.setItems(sortedData);
        });
        maHD_TextField.textProperty().addListener((observable, oldValue, newValue) -> {
            FilteredList<ThuChi_Module> filteredData = new FilteredList<>(phieu_Data, b -> true);
            filteredData.setPredicate(needed -> {
                if (maHD_TextField.getText().isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = maHD_TextField.getText().toLowerCase();
                if (needed.getLoai().split("_")[1].toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else {
                    return false;
                }
            });
            SortedList<ThuChi_Module> sortedData = new SortedList<>(filteredData);
            sortedData.comparatorProperty().bind(ctPhieu_Table.comparatorProperty());
            ctPhieu_Table.setItems(sortedData);
        });
        tenKH_TextField.textProperty().addListener((observable, oldValue, newValue) -> {
            FilteredList<ThuChi_Module> filteredData = new FilteredList<>(phieu_Data, b -> true);
            filteredData.setPredicate(needed -> {
                if (tenKH_TextField.getText().isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = tenKH_TextField.getText().toLowerCase();
                if (needed.getNoiDung().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else {
                    return false;
                }
            });
            SortedList<ThuChi_Module> sortedData = new SortedList<>(filteredData);
            sortedData.comparatorProperty().bind(ctPhieu_Table.comparatorProperty());
            ctPhieu_Table.setItems(sortedData);
        });

    }
}
