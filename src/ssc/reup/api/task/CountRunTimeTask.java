/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ssc.reup.api.task;
import javafx.concurrent.Task;
import ssc.automation.selennium.SeleniumJSUtils;
/**
 * @author inet
 */
public class CountRunTimeTask extends Task<Void> {
    @Override
    protected Void call() throws Exception {
        int i=0;
        while (true) {
            int s =i%60;
            int m =i/60%60;       
            int h =i/60/60;
            updateMessage(h+" h "+m+" m "+s+" s");
            SeleniumJSUtils.Sleep(1000);
            i++;
        }
    }
    public void start(){
        Thread t=new Thread(this);
        t.setDaemon(true);
        t.start();
    }
}
