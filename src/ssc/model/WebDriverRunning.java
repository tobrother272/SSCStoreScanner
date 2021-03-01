/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ssc.model;

import Setting.ToolSetting;
import org.openqa.selenium.WebDriver;
import ssc.automation.selennium.constants;

/**
 *
 * @author PC
 */
public class WebDriverRunning {
    private WebDriver driver;
    private boolean running;

    public WebDriver getDriver() {
        return driver;
    }

    public void setDriver(WebDriver driver) {
        this.driver = driver;
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public WebDriverRunning() {
        this.driver = null;
        this.running = true;
    }
    
   
    
}
