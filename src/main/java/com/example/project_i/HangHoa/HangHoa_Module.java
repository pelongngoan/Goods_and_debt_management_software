package com.example.project_i.HangHoa;

import javafx.beans.property.*;

import java.time.LocalDate;

public class HangHoa_Module {
    private IntegerProperty id = new SimpleIntegerProperty();
    private StringProperty tenHang = new SimpleStringProperty();
    private StringProperty donViTinh = new SimpleStringProperty();
    private StringProperty phanLoai= new SimpleStringProperty();
    private FloatProperty giaNhap = new SimpleFloatProperty();
    private FloatProperty giaXuat= new SimpleFloatProperty();
    private FloatProperty giaBanLe= new SimpleFloatProperty();
    private FloatProperty tonKho = new SimpleFloatProperty();

    public HangHoa_Module() {
    }

    public HangHoa_Module(Integer id, String tenHang, String donViTinh, String phanLoai, Float giaNhap, Float giaXuat, Float giaBanLe,Float tonKho) {
        setId(id);
        setTenHang(tenHang);
        setDonViTinh(donViTinh);
        setPhanLoai(phanLoai);
        setGiaNhap(giaNhap);
        setGiaXuat(giaXuat);
        setGiaBanLe(giaBanLe);
        setTonKho(tonKho);
    }

    public HangHoa_Module(String tenHang, String donViTinh, Float giaNhap) {
        setTenHang(tenHang);
        setDonViTinh(donViTinh);
        setGiaNhap(giaNhap);
    }

    public HangHoa_Module(String tenHang) {
        setTenHang(tenHang);
    }

    public HangHoa_Module(String tenHang, float giaNhap) {
        setTenHang(tenHang);
        setGiaNhap(giaNhap);
    }

    public HangHoa_Module(Integer id, String tenHang, String donViTinh, Float giaNhap,Float giaXuat, Float giaBanLe) {
        setId(id);
        setTenHang(tenHang);
        setDonViTinh(donViTinh);
        setGiaNhap(giaNhap);
        setGiaXuat(giaXuat);
        setGiaBanLe(giaBanLe);
    }



    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getTenHang() {
        return tenHang.get();
    }

    public StringProperty tenHangProperty() {
        return tenHang;
    }

    public void setTenHang(String tenHang) {
        this.tenHang.set(tenHang);
    }

    public String getDonViTinh() {
        return donViTinh.get();
    }

    public StringProperty donViTinhProperty() {
        return donViTinh;
    }

    public void setDonViTinh(String donViTinh) {
        this.donViTinh.set(donViTinh);
    }

    public String getPhanLoai() {
        return phanLoai.get();
    }

    public StringProperty phanLoaiProperty() {
        return phanLoai;
    }

    public void setPhanLoai(String phanLoai) {
        this.phanLoai.set(phanLoai);
    }

    public float getGiaNhap() {
        return giaNhap.get();
    }

    public FloatProperty giaNhapProperty() {
        return giaNhap;
    }

    public void setGiaNhap(float giaNhap) {
        this.giaNhap.set(giaNhap);
    }

    public float getGiaXuat() {
        return giaXuat.get();
    }

    public FloatProperty giaXuatProperty() {
        return giaXuat;
    }

    public void setGiaXuat(float giaXuat) {
        this.giaXuat.set(giaXuat);
    }

    public float getGiaBanLe() {
        return giaBanLe.get();
    }

    public FloatProperty giaBanLeProperty() {
        return giaBanLe;
    }

    public void setGiaBanLe(float giaBanLe) {
        this.giaBanLe.set(giaBanLe);
    }

    public float getTonKho() {
        return tonKho.get();
    }

    public FloatProperty tonKhoProperty() {
        return tonKho;
    }

    public void setTonKho(float tonKho) {
        this.tonKho.set(tonKho);
    }
}
