
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ssc.reup.api;

import Setting.ToolSetting;
import java.nio.file.Paths;
import java.util.ArrayList;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.stage.Stage;
import java.util.List;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.StageStyle;


/**
 * @author inet
 */
public class SscReupApi extends Application {
     public boolean isPrimeNumber(int n) {
        if (n < 2) {
            return false;
        }
        int squareRoot = (int) Math.sqrt(n);
        for (int i = 2; i <= squareRoot; i++) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }
    
    public List<Integer> getkq(int number){
        List<Integer> danhsachketqua=new ArrayList<>();
        int currentV= number;
        while(!isPrimeNumber(currentV)){
            for (int i=0;i<currentV;i++) {
                if(isPrimeNumber(i)&&currentV%i==0){
                    danhsachketqua.add(i);
                    currentV=currentV/i;
                    break;
                }
            }
        }
        danhsachketqua.add(currentV);
        return danhsachketqua;
    }
    
    
    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("SSC Account Manager");
        Parent root = FXMLLoader.load(getClass().getResource("/ssc/reup/api/Fxml/FXMLSplash.fxml"));
        String uri = Paths.get(System.getProperty("user.dir") + "\\assets\\Styles.css").toUri().toString();
        root.getStylesheets().add(uri);
        stage.initStyle(StageStyle.TRANSPARENT);
        Scene scene = new Scene(root);
        scene.setFill(null);
        stage.setScene(scene);
        stage.show();
        scene.getWindow().centerOnScreen();
    }

    /**
     * @param args the command line arguments
     */
    static void main(String[] args) {
        launch(args);
    }

}
