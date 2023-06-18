package com.example.project_i.Phieu_Nhap_Xuat;

import com.example.project_i.HangHoa.HangHoa_Module;
import javafx.scene.control.TextField;
import org.controlsfx.control.textfield.TextFields;

public class ctPhieu_Nhap_Xuat_Module extends HangHoa_Module {
    private Integer stt;
    private Double phamTram_CK_TungSP;
    private Double soTien_CK_TungSP;
    private Double soLuong;
    private Double thanhTien;
    private String ghiChu;

    public ctPhieu_Nhap_Xuat_Module() {
    }

    public ctPhieu_Nhap_Xuat_Module(Integer stt, Integer id, Integer maHang, String tenHang, String donViTinh, Double soLuong, Double giaNhap, Double thanhTien, Double phamTram_CK_TungSP, Double soTien_CK_TungSP, String ghiChu) {
        super(id,maHang,tenHang,donViTinh,giaNhap);
        this.stt = stt;
        this.soLuong = soLuong;
        this.thanhTien = thanhTien;
        this.phamTram_CK_TungSP = phamTram_CK_TungSP;
        this.soTien_CK_TungSP =soTien_CK_TungSP;
        this.ghiChu = ghiChu;
    }

    public ctPhieu_Nhap_Xuat_Module( String tenHang) {
        super(tenHang);
    }

    public Double getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(Double soLuong) {
        this.soLuong = soLuong;
    }

    public Double getThanhTien() {
        return thanhTien;
    }

    public void setThanhTien(Double thanhTien) {
        this.thanhTien = thanhTien;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public Integer getStt() {
        return stt;
    }

    public void setStt(Integer stt) {
        this.stt = stt;
    }

    public Double getPhamTram_CK_TungSP() {
        return phamTram_CK_TungSP;
    }

    public void setPhamTram_CK_TungSP(Double phamTram_CK_TungSP) {
        this.phamTram_CK_TungSP = phamTram_CK_TungSP;
    }

    public Double getSoTien_CK_TungSP() {
        return soTien_CK_TungSP;
    }

    public void setSoTien_CK_TungSP(Double soTien_CK_TungSP) {
        this.soTien_CK_TungSP = soTien_CK_TungSP;
    }
}
