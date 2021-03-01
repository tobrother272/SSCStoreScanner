/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ssc.facking;

import Setting.ToolSetting;
import javafx.application.Platform;
import javafx.scene.control.Label;
import ssc.model.WebDriverRunning;
import ssc.model.WebdriverList;
import ssc.proxy.GetTMProxy;
import ssc.proxy.ProxyInfo;

/**
 *
 * @author PC
 */
public class ProrxyRunning {

    public static ProrxyRunning instance;
    private Label lbCountScan;

    public Label getLbCountScan() {
        return lbCountScan;
    }

    public void setLbCountScan(Label lbCountScan) {
        this.lbCountScan = lbCountScan;
    }

    public static ProrxyRunning getInstance() {
        if (instance == null) {
            instance = new ProrxyRunning();
        }
        return instance;
    }
    private int countRunning;
    private int countRun;

    public int getCountRunning() {
        return countRunning;
    }

    public void setCountRunning(int countRunning) {
        this.countRunning = countRunning;
    }

    public int getCountRun() {
        return countRun;
    }

    public void increaseRun() {
        this.countRun++;
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                lbCountScan.setText(countRun + "/" + ToolSetting.getInstance().getMaxStorePerIp());
            }
        });
    }

    public void increaseRunning() {
        this.countRunning++;
    }

    public void countDownRunning() {
        this.countRunning--;
    }
    private ProxyInfo currentProxy;

    public ProxyInfo getCurrentProxy() {
        return currentProxy;
    }

    public void setCurrentProxy(ProxyInfo currentProxy) {
        this.currentProxy = currentProxy;
    }

    public ProrxyRunning() {
        countRunning = 0;
        countRun = 0;
    }
    private boolean gettingProxy = false;

    public ProxyInfo getAvailableSanStoreProxy() {
        try {
            ProxyInfo pi= GetTMProxy.GetTMProxy(ToolSetting.getInstance().getStoreScanKey());
            return pi;
        } catch (Exception e) {
        }
        return null;
    }

    public ProxyInfo getAvailableProxy() {
        if (gettingProxy) {
            return null;
        }
        if (countRun >= ToolSetting.getInstance().getMaxStorePerIp()) {
            if (countRunning == 0) {
                for (WebDriverRunning wdr : WebdriverList.getInstance().getArrWebdriver()) {
                    wdr.getDriver().quit();
                    wdr.setDriver(null);
                }
                countRun = 0;
                currentProxy = null;
            } else {
                return null;
            }
        }
        if (currentProxy == null) {
            gettingProxy = true;
            currentProxy = GetTMProxy.GetTMProxy(ToolSetting.getInstance().getTmKey());
            gettingProxy = false;
        }
        return currentProxy;

    }

}
