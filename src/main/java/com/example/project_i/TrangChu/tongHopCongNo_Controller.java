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
import java.sql.*;
import java.util.ResourceBundle;

public class tongHopCongNo_Controller implements Initializable {
    @FXML
    private Button back_Button;

    @FXML
    private TableColumn<tongHopCongNo_Module, Float> chi_Column;

    @FXML
    private TableColumn<tongHopCongNo_Module, Float> chi_Column1;

    @FXML
    private TableColumn<tongHopCongNo_Module, Float> conNo_Column;

    @FXML
    private TableColumn<tongHopCongNo_Module, Float> conNo_Column1;

    @FXML
    private DatePicker denNgay_DatePicker;

    @FXML
    private AnchorPane dien_AnchorPane;

    @FXML
    private AnchorPane hien_AnchorPane;

    @FXML
    private Label ngay_Label;

    @FXML
    private TableColumn<tongHopCongNo_Module, Float> noDau_Column;

    @FXML
    private TableColumn<tongHopCongNo_Module, Float> noDau_Column1;

    @FXML
    private TableColumn<tongHopCongNo_Module, Integer> stt_Column;

    @FXML
    private TableColumn<tongHopCongNo_Module, Integer> stt_Column1;

    @FXML
    private TableColumn<tongHopCongNo_Module, String> ten_Column;

    @FXML
    private TableColumn<tongHopCongNo_Module, String > ten_Column1;

    @FXML
    private Button thoat_Button;

    @FXML
    private Button thucHien_Button;

    @FXML
    private TableColumn<tongHopCongNo_Module, Float> tra_Column;

    @FXML
    private TableColumn<tongHopCongNo_Module, Float> tra_Column1;

    @FXML
    private DatePicker tuNgay_DatePicker;

    @FXML
    private TableView<tongHopCongNo_Module> xnt_TableView;

    @FXML
    private TableView<tongHopCongNo_Module> xnt_TableView1;

    ObservableList<tongHopCongNo_Module> data = FXCollections.observableArrayList();
    ObservableList<tongHopCongNo_Module> data1 = FXCollections.observableArrayList();
    Connection conn = null;
    PreparedStatement preparedStatement= null;
    ResultSet resultSet = null;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
            ten_Column.setCellValueFactory(new PropertyValueFactory<>("ten"));
            ten_Column1.setCellValueFactory(new PropertyValueFactory<>("ten"));
            noDau_Column.setCellValueFactory(new PropertyValueFactory<>("noDau"));
            noDau_Column1.setCellValueFactory(new PropertyValueFactory<>("noDau"));
        chi_Column.setCellValueFactory(new PropertyValueFactory<>("tienChi"));
        chi_Column1.setCellValueFactory(new PropertyValueFactory<>("tienChi"));
        tra_Column.setCellValueFactory(new PropertyValueFactory<>("tienTra"));
        tra_Column1.setCellValueFactory(new PropertyValueFactory<>("tienTra"));
        conNo_Column.setCellValueFactory(new PropertyValueFactory<>("conNo"));
        conNo_Column1.setCellValueFactory(new PropertyValueFactory<>("conNo"));
        conn = DBConnection.ConnectionDB();
        thucHien_Button.setOnAction(event -> {
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
                    preparedStatement = conn.prepareStatement("spTongHopCongNoNCC ?,?");
                    preparedStatement.setDate(1, Date.valueOf(tuNgay_DatePicker.getValue()));
                    preparedStatement.setDate(2, Date.valueOf(denNgay_DatePicker.getValue()));
                    resultSet = preparedStatement.executeQuery();
                    while (resultSet.next()){
                        data.add(new tongHopCongNo_Module(resultSet.getString(1), resultSet.getFloat(2), resultSet.getFloat(3), resultSet.getFloat(4),resultSet.getFloat(5)));
                    }

                    xnt_TableView.setItems(data);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                try {
                    preparedStatement = conn.prepareStatement("spTongHopCongNoKH ?,?");
                    preparedStatement.setDate(1, Date.valueOf(tuNgay_DatePicker.getValue()));
                    preparedStatement.setDate(2, Date.valueOf(denNgay_DatePicker.getValue()));
                    resultSet = preparedStatement.executeQuery();
                    while (resultSet.next()){
                        data1.add(new tongHopCongNo_Module(resultSet.getString(1), resultSet.getFloat(2), resultSet.getFloat(3), resultSet.getFloat(4),resultSet.getFloat(5)));
                    }
                    xnt_TableView1.setItems(data1);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                ngay_Label.setText("Từ: "+tuNgay_DatePicker.getValue()+ "\t\tĐến: "+ denNgay_DatePicker.getValue());

            }
        });
        back_Button.setOnAction(event -> {
            data.clear();
            data1.clear();
            dien_AnchorPane.setVisible(true);
            hien_AnchorPane.setVisible(false);

        });
        stt_Column.setSortable(false);
        stt_Column.setCellValueFactory(column-> new ReadOnlyObjectWrapper<Integer>(xnt_TableView.getItems().indexOf(column.getValue())+1));
        stt_Column1.setSortable(false);
        stt_Column1.setCellValueFactory(column-> new ReadOnlyObjectWrapper<Integer>(xnt_TableView1.getItems().indexOf(column.getValue())+1));
    }
}
