package com.example.project_i.TrangChu;

import com.example.project_i.KetNoi_Database.DBConnection;
import com.example.project_i.KhachHang.KhachHang_Module;
import com.example.project_i.Phieu_Nhap_Xuat.ctPhieu_Nhap_Xuat_Module;
import com.example.project_i.Phieu_Nhap_Xuat.phieu_Nhap_Xuat_Module;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import javax.swing.event.ChangeListener;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class timKiemNhapXuat_Controller implements Initializable {


    @FXML
    private Button chon_Button;

    @FXML
    private TableView<ctPhieu_Nhap_Xuat_Module> ctPhieu_Table;

    @FXML
    private DatePicker denNgay_DatePicker;

    @FXML
    private TableColumn<ctPhieu_Nhap_Xuat_Module, Float> donGia_Column;

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
    private TableColumn<phieu_Nhap_Xuat_Module, LocalDate> ngayPS_Column;

    @FXML
    private TableView<phieu_Nhap_Xuat_Module> phieu_Table;

    @FXML
    private TableColumn<ctPhieu_Nhap_Xuat_Module, Float> soLuong_Column;

    @FXML
    private TableColumn<phieu_Nhap_Xuat_Module, String> soPhieu_Column;

    @FXML
    private TableColumn<ctPhieu_Nhap_Xuat_Module,Integer> stt_Column;

    @FXML
    private CheckBox tenKH_CheckBox;

    @FXML
    private TextField tenKH_TextField;

    @FXML
    private TableColumn<phieu_Nhap_Xuat_Module, String> tenKhach_Column;

    @FXML
    private TableColumn<ctPhieu_Nhap_Xuat_Module, String> tenSP_Column;

    @FXML
    private TableColumn<ctPhieu_Nhap_Xuat_Module, Float> thanhTien_Column;

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

    String sql;
    PreparedStatement preparedStatement = null;
    Connection conn = null;
    ResultSet resultSet = null;
    static ObservableList<phieu_Nhap_Xuat_Module> phieu_Data = FXCollections.observableArrayList();
    static ObservableList<ctPhieu_Nhap_Xuat_Module> ctPhieu_Data = FXCollections.observableArrayList();
    int phieu_index = -1;
    int ma_phieu = -1;
    FilteredList<phieu_Nhap_Xuat_Module> filteredData = new FilteredList<>(phieu_Data, b -> true);
    SortedList<phieu_Nhap_Xuat_Module> sortedData = new SortedList<>(filteredData);
    void setTable1(){
        filteredData = new FilteredList<>(phieu_Data, b -> true);
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
        sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(phieu_Table.comparatorProperty());
        phieu_Table.setItems(sortedData);
    }
    void setTable2(){

    }
    boolean comBoBox = false;
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loaiHD_ComboBox.getItems().addAll("PHNHAP","PHXUAT","PHBANLE");
        conn = DBConnection.ConnectionDB();
        ngayPS_Column.setCellValueFactory(new PropertyValueFactory<>("ngayPS"));
        soPhieu_Column.setCellValueFactory(new PropertyValueFactory<>("loai"));
        tenKhach_Column.setCellValueFactory(new PropertyValueFactory<>("maKH"));
        thanhTien_Column.setCellValueFactory(cellData -> cellData.getValue().thanhTienProperty().asObject());
        tenSP_Column.setCellValueFactory(new PropertyValueFactory<>("tenHang"));
        soLuong_Column.setCellValueFactory(cellData -> cellData.getValue().soLuongProperty().asObject());
        donGia_Column.setCellValueFactory(cellData -> cellData.getValue().giaNhapProperty().asObject());
        thoat_Button.setOnMouseClicked(event -> timKiem_AnchorPane.setVisible(false));
        ngayPS_Column.getSortType();
        stt_Column.setSortable(false);
        stt_Column.setCellValueFactory(column-> new ReadOnlyObjectWrapper<Integer>(ctPhieu_Table.getItems().indexOf(column.getValue())+1));

        timKiem_Button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                phieu_Data.clear();
                if (!tuNgay_DatePicker.getEditor().getText().isEmpty() && !denNgay_DatePicker.getEditor().getText().isEmpty()) {
                    try {
                        preparedStatement = conn.prepareStatement("danhMucNX_get_few ?,?");
                        preparedStatement.setDate(1, Date.valueOf(tuNgay_DatePicker.getValue()));
                        preparedStatement.setDate(2, Date.valueOf(denNgay_DatePicker.getValue()));
                        resultSet = preparedStatement.executeQuery();
                        while (resultSet.next()) {
                            phieu_Data.add(new phieu_Nhap_Xuat_Module(resultSet.getString(1), resultSet.getDate(3).toLocalDate(), resultSet.getString(4)));
                        }
                        phieu_Table.setItems(phieu_Data);
                    } catch (SQLException e) {

                    }
                } else {
                    try {
                        preparedStatement = conn.prepareStatement(sql);
                        resultSet = preparedStatement.executeQuery();
                        while (resultSet.next()){
                            phieu_Data.add(new phieu_Nhap_Xuat_Module(resultSet.getString(1), resultSet.getDate(3).toLocalDate(),resultSet.getString(4) ));
                        }
                        phieu_Table.setItems(phieu_Data);
                    } catch (SQLException e){

                    }
                }
            }
        });
        timTatCa_CheckBox.setOnMouseClicked(event -> sql= "danhMucNX_get_all");
        phieu_Table.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                ctPhieu_Data.clear();

                phieu_index = phieu_Table.getSelectionModel().getSelectedIndex();
                ma_phieu = Integer.parseInt(soPhieu_Column.getCellData(phieu_index).split("_")[1]);
                if (soPhieu_Column.getCellData(phieu_index).split("_")[0].matches("PHNHAP")) {
                    try {
                        preparedStatement = conn.prepareStatement("ctPhieuNhap_get_one ?");
                        preparedStatement.setInt(1, ma_phieu);
                        resultSet = preparedStatement.executeQuery();
                        while (resultSet.next()) {
                            ctPhieu_Data.add(new ctPhieu_Nhap_Xuat_Module(resultSet.getString(1), resultSet.getFloat(2), resultSet.getFloat(3)));
                        }
                        ctPhieu_Table.setItems(ctPhieu_Data);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                } else if (soPhieu_Column.getCellData(phieu_index).split("_")[0].matches("PHXUAT")){
                    try {
                        preparedStatement = conn.prepareStatement("ctPhieuXuat_get_one ?");
                        preparedStatement.setInt(1, ma_phieu);
                        resultSet = preparedStatement.executeQuery();
                        while (resultSet.next()) {
                            ctPhieu_Data.add(new ctPhieu_Nhap_Xuat_Module(resultSet.getString(1), resultSet.getFloat(2), resultSet.getFloat(3)));
                        }
                        ctPhieu_Table.setItems(ctPhieu_Data);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                } else if (soPhieu_Column.getCellData(phieu_index).split("_")[0].matches("PHBANLE")){
                    try {
                        preparedStatement = conn.prepareStatement("ctPhieuBanLe_get_one ?");
                        preparedStatement.setInt(1, ma_phieu);
                        resultSet = preparedStatement.executeQuery();
                        while (resultSet.next()) {
                            ctPhieu_Data.add(new ctPhieu_Nhap_Xuat_Module(resultSet.getString(1), resultSet.getFloat(2), resultSet.getFloat(3)));
                        }
                        ctPhieu_Table.setItems(ctPhieu_Data);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
        loaiHD_CheckBox.setOnMouseClicked(event -> {loaiHD_ComboBox.setDisable(false);});
        loaiHD_ComboBox.getEditor().textProperty().addListener((observable, oldValue, newValue) -> {
            setTable1();
        });
        tenKH_TextField.textProperty().addListener((observable, oldValue, newValue)->{
            filteredData = new FilteredList<>(phieu_Data, b -> true);
            filteredData.setPredicate(needed -> {
                if (tenKH_TextField.getText().isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = tenKH_TextField.getText().toLowerCase();
                if (needed.getMaKH().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else {
                    return false;
                }
            });
            sortedData = new SortedList<>(filteredData);
            sortedData.comparatorProperty().bind(phieu_Table.comparatorProperty());
            phieu_Table.setItems(sortedData);
        });
        maHD_TextField.textProperty().addListener((observable, oldValue, newValue)->{
            filteredData = new FilteredList<>(phieu_Data, b -> true);
            filteredData.setPredicate(needed -> {
                if (maHD_TextField.getText().isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = maHD_TextField.getText().toLowerCase();
                if (needed.getLoai().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else {
                    return false;
                }
            });
            sortedData = new SortedList<>(filteredData);
            sortedData.comparatorProperty().bind(phieu_Table.comparatorProperty());
            phieu_Table.setItems(sortedData);
        });
    }
}
