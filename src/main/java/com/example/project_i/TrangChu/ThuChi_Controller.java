package com.example.project_i.TrangChu;

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

public class ThuChi_Controller implements Initializable {

    @FXML
    private TableColumn<ThuChi_Module, String> loai_Column;

    @FXML
    private AnchorPane quanLyTC_AnchorPane;

    @FXML
    private TableView<ThuChi_Module> quanLyTC_Table;

    @FXML
    private TableColumn<ThuChi_Module, String> noiDung_Column;
    @FXML
    private TableColumn<ThuChi_Module, Integer> id_Column;

    @FXML
    private Button them_Button,sua_Button,xoa_Button,thoat_Button;

    @FXML
    private TextField timKiem_TextField;

    public Connection conn = null;
    public PreparedStatement preparedStatement = null;
    public ResultSet resultSet = null;
    int ma_index = -1;
    int index = -1;
    boolean AU = true ;
    ObservableList<ThuChi_Module> TC_Data= FXCollections.observableArrayList();

    public void setScene() throws IOException {
        URL url = new File("src/main/resources/com/example/project_i/NoiDungThuChi/thuChiAU.fxml").toURI().toURL();
        FXMLLoader loader = new FXMLLoader(url);
        Parent root = loader.load();
        if (AU == false) {

            thuChiAU_Controller thuChiAUController = loader.getController();
            thuChiAUController.loai_ComboBox.getEditor().setText(loai_Column.getCellData(index));
            thuChiAUController.noiDung_TexField.setText(noiDung_Column.getCellData(index));
            thuChiAUController.noiDung = noiDung_Column.getCellData(index);
            thuChiAUController.AU = false;
            thuChiAUController.id = ma_index;
        }
        Stage stage = new Stage();
        stage.setTitle("Thông tin nhà cung cấp!");
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(them_Button.getScene().getWindow());
        stage.show();

    }


    void setQuanLyTC_Table(){
        TC_Data = DBConnection.getTC_Data();
        quanLyTC_Table.setItems(TC_Data);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        id_Column.setCellValueFactory(new PropertyValueFactory<>("idTC"));
        loai_Column.setCellValueFactory(new PropertyValueFactory<>("loai"));
        noiDung_Column.setCellValueFactory(new PropertyValueFactory<>("noiDung"));
        setQuanLyTC_Table();

        timKiem_TextField.textProperty().addListener((observable, oldValue, newValue) -> {
            FilteredList<ThuChi_Module> filteredData = new FilteredList<>(TC_Data, b -> true);
            filteredData.setPredicate(needed -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (needed.getNoiDung().toLowerCase().indexOf(lowerCaseFilter) != -1 ) {
                    return true;
                } else
                    return false;
            });
            SortedList<ThuChi_Module> sortedData = new SortedList<>(filteredData);
            sortedData.comparatorProperty().bind(quanLyTC_Table.comparatorProperty());
            quanLyTC_Table.setItems(sortedData);
        });

        them_Button.setOnAction(event -> {
            AU = true;
            try {
                setScene();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        sua_Button.setOnAction(event -> {
            if (ma_index > -1) {
                try {
                    AU = false;
                    setScene();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Thông báo lỗi ");
                alert.setContentText("Bạn chưa chọn sản phẩm!");
                alert.showAndWait();
            }
        });
        xoa_Button.setOnAction(event->{
            conn = DBConnection.ConnectionDB();
            if (index > -1) {
                String sql = "DELETE danhMucThuChi WHERE id = ? ";
                try {
                    preparedStatement = conn.prepareStatement(sql);
                    preparedStatement.setString(1, String.valueOf(ma_index));
                    preparedStatement.execute();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e);
                }
                index = -1;
                setQuanLyTC_Table();
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Thông báo lỗi ");
                alert.setContentText("Bạn chưa chọn sản phẩm để xóa !");
                alert.showAndWait();
            }
        });
        thoat_Button.setOnAction(event -> {
            quanLyTC_AnchorPane.setVisible(false);
        });
        quanLyTC_Table.setRowFactory(tableView->{
            TableRow<ThuChi_Module> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (! row.isEmpty()) {
                    index= row.getIndex();
                    ma_index = id_Column.getCellData(index);
                    System.out.println(index);
                    System.out.println(ma_index);
                }
            });
            return row;
        });
        quanLyTC_Table.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.UP || event.getCode() == KeyCode.DOWN){
                    ma_index = quanLyTC_Table.getSelectionModel().getSelectedItem().getIdTC();
                    index= quanLyTC_Table.getSelectionModel().getSelectedIndex();
                    System.out.println(index);
                }
            }
        });
        quanLyTC_AnchorPane.setOnKeyPressed(event -> {
            switch (event.getCode()){
                case F5 -> them_Button.fire();
                case F6 -> sua_Button.fire();
                case F12 -> thoat_Button.fire();
            }
        });
    }
}
