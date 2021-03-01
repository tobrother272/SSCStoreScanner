
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ssc.reup.api.task;

import Setup.CheckInit;
import Setup.SetInit;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.prefs.Preferences;
import javafx.concurrent.Task;
import Utils.MyFileUtils;
import Setting.ToolSetting;
import Setup.InitialingTool;
import Utils.CMDUtils;
import static Utils.Constants.DATA.FFMPEG;
import static Utils.Constants.DATA.RESOURCE_DATA;
import static Utils.Constants.DATA.RESOURCE_TOOL;
import static Utils.Constants.checkReady.SDK;
import static Utils.UIUtils.printLogToDesktop;

/**
 * @author inet
 */
public class PlashTask extends Task<Boolean> {

    Preferences pre;
    private static final String iconImageLoc = "http://icons.iconarchive.com/icons/scafer31000/bubble-circle-3/16/GameCenter-icon.png";
    private String checkUpdate;

    public String getCheckUpdate() {
        return checkUpdate;
    }

    public void setCheckUpdate(String checkUpdate) {
        this.checkUpdate = checkUpdate;
    }

    public void updateMyMessage(String message) {
        updateMessage(message);
    }

    @Override
    protected Boolean call() {
        try {
            printLogToDesktop();
            pre=ToolSetting.getInstance().getPre();
            CMDUtils.checkAndKillProcess("firefox.exe");
            CMDUtils.checkAndKillProcess("geckodriver.exe");
            checkUpdate = "";
            updateMessage("Đang kiểm tra quyền admin ...");
            if (!System.getProperty("user.dir").contains("C:")) {
                updateMessage("Vui lòng copy tool vào ổ C để chạy tối ưu !");
                return false;
            }
            List<String> arrXpathJAVA = CheckInit.getListJAVA();
            List<String> myJAVAList = new ArrayList<>();
            myJAVAList.add("JAVA_HOME=C:\\Program Files\\Java\\jdk1.8.0_161");
            myJAVAList.add("ANDROID_HOME=C:\\SSC\\Tool\\sdk");
            myJAVAList.add("JAVA_TOOL_OPTIONS=-Dfile.encoding=UTF8");
            for (String string : myJAVAList) {
                if (!arrXpathJAVA.contains(string)) {
                    if (string.split("=").length == 3) {
                        SetInit.setupRootEnviroment(string.split("=")[0], string.split("=")[1] + "=" + string.split("=")[2]);
                    } else {
                        SetInit.setupRootEnviroment(string.split("=")[0], string.split("=")[1]);
                    }
                }
            }
            //SetInit.setupEnviroment("C:\\SSC\\Tool\\sdk\\platform-tools;C:\\SSC\\Tool\\sdk\\tools;C:\\Program Files\\Java\\jdk1.8.0_161\\bin");
            updateMessage("Hoàn thành , mở tool sau 3 s .");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            updateMessage("Error : " + e.getMessage());
            return true;
        }

    }

}
