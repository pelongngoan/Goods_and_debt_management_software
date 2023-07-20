package com.example.project_i.TrangChu;

import com.example.project_i.KetNoi_Database.DBConnection;
import com.example.project_i.KhachHang.KhachHang_Module;
import com.example.project_i.Phieu_Nhap_Xuat.ctPhieu_Nhap_Xuat_Module;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class PhieuThu_Controller implements Initializable {
    @FXML
    private TableColumn<KhachHang_Module, String> diaChi_Column;

    @FXML
    private TextField diaChi_TextField;


    @FXML
    private Button huy_Button;

    @FXML
    private TableColumn<KhachHang_Module, Integer> id_Column;

    @FXML
    private Button luu_Button;

    @FXML
    private TableColumn<KhachHang_Module, String> mst_Column;

    @FXML
    private TextField mst_TextField;

    @FXML
    private TableColumn<KhachHang_Module, String> nganHang_Column;

    @FXML
    private TextField nganHang_TextField;

    @FXML
    private DatePicker ngayPS_Date;

    @FXML
    private TableColumn<KhachHang_Module, String> nguoiDD_Column;

    @FXML
    private TextField nguoiDD_TextField;

    @FXML
    private TableColumn<KhachHang_Module, Double> noDau_Column;

    @FXML
    private ComboBox<String> noiDung_ComboBox;

    @FXML
    private TableColumn<KhachHang_Module, String> sdt_Column;

    @FXML
    private TextField sdt_TextField;

    @FXML
    private TextField soPhieu_TextField;

    @FXML
    private TextField soTien_TextField;

    @FXML
    private Button tao_Button,tao_Button1;

    @FXML
    private AnchorPane phieuThu_Pane;

    @FXML
    private TableColumn<KhachHang_Module, String> ten_Column;
    @FXML
    private TableView<KhachHang_Module> table_TableView;

    @FXML
    private Button thoat_Button;

    @FXML
    private TableColumn<KhachHang_Module, String> tk_Column;

    @FXML
    private TextField tk_TextField;

    Connection conn = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet= null;
    int maPhieu;
    ObservableList<KhachHang_Module> data = FXCollections.observableArrayList();
    void setNguoiDD_TextField(){
        FilteredList<KhachHang_Module> filteredData = new FilteredList<>(data, b -> true);
        filteredData.setPredicate(needed -> {
            if (nguoiDD_TextField.getText().isEmpty()) {
                table_TableView.setVisible(false);
                return true;
            }
            String lowerCaseFilter = nguoiDD_TextField.getText().toLowerCase();
            if (needed.getTenKH().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                table_TableView.setVisible(true);
                return true;
            } else {
                table_TableView.setVisible(false);
                return false;
            }
        });
        SortedList<KhachHang_Module> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(table_TableView.comparatorProperty());
        table_TableView.setItems(sortedData);

    }
    int table_Index=0,id_DD=-1;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        id_Column.setCellValueFactory(new PropertyValueFactory<>("maKH"));
        ten_Column.setCellValueFactory(new PropertyValueFactory<>("tenKH"));
        diaChi_Column.setCellValueFactory(new PropertyValueFactory<>("diaChi"));
        noDau_Column.setCellValueFactory(new PropertyValueFactory<>("noDau"));
        nguoiDD_Column.setCellValueFactory(new PropertyValueFactory<>("nguoiDaiDien"));
        sdt_Column.setCellValueFactory(new PropertyValueFactory<>("sdt"));
        mst_Column.setCellValueFactory(new PropertyValueFactory<>("mst"));
        nganHang_Column.setCellValueFactory(new PropertyValueFactory<>("nganHang"));
        tk_Column.setCellValueFactory(new PropertyValueFactory<>("tk"));
        conn = DBConnection.ConnectionDB();
        data = DBConnection.getKH_Data();
        table_TableView.setItems(data);
        nguoiDD_TextField.textProperty().addListener((observable, oldValue, newValue) -> {
            setNguoiDD_TextField();
        });
        nguoiDD_TextField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.DOWN) {
                table_TableView.requestFocus();
                table_TableView.getSelectionModel().select(0);
            }
        });
        table_TableView.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.UP && table_Index == 0) {
                nguoiDD_TextField.requestFocus();
            } else if (event.getCode() == KeyCode.UP || event.getCode() == KeyCode.DOWN) {
                table_Index = table_TableView.getSelectionModel().getSelectedIndex();
                System.out.println(table_Index);
            } else if (event.getCode() == KeyCode.ENTER) {
                id_DD = id_Column.getCellData(table_Index);
                System.out.println(id_DD);
                //nguoiDD_TextField.setText(ten_Column.getCellData(table_Index));
                diaChi_TextField.setText(diaChi_Column.getCellData(table_Index));
                sdt_TextField.setText(sdt_Column.getCellData(table_Index));
                mst_TextField.setText(mst_Column.getCellData(table_Index));
                nganHang_TextField.setText(nganHang_Column.getCellData(table_Index));
                tk_TextField.setText(tk_Column.getCellData(table_Index));

                nguoiDD_TextField.setText(ten_Column.getCellData(table_Index));
                table_TableView.setVisible(false);
            }
        });
        try {
            preparedStatement = conn.prepareStatement("SELECT noiDungTC FROM danhMucThuChi WHERE loaiTC='Thu'");
            resultSet=preparedStatement.executeQuery();
            while (resultSet.next()){
                noiDung_ComboBox.getItems().add(resultSet.getString(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        tao_Button.setOnAction(event -> {
            tao_Button.setDisable(true);
            ngayPS_Date.setDisable(false);
            noiDung_ComboBox.setDisable(false);
            nguoiDD_TextField.setDisable(false);
            soTien_TextField.setDisable(false);
            luu_Button.setDisable(false);
            huy_Button.setDisable(false);
            ngayPS_Date.setValue(java.time.LocalDate.now());
            try {
                preparedStatement = conn.prepareStatement("SELECT MAX(SOPH+1) FROM PHTHU");
                resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    maPhieu = resultSet.getInt(1);
                    soPhieu_TextField.setText("PHTHU_" + resultSet.getString(1));
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        });
        luu_Button.setOnAction(event -> {
            try {
                preparedStatement = conn.prepareStatement("phieuThu_add ?,?,?,?,?");
                preparedStatement.setString(1, ngayPS_Date.getValue().toString());
                preparedStatement.setInt(2, id_DD);
                preparedStatement.setString(3, soTien_TextField.getText());
                preparedStatement.setString(4, noiDung_ComboBox.getEditor().getText());
                preparedStatement.setInt(5, maPhieu);
                preparedStatement.execute();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            luu_Button.setDisable(true);
            huy_Button.setDisable(false);
            tao_Button.setVisible(false);
            tao_Button1.setVisible(true);
            tao_Button1.setDisable(false);
            ngayPS_Date.setDisable(true);
            noiDung_ComboBox.setDisable(true);
            nguoiDD_TextField.setDisable(true);
            soTien_TextField.setDisable(true);
        });
        huy_Button.setOnAction(event -> {
            try {
                preparedStatement = conn.prepareStatement("DELETE PHTHU WHERE SOPH = ?");
                preparedStatement.setInt(1,maPhieu);
                preparedStatement.execute();
                reload();
            } catch (SQLException e){
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        tao_Button1.setOnAction(event -> {
            try {
                reload();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        thoat_Button.setOnAction(event -> {
            Stage stage = (Stage) thoat_Button.getScene().getWindow();
            stage.close();
        });

    }

    @FXML
    void reload() throws IOException {
        tao_Button1.setDisable(true);
        tao_Button.setDisable(false);
        AnchorPane root = FXMLLoader.load(new File("src/main/resources/com/example/project_i/PhieuThu/phieuThu_Form.fxml").toURI().toURL());
        phieuThu_Pane.getChildren().setAll(root);
    }

}
