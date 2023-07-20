package com.example.project_i.TrangChu;

public class tongHopXNT_Module {
    private String ten;
    private double tonDau;
    private double nhap;
    private double xuat;
    private double banLe;
    private double tongCon;

    public tongHopXNT_Module(String ten, double tonDau, double nhap, double xuat,double banLe, double tongCon) {
        this.ten = ten;
        this.tonDau = tonDau;
        this.nhap = nhap;
        this.xuat = xuat;
        this.banLe = banLe;
        this.tongCon = tongCon;
    }

    public double getBanLe() {
        return banLe;
    }

    public void setBanLe(double banLe) {
        this.banLe = banLe;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public double getTonDau() {
        return tonDau;
    }

    public void setTonDau(double tonDau) {
        this.tonDau = tonDau;
    }

    public double getNhap() {
        return nhap;
    }

    public void setNhap(double nhap) {
        this.nhap = nhap;
    }

    public double getXuat() {
        return xuat;
    }

    public void setXuat(double xuat) {
        this.xuat = xuat;
    }

    public double getTongCon() {
        return tongCon;
    }

    public void setTongCon(double tongCon) {
        this.tongCon = tongCon;
    }
}
