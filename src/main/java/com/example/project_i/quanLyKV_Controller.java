package com.example.project_i;

import com.example.project_i.KetNoi_Database.DBConnection;
import com.example.project_i.KhachHang.KhachHang_Module;
import com.example.project_i.KhachHang.KhuVuc_Module;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.StringConverter;
import javafx.util.converter.IntegerStringConverter;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class quanLyKV_Controller implements Initializable {
    @FXML
    private TableView<KhuVuc_Module> khuVuc_Table;
    @FXML
    private TableColumn<KhuVuc_Module, Integer> id_Column;

    @FXML
    private TableColumn<KhuVuc_Module, String> ten_Column;

    @FXML
    private Button thoat_Button;

    @FXML
    private TextField timKiem_TextField;

    @FXML
    private Button xoa_Button;
    @FXML
    private AnchorPane khuVuc_AnchorPane;
    Connection conn = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    ObservableList<KhuVuc_Module> khuVuc_Data = FXCollections.observableArrayList();
    int id_index = -1;
    int index = -1;
    int max_index ;
    public void setTimKiem_TextField(){
        FilteredList<KhuVuc_Module> filteredData = new FilteredList<>(khuVuc_Data, b -> true);
        filteredData.setPredicate(needed -> {
            if (timKiem_TextField.getText() == null || timKiem_TextField.getText().isEmpty()) {
                return true;
            }
            String lowerCaseFilter = timKiem_TextField.getText().toLowerCase();
            if (needed.getTenKV().toLowerCase().indexOf(lowerCaseFilter) != -1 ) {
                return true;
            } else
                return false;
        });
        SortedList<KhuVuc_Module> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(khuVuc_Table.comparatorProperty());
        khuVuc_Table.setItems(sortedData);
    }

    void setKhuVuc_Table(){
        khuVuc_Data.clear();
        khuVuc_Data = DBConnection.getKV_Data();
        khuVuc_Data.add(new KhuVuc_Module(0, " "));
        khuVuc_Table.setItems(khuVuc_Data);

    }

    public void setThoat_Button() {
        khuVuc_AnchorPane.setVisible(false);
    }
    public void setXoa_Button(){
        if (index!=-1 && index!=max_index) {
            try {
                preparedStatement = conn.prepareStatement("danhMucKV_delete ?");
                preparedStatement.setString(1, String.valueOf(id_index));
                preparedStatement.execute();
            } catch (SQLException e) {
                System.out.println("sai");
            }
            max_index --;
            setKhuVuc_Table();
            setTimKiem_TextField();
            khuVuc_Table.getSelectionModel().select(index-1, ten_Column);
            id_index = khuVuc_Table.getSelectionModel().getSelectedItem().getIdKV();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Thông báo lỗi ");
            alert.setContentText("Bạn chưa chọn khu vực để xóa!");
            alert.showAndWait();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        conn = DBConnection.ConnectionDB();
        id_Column.setCellValueFactory(new PropertyValueFactory<>("idKV"));
        ten_Column.setCellValueFactory(new PropertyValueFactory<>("tenKV"));
        khuVuc_Table.setEditable(true);
        setKhuVuc_Table();
        for (KhuVuc_Module khuVucModule:khuVuc_Data){
            max_index++;
        }
        ten_Column.setCellFactory(TextFieldTableCell.forTableColumn());
        id_Column.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        khuVuc_Table.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                index = khuVuc_Table.getSelectionModel().getSelectedIndex()+1;
                id_index = khuVuc_Table.getSelectionModel().getSelectedItem().getIdKV();
            }
        });
        khuVuc_Table.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.UP || event.getCode() == KeyCode.DOWN || event.getCode() == KeyCode.ENTER){
                    index = khuVuc_Table.getSelectionModel().getSelectedIndex()+1;
                    id_index = khuVuc_Table.getSelectionModel().getSelectedItem().getIdKV();
                    System.out.println(index);
                    System.out.println(max_index);
                    System.out.println(id_index);
                }
            }
        });

        ten_Column.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<KhuVuc_Module, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<KhuVuc_Module, String> khuVucModuleStringCellEditEvent) {
                boolean exist = false;
                conn = DBConnection.ConnectionDB();
                for (KhuVuc_Module khuVucModule:khuVuc_Data){
                    if (khuVucModule.getTenKV().matches(khuVucModuleStringCellEditEvent.getNewValue())){
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Thông báo lỗi ");
                        alert.setContentText("Khu vực này đã tồn tại rồi!");
                        alert.showAndWait();
                        exist = true;
                        break;
                    } else exist = false;
                }
                if (khuVucModuleStringCellEditEvent.getNewValue().isEmpty()){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Thông báo !");
                    alert.setContentText("Bạn chưa nhập dữ liệu!");
                    alert.showAndWait();
                    setKhuVuc_Table();
                    khuVuc_Table.getSelectionModel().select(index, ten_Column);
                } else if (index!=max_index && exist == false) {
                    try {
                        preparedStatement = conn.prepareStatement("danhMucKV_update ?, ?");
                        preparedStatement.setString(1, String.valueOf(id_index));
                        preparedStatement.setString(2, khuVucModuleStringCellEditEvent.getNewValue());
                        preparedStatement.execute();
                        setKhuVuc_Table();
                        khuVuc_Table.getSelectionModel().select(index, ten_Column);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                } else if (exist == false){
                    try {
                        preparedStatement = conn.prepareStatement("danhMucKV_add ?");
                        preparedStatement.setString(1, khuVucModuleStringCellEditEvent.getNewValue());
                        preparedStatement.execute();
                        max_index++;
                        setKhuVuc_Table();
                        khuVuc_Table.getSelectionModel().select(index, ten_Column);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    setKhuVuc_Table();
                    khuVuc_Table.getSelectionModel().select(max_index, ten_Column);
                }
                System.out.println(index);
                System.out.println(max_index);
                System.out.println(id_index);
            }
        });
        khuVuc_AnchorPane.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.BACK_SPACE || event.getCode() == KeyCode.DELETE || event.getCode() == KeyCode.F9 ){
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Thông báo!");
                    alert.setContentText("Bạn chắc chắn muốn xóa khu vực này không ?");
                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get().equals(ButtonType.OK)) {
                        setXoa_Button();
                    } else if (event.getCode() == KeyCode.F12){
                        setThoat_Button();
                    };
                }
            }
        });
    }
}
