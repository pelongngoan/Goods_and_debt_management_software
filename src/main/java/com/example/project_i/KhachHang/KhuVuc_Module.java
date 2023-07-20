package com.example.project_i.KhachHang;

public class KhuVuc_Module {
    public Integer idKV;
    public String tenKV;

    public KhuVuc_Module() {
    }

    public KhuVuc_Module(Integer idKV, String tenKV) {
        this.idKV = idKV;
        this.tenKV = tenKV;
    }

    public Integer getIdKV() {
        return idKV;
    }

    public void setIdKV(Integer idKV) {
        this.idKV = idKV;
    }

    public String getTenKV() {
        return tenKV;
    }

    public void setTenKV(String tenKV) {
        this.tenKV = tenKV;
    }
}
