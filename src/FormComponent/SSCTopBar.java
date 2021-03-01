/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FormComponent;

import Setting.ToolSetting;
import Setup.CloseAppTask;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Platform;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 * @author PC
 */
public class SSCTopBar {

    private HBox topBar;

    public SSCTopBar(Scene scene) {
        topBar = (HBox) scene.lookup("#paneNavTopBar");
    }

    public void initMenu() {
        List<SSCButtonMenu> arr = new ArrayList<>();
        arr.add(new SSCButtonMenu("CLOSE", "Đóng tool", new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ToolSetting.getInstance().getPre().putInt("currentLoX", (int) topBar.getScene().getWindow().getX());
                ToolSetting.getInstance().getPre().putInt("currentLoY", (int) topBar.getScene().getWindow().getY());
                CloseAppTask cat = new CloseAppTask();
                cat.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                    @Override
                    public void handle(WorkerStateEvent event) {
                        Platform.exit();
                        System.exit(0);
                    }
                });
                Thread t = new Thread(cat);
                t.setDaemon(true);
                t.start();
            }
        }));
        arr.add(new SSCButtonMenu("MINUS", "Thu nhỏ", new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                   ((Stage)topBar.getScene().getWindow()).setIconified(true);
            }
        }));
        arr.add(new SSCButtonMenu("COG", "Cài đặt", new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

            }
        }));
        for (SSCButtonMenu btnMenu : arr) {
            topBar.getChildren().add(btnMenu.getButton("topBarButton"));
        }
    }
}
