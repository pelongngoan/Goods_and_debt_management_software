package com.example.project_i.Phieu_Nhap_Xuat;
import com.example.project_i.HangHoa.HangHoa_Module;
import com.example.project_i.KetNoi_Database.DBConnection;
import com.example.project_i.KhachHang.KhachHang_Module;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.converter.DefaultStringConverter;
import javafx.util.converter.DoubleStringConverter;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;

import javax.swing.*;
import javax.swing.plaf.TableUI;
import javax.swing.table.DefaultTableModel;
import java.net.URL;
import java.sql.*;
import java.sql.Date;
import java.util.*;
public class phieuNhap_Controller implements Initializable {
    @FXML
    private Button taoPhieu_Button, luuPhieu_Button;
    @FXML
    private TextField tenNhaCungCap_TextField,soPhieu_TextField,
            diaChi_TextField,sdt_TextField, tongTienSauCK_TextField,
            tongTienSauVAT_TextField, soTienCanTT_TextField,VAT_TextField
            , thanhToanNgay_TextField, tongTienVAT_TextField;
    @FXML
    private DatePicker ngayPS_Field;
                            /*Bảng Phiếu Nhập*/
    @FXML
    private TableView<ctPhieu_Nhap_Xuat_Module> phieuNhap_Table;
    @FXML
    private TableColumn<ctPhieu_Nhap_Xuat_Module, Integer> stt_Column;
    @FXML
    private TableColumn<ctPhieu_Nhap_Xuat_Module, Integer> maHang_Column;
    @FXML
    private TableColumn<ctPhieu_Nhap_Xuat_Module, Integer> id_Column;
    @FXML
    private TableColumn<ctPhieu_Nhap_Xuat_Module, String> tenHang_Column ;
    @FXML
    private TableColumn<ctPhieu_Nhap_Xuat_Module, String> donViTinh_Column;
    @FXML
    private TableColumn<ctPhieu_Nhap_Xuat_Module, Double> soLuong_Column;
    @FXML
    private TableColumn<ctPhieu_Nhap_Xuat_Module, Double> giaNhap_Column;
    @FXML
    private TableColumn<ctPhieu_Nhap_Xuat_Module, Double> thanhTien_Column;
    @FXML
    private TableColumn<ctPhieu_Nhap_Xuat_Module, Double> phanTram_CK_TungSP;
    @FXML
    private TableColumn<ctPhieu_Nhap_Xuat_Module, Double> soTien_CK_TungSP;
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


    public Connection conn = null ;
    public PreparedStatement preparedStatement = null;
    public ResultSet resultSet = null;
    ObservableList<ctPhieu_Nhap_Xuat_Module> phieuNhap_Data;

