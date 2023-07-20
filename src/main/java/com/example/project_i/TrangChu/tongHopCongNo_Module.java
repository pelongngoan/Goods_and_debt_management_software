package com.example.project_i.TrangChu;

import javafx.beans.property.*;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class tongHopCongNo_Module {
    private String ten;
    private FloatProperty noDau=new SimpleFloatProperty();
    private FloatProperty tienChi=new SimpleFloatProperty();
    private FloatProperty tienTra=new SimpleFloatProperty();
    private ReadOnlyFloatWrapper conNo=new ReadOnlyFloatWrapper();

    public tongHopCongNo_Module(String ten, Float noDau, Float tienChi, Float tienTra, Float conNo) {
        setTen(ten);
        setNoDau(noDau);
        setTienChi(tienChi);
        setTienTra(tienTra);
        setConNo(conNo);

    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public float getNoDau() {
        return noDau.get();
    }

    public FloatProperty noDauProperty() {
        return noDau;
    }

    public void setNoDau(float noDau) {
        this.noDau.set(noDau);
    }

    public float getTienChi() {
        return tienChi.get();
    }

    public FloatProperty tienChiProperty() {
        return tienChi;
    }

    public void setTienChi(float tienChi) {
        this.tienChi.set(tienChi);
    }

    public float getTienTra() {
        return tienTra.get();
    }

    public FloatProperty tienTraProperty() {
        return tienTra;
    }

    public void setTienTra(float tienTra) {
        this.tienTra.set(tienTra);
    }

    public float getConNo() {
        return conNo.get();
    }

    public ReadOnlyFloatWrapper conNoProperty() {
        return conNo;
    }

    public void setConNo(float conNo) {
        this.conNo.set(conNo);
    }
}
