module com.example.project_i {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.microsoft.sqlserver.jdbc;
    requires java.sql;
    requires java.naming;
    requires java.desktop;
    requires org.controlsfx.controls;
    opens com.example.project_i to javafx.fxml;
    exports com.example.project_i;

    exports com.example.project_i.HangHoa;
    opens com.example.project_i.HangHoa to javafx.fxml;
    exports com.example.project_i.KhachHang;
    opens com.example.project_i.KhachHang to javafx.fxml;
    exports com.example.project_i.KetNoi_Database;
    opens com.example.project_i.KetNoi_Database to javafx.fxml;
    exports com.example.project_i.Login;
    opens com.example.project_i.Login to javafx.fxml;
    exports com.example.project_i.HoaDonBanLe;
    opens com.example.project_i.HoaDonBanLe to javafx.fxml;
    exports com.example.project_i.TrangChu;
    opens com.example.project_i.TrangChu to javafx.fxml;
    exports com.example.project_i.PhieuChi;
    opens com.example.project_i.PhieuChi to javafx.fxml;
    exports com.example.project_i.Phieu_Nhap_Xuat;
    opens com.example.project_i.Phieu_Nhap_Xuat to javafx.fxml;
    exports com.example.project_i.PhieuThu;
    opens com.example.project_i.PhieuThu to javafx.fxml;

    opens com.example.project_i.PhieuXuat to javafx.fxml;
}