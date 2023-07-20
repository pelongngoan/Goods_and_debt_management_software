package com.example.project_i.Phieu_Nhap_Xuat;

import java.time.LocalDate;

public class phieu_Nhap_Xuat_Module{
    public Integer maPhieu;
    public LocalDate ngayPS;
    public String maKH;
    public String loai;

    public String getLoai() {
        return loai;
    }

    public void setLoai(String loai) {
        this.loai = loai;
    }

    public phieu_Nhap_Xuat_Module() {
    }

    public phieu_Nhap_Xuat_Module(String loai, LocalDate ngayPS, String maKH) {
        this.loai = loai;
        this.ngayPS = ngayPS;
        this.maKH = maKH;
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

    public String getMaKH() {
        return maKH;
    }

    public void setMaKH(String maKH) {
        this.maKH = maKH;
    }
}