    int maPhieu;
    public void setTaoPhieu_Button(){
        taoPhieu_Button.setDisable(true);
        soPhieu_TextField.setDisable(false);
        ngayPS_Field.setDisable(false);
        tenNhaCungCap_TextField.setDisable(false);
        phieuNhap_Table.setDisable(false);
        luuPhieu_Button.setDisable(false);
        ngayPS_Field.setValue(java.time.LocalDate.now());
        try {
            preparedStatement = conn.prepareStatement("SELECT MAX(SOPH+1) FROM PHNHAP");
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                soPhieu_TextField.setText("PHNHAP_"+resultSet.getString(1));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    int index = -1;
    int index1 = -1;
    public String curentID;
    @FXML
    void getSelected(MouseEvent event1){
        index = phieuNhap_Table.getSelectionModel().getSelectedIndex();
        if (index <= -1) {
            return ;
        }

    }


    ObservableList<KhachHang_Module> khachHang_Data = FXCollections.observableArrayList();
    @FXML
    void setTenNhaCungCap_TextField(KeyEvent event) {
        searchID_Column.setCellValueFactory(new PropertyValueFactory<>("maKH"));
        searchName_Column.setCellValueFactory(new PropertyValueFactory<>("tenKh"));
        searchDiaChi_Column.setCellValueFactory(new PropertyValueFactory<>("diaChi"));
        searchSDT_Column.setCellValueFactory(new PropertyValueFactory<>("sdt"));
        khachHang_Data = DBConnection.getKH_Data();
        search_Table.setItems(khachHang_Data);
        FilteredList<KhachHang_Module> filteredData = new FilteredList<>(khachHang_Data, b -> true);
        tenNhaCungCap_TextField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(needed -> {
                if (newValue == null || newValue.isEmpty()) {
                    search_Table.setVisible(false);
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (needed.getTenKh().toLowerCase().indexOf(lowerCaseFilter) != -1 ) {
                    search_Table.setVisible(true);
                    return true; // Filter matches name
                } else {
                    search_Table.setVisible(true);
                    return false;
                } // Does not match.
            });
        });
        SortedList<KhachHang_Module> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(search_Table.comparatorProperty());
        search_Table.setItems(sortedData);
    }

    @FXML
    private AnchorPane phieuNhap_Pane;
    public void setPhieuNhap_Pane(KeyEvent event){
        if (event.getCode() == KeyCode.F5 ){
            setTaoPhieu_Button();
        }
    }

    int stt = 0;
    double tt_CK = 0;
    double tt_TT = 0;
    double tt_VAT = 0;
    double ttSau_VAT = 0 ;
    double ttSau_CK = 0;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        conn = DBConnection.ConnectionDB();
        phieuNhap_Data = FXCollections.observableArrayList();
        stt_Column.setCellValueFactory(new PropertyValueFactory<ctPhieu_Nhap_Xuat_Module,Integer>("stt"));
        id_Column.setCellValueFactory(new PropertyValueFactory<ctPhieu_Nhap_Xuat_Module,Integer>("id"));
        maHang_Column.setCellValueFactory(new PropertyValueFactory<ctPhieu_Nhap_Xuat_Module,Integer>("maHang"));
        tenHang_Column.setCellValueFactory(new PropertyValueFactory<ctPhieu_Nhap_Xuat_Module,String>("tenHang"));
        donViTinh_Column.setCellValueFactory(new PropertyValueFactory<ctPhieu_Nhap_Xuat_Module,String>("donViTinh"));
        soLuong_Column.setCellValueFactory(new PropertyValueFactory<ctPhieu_Nhap_Xuat_Module,Double>("soLuong"));
        giaNhap_Column.setCellValueFactory(new PropertyValueFactory<ctPhieu_Nhap_Xuat_Module,Double>("giaNhap"));
        thanhTien_Column.setCellValueFactory(new PropertyValueFactory<ctPhieu_Nhap_Xuat_Module,Double>("thanhTien"));
        phanTram_CK_TungSP.setCellValueFactory(new PropertyValueFactory<ctPhieu_Nhap_Xuat_Module,Double>("phamTram_CK_TungSP"));
        soTien_CK_TungSP.setCellValueFactory(new PropertyValueFactory<ctPhieu_Nhap_Xuat_Module,Double>("soTien_CK_TungSP"));
        ghiChu.setCellValueFactory(new PropertyValueFactory<ctPhieu_Nhap_Xuat_Module,String>("ghiChu"));
        phieuNhap_Data.add(new ctPhieu_Nhap_Xuat_Module(" "));

        phieuNhap_Table.setItems(phieuNhap_Data);
        phieuNhap_Table.setEditable(true);
        List<String> list2 = new ArrayList<>();

        try {
            preparedStatement = conn.prepareStatement("select * from danhMucHH");
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String text= resultSet.getString(2);
                list2.add(new String(String.valueOf(resultSet.getInt(1))+". "+resultSet.getString(2)+" - "+resultSet.getString(3)));
            }
        }catch (SQLException e){

        }

