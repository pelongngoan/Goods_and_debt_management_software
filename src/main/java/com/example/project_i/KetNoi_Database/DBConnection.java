package com.example.project_i.KetNoi_Database;

import com.example.project_i.HangHoa.HangHoa_Module;
import com.example.project_i.KhachHang.KhachHang_Module;
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
            PreparedStatement ps = conn.prepareStatement("select * from danhMucKH");
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                list.add(new KhachHang_Module(rs.getInt(1), rs.getString(2)));
            }
        } catch (Exception e) {
        }
        return list;
    }
    public static ObservableList<HangHoa_Module> getHH_Data(){
        Connection conn = ConnectionDB();
        ObservableList<HangHoa_Module> list = FXCollections.observableArrayList();
        try {
            PreparedStatement ps = conn.prepareStatement("select * from danhMucHH");
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                list.add(new HangHoa_Module(rs.getInt(1), rs.getString(2)));
            }
        } catch (Exception e) {
        }
        return list;
    }
}


