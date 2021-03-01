/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ssc.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author PC
 */
public class WebdriverList {
    public WebdriverList(){
        arrWebdriver=new ArrayList<>();
    }
    private List<WebDriverRunning> arrWebdriver;

    public List<WebDriverRunning> getArrWebdriver() {
        return arrWebdriver;
    }

    public void setArrWebdriver(List<WebDriverRunning> arrWebdriver) {
        this.arrWebdriver = arrWebdriver;
    }
    
    public static WebdriverList instance;
    public static WebdriverList getInstance(){
        if(instance==null){
            instance=new WebdriverList();
        }
        return instance;
    }
    public WebDriverRunning getAvailable(){
        try {
            for (WebDriverRunning webDriverRunning : arrWebdriver) {
                if(!webDriverRunning.isRunning()){
                    webDriverRunning.setRunning(true);
                    return webDriverRunning;
                }
            }
        } catch (Exception e) {
        }
        WebDriverRunning driverRunning=new WebDriverRunning();
        arrWebdriver.add(driverRunning);
        
        return driverRunning;  
    }
}