        donViTinh_Column.setCellFactory(TextFieldTableCell.forTableColumn());
        soLuong_Column.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        giaNhap_Column.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        //thanhTien_Column.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        phanTram_CK_TungSP.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        ghiChu.setCellFactory(TextFieldTableCell.forTableColumn());
        phieuNhap_Table.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        phieuNhap_Table.getSelectionModel().setCellSelectionEnabled(true);
        tenHang_Column.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<ctPhieu_Nhap_Xuat_Module, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<ctPhieu_Nhap_Xuat_Module, String> event) {
                ctPhieu_Nhap_Xuat_Module ctPhieuNhapXuatModule = event.getRowValue();
                String[] part = event.getNewValue().split(". ");
                try {
                    preparedStatement = conn.prepareStatement("danhMucHH_get_one ?");
                    preparedStatement.setString(1, part[0]);
                    resultSet = preparedStatement.executeQuery();
                    while (resultSet.next()) {

                        stt++;
                        ctPhieuNhapXuatModule.setStt(stt);
                        ctPhieuNhapXuatModule.setId(resultSet.getInt(1));
                        ctPhieuNhapXuatModule.setMaHang(resultSet.getInt(2));
                        ctPhieuNhapXuatModule.setTenHang(resultSet.getInt(2)+" - "+resultSet.getString(3));
                        ctPhieuNhapXuatModule.setDonViTinh(resultSet.getString(4));
                        ctPhieuNhapXuatModule.setGiaNhap(resultSet.getDouble(5));
                        ctPhieuNhapXuatModule.setSoLuong(1.0);
                        ctPhieuNhapXuatModule.setThanhTien(resultSet.getDouble(5));
                        ctPhieuNhapXuatModule.setPhamTram_CK_TungSP(0.0);
                        ctPhieuNhapXuatModule.setThanhTien(0.0);
                        phieuNhap_Table.refresh();
                        phieuNhap_Table.setItems(phieuNhap_Data);
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                phieuNhap_Table.getSelectionModel().select(event.getTablePosition().getRow(),donViTinh_Column);
                Platform.runLater(()->phieuNhap_Table.edit(event.getTablePosition().getRow(), donViTinh_Column));

            }
        });
        SortedSet<String> entries = new TreeSet<>();
        for (String a:list2){
            entries.add(a.toString());
        }
        tenHang_Column.setCellFactory(param->{
            return new ObjectTableCell<AutoCompleteTextField<String>, ctPhieu_Nhap_Xuat_Module, String>(new AutoCompleteTextField<>()) {
                @SuppressWarnings("unchecked")
                @Override
                protected void onEditStart(AutoCompleteTextField<String> component) {

                    component.setEntries(entries);
                    component.getEntryMenu().setOnAction(e -> {
                        ((MenuItem) e.getTarget()).addEventHandler(Event.ANY, event -> {
                            var lastSelected = component.getSelectedObject() ;
                                    component.setText(lastSelected.toString());
                                    setItem(lastSelected);
                                    commitEdit(lastSelected);

                            });
                        });
                    component.setSelectedObject(getItem());
                    var obj = component.getSelectedObject();
                    component.setText(obj==null? null:obj.toString());
                    component.requestFocus();
                    component.selectAll();

                }
                @Override
                protected void onEditCommit() {

                }
            };
        });
        donViTinh_Column.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<ctPhieu_Nhap_Xuat_Module, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<ctPhieu_Nhap_Xuat_Module, String> event) {
                event.getRowValue().setDonViTinh(event.getNewValue());
                phieuNhap_Table.getSelectionModel().select(event.getTablePosition().getRow(),soLuong_Column);
                Platform.runLater(()->phieuNhap_Table.edit(event.getTablePosition().getRow(), soLuong_Column));
            }
        });
        soLuong_Column.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<ctPhieu_Nhap_Xuat_Module, Double>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<ctPhieu_Nhap_Xuat_Module, Double> event) {
                event.getRowValue().setSoLuong(event.getNewValue());
                event.getRowValue().setThanhTien(event.getRowValue().getSoLuong()*event.getRowValue().getGiaNhap());
                phieuNhap_Table.getSelectionModel().select(event.getTablePosition().getRow(),giaNhap_Column);
                Platform.runLater(() -> phieuNhap_Table.edit(event.getTablePosition().getRow(), giaNhap_Column));
                }

        });
        giaNhap_Column.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<ctPhieu_Nhap_Xuat_Module, Double>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<ctPhieu_Nhap_Xuat_Module, Double> event) {
                event.getRowValue().setGiaNhap(event.getNewValue());
                event.getRowValue().setThanhTien(event.getRowValue().getSoLuong()*event.getRowValue().getGiaNhap());
                phieuNhap_Table.getSelectionModel().select(event.getTablePosition().getRow(),thanhTien_Column);
                Platform.runLater(() -> phieuNhap_Table.edit(event.getTablePosition().getRow(), thanhTien_Column));
            }
        });
        thanhTien_Column.setOnEditStart(new EventHandler<TableColumn.CellEditEvent<ctPhieu_Nhap_Xuat_Module, Double>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<ctPhieu_Nhap_Xuat_Module, Double> event) {
                int row = event.getTablePosition().getRow();
                phieuNhap_Table.getSelectionModel().select(row,phanTram_CK_TungSP);
                Platform.runLater(() -> phieuNhap_Table.edit(row, phanTram_CK_TungSP));

            }
        });
        phanTram_CK_TungSP.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<ctPhieu_Nhap_Xuat_Module, Double>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<ctPhieu_Nhap_Xuat_Module, Double> event) {
                ctPhieu_Nhap_Xuat_Module ctPhieuNhapXuatModule = event.getRowValue();
                int row = event.getTablePosition().getRow();
                ctPhieuNhapXuatModule.setPhamTram_CK_TungSP(event.getNewValue());
                ctPhieuNhapXuatModule.setSoTien_CK_TungSP(event.getRowValue().getPhamTram_CK_TungSP()*event.getRowValue().getThanhTien()/100.0);
                phieuNhap_Table.getSelectionModel().select(row,soTien_CK_TungSP);
                Platform.runLater(() -> phieuNhap_Table.edit(row, soTien_CK_TungSP));
            }
        });
        soTien_CK_TungSP.setOnEditStart(new EventHandler<TableColumn.CellEditEvent<ctPhieu_Nhap_Xuat_Module, Double>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<ctPhieu_Nhap_Xuat_Module, Double> event) {
                phieuNhap_Table.getSelectionModel().select(event.getTablePosition().getRow(),ghiChu);
                Platform.runLater(() -> phieuNhap_Table.edit(event.getTablePosition().getRow(), ghiChu));
            }
        });
        ghiChu.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<ctPhieu_Nhap_Xuat_Module, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<ctPhieu_Nhap_Xuat_Module, String> event) {
                ctPhieu_Nhap_Xuat_Module ctPhieuNhapXuatModule = event.getRowValue();
                int row = event.getTablePosition().getRow();
                tt_TT=tt_TT+ctPhieuNhapXuatModule.getThanhTien();
                tt_CK = tt_CK + ctPhieuNhapXuatModule.getSoTien_CK_TungSP();
                ttSau_CK=tt_TT-tt_CK;
                //tt_VAT = tt_VAT +ttSau_CK*(1-Double.valueOf(VAT_TextField.getText()));
                phieuNhap_Table.getSelectionModel().select(row+1);
                ctPhieuNhapXuatModule.setGhiChu(event.getNewValue());
                tongTienSauCK_TextField.setText(String.valueOf(tt_TT-tt_CK));
                //tongTienSauVAT_TextField.setText();
                phieuNhap_Data.add(new ctPhieu_Nhap_Xuat_Module(" "));
                Platform.runLater(()->phieuNhap_Table.edit(row+1, tenHang_Column));

                //tongTienVAT_TextField.setText(String.valueOf((tt_TT-tt_CK)*(1-Double.valueOf(VAT_TextField.getPromptText()))));
            }
        });
    }

}
