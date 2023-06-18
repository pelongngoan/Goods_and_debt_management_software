package com.example.project_i;

import com.example.project_i.Login.LoginController;
import com.example.project_i.TrangChu.MenuController;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.net.URL;

public class FXML_Loader {
    private Pane view;
    public Pane getPage(String fileName){
        try {
            URL fileURL = LoginController.class.getResource(fileName);
            System.out.println("Done here");
            /*if (fileURL == null){
                throw new java.io.FileNotFoundException("FXML file can't be found");
            }*/
            System.out.println("Done this");

            view = new FXMLLoader().load(fileURL);
            System.out.println("Done all");
        } catch (Exception e){
            System.out.println("No page "+fileName+" please check FXML_Loader ");
        }
        return view;
    }
}
