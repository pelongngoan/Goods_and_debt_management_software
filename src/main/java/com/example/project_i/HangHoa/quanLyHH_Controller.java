package com.example.project_i.HangHoa;

import com.example.project_i.KetNoi_Database.DBConnection;
import com.example.project_i.Phieu_Nhap_Xuat.ctPhieu_Nhap_Xuat_Module;
import com.example.project_i.TrangChu.MenuController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
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
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;


public class quanLyHH_Controller implements Initializable {

    @FXML
    private TableColumn<HangHoa_Module, String> donViTinh_Col;

    @FXML
    private TableColumn<HangHoa_Module, Double> giaBanLe_Col;

    @FXML
    private TableColumn<HangHoa_Module, Double> giaNhap_Col;

    @FXML
    private TableColumn<HangHoa_Module, Double> giaXuat_Col;

    @FXML
    private TableColumn<HangHoa_Module, Integer> maHang_Col;

    @FXML
    private TableColumn<HangHoa_Module, String> phanLoai_Col;

    @FXML
    private TableView<HangHoa_Module> quanLyHH_Table;

    @FXML
    private TableColumn<HangHoa_Module, Integer> stt_HH_Col;

    @FXML
    private TableColumn<HangHoa_Module, String> tenHang_Col;

    @FXML
    private TableColumn<HangHoa_Module, Double> tonKho_Col;

    @FXML
    private Button themHH_Button, suaHH_Button, xoaHH_Button, thoat_Button;

    @FXML
    private TextField timKiem_TextField, donViTinh_Label, giaNhap_Label, giaXuat_Label, giaBanLe_Label, maHH_Label, nhomHH_Label, tenHH_Label;

    @FXML
    private AnchorPane quanLyHH_Anchor;

    public Connection conn = null ;
    public PreparedStatement preparedStatement = null;
    public ResultSet resultSet = null;
    static ObservableList<HangHoa_Module> products_Data = FXCollections.observableArrayList();
    int index = -1;
    public static String curentID;
    public void setCellQuanLyHH_Table(){
        conn = DBConnection.ConnectionDB();
        products_Data.clear();
        maHang_Col.setCellValueFactory(new PropertyValueFactory<HangHoa_Module,Integer>("maHang"));
        tenHang_Col.setCellValueFactory(new PropertyValueFactory<HangHoa_Module,String>("tenHang"));
        donViTinh_Col.setCellValueFactory(new PropertyValueFactory<HangHoa_Module,String>("donViTinh"));
        phanLoai_Col.setCellValueFactory(new PropertyValueFactory<HangHoa_Module,String>("phanLoai"));
        giaNhap_Col.setCellValueFactory(new PropertyValueFactory<HangHoa_Module,Double>("giaNhap"));
        giaXuat_Col.setCellValueFactory(new PropertyValueFactory<HangHoa_Module,Double>("giaXuat"));
        giaBanLe_Col.setCellValueFactory(new PropertyValueFactory<HangHoa_Module,Double>("giaBanLe"));
        tonKho_Col.setCellValueFactory(new PropertyValueFactory<HangHoa_Module,Double>("tonKho"));
        loadDataFromDatabase();
    }
    public void loadDataFromDatabase() {
        try {
            preparedStatement = conn.prepareStatement("danhMucHH_get_All");
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                products_Data.add(new HangHoa_Module(resultSet.getInt(1),resultSet.getInt(2), resultSet.getString(3), resultSet.getString(4),resultSet.getString(5), resultSet.getDouble(6), resultSet.getDouble(7), resultSet.getDouble(8), resultSet.getDouble(9)));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        quanLyHH_Table.setItems(products_Data);
    }
    @FXML
    void setTimKiem_TextField() {
        FilteredList<HangHoa_Module> filteredData = new FilteredList<>(products_Data, b -> true);

        timKiem_TextField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(needed -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (needed.getTenHang().toLowerCase().indexOf(lowerCaseFilter) != -1 ) {
                    return true; // Filter matches name
                } else if (needed.getDonViTinh().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (needed.getMaHang().toString().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (needed.getPhanLoai().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else
                    return false; // Does not match.
            });
        });
        SortedList<HangHoa_Module> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(quanLyHH_Table.comparatorProperty());
        quanLyHH_Table.setItems(sortedData);
    }
    public static String tenHang;
    public static String maHang;
    public static String nhomSP;
    public static String donViTinh;
    public static String giaNhap;
    public static String giaXuat;
    public static String giaBanLe;
    public static String tonKho;
    @FXML
    void getSelected(MouseEvent event1){
        index = quanLyHH_Table.getSelectionModel().getSelectedIndex();
        if (index <= -1) {
            return ;
        }
        curentID = String.valueOf(maHang_Col.getCellData(index));
        System.out.println(curentID);
        System.out.println(index);
    }

    public void setXoaHH_Button(){
        String sql = "danhMucHH_delete ?";
        try {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, curentID);
            preparedStatement.execute();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        setCellQuanLyHH_Table();
        setTimKiem_TextField();
    }
    public static int chinhSua;
    public void setThemHH_Button() throws IOException {
        chinhSua = 1;
        setNewForm();
    }
    public void setSuaHH_Button() throws IOException {
        chinhSua = 0;
        curentID = String.valueOf(maHang_Col.getCellData(index));
        maHang = maHang_Col.getCellData(index).toString();
        tenHang = tenHang_Col.getCellData(index).toString();
        nhomSP = phanLoai_Col.getCellData(index).toString();
        donViTinh = donViTinh_Col.getCellData(index).toString();
        giaNhap = giaNhap_Col.getCellData(index).toString();
        giaXuat = giaXuat_Col.getCellData(index).toString();
        giaBanLe = giaBanLe_Col.getCellData(index).toString();
        tonKho = tonKho_Col.getCellData(index).toString();
        setNewForm();
    }
    void setNewForm() throws IOException {
        URL url = new File("src/main/resources/com/example/project_i/HangHoa/addHangHoa_Form.fxml").toURI().toURL();
        Parent root = FXMLLoader.load(url);
        Stage stage = new Stage();
        stage.setTitle("Thêm sản phẩm");
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void setThoat_Button(){
        quanLyHH_Anchor.setVisible(false);
    }
    @FXML
    void setQuanLyHH_Anchor(KeyEvent event){
        quanLyHH_Anchor.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case F5 -> {
                        try {
                            System.out.println("Press F5");
                            setThemHH_Button();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    case F6 -> {
                        try {
                            setSuaHH_Button();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    case F9 -> setXoaHH_Button();
                    case F12 -> setThoat_Button();
                }
            }
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCellQuanLyHH_Table();
        setTimKiem_TextField();
    }

}
