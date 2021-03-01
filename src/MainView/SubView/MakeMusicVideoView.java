/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MainView.SubView;

import FormComponent.SSCFormFormat;
import FormComponent.SSCInfoDialog;
import FormComponent.SSCInputDialog;
import FormComponent.SSCTextField;
import FormComponent.SSCButtonChildTab;
import FormComponent.SSCCheckBox;
import FormComponent.SSCCombobox;
import FormComponent.SSCMessage;
import Setting.ToolSetting;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import ssc.facking.ProrxyRunning;
import ssc.model.FoodyStore;
import ssc.model.FoodyStoreStatic;
import ssc.model.ScanStoreTask;
import ssc.model.WebdriverList;
import ssc.proxy.ProxyInfo;
import task.EventFinistTask;
import ssc.reup.api.task.ExportJsonTask;

/**
 * @author PC
 */
public class MakeMusicVideoView extends SSCChildView {
    
    public MakeMusicVideoView(Scene scene) {
        super(scene);
    }
    
    @Override
    public String getBreadScrumbTitle() {
        return "Foody Scaner";
    }
    
    @Override
    public String getBreadScrumbDescription() {
        return "Scan data cửa hàng foody";
    }
    
    @Override
    public String getLeftMenuIcon() {
        return "SKYATLAS";
    }
    
