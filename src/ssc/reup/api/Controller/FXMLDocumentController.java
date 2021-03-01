/*
 * and open the template in the editor.
 */
package ssc.reup.api.Controller;

import FormComponent.SSCCurrentProgress;
import FormComponent.SSCTopBar;
import MainView.SubView.Login.LoginView;
import MainView.SubView.MakeMusicVideoView;
import MainView.SubView.SettingView;
import Setup.CloseAppTask;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import Setting.ToolSetting;
import ssc.reup.api.task.LoadLoginTask;
/**
 * @author inet
 */
public class FXMLDocumentController implements Initializable {
    @FXML
    private AnchorPane apInitAppViewContainer;
    @FXML
    private AnchorPane paneContentContainer;
    @FXML
    private Button btnCloseAfter;
    @FXML
    private Button btGoToSimple;
    @FXML
    private Button btnTest;
    @FXML
    private ButtonBar btnGroupTopic;
    @FXML
    private ButtonBar btGroupExportVideo;
    @FXML
    private ButtonBar btnBarTestVideoView;
    ///@FXML
    ///private ImageView imgLogin;
    @FXML
    private TabPane tabContentContainer;
    public static ObservableList<String> operaigon
            = FXCollections.observableArrayList(
                    "Tất cả",
                    "Viettel",
                    "Vinaphone",
                    "Mobile Phone",
                    "Vietnam Mobile",
                    "Cambodia"
            );
    private Stage mainStage;
    private LoginView loginView;
    private SettingView settingView;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //imgLogin.setImage(new Image(getClass().getResource("assets/login_bg.png").toString()));
        ToolSetting.getInstance().setToolReady(true);
        paneContentContainer.setVisible(true);
        apInitAppViewContainer.setVisible(true);
        loginView = new LoginView();
        settingView = new SettingView();
        LoadLoginTask load = new LoadLoginTask();
        load.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                loginView.initView(apInitAppViewContainer.getScene());
                settingView.initView(apInitAppViewContainer.getScene());
                SSCCurrentProgress.getLoadingProgressBar(apInitAppViewContainer.getScene()).setVisible(false);
                new MakeMusicVideoView(apInitAppViewContainer.getScene());
                new SSCTopBar(apInitAppViewContainer.getScene()).initMenu();
            }
        });
        load.start();
        //iconResetIp.textProperty().bind(CurrentIP.getInstance().iconIpProperty());
        //spResetting.visibleProperty().bind(CurrentIP.getInstance().resettingProperty());
        btnCloseAfter.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ToolSetting.getInstance().getPre().putInt("currentLoX", (int) btnCloseAfter.getScene().getWindow().getX());
                ToolSetting.getInstance().getPre().putInt("currentLoY", (int) btnCloseAfter.getScene().getWindow().getY());
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
        });
        
        btnGroupTopic.setVisible(false);
        btnBarTestVideoView.setVisible(false);
        btGroupExportVideo.setVisible(false);
    }

}
