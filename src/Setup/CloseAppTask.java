/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Setup;

import Utils.CMDUtils;
import javafx.concurrent.Task;
/**
 * @author magictool
 */
public class CloseAppTask extends Task<Boolean> {

    @Override
    protected Boolean call() throws Exception {
        updateTitle("Tool Closing ...");
        try {
            updateMessage("Closing ADB ... ");
            CMDUtils.checkAndKillProcess("firefox.exe");
            CMDUtils.checkAndKillProcess("geckodriver.exe");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
