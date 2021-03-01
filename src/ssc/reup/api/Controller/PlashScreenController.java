/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ssc.reup.api.Controller;

import com.jfoenix.controls.JFXTextArea;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import javax.imageio.ImageIO;
import Setting.ToolSetting;
import Utils.CMDUtils;
import static Utils.Constants.PRE.SETUP_READY;
import static Utils.Constants.UPDATE_PATH;
import static ssc.reup.api.Utils.Graphics.addDragListeners;
import ssc.reup.api.task.PlashTask;
import Setup.CloseAppTask;

/**
 * @author inet
 */
public class PlashScreenController implements Initializable {

    Stage mainStage;

    private void showStage() {
        if (mainStage != null) {
            mainStage.show();
            mainStage.toFront();
        }
    }
    private static final String iconImageLoc = "http://icons.iconarchive.com/icons/scafer31000/bubble-circle-3/16/GameCenter-icon.png";
    // a timer allowing the tray icon to provide a periodic notification event.

    // format used to display the current time in a tray icon notification.
    private DateFormat timeFormat = SimpleDateFormat.getTimeInstance();

    private void addAppToTray() {
        try {
            // ensure awt toolkit is initialized.
            java.awt.Toolkit.getDefaultToolkit();

            // app requires system tray support, just exit if there is no support.
            if (!java.awt.SystemTray.isSupported()) {
                CloseAppTask closeAppTask = new CloseAppTask();
                closeAppTask.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                    @Override
                    public void handle(WorkerStateEvent t) {
                        Platform.exit();
                    }
                });
                Thread th = new Thread(closeAppTask);
                th.setDaemon(true);
                th.start();
            }

            // set up a system tray icon.
            java.awt.SystemTray tray = java.awt.SystemTray.getSystemTray();
            URL imageLoc = new URL(
                    iconImageLoc
            );
            java.awt.Image image = ImageIO.read(imageLoc);
            java.awt.TrayIcon trayIcon = new java.awt.TrayIcon(image);
            // if the user double-clicks on the tray icon, show the main app stage.
            trayIcon.addActionListener(event -> Platform.runLater(
                    this::showStage
            ));
            // if the user selects the default menu item (which includes the app name), 
            // show the main app stage.
            java.awt.MenuItem openItem = new java.awt.MenuItem("Mở rộng");
            openItem.addActionListener(event -> Platform.runLater(this::showStage));

            // the convention for tray icons seems to be to set the default icon for opening
            // the application stage in a bold font.
            java.awt.Font defaultFont = java.awt.Font.decode(null);
            java.awt.Font boldFont = defaultFont.deriveFont(java.awt.Font.BOLD);
            openItem.setFont(boldFont);

