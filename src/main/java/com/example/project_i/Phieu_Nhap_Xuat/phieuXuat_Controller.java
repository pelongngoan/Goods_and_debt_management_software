package com.example.project_i.Phieu_Nhap_Xuat;

import com.example.project_i.KetNoi_Database.DBConnection;
import com.example.project_i.KhachHang.KhachHang_Module;
import com.example.project_i.KhachHang.KhuVuc_Module;
import com.example.project_i.KhachHang.NhaCungCap_Module;
import com.example.project_i.Phieu_Nhap_Xuat.ctPhieu_Nhap_Xuat_Module;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.FloatStringConverter;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class phieuXuat_Controller implements Initializable {
    @FXML
    private Button taoPhieu_Button, luuPhieu_Button, themNCC_Button,thoat_Button, huy_Button, taoPhieu_Button1,suaPhieu_Button;
    @FXML
    private TextField tenNhaCungCap_TextField, soPhieu_TextField, tongTienTT_TextField,
            diaChi_TextField, sdt_TextField, tongTienSauVAT_TextField, soTienCanTT_TextField,
            thanhToanNgay_TextField, tongTienVAT_TextField;
    @FXML
    private DatePicker ngayPS_Field;
    @FXML
    private AnchorPane phieuNhap_Pane;
    
    @FXML
    private TableView<ctPhieu_Nhap_Xuat_Module> phieuNhap_Table;
    @FXML
    private TableColumn<ctPhieu_Nhap_Xuat_Module, Integer> stt_Column;
    @FXML
    private TableColumn<ctPhieu_Nhap_Xuat_Module, Integer> maHang_Column;
    @FXML
    private TableColumn<ctPhieu_Nhap_Xuat_Module, Integer> id_Column;
    @FXML
    private TableColumn<ctPhieu_Nhap_Xuat_Module, String> tenHang_Column;
    @FXML
    private TableColumn<ctPhieu_Nhap_Xuat_Module, String> donViTinh_Column;
    @FXML
    private TableColumn<ctPhieu_Nhap_Xuat_Module, Float> soLuong_Column;
    @FXML
    private TableColumn<ctPhieu_Nhap_Xuat_Module, Float> gia_Column;
    @FXML
    private TableColumn<ctPhieu_Nhap_Xuat_Module, Double> thanhTien_Column;
    @FXML
    private TableColumn<ctPhieu_Nhap_Xuat_Module, String> ghiChu;
    /*Bảng tìm Nhà Cung Cấp*/
    @FXML
    private TableView<KhachHang_Module> search_Table;
    @FXML
    private TableColumn<KhachHang_Module, Integer> searchID_Column;
    @FXML
    private TableColumn<KhachHang_Module, String> searchName_Column;
    @FXML
    private TableColumn<KhachHang_Module, String> searchDiaChi_Column;
    @FXML
    private TableColumn<KhachHang_Module, String> searchSDT_Column;

    @FXML
    private Label thanhToanNgay_Label;
    @FXML
    private TextArea ghiChu_TextArea;


    public Connection conn = null;
    public PreparedStatement preparedStatement = null;
    public ResultSet resultSet = null;
    ObservableList<ctPhieu_Nhap_Xuat_Module> data;
    int maPhieu;
    public int maxRow = 0;
    ObservableList<KhachHang_Module> NCC_Data = FXCollections.observableArrayList();

    public void setTenNhaCungCap_TextField() {
        FilteredList<KhachHang_Module> filteredData = new FilteredList<>(NCC_Data, b -> true);
        filteredData.setPredicate(needed -> {
            if (tenNhaCungCap_TextField.getText().isEmpty()) {
                search_Table.setVisible(false);
                return true;
            }
            String lowerCaseFilter = tenNhaCungCap_TextField.getText().toLowerCase();
            if (needed.getTenKH().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                search_Table.setVisible(true);
                return true;
            } else {
                search_Table.setVisible(true);
                return false;
            }
        });
        SortedList<KhachHang_Module> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(search_Table.comparatorProperty());
        search_Table.setItems(sortedData);
    }


    int search_Table_Index = 0;
    @FXML
    void setThemNCC_Button() throws IOException {
        URL url = new File("src/main/resources/com/example/project_i/KhachHang/addKhachHang_Form.fxml").toURI().toURL();
        Parent root = FXMLLoader.load(url);
        Stage stage = new Stage();
        stage.setTitle("Thêm KH");
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    void reload() throws IOException {
        taoPhieu_Button1.setDisable(true);
        taoPhieu_Button.setDisable(false);
        AnchorPane root = FXMLLoader.load(new File("src/main/resources/com/example/project_i/PhieuXuat/phieuXuat_Form.fxml").toURI().toURL());
        phieuNhap_Pane.getChildren().setAll(root);
    }

    double tt_TT = 0;
    double tt_VAT = 0;
    double ttSau_VAT = 0;
    double VAT = 0;
    int index = -1;
    void disable(){
        ngayPS_Field.setDisable(false);
        tenNhaCungCap_TextField.setDisable(false);
        phieuNhap_Table.setDisable(false);
        luuPhieu_Button.setDisable(false);
        tongTienTT_TextField.setDisable(false);
        thoat_Button.setDisable(false);
        VAT_ComboBox.setDisable(false);
        thanhToanNgay_TextField.setDisable(false);
        ghiChu_TextArea.setDisable(false);
        tongTienSauVAT_TextField.setDisable(false);
        soTienCanTT_TextField.setDisable(false);
        tongTienVAT_TextField.setDisable(false);
        themNCC_Button.setDisable(false);
    }
    void setBill() throws IOException {
        URL url = new File("src/main/resources/com/example/project_i/bill.fxml").toURI().toURL();
        Parent root = FXMLLoader.load(url);
        Stage stage = new Stage();
        stage.setTitle("Hóa đơn");
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    void calculate() {
        tt_TT = 0;
        for (ctPhieu_Nhap_Xuat_Module ctPhieuNhapXuatModule : data) {
            System.out.println(ctPhieuNhapXuatModule.getThanhTien());
            tt_TT = tt_TT + ctPhieuNhapXuatModule.getThanhTien();
        }
        tt_VAT = VAT*tt_TT/100;
        ttSau_VAT = tt_TT + tt_VAT;
        tongTienVAT_TextField.setText(String.valueOf(tt_VAT));
        tongTienTT_TextField.setText(String.valueOf(tt_TT));
        soTienCanTT_TextField.setText(String.valueOf(ttSau_VAT));
        tongTienSauVAT_TextField.setText(String.valueOf(ttSau_VAT));
    }
    int id_KH =-1;
    @FXML
    private ComboBox<String> VAT_ComboBox;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        phieuNhap_Pane.setOnKeyPressed(event -> {
            switch (event.getCode()){
                case F5 -> {
                    taoPhieu_Button.fire();
                    taoPhieu_Button1.fire();
                }
                case F6 -> {
                    phieuNhap_Table.requestFocus();
                    phieuNhap_Table.getSelectionModel().select(maxRow, tenHang_Column);
                }
                case F10-> {
                    try {
                        setBill();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
        taoPhieu_Button1.setOnAction(event -> {
            try {
                reload();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        taoPhieu_Button.setOnAction(event -> {
            taoPhieu_Button.setDisable(true);
            disable();
            ngayPS_Field.setValue(java.time.LocalDate.now());
            sdt_TextField.clear();
            diaChi_TextField.clear();
            try {
                preparedStatement = conn.prepareStatement("SELECT MAX(SOPH+1) FROM PHXUAT");
                resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    maPhieu = Integer.parseInt(resultSet.getString(1));
                    soPhieu_TextField.setText("PHXUAT_" + resultSet.getString(1));
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        });
        tenNhaCungCap_TextField.textProperty().addListener((observable, oldValue, newValue) -> {
            setTenNhaCungCap_TextField();
        });
        tenNhaCungCap_TextField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.DOWN) {
                search_Table.requestFocus();
                search_Table.getSelectionModel().select(0);
            }
        });
        search_Table.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.UP && search_Table_Index == 0) {
                tenNhaCungCap_TextField.requestFocus();
            } else if (event.getCode() == KeyCode.UP || event.getCode() == KeyCode.DOWN) {
                search_Table_Index = search_Table.getSelectionModel().getSelectedIndex();
                System.out.println(search_Table_Index);
            } else if (event.getCode() == KeyCode.ENTER) {
                id_KH = searchID_Column.getCellData(search_Table_Index);
                System.out.println(searchName_Column.getCellData(search_Table_Index));
                diaChi_TextField.setText(searchDiaChi_Column.getCellData(search_Table_Index));
                sdt_TextField.setText(searchSDT_Column.getCellData(search_Table_Index));
                tenNhaCungCap_TextField.setText(searchName_Column.getCellData(search_Table_Index));
                search_Table.setVisible(false);
            }
        });
        searchID_Column.setCellValueFactory(new PropertyValueFactory<>("maKH"));
        searchName_Column.setCellValueFactory(new PropertyValueFactory<>("tenKH"));
        searchDiaChi_Column.setCellValueFactory(new PropertyValueFactory<>("diaChi"));
        searchSDT_Column.setCellValueFactory(new PropertyValueFactory<>("sdt"));
        NCC_Data = DBConnection.getKH_Data();
        search_Table.setItems(NCC_Data);
        VAT_ComboBox.getItems().add("5");
        VAT_ComboBox.getItems().add("10");
        VAT_ComboBox.getItems().add("15");
        VAT_ComboBox.getItems().add("20");
        VAT_ComboBox.getEditor().textProperty().addListener((obs, old, niu) -> {
            if (!VAT_ComboBox.getEditor().getText().matches("[0-9.]+") ||
                    VAT_ComboBox.getEditor().getText() == null || VAT_ComboBox.getEditor().getText().length() == 0 ) {
                VAT_ComboBox.getEditor().setText("0");
                VAT = 0;

                tongTienVAT_TextField.setText(String.valueOf(tt_VAT));
                tongTienSauVAT_TextField.setText(String.valueOf(ttSau_VAT));
                soTienCanTT_TextField.setText(String.valueOf(ttSau_VAT));
            } else {
                VAT = Double.parseDouble(VAT_ComboBox.getEditor().getText());
                calculate();
                tongTienVAT_TextField.setText(String.valueOf(tt_VAT));
                tongTienSauVAT_TextField.setText(String.valueOf(ttSau_VAT));
                soTienCanTT_TextField.setText(String.valueOf(ttSau_VAT));
            }

        });

        conn = DBConnection.ConnectionDB();
        data = FXCollections.observableArrayList();
        id_Column.setCellValueFactory(new PropertyValueFactory<ctPhieu_Nhap_Xuat_Module, Integer>("id"));
        maHang_Column.setCellValueFactory(new PropertyValueFactory<ctPhieu_Nhap_Xuat_Module, Integer>("maHang"));
        tenHang_Column.setCellValueFactory(new PropertyValueFactory<ctPhieu_Nhap_Xuat_Module, String>("tenHang"));
        donViTinh_Column.setCellValueFactory(new PropertyValueFactory<ctPhieu_Nhap_Xuat_Module, String>("donViTinh"));
        soLuong_Column.setCellValueFactory(cellData -> cellData.getValue().soLuongProperty().asObject());
        gia_Column.setCellValueFactory(cellData -> cellData.getValue().giaXuatProperty().asObject());
        thanhTien_Column.setCellValueFactory(new PropertyValueFactory<ctPhieu_Nhap_Xuat_Module, Double>("thanhTien"));
        ghiChu.setCellValueFactory(new PropertyValueFactory<ctPhieu_Nhap_Xuat_Module, String>("ghiChu"));

        data.add(new ctPhieu_Nhap_Xuat_Module(" "));

        phieuNhap_Table.setItems(data);
        phieuNhap_Table.setEditable(true);

        List<String> list2 = new ArrayList<>();
        try {
            preparedStatement = conn.prepareStatement("select tenHang from danhMucHH");
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                list2.add(new String(resultSet.getString(1)));
            }
        } catch (SQLException e) {
        }
        phieuNhap_Table.setRowFactory(tableView->{
            TableRow<ctPhieu_Nhap_Xuat_Module> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (! row.isEmpty()) {
                    index= row.getIndex();
                    System.out.println(index);
                }
            });
            return row;
        });
        phieuNhap_Table.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.UP) || event.getCode().equals(KeyCode.DOWN)) {
                index = phieuNhap_Table.getSelectionModel().getSelectedIndex();
            }
            if (event.getCode().equals(KeyCode.BACK_SPACE)){
                data.remove(index);
                maxRow--;
                System.out.println("jh"+maxRow);
                if (maxRow==0){
                    data.add(new ctPhieu_Nhap_Xuat_Module(" "));
                }
                phieuNhap_Table.refresh();

            }
        });

        donViTinh_Column.setCellFactory(TextFieldTableCell.forTableColumn());
        soLuong_Column.setCellFactory(TextFieldTableCell.forTableColumn(new FloatStringConverter()));
        gia_Column.setCellFactory(TextFieldTableCell.forTableColumn(new FloatStringConverter()));
        ghiChu.setCellFactory(TextFieldTableCell.forTableColumn());
        phieuNhap_Table.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        phieuNhap_Table.getSelectionModel().setCellSelectionEnabled(true);
        tenHang_Column.setOnEditCommit(event-> {
            boolean exist= false;
            for (ctPhieu_Nhap_Xuat_Module data:data){
                if (data.getTenHang().matches(event.getNewValue())){
                    exist = true;
                    break;
                } else exist = false;
            }
            ctPhieu_Nhap_Xuat_Module ctPhieuNhapXuatModule = event.getRowValue();
            if (exist==false) {
                try {
                    preparedStatement = conn.prepareStatement("SELECT id,tenHang,donViTinh,giaXuat FROM danhMucHH WHERE tenHang = ?");
                    preparedStatement.setString(1, event.getNewValue());
                    resultSet = preparedStatement.executeQuery();
                    while (resultSet.next()) {
                        ctPhieuNhapXuatModule.setId(resultSet.getInt(1));
                        ctPhieuNhapXuatModule.setTenHang(resultSet.getString(2));
                        ctPhieuNhapXuatModule.setDonViTinh(resultSet.getString(3));
                        ctPhieuNhapXuatModule.setGiaXuat(resultSet.getFloat(4));
                        ctPhieuNhapXuatModule.setSoLuong(1);
                        phieuNhap_Table.refresh();
                        phieuNhap_Table.setItems(data);
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                for (ctPhieu_Nhap_Xuat_Module data:data){
                    System.out.println(data.getTenHang());
                    System.out.println(data.getGiaXuat());
                }
                phieuNhap_Table.getSelectionModel().select(event.getTablePosition().getRow(), donViTinh_Column);
                Platform.runLater(() -> phieuNhap_Table.edit(event.getTablePosition().getRow(), donViTinh_Column));
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Thông báo");
                alert.setContentText("Mặt hàng này đã tổng tại");
                alert.showAndWait();
                phieuNhap_Table.getSelectionModel().select(event.getTablePosition().getRow(), tenHang_Column);
                Platform.runLater(() -> phieuNhap_Table.edit(event.getTablePosition().getRow(), tenHang_Column));
            }
        });
        SortedSet<String> entries = new TreeSet<>();
        for (String a : list2) {
            entries.add(a.toString());
        }
        tenHang_Column.setCellFactory(param -> {
            return new ObjectTableCell<AutoCompleteTextField<String>, ctPhieu_Nhap_Xuat_Module, String>(new AutoCompleteTextField<>()) {
                @SuppressWarnings("unchecked")
                @Override
                protected void onEditStart(AutoCompleteTextField<String> component) {
                    component.setEntries(entries);
                    component.getEntryMenu().setOnAction(e -> {
                        ((MenuItem) e.getTarget()).addEventHandler(Event.ANY, event -> {
                            var lastSelected = component.getSelectedObject();
                            component.setText(lastSelected.toString());
                            setItem(lastSelected);
                            commitEdit(lastSelected);
                        });
                    });
                    component.setSelectedObject(getItem());
                    var obj = component.getSelectedObject();
                    component.setText(obj == null ? null : obj.toString());
                    component.requestFocus();
                    component.selectAll();

                }
                @Override
                protected void onEditCommit() {

                }
            };
        });
        donViTinh_Column.setOnEditCommit(event-> {
            event.getRowValue().setDonViTinh(event.getNewValue());
            phieuNhap_Table.getSelectionModel().select(event.getTablePosition().getRow(), soLuong_Column);
            Platform.runLater(() -> phieuNhap_Table.edit(event.getTablePosition().getRow(), soLuong_Column));
        });
        soLuong_Column.setOnEditCommit(event-> {
            event.getRowValue().setSoLuong(event.getNewValue());
            phieuNhap_Table.getSelectionModel().select(event.getTablePosition().getRow(), gia_Column);
            Platform.runLater(() -> phieuNhap_Table.edit(event.getTablePosition().getRow(), gia_Column));
        });
        gia_Column.setOnEditCommit(event-> {
            event.getRowValue().setGiaXuat(event.getNewValue());
            phieuNhap_Table.getSelectionModel().select(event.getTablePosition().getRow(), ghiChu);
            Platform.runLater(() -> phieuNhap_Table.edit(event.getTablePosition().getRow(), ghiChu));
        });
        ghiChu.setOnEditCommit(event-> {
            index = phieuNhap_Table.getSelectionModel().getSelectedIndex();
            ctPhieu_Nhap_Xuat_Module ctPhieuNhapXuatModule = event.getRowValue();
            ctPhieuNhapXuatModule.setGhiChu(event.getNewValue());
            if (index == maxRow) {
                data.add(new ctPhieu_Nhap_Xuat_Module(" "));
                maxRow++;
            }
            calculate();
            phieuNhap_Table.getSelectionModel().select(maxRow, tenHang_Column);
            Platform.runLater(() -> phieuNhap_Table.edit(maxRow, tenHang_Column));
        });
        stt_Column.setSortable(false);
        stt_Column.setCellValueFactory(column-> new ReadOnlyObjectWrapper<Integer>(phieuNhap_Table.getItems().indexOf(column.getValue())+1));
        thanhToanNgay_Label.addEventHandler(MouseEvent.MOUSE_CLICKED, even->{
            thanhToanNgay_TextField.setText(String.valueOf(ttSau_VAT));
        });

        luuPhieu_Button.setOnAction(event -> {
            try {
                preparedStatement = conn.prepareStatement("phieuXuat_add ?,?,?,?,?,?,?");
                preparedStatement.setString(1, ngayPS_Field.getValue().toString());
                preparedStatement.setString(2, tenNhaCungCap_TextField.getText());
                preparedStatement.setString(3, soTienCanTT_TextField.getText());
                preparedStatement.setString(4, ghiChu_TextArea.getText());
                preparedStatement.setString(5, String.valueOf(VAT));
                preparedStatement.setInt(6, maPhieu);
                preparedStatement.setInt(7,id_KH);
                preparedStatement.execute();
                conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            conn = DBConnection.ConnectionDB();
            data.remove(maxRow);
            for (ctPhieu_Nhap_Xuat_Module ctPhieuNhapXuatModule:data) {
                try {
                    preparedStatement = conn.prepareStatement("ctPhieuXuat_add ?,?,?,?,?");
                    preparedStatement.setInt(1,maPhieu);
                    preparedStatement.setInt(2,ctPhieuNhapXuatModule.getId());
                    preparedStatement.setString(3,String.valueOf(ctPhieuNhapXuatModule.getSoLuong()));
                    preparedStatement.setString(4,String.valueOf(ctPhieuNhapXuatModule.getGiaXuat()));
                    preparedStatement.setString(5,String.valueOf(ctPhieuNhapXuatModule.getGhiChu()));

                    preparedStatement.execute();
                } catch (SQLException e){
                    throw new RuntimeException(e);
                }
            }
            luuPhieu_Button.setDisable(true);
            huy_Button.setDisable(false);
            suaPhieu_Button.setDisable(false);
            taoPhieu_Button.setVisible(false);
            taoPhieu_Button1.setVisible(true);
            taoPhieu_Button1.setDisable(false);
            tenNhaCungCap_TextField.setDisable(true);
            phieuNhap_Table.setDisable(true);
            tongTienTT_TextField.setDisable(true);
            VAT_ComboBox.setDisable(true);
            thanhToanNgay_TextField.setDisable(true);
            ghiChu_TextArea.setDisable(true);
            tongTienSauVAT_TextField.setDisable(true);
            soTienCanTT_TextField.setDisable(true);
            tongTienVAT_TextField.setDisable(true);
            themNCC_Button.setDisable(true);
        });
        huy_Button.setOnAction(event -> {
            try {
                preparedStatement = conn.prepareStatement("phieuXuat_delete ?");
                preparedStatement.setInt(1,maPhieu);
                preparedStatement.execute();
                reload();
            } catch (SQLException e){
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        suaPhieu_Button.setOnAction(event -> {
            for (ctPhieu_Nhap_Xuat_Module ctPhieuNhapXuatModule:data) {
                try {
                    preparedStatement = conn.prepareStatement("ctPhieuXuat_add ?,?,?,?,?");
                    preparedStatement.setInt(1,maPhieu);
                    preparedStatement.setInt(2,ctPhieuNhapXuatModule.getId());
                    preparedStatement.setString(3,String.valueOf(ctPhieuNhapXuatModule.getSoLuong()));
                    preparedStatement.setString(4,String.valueOf(ctPhieuNhapXuatModule.getGiaXuat()));
                    preparedStatement.setString(5,String.valueOf(ctPhieuNhapXuatModule.getGhiChu()));

                    preparedStatement.execute();
                } catch (SQLException e){
                    throw new RuntimeException(e);
                }
            }
            data.add(new ctPhieu_Nhap_Xuat_Module(" "));
            disable();
        });
        thoat_Button.setOnAction(event -> {
            phieuNhap_Pane.setVisible(false);
        });
    }

}
