/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ssc.reup.api.task;

import static Utils.CMDUtils.OpenMittm;
import javafx.concurrent.Task;

/**
 *
 * @author PC
 */
public class OpenMittmTask extends Task<Boolean>{

    @Override
    protected Boolean call()  {
        try {
            OpenMittm();
        } catch (Exception e) {
        }
        return true;
    }
    
    public void start(){
        Thread t=new Thread(this);
        t.setDaemon(true);
        t.start();
    }
}
