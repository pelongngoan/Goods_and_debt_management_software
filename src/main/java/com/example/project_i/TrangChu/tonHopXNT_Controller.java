package com.example.project_i.TrangChu;

import com.example.project_i.KetNoi_Database.DBConnection;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class tonHopXNT_Controller implements Initializable {
    @FXML
    private Label ngay_Label;

    @FXML
    private TableColumn<tongHopXNT_Module, Double> nh_Column;

    @FXML
    private TableColumn<tongHopXNT_Module, Integer> stt_Column;

    @FXML
    private TableColumn<tongHopXNT_Module, Double> tc_Column;

    @FXML
    private TableColumn<tongHopXNT_Module, Double> td_Column;

    @FXML
    private TableColumn<tongHopXNT_Module, String> ten_Column;

    @FXML
    private TableView<tongHopXNT_Module> xnt_TableView;

    @FXML
    private TableColumn<tongHopXNT_Module, Double> xuat_Column;
    @FXML
    private TableColumn<tongHopXNT_Module, Double> banLe_Column;

    @FXML
    private DatePicker denNgay_DatePicker;

    @FXML
    private AnchorPane dien_AnchorPane;

    @FXML
    private AnchorPane hien_AnchorPane;
    @FXML
    private Button thoat_Button,back_Button;

    @FXML
    private Button thucHien_Button;

    @FXML
    private DatePicker tuNgay_DatePicker;

    PreparedStatement preparedStatement =null;
    Connection conn = null;
    ResultSet resultSet = null;
    ObservableList<tongHopXNT_Module> data = FXCollections.observableArrayList();
    public void setThucHien_Button() {

        if (tuNgay_DatePicker.getValue() == null || denNgay_DatePicker.getValue() == null ) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Thông báo !");
            alert.setContentText("Chưa nhập đủ thông tin!");
            alert.showAndWait();
        } else {
            dien_AnchorPane.setVisible(false);
            hien_AnchorPane.setVisible(true);

            conn = DBConnection.ConnectionDB();
            try {
                preparedStatement = conn.prepareStatement("tongHopXNT ?, ?");
                preparedStatement.setString(1, tuNgay_DatePicker.getValue().toString());
                preparedStatement.setString(2, denNgay_DatePicker.getValue().toString());
                resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    data.add(new tongHopXNT_Module(resultSet.getString(1), resultSet.getDouble(2), resultSet.getDouble(3), resultSet.getDouble(4), resultSet.getDouble(5), resultSet.getDouble(6)));
                }
            } catch (SQLException e) {
                System.out.println("jsdhjsdsdh");
            }
            for (tongHopXNT_Module tongHopXNTModule:data){
                System.out.println(tongHopXNTModule.getTen()+"   "+tongHopXNTModule.getTonDau());
            }
            xnt_TableView.setItems(data);
            ngay_Label.setText("Từ: "+tuNgay_DatePicker.getValue()+ "\t\tĐến: "+ denNgay_DatePicker.getValue());

        }
    }
    public void setThoat_Button(){
        dien_AnchorPane.setVisible(false);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ten_Column.setCellValueFactory(new PropertyValueFactory<>("ten"));

        td_Column.setCellValueFactory(new PropertyValueFactory<>("tonDau"));
        nh_Column.setCellValueFactory(new PropertyValueFactory<>("nhap"));
        xuat_Column.setCellValueFactory(new PropertyValueFactory<>("xuat"));
        banLe_Column.setCellValueFactory(new PropertyValueFactory<>("banLe"));
        tc_Column.setCellValueFactory(new PropertyValueFactory<>("tongCon"));
        stt_Column.setSortable(false);
        stt_Column.setCellValueFactory(column-> new ReadOnlyObjectWrapper<Integer>(xnt_TableView.getItems().indexOf(column.getValue())+1));
        back_Button.setOnAction(event -> {
            hien_AnchorPane.setVisible(false);
            dien_AnchorPane.setVisible(true);
        });
    }
}
