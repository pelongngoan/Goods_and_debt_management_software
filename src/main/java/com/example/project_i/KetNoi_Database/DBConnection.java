package com.example.project_i.KetNoi_Database;

import com.example.project_i.HangHoa.HangHoa_Module;
import com.example.project_i.KhachHang.KhachHang_Module;
import com.example.project_i.KhachHang.KhuVuc_Module;
import com.example.project_i.KhachHang.NhaCungCap_Module;
import com.example.project_i.TrangChu.ThuChi_Module;
import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
public class DBConnection {
    public static Connection ConnectionDB() {
        SQLServerDataSource ds = new SQLServerDataSource();
        Connection connection = null;
        ds.setUser("sa"); //user
        ds.setPassword("123"); //password
        ds.setServerName("DESKTOP-UB4KCUD\\SQLEXPRESS"); //server name
        ds.setPortNumber(1433); //port tcp/ip
        ds.setDatabaseName("quanLyHhCn"); //db name
        ds.setEncrypt("true");
        ds.setTrustServerCertificate(true);
        try {
            connection = ds.getConnection();
        } catch (SQLServerException e) {
            throw new RuntimeException(e);
        }
        return connection;
    }
    public static ObservableList<KhachHang_Module> getKH_Data(){
        Connection conn = ConnectionDB();
        ObservableList<KhachHang_Module> list = FXCollections.observableArrayList();
        try {
            PreparedStatement ps = conn.prepareStatement("danhMucKH_get_all");
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()){
                list.add(new KhachHang_Module(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4),resultSet.getString(5),resultSet.getString(6),resultSet.getString(7),resultSet.getString(8),resultSet.getString(9),resultSet.getDouble(10)));
            }
        } catch (Exception e) {
        }
        return list;
    }
    public static ObservableList<NhaCungCap_Module> getNCC_Data(){
        Connection conn = ConnectionDB();
        ObservableList<NhaCungCap_Module> list = FXCollections.observableArrayList();
        try {
            PreparedStatement ps = conn.prepareStatement("danhMucNCC_get_all");
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()){
                list.add(new NhaCungCap_Module(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4),resultSet.getString(5),resultSet.getString(6),resultSet.getString(7),resultSet.getString(8),resultSet.getString(9),resultSet.getFloat(10)));
            }
        } catch (Exception e) {
        }
        return list;
    }
    public static ObservableList<HangHoa_Module> getHH_Data(){
        Connection conn = ConnectionDB();
        ObservableList<HangHoa_Module> list = FXCollections.observableArrayList();
        try {
            PreparedStatement ps = conn.prepareStatement("danhMucHH_get_all");
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                list.add(new HangHoa_Module(rs.getInt(1), rs.getString(2),rs.getString(3),rs.getString(4),rs.getFloat(5),rs.getFloat(6),rs.getFloat(7),rs.getFloat(8)));
            }
        } catch (Exception e) {
        }
        return list;
    }
    public static ObservableList<KhuVuc_Module> getKV_Data(){
        Connection conn = ConnectionDB();
        ObservableList<KhuVuc_Module> list = FXCollections.observableArrayList();
        try {
            PreparedStatement ps = conn.prepareStatement("danhMucKV_get_all");
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                list.add(new KhuVuc_Module(rs.getInt(1), rs.getString(2)));
            }
        } catch (Exception e) {
        }

        return list;
    }
    public static ObservableList<ThuChi_Module> getTC_Data(){
        Connection conn = ConnectionDB();
        ObservableList<ThuChi_Module> list = FXCollections.observableArrayList();
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM danhMucThuChi");
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                list.add(new ThuChi_Module(rs.getInt(1), rs.getString(2),rs.getString(3)));
            }
        } catch (Exception e) {
        }

        return list;
    }
}


