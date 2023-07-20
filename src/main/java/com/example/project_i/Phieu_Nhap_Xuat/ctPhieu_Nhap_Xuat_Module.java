package com.example.project_i.Phieu_Nhap_Xuat;

import com.example.project_i.HangHoa.HangHoa_Module;
import javafx.beans.property.FloatProperty;
import javafx.beans.property.ReadOnlyFloatWrapper;
import javafx.beans.property.SimpleFloatProperty;

public class ctPhieu_Nhap_Xuat_Module extends HangHoa_Module {
    private FloatProperty soLuong = new SimpleFloatProperty();
    private ReadOnlyFloatWrapper thanhTien= new ReadOnlyFloatWrapper();
    private String ghiChu;

    public ctPhieu_Nhap_Xuat_Module(String tenHang, float soLuong, float giaNhap) {
        super(tenHang, giaNhap);
        setSoLuong(soLuong);
        this.thanhTien.bind(this.soLuong.multiply(this.giaNhapProperty()).add(this.soLuong.multiply(this.giaXuatProperty())).add(this.soLuong.multiply(this.giaBanLeProperty())));
    }

    public ctPhieu_Nhap_Xuat_Module() {
    }

    public ctPhieu_Nhap_Xuat_Module(Integer id, String tenHang, String donViTinh, Float soLuong, Float giaNhap, Float giaXuat,Float giaBanLe,  String ghiChu) {
        super(id,tenHang,donViTinh,giaNhap,giaXuat,giaBanLe);
        setSoLuong(soLuong);
        this.thanhTien.bind(this.soLuong.multiply(this.giaNhapProperty()).add(this.soLuong.multiply(this.giaXuatProperty())).add(this.soLuong.multiply(this.giaBanLeProperty())));
        this.ghiChu = ghiChu;
    }


    public ctPhieu_Nhap_Xuat_Module( String tenHang) {
        super(tenHang);
        this.thanhTien.bind(this.soLuong.multiply(this.giaNhapProperty()).add(this.soLuong.multiply(this.giaXuatProperty())).add(this.soLuong.multiply(this.giaBanLeProperty())));

    }

    public float getSoLuong() {
        return soLuong.get();
    }

    public FloatProperty soLuongProperty() {
        return soLuong;
    }

    public void setSoLuong(float soLuong) {
        this.soLuong.set(soLuong);
    }

    public float getThanhTien() {
        return thanhTien.get();
    }

    public ReadOnlyFloatWrapper thanhTienProperty() {
        return thanhTien;
    }

    public void setThanhTien(float thanhTien) {
        this.thanhTien.set(thanhTien);
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }


}
