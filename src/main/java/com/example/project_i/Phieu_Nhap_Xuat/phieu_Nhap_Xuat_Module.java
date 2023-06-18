package com.example.project_i.Phieu_Nhap_Xuat;

import com.example.project_i.HangHoa.HangHoa_Module;

import java.time.LocalDate;

public class phieu_Nhap_Xuat_Module extends HangHoa_Module {
    public Integer maPhieu;
    public LocalDate ngayPS;
    public Integer maKH;

    public phieu_Nhap_Xuat_Module() {
    }

    public phieu_Nhap_Xuat_Module(Integer maHang, String tenHang, String donViTinh, Double giaNhap) {
        super(maHang,tenHang,donViTinh,giaNhap);

    }

    public Integer getMaPhieu() {
        return maPhieu;
    }

    public void setMaPhieu(Integer maPhieu) {
        this.maPhieu = maPhieu;
    }

    public LocalDate getNgayPS() {
        return ngayPS;
    }

    public void setNgayPS(LocalDate ngayPS) {
        this.ngayPS = ngayPS;
    }

    public Integer getMaKH() {
        return maKH;
    }

    public void setMaKH(Integer maKH) {
        this.maKH = maKH;
    }
}
