package com.example.project_i.TrangChu;

import java.time.LocalDate;

public class ThuChi_Module {
    private Integer idTC;
    private String loai;
    private String noiDung;
    private LocalDate ngayPS;
    private Float soTien;
    private String nguoiDD;

    public ThuChi_Module(String loai, String noiDung, LocalDate ngayPS, Float soTien, String nguoiDD) {
        this.loai = loai;
        this.noiDung = noiDung;
        this.ngayPS = ngayPS;
        this.soTien = soTien;
        this.nguoiDD = nguoiDD;
    }

    public LocalDate getNgayPS() {
        return ngayPS;
    }

    public void setNgayPS(LocalDate ngayPS) {
        this.ngayPS = ngayPS;
    }

    public Float getSoTien() {
        return soTien;
    }

    public void setSoTien(Float soTien) {
        this.soTien = soTien;
    }

    public String getNguoiDD() {
        return nguoiDD;
    }

    public void setNguoiDD(String nguoiDD) {
        this.nguoiDD = nguoiDD;
    }

    public ThuChi_Module(Integer idTC, String loai, String noiDung) {
        this.idTC = idTC;
        this.loai = loai;
        this.noiDung = noiDung;
    }

    public Integer getIdTC() {
        return idTC;
    }

    public void setIdTC(Integer idTC) {
        this.idTC = idTC;
    }

    public String getLoai() {
        return loai;
    }

    public void setLoai(String loai) {
        this.loai = loai;
    }

    public String getNoiDung() {
        return noiDung;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }
}
