package com.example.project_i;

import com.example.project_i.HangHoa.HangHoa_Module;
import com.example.project_i.KetNoi_Database.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Main_test {
    public static void main(String[] args) {
        ObservableList<HangHoa_Module> observableList = FXCollections.observableArrayList();
        PreparedStatement preparedStatement =null;
        Connection conn= DBConnection.ConnectionDB();
        ResultSet resultSet ;
       /* try {
            preparedStatement = conn.prepareStatement("danhMucHH_get_All");
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                observableList.add(new HangHoa_Module(resultSet.getInt(1),resultSet.getInt(2), resultSet.getString(3), resultSet.getString(4),resultSet.getString(5), resultSet.getFloat(6), resultSet.getDouble(7), resultSet.getDouble(8), resultSet.getDouble(9)));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        for (HangHoa_Module be:observableList){
            System.out.println(be.getTenHang()+" "+be.getMaHang());
        }*/
    }
}
