package com.example.project_i.HangHoa;

import com.example.project_i.KetNoi_Database.DBConnection;
import javafx.beans.property.ReadOnlyObjectWrapper;
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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class quanLyHH_Controller implements Initializable {

    @FXML
    private TableColumn<HangHoa_Module, String> donViTinh_Col;

    @FXML
    private TableColumn<HangHoa_Module, Float> giaBanLe_Col;

    @FXML
    private TableColumn<HangHoa_Module, Float> giaNhap_Col;

    @FXML
    private TableColumn<HangHoa_Module, Float> giaXuat_Col;

    @FXML
    private TableColumn<HangHoa_Module, Integer> maHang_Col;

    @FXML
    private TableColumn<HangHoa_Module, String> phanLoai_Col;

    @FXML
    private TableView<HangHoa_Module> quanLyHH_Table = new TableView<>();

    @FXML
    private TableColumn<HangHoa_Module, Integer> stt_HH_Col;

    @FXML
    private TableColumn<HangHoa_Module, String> tenHang_Col;

    @FXML
    private TableColumn<HangHoa_Module, Float> tonKho_Col;

    @FXML
    private Button themHH_Button, suaHH_Button, xoaHH_Button, thoat_Button;

    @FXML
    private TextField timKiem_TextField, donViTinh_Label, giaNhap_Label, giaXuat_Label, giaBanLe_Label, maHH_Label, nhomHH_Label, tenHH_Label;

    @FXML
    private AnchorPane quanLyHH_Anchor;

    public Connection conn = null;
    public PreparedStatement preparedStatement = null;
    public ResultSet resultSet = null;
    ObservableList<HangHoa_Module> products_Data = FXCollections.observableArrayList();
    int index = -1;
    public static Integer curentID;
    public static String tenHang;
    public static String nhomSP;
    public static String donViTinh;
    public static String giaNhap;
    public static String giaXuat;
    public static String giaBanLe;
    public static String tonKho;

    public static boolean chinhSua = false;
    int max_index;

    void setNewForm(ActionEvent event) throws IOException {

        Parent addPart = FXMLLoader.load(getClass().getResource("addHangHoa_Form.fxml"));
        Scene scene = new Scene(addPart);
        Stage stage = new Stage();
        //Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(suaHH_Button.getScene().getWindow());
        stage.show();
    }

    public void refresh() {
        products_Data.clear();
        System.out.println("ffffrrrreess");

        /*products_Data = DBConnection.getHH_Data();
        quanLyHH_Table.setItems(products_Data);*/
        max_index = products_Data.size();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        stt_HH_Col.setSortable(false);
        stt_HH_Col.setCellValueFactory(column-> new ReadOnlyObjectWrapper<Integer>(quanLyHH_Table.getItems().indexOf(column.getValue())+1));
        maHang_Col.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        tenHang_Col.setCellValueFactory(cellData -> cellData.getValue().tenHangProperty());
        donViTinh_Col.setCellValueFactory(cellData -> cellData.getValue().donViTinhProperty());
        phanLoai_Col.setCellValueFactory(cellData -> cellData.getValue().phanLoaiProperty());
        giaNhap_Col.setCellValueFactory(cellData -> cellData.getValue().giaNhapProperty().asObject());
        giaXuat_Col.setCellValueFactory(cellData -> cellData.getValue().giaXuatProperty().asObject());
        giaBanLe_Col.setCellValueFactory(cellData -> cellData.getValue().giaBanLeProperty().asObject());
        tonKho_Col.setCellValueFactory(cellData -> cellData.getValue().tonKhoProperty().asObject());
        refresh();
        products_Data = DBConnection.getHH_Data();
        quanLyHH_Table.setItems(products_Data);
        quanLyHH_Table.refresh();
        System.out.println(max_index);
        quanLyHH_Anchor.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case F5 -> themHH_Button.fire();
                    case F6 -> suaHH_Button.fire();
                    case F9 -> xoaHH_Button.fire();
                    case F12 -> thoat_Button.fire();
                    case F2 -> refresh();
                }
            }
        });
        quanLyHH_Table.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                index = quanLyHH_Table.getSelectionModel().getSelectedIndex();
                curentID = maHang_Col.getCellData(index);
                System.out.println(index + " and " + curentID);
            }
        });

        timKiem_TextField.textProperty().addListener((observable, oldValue, newValue) -> {
            FilteredList<HangHoa_Module> filteredData = new FilteredList<>(products_Data, b -> true);
            filteredData.setPredicate(needed -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (needed.getTenHang().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else
                    return false;
            });
            SortedList<HangHoa_Module> sortedData = new SortedList<>(filteredData);
            sortedData.comparatorProperty().bind(quanLyHH_Table.comparatorProperty());
            quanLyHH_Table.setItems(sortedData);
        });

        xoaHH_Button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                conn = DBConnection.ConnectionDB();
                try {
                    preparedStatement = conn.prepareStatement("danhMucHH_delete ?");
                    preparedStatement.setInt(1, curentID);
                    preparedStatement.execute();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e);
                }
                refresh();
                quanLyHH_Table.getSelectionModel().select(index);
            }
        });

        themHH_Button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                chinhSua = false;
                try {
                    setNewForm(event);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        suaHH_Button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (index < 0) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Thông báo!");
                    alert.setContentText("Chưa chọn sản phẩm!");
                    alert.showAndWait();
                } else {
                    chinhSua = true;
                    curentID = maHang_Col.getCellData(index);
                    tenHang = tenHang_Col.getCellData(index);
                    nhomSP = phanLoai_Col.getCellData(index);
                    donViTinh = donViTinh_Col.getCellData(index);
                    giaNhap = String.valueOf(giaNhap_Col.getCellData(index));
                    giaXuat = String.valueOf(giaXuat_Col.getCellData(index));
                    giaBanLe = String.valueOf(giaBanLe_Col.getCellData(index));
                    tonKho = tonKho_Col.getCellData(index).toString();
                    try {
                        setNewForm(event);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
        thoat_Button.setOnAction(even -> quanLyHH_Anchor.setVisible(false));
    }

}