    @Override
    public int getTabIndex() {
        return 6;
    }
    private SSCCombobox cbCity;
    private SSCCombobox cbCategory;
    private SSCCombobox cbThreadRunning;
    private SSCTextField firefoxExe;
    private SSCTextField profilePath;
    private SSCInputDialog scanConfigDialog;
    private SSCCheckBox cbHideMode;
    private SSCTextField proxyAPI;
    private SSCTextField txtSearch;
    private SSCTextField proxyAPIScanStore;
    private SSCTextField resetIpAfter;
    private SSCTextField txtFromPage;
    private int numberStorePerIp;
    private Label lbCurrentProxy;
    private Label lbCountStoreScan;
    private Label lbLoadStoreMessage;
    public void initMixDialog() {
        
        cbCity = new SSCCombobox("Tỉnh/TP", "Chọn 1 tỉnh thành", scanConfigDialog.getForm().getFormElement(), "cbCity", Arrays.asList("require"));
        cbCity.setItems(City);
        cbCategory = new SSCCombobox("Category", "Chọn 1 Category", scanConfigDialog.getForm().getFormElement(), "cbCategory", Arrays.asList("require"));
        cbCategory.setItems(Category);
        txtFromPage= new SSCTextField("Trang bắt đầu", "Nhập trang bắt đầu", scanConfigDialog.getForm().getFormElement(), "txtFromPage", Arrays.asList("require"));
       
        cbThreadRunning = new SSCCombobox("Số luồng lấy data", "Chọn số luồng", scanConfigDialog.getForm().getFormElement(), "cbThreadRunning", Arrays.asList("require"));
        cbThreadRunning.setItems(THREADS);
        firefoxExe = new SSCTextField("Đường dẫn firefox", "Click đúp chọn đường dẫn firefox", scanConfigDialog.getForm().getFormElement(), "firefoxExe", Arrays.asList("require"));
        firefoxExe.enableSelectFileMode(Arrays.asList("exe"));
        profilePath = new SSCTextField("Đường dẫn thu mục profile", "Mở about:support copy", scanConfigDialog.getForm().getFormElement(), "profilePath", Arrays.asList("require"));
        txtSearch = new SSCTextField("Từ khóa", "Từ khóa tìm kiếm", scanConfigDialog.getForm().getFormElement(), "txtSearch", Arrays.asList("require"));
       
        cbHideMode = new SSCCheckBox("Ẩn trình duyệt", scanConfigDialog.getForm().getFormElement(), "cbHideMode");
        proxyAPIScanStore = new SSCTextField("TMproxy Api Quét danh sách", "Nhập key api quét list store", scanConfigDialog.getForm().getFormElement(), "proxyAPIScanStore", Arrays.asList("require"));
        proxyAPI = new SSCTextField("TMproxy Api Quét Thông Tin", "Nhập key api quét info store", scanConfigDialog.getForm().getFormElement(), "proxyAPI", Arrays.asList("require"));
        resetIpAfter = new SSCTextField("Reset proxy sau", "Nhập số lượng cửa hàng càn lấy mỗi IP", scanConfigDialog.getForm().getFormElement(), "resetIpAfter", Arrays.asList("require"));
        
        cbCity.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
               txtFromPage.setText(""+ToolSetting.getInstance().getPre().getInt(cbCity.getValue()+"-"+cbCategory.getValue()+"-"+txtSearch.getText().toLowerCase(), 0));
            }
        });
        cbCategory.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                txtFromPage.setText(""+ToolSetting.getInstance().getPre().getInt(cbCity.getValue()+"-"+cbCategory.getValue()+"-"+txtSearch.getText().toLowerCase(), 0));
            }
        });
        txtSearch.getTextField().textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
               txtFromPage.setText(""+ToolSetting.getInstance().getPre().getInt(cbCity.getValue()+"-"+cbCategory.getValue()+"-"+txtSearch.getText().toLowerCase(), 0));
            }
        });
        
        scanConfigDialog.getForm().getSubmit().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (profilePath.isValidate() || firefoxExe.isValidate() || cbCategory.isValidate()
                        || cbCity.isValidate() || cbThreadRunning.isValidate() || proxyAPI.isValidate()
                        || resetIpAfter.isValidate()||txtFromPage.isValidate()) {
                    return;
                }
                
                numberStorePerIp = Integer.parseInt(resetIpAfter.getText().trim());
                scanConfigDialog.getForm().saveData();
                ToolSetting.getInstance().setFirefoxPath(firefoxExe.getText());
                ToolSetting.getInstance().setProfilePath(profilePath.getText());
                ToolSetting.getInstance().setHideBrowser(cbHideMode.isSelected());
                ToolSetting.getInstance().setTmKey(proxyAPI.getText());
                ToolSetting.getInstance().setStoreScanKey(proxyAPIScanStore.getText());
                ToolSetting.getInstance().setMaxStorePerIp(Integer.parseInt(resetIpAfter.getText().trim()));
                ProrxyRunning.getInstance().setLbCountScan(lbCountStoreScan);
                ScanStoreTask scanStoreTask = new ScanStoreTask(Integer.parseInt(txtFromPage.getText()),cbCity.getValue().toString(), cbCategory.getValue().toString(), 1000, data,txtSearch.getText().replaceAll(" ", "+"));
                
                lbLoadStoreMessage.textProperty().bind(scanStoreTask.messageProperty());
                
                scanStoreTask.start();
                countRunning = 0;
                currentPosition = 0;
                scanConfigDialog.hide();
                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        if (countRunning < (cbThreadRunning.getSelectionModel().getSelectedIndex() + 1) && currentPosition < data.size()) {
                            ProxyInfo proxy = ProrxyRunning.getInstance().getAvailableProxy();
                            if (proxy == null) {
                                return;
                            }
                            //
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    lbCurrentProxy.setText(proxy.getIp() + ":" + proxy.getPort());
                                }
                            });
                            countRunning++;
                            FoodyStore store = data.get(currentPosition++);
                            store.setWebDriverRunning(WebdriverList.getInstance().getAvailable());
                            store.setProxy(proxy);
                            store.eventListener(new EventFinistTask() {
                                @Override
                                public void main() {
                                    countRunning--;
                                }
                            });
                            store.start();
                        }
                        if (currentPosition >= data.size() && countRunning == 0 && currentPosition != 0) {
                            timer.cancel();
                        }
                    }
                }, 100, 1000);
            }
        });
    }
    private TableView tvVideoSetupProgress;
    int countRunning = 0;
    int currentPosition = 0;
    public static ObservableList<String> THREADS
            = FXCollections.observableArrayList(
                    "1",
                    "2",
                    "3",
                    "4",
                    "5",
                    "6",
                    "7",
                    "8",
                    "9",
                    "10"
            );
    public static ObservableList<String> City
            = FXCollections.observableArrayList(
                    "ho-chi-minh",
                    "ha-noi",
                    "da-nang",
                    "can-tho",
                    "khanh-hoa",
                    "vung-tau",
                    "hai-phong",
                    "binh-thuan",
                    "lam-dong",
                    "dong-nai",
                    "quang-ninh",
                    "hue",
                    "binh-duong",
                    "hai-duong",
                    "ninh-thuan"
            );
    public static ObservableList<String> Category
            = FXCollections.observableArrayList(
                    "travel",
                    "food",
                    "wedding",
                    "beauty",
                    "entertain",
                    "shop",
                    "edu",
                    "service"
            );
    private ObservableList<FoodyStore> data;
    
    @Override
    public void initView(Scene scene) {
        tvVideoSetupProgress = (TableView) scene.lookup("#tvVideoSetupProgress");
        lbCurrentProxy = (Label) scene.lookup("#lbCurrentProxy");
        lbCountStoreScan= (Label) scene.lookup("#lbCountStoreScan");
        lbLoadStoreMessage= (Label) scene.lookup("#lbLoadStoreMessage");
        scanConfigDialog = new SSCInputDialog(scene, 3, "Cài đặt", "Cài đặt filter scan data", "Scan ngay");
        data = FXCollections.observableArrayList();
        FoodyStoreStatic.initTable(tvVideoSetupProgress, data);
        initMixDialog();
        tvVideoSetupProgress.setRowFactory(tv -> {
            TableRow<String> row = new TableRow<>();
            row.setOnMouseClicked((MouseEvent event) -> {
                if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
                    FoodyStore zipVideo = (FoodyStore) tvVideoSetupProgress.getSelectionModel().getSelectedItem();
                    List<SSCFormFormat> arr = new ArrayList<>();
                    /*
                    arr.add(new SSCFormFormat(zipVideo.getTopic(), 0, "Chủ đề"));
                    arr.add(new SSCFormFormat(zipVideo.getTitleVideo(), 0, "Tiêu đề video"));
                    arr.add(new SSCFormFormat(zipVideo.getTags(), 0, "Từ khóa video"));
                    arr.add(new SSCFormFormat(zipVideo.getMusicFolderPath(), 0, "Đường dẫn thư mục nhạc"));
                    arr.add(new SSCFormFormat(zipVideo.getZipPath(), 0, "Đường dẫn zip"));
                    arr.add(new SSCFormFormat(zipVideo.getDescription(), 1, "Mô tả video"));
                     */
                    new SSCInfoDialog(scene, arr, "Thông tin", "Thông tin zip video", "Thoát").show();
                }
            });
            return row;
        });
        bindData();
    }
    
    @Override
    public void reloadView() {
        scanConfigDialog.hide();
    }
    
    @Override
    public List<SSCButtonChildTab> getListMenuButton() {
        List<SSCButtonChildTab> arrButton = new ArrayList<>();
        arrButton.add(new SSCButtonChildTab("Scan thông tin cửa hàng", "ASTERISK", "Cài đặt chạy thông tin cửa hàng", new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                scanConfigDialog.show();
            }
        }));
        arrButton.add(new SSCButtonChildTab("Xuất ra json", "ASTERISK", "Xuất danh sách của hàng ra json", new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ObservableList<FoodyStore> data = FXCollections.observableArrayList();
                data.addAll(tvVideoSetupProgress.getSelectionModel().getSelectedItems());
                if (data.size() == 0) {
                    SSCMessage.showWarning(scene, "Chọn danh sách quán cần export");
                    return;
                }
                FileChooser fileChooser = new FileChooser();
                //Set extension filter for text files
                FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.json)", "*.json");
                fileChooser.getExtensionFilters().add(extFilter);
                //Show save file dialogi
                File file = fileChooser.showSaveDialog(new Stage());
                if (file != null) {
                    ExportJsonTask exportJsonTask = new ExportJsonTask(data, file.getAbsolutePath());
                    exportJsonTask.start();
                }
            }
        }));
        return arrButton;
    }
    
}
