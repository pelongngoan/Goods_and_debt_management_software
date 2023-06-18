package com.example.project_i.KhachHang;

public class KhachHang_Module {
    public Integer maKH;
    public String tenKh;
    public String diaChi;
    public String sdt;
    public String maSoThue;
    public String diaChiMail;

    public KhachHang_Module() {
    }

    public KhachHang_Module(Integer maKH, String tenKh, String diaChi, String sdt, String maSoThue, String diaChiMail) {
        this.maKH = maKH;
        this.tenKh = tenKh;
        this.diaChi = diaChi;
        this.sdt = sdt;
        this.maSoThue = maSoThue;
        this.diaChiMail = diaChiMail;
    }

    public KhachHang_Module(Integer maKH, String tenKh) {
        this.maKH = maKH;
        this.tenKh = tenKh;
    }

    public Integer getMaKH() {
        return maKH;
    }

    public void setMaKH(Integer maKH) {
        this.maKH = maKH;
    }

    public String getTenKh() {
        return tenKh;
    }

    public void setTenKh(String tenKh) {
        this.tenKh = tenKh;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getMaSoThue() {
        return maSoThue;
    }

    public void setMaSoThue(String maSoThue) {
        this.maSoThue = maSoThue;
    }

    public String getDiaChiMail() {
        return diaChiMail;
    }

    public void setDiaChiMail(String diaChiMail) {
        this.diaChiMail = diaChiMail;
    }
}
