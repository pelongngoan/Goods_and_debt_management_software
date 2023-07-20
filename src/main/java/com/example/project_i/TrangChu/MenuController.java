package com.example.project_i.TrangChu;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MenuController implements Initializable{

    @FXML
    private Menu baoCaoTongHop_Menu;

    @FXML
    private Menu danhMucQuanLy_Menu;

    @FXML
    public BorderPane mainPane;

    @FXML
    private MenuBar menuBar;

    @FXML
    private MenuItem nhapHangNCC_MenuItem;

    @FXML
    private Menu quanLyChungTu_Menu,troGiup_Menu;

    @FXML
    private MenuItem quanLyHH_MenuItem;

    @FXML
    private MenuItem quanLyKH_MenuItem;
    @FXML
    private MenuItem xnt;

    @FXML
    private MenuItem quanLyKV_MenuItem;

    @FXML
    private MenuItem quanLyNCC_MenuItem;

    @FXML
    private MenuItem quanLyNDTC_MenuItem;

    @FXML
    private MenuItem quanLyNhomSP_MenuItem;

    @FXML
    private Menu timKiem_Menu;

    @FXML
    private MenuItem tongHopCN_MenuItem;

    @FXML
    private MenuItem vietPC_MenuItem,timKiemTC_MenuItem,timKiemNX_MenuItem;

    @FXML
    private MenuItem vietPT_MenuItem;

    @FXML
    private MenuItem xuatHangBB_MenuItem;

    @FXML
    private MenuItem xuatHangBL_MenuItem;

    public void switchForm(ActionEvent e) throws IOException {
        URL url = null;
        if (e.getSource()==quanLyKV_MenuItem) {
            url = new File("src/main/resources/com/example/project_i/khuVuc_Form.fxml").toURI().toURL();
        } else if (e.getSource()==quanLyNCC_MenuItem) {
            url = new File("src/main/resources/com/example/project_i/KhachHang/nhaCungCap.fxml").toURI().toURL();
        } else if (e.getSource()==quanLyKH_MenuItem) {
            url = new File("src/main/resources/com/example/project_i/KhachHang/quanLyKH_Form.fxml").toURI().toURL();
        } else if (e.getSource()==quanLyHH_MenuItem) {
            url = new File("src/main/resources/com/example/project_i/HangHoa/quanLyHH_Form.fxml").toURI().toURL();
        } else if (e.getSource()==quanLyNDTC_MenuItem) {
            url = new File("src/main/resources/com/example/project_i/NoiDungThuChi/quanLyThuChi_Form.fxml").toURI().toURL();
        } else if (e.getSource()==xuatHangBB_MenuItem) {
            url = new File("src/main/resources/com/example/project_i/PhieuXuat/phieuXuat_Form.fxml").toURI().toURL();
        } else if (e.getSource()==xuatHangBL_MenuItem) {
            url = new File("src/main/resources/com/example/project_i/hoaDonBL_Form.fxml").toURI().toURL();
        } else if (e.getSource()==nhapHangNCC_MenuItem) {
            url = new File("src/main/resources/com/example/project_i/Phieu_Nhap_Xuat/phieuNhap_Form.fxml").toURI().toURL();
        } else if (e.getSource()==xnt) {
            url = new File("src/main/resources/com/example/project_i/TrangChu/tongHopXNT.fxml").toURI().toURL();
        } else if (e.getSource()==timKiemTC_MenuItem) {
            url = new File("src/main/resources/com/example/project_i/TrangChu/timKiemThuChi.fxml").toURI().toURL();
        } else if (e.getSource()==timKiemNX_MenuItem) {
            url = new File("src/main/resources/com/example/project_i/TrangChu/timKiemNhapXuat.fxml").toURI().toURL();
        }else if (e.getSource()==tongHopCN_MenuItem) {
            url = new File("src/main/resources/com/example/project_i/TrangChu/tongHopCongNo.fxml").toURI().toURL();
        }else if (e.getSource()==quanLyNhomSP_MenuItem) {
            url = new File("src/main/resources/com/example/project_i/test.fxml").toURI().toURL();
            System.out.println("dsads");
        }

        System.out.println("xxxxx");
        AnchorPane view = FXMLLoader.load(url);
        mainPane.setCenter(view);
        System.out.println("cccc");
        //menuBar.addEventFilter(KeyEvent.ANY, event -> e.consume());

    }
    public void phieuThu() throws IOException {
        Parent addPart = FXMLLoader.load(new File("src/main/resources/com/example/project_i/PhieuThu/phieuThu_Form.fxml").toURI().toURL());
        Scene scene = new Scene(addPart);
        Stage stage = new Stage();
        //Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(mainPane.getScene().getWindow());
        stage.show();
    }
    public void phieuChi() throws IOException {
        Parent addPart = FXMLLoader.load(new File("src/main/resources/com/example/project_i/PhieuChi/phieuChi_Form.fxml").toURI().toURL());
        Scene scene = new Scene(addPart);
        Stage stage = new Stage();
        //Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(mainPane.getScene().getWindow());
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