            // to really exit the application, the user must go to the system tray icon
            // and select the exit option, this will shutdown JavaFX and remove the
            // tray icon (removing the tray icon will also shut down AWT).
            java.awt.MenuItem exitItem = new java.awt.MenuItem("Exit");
            exitItem.addActionListener(event -> {
                CloseAppTask closeAppTask = new CloseAppTask();
                openItem.setLabel("Đang đóng phần mềm");
                closeAppTask.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                    @Override
                    public void handle(WorkerStateEvent t) {
                        Platform.exit();
                        tray.remove(trayIcon);
                    }
                });
                Thread th = new Thread(closeAppTask);
                th.setDaemon(true);
                th.start();

            });
            // setup the popup menu for the application.
            final java.awt.PopupMenu popup = new java.awt.PopupMenu();
            popup.add(openItem);
            popup.addSeparator();
            popup.add(exitItem);
            trayIcon.setPopupMenu(popup);
            // create a timer which periodically displays a notification message.
            // add the application tray icon to the system tray.
            tray.add(trayIcon);
        } catch (java.awt.AWTException | IOException e) {
            System.out.println("Unable to init system tray");
            e.printStackTrace();
        }
    }

    Preferences pre;
    @FXML
    private Button lbPlashMessage;

    @FXML
    private Button btnUpdate;

    @FXML
    private StackPane spContent;

    @FXML
    private AnchorPane apMain;
    @FXML
    private JFXTextArea textAreaTest;
    @FXML
    private ImageView ivS1;
    @FXML
    private ImageView ivS2;
    @FXML
    private ImageView ivCo;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        PlashTask plashTask = new PlashTask();
        File file = new File(System.getProperty("user.dir") + "\\assets\\S.png");
        Image image = new Image(file.toURI().toString());

        File file1 = new File(System.getProperty("user.dir") + "\\assets\\co.png");
        Image image1 = new Image(file1.toURI().toString());
        ivCo.setImage(image1);
        ivS1.setImage(image);
        ivS2.setImage(image);

        //ivCo.setLayoutY(lbPlashMessage.getLayoutY()-ivCo.getFitHeight()+20);
        //ivCo.setLayoutX(ivS1.getLayoutX() + ivS1.getFitWidth() / 2 + 10);
        TranslateTransition translateTransition1 = new TranslateTransition(Duration.seconds(2), ivS1);
        translateTransition1.setFromY(-200);
        translateTransition1.setToY(0);
        translateTransition1.setFromX(100);
        translateTransition1.setToX(0);

        translateTransition1.setCycleCount(1);
        translateTransition1.play();

        TranslateTransition translateTransition2 = new TranslateTransition(Duration.seconds(2), ivS2);
        translateTransition2.setFromY(200);
        translateTransition2.setToY(0);
        translateTransition2.setFromX(-100);
        translateTransition2.setToX(0);
        translateTransition2.play();

        //lbPlashMessage.setLayoutX(ivS1.getLayoutX());
        //lbPlashMessage.setLayoutY(ivS6.getLayoutY() + ivS6.getFitHeight()-lbPlashMessage.getPrefHeight()+10);
        btnUpdate.setPrefWidth(130);
        btnUpdate.setLayoutY(lbPlashMessage.getLayoutY() - lbPlashMessage.getPrefHeight() - 5);
        btnUpdate.setLayoutX(lbPlashMessage.getLayoutX() + lbPlashMessage.getPrefWidth() - btnUpdate.getPrefWidth());
        btnUpdate.getStyleClass().setAll("btn", "btn-primary", "btn-xs");
        btnUpdate.setText("Cập Nhật Bản Mới");

        translateTransition2.setOnFinished((e) -> {
            lbPlashMessage.setVisible(true);
            lbPlashMessage.setPrefWidth(0);
            TranslateTransition translateTransition3 = new TranslateTransition(Duration.seconds(1), spContent);
            translateTransition3.setFromX(0);
            translateTransition3.setToX(-300);
            translateTransition3.play();
            Timer animTimer = new Timer();
            animTimer.scheduleAtFixedRate(new TimerTask() {
                int i = 0;

                @Override
                public void run() {
                    if (i < 100) {
                        lbPlashMessage.setPrefWidth(lbPlashMessage.getPrefWidth() + 6);
                    } else {
                        this.cancel();
                    }
                    i++;
                }

            }, 0, 10);
            lbPlashMessage.textProperty().bind(plashTask.messageProperty());
            Thread t = new Thread(plashTask);
            t.setDaemon(true);
            t.start();
        });
        btnUpdate.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                CMDUtils.runTask(UPDATE_PATH);
                Platform.exit();
                System.exit(0);

            }
        });
        FadeTransition fadeOut = new FadeTransition(Duration.seconds(2), spContent);
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);
        fadeOut.setCycleCount(1);
        plashTask.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent t) {
                if (plashTask.getValue()) {
                    fadeOut.play();
                } else {
                    if (plashTask.getCheckUpdate().equals("cập nhật")) {
                        btnUpdate.setVisible(true);
                    }
                    lbPlashMessage.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            if (plashTask.getCheckUpdate().contains("cập nhật")) {
                                fadeOut.play();
                            } else {
                                try {
                                    Runtime.getRuntime().exec("cmd /c start update.exe");
                                    Platform.exit();
                                    System.exit(0);
                                } catch (Exception e) {
                                }
                            }
                        }
                    });
                }
            }
        });
        fadeOut.setOnFinished((e) -> {
            try {

                if (!ToolSetting.getInstance().getPre().getBoolean(SETUP_READY, false)) {
                    mainStage = new Stage();
                    mainStage.initStyle(StageStyle.TRANSPARENT);
                    mainStage.setX(ToolSetting.getInstance().getPre().getInt("currentLoX", 0));
                    mainStage.setY(ToolSetting.getInstance().getPre().getInt("currentLoY", 0));
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ssc/reup/api/Fxml/FXMLMain.fxml"));
                    Parent root = (Parent) fxmlLoader.load();
                    String uri = Paths.get(System.getProperty("user.dir") + "\\assets\\Styles.css").toUri().toString();
                    root.getStylesheets().add(uri);
                    addDragListeners(root);
                    mainStage.setScene(new Scene(root));
                    mainStage.show();
                    Platform.setImplicitExit(false);
                    javax.swing.SwingUtilities.invokeLater(this::addAppToTray);
                    Stage stage = (Stage) lbPlashMessage.getScene().getWindow();
                    stage.close();
                } else {
                    Stage stageAccount = new Stage();
                    stageAccount.initStyle(StageStyle.TRANSPARENT);
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ssc/reup/api/Fxml/FXMLSetUp.fxml"));
                    Parent root = (Parent) fxmlLoader.load();
                    String uri = Paths.get(System.getProperty("user.dir") + "\\assets\\Styles.css").toUri().toString();
                    root.getStylesheets().add(uri);
                    addDragListeners(root);
                    stageAccount.setScene(new Scene(root));
                    stageAccount.show();
                    Stage stage = (Stage) lbPlashMessage.getScene().getWindow();
                    stage.close();
                }
            } catch (Exception ex) {
                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

    }
}
