package com.example.project_i.KhachHang;

import com.example.project_i.KetNoi_Database.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.security.Key;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class quanLyKH_Controller implements Initializable {

    @FXML
    private TableColumn<KhachHang_Module, String> diaChi_Column;

    @FXML
    private TableColumn<KhachHang_Module, String> fax_Column;

    @FXML
    private TableColumn<KhachHang_Module, String> khuVuc_Column;

    @FXML
    private ComboBox khuVuc_ComboBox;

    @FXML
    private TableColumn<KhachHang_Module, String> maKH_Column;

    @FXML
    private TableColumn<KhachHang_Module, String> mst_Column;

    @FXML
    private TableColumn<KhachHang_Module, String> nganHang_Column;

    @FXML
    private TableColumn<KhachHang_Module, String> nguoiDD_Column;

    @FXML
    private TableColumn<KhachHang_Module, Double> noDau_Column;

    @FXML
    private TableView<KhachHang_Module> quanLyKH_Table;

    @FXML
    private TableColumn<KhachHang_Module, String> sdt_Column;

    @FXML
    private Button sua_Button;

    @FXML
    private TableColumn<KhachHang_Module, String> tenKH_Column;

    @FXML
    private Button them_Button;

    @FXML
    private Button thoat_Button;

    @FXML
    private TextField timKiem_TextField;

    @FXML
    private TableColumn<KhachHang_Module, String> tk_Column;

    @FXML
    private Button xoa_Button;
    @FXML
    private AnchorPane quanLyKH_AnchorPane;
    public Connection conn = null;
    public PreparedStatement preparedStatement = null;
    public ResultSet resultSet = null;
    int ma_index = -1;
    int index = -1;
    public static String idKH, tenKH, khuVucKH, diaChiKH, noDauKH = "0", nguoiDaiDienKH, sdtKH, nganHangKH, tkKH, mstKH;
    public static boolean themKH ;
    ObservableList<KhachHang_Module> KH_Data= FXCollections.observableArrayList();

    void setScene() throws IOException {
        URL url = new File("src/main/resources/com/example/project_i/KhachHang/addKhachHang_Form.fxml").toURI().toURL();
        Parent root = FXMLLoader.load(url);
        Stage stage = new Stage();
        stage.setTitle("Thông tin khách hàng!");
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void setThem_Button() throws IOException {
        themKH = true;
        setScene();
    }
    public void setSua_Button() throws IOException {
        themKH = false;
        if (ma_index!=-1) {
            System.out.println(ma_index);
            idKH = String.valueOf(maKH_Column.getCellData(index));
            tenKH = String.valueOf(tenKH_Column.getCellData(index));
            khuVucKH = String.valueOf(khuVuc_Column.getCellData(index));
            diaChiKH = String.valueOf(diaChi_Column.getCellData(index));
            noDauKH = String.valueOf(noDau_Column.getCellData(index));
            nguoiDaiDienKH = String.valueOf(nguoiDD_Column.getCellData(index));
            sdtKH = String.valueOf(sdt_Column.getCellData(index));
            nganHangKH = String.valueOf(nganHang_Column.getCellData(index));
            tkKH = String.valueOf(tk_Column.getCellData(index));
            mstKH = String.valueOf(mst_Column.getCellData(index));
            setScene();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Thông báo lỗi ");
            alert.setContentText("Bạn chưa chọn khách hàng!");
            alert.showAndWait();
        }
    }

    public void setXoa_Button() throws SQLException {
        conn = DBConnection.ConnectionDB();
        if (index!=-1) {
            String sql = "danhMucKH_delete ?";
            try {
                preparedStatement = conn.prepareStatement(sql);
                preparedStatement.setString(1, String.valueOf(ma_index));
                preparedStatement.execute();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
            index = -1;
            setQuanLyKH_Table();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Thông báo lỗi ");
            alert.setContentText("Bạn chưa chọn khách hàng để xóa !");
            alert.showAndWait();
        }
        conn.close();
    }

    public void setThoat_Button(){
        quanLyKH_AnchorPane.setVisible(false);
    }

    @FXML
    void setTimKiem_TextField() {
        FilteredList<KhachHang_Module> filteredData = new FilteredList<>(KH_Data, b -> true);
        timKiem_TextField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(needed -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (needed.getTenKH().toLowerCase().indexOf(lowerCaseFilter) != -1 ) {
                    return true;
                } else
                    return false;
            });
        });
        SortedList<KhachHang_Module> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(quanLyKH_Table.comparatorProperty());
        quanLyKH_Table.setItems(sortedData);
    }

    void setQuanLyKH_Table(){
        KH_Data = DBConnection.getKH_Data();
        quanLyKH_Table.setItems(KH_Data);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        maKH_Column.setCellValueFactory(new PropertyValueFactory<>("maKH"));
        tenKH_Column.setCellValueFactory(new PropertyValueFactory<>("tenKH"));
        khuVuc_Column.setCellValueFactory(new PropertyValueFactory<>("khuVuc"));
        diaChi_Column.setCellValueFactory(new PropertyValueFactory<>("diaChi"));
        nguoiDD_Column.setCellValueFactory(new PropertyValueFactory<>("nguoiDaiDien"));
        noDau_Column.setCellValueFactory(new PropertyValueFactory<>("noDau"));
        sdt_Column.setCellValueFactory(new PropertyValueFactory<>("sdt"));
        nganHang_Column.setCellValueFactory(new PropertyValueFactory<>("nganHang"));
        tk_Column.setCellValueFactory(new PropertyValueFactory<>("tk"));
        mst_Column.setCellValueFactory(new PropertyValueFactory<>("mst"));
        setQuanLyKH_Table();
        quanLyKH_Table.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                ma_index = quanLyKH_Table.getSelectionModel().getSelectedItem().getMaKH();
                index= quanLyKH_Table.getSelectionModel().getSelectedIndex();
                System.out.println(index);
            }
        });
        quanLyKH_Table.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.UP || event.getCode() == KeyCode.DOWN){
                    ma_index = quanLyKH_Table.getSelectionModel().getSelectedItem().getMaKH();
                    index= quanLyKH_Table.getSelectionModel().getSelectedIndex();
                    System.out.println(index);
                }
            }
        });
        quanLyKH_AnchorPane.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()){
                    case F5 -> {
                        try {
                            setThem_Button();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    case F6 -> {
                        try {
                            setSua_Button();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    case F9 -> {
                        try {
                            setXoa_Button();
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    case F12 -> setThoat_Button();
                }
            }
        });
    }
}
