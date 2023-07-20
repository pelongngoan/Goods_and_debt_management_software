package com.example.project_i.KhachHang;

import org.w3c.dom.Node;

public class KhachHang_Module {
    private Integer maKH;
    private String tenKH;
    private String khuVuc;
    private String diaChi;
    private String nguoiDaiDien;
    private String sdt;
    private String mst;
    private String tk;
    private String nganHang;
    private Double noDau;

    public KhachHang_Module() {
    }

    public KhachHang_Module(Integer maKH, String tenKH, String khuVuc, String diaChi, String nguoiDaiDien, String sdt, String mst, String tk, String nganHang, Double noDau) {
        this.maKH = maKH;
        this.tenKH = tenKH;
        this.khuVuc = khuVuc;
        this.diaChi = diaChi;
        this.nguoiDaiDien = nguoiDaiDien;
        this.sdt = sdt;
        this.mst = mst;
        this.tk = tk;
        this.nganHang = nganHang;
        this.noDau = noDau;
    }

    public Integer getMaKH() {
        return maKH;
    }

    public void setMaKH(Integer maKH) {
        this.maKH = maKH;
    }

    public String getTenKH() {
        return tenKH;
    }

    public void setTenKH(String tenKH) {
        this.tenKH = tenKH;
    }

    public String getKhuVuc() {
        return khuVuc;
    }

    public void setKhuVuc(String khuVuc) {
        this.khuVuc = khuVuc;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getNguoiDaiDien() {
        return nguoiDaiDien;
    }

    public void setNguoiDaiDien(String nguoiDaiDien) {
        this.nguoiDaiDien = nguoiDaiDien;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getMst() {
        return mst;
    }

    public void setMst(String mst) {
        this.mst = mst;
    }

    public String getTk() {
        return tk;
    }

    public void setTk(String tk) {
        this.tk = tk;
    }

    public String getNganHang() {
        return nganHang;
    }

    public void setNganHang(String nganHang) {
        this.nganHang = nganHang;
    }

    public Double getNoDau() {
        return noDau;
    }

    public void setNoDau(Double noDau) {
        this.noDau = noDau;
    }
}
