/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ssc.reup.api.task;

import java.nio.file.Files;
import java.nio.file.Paths;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import ssc.model.FoodyStore;

/**
 *
 * @author PC
 */
public class ExportJsonTask extends Task<Boolean>{
    private ObservableList<FoodyStore> data;
    private String jsonF;
    public ExportJsonTask(ObservableList<FoodyStore> data,String jsonF) {
        this.data = data;
        this.jsonF=jsonF;
    }
    @Override
    protected Boolean call()  {
        try {
            JSONArray arrStore=new JSONArray();
            for (FoodyStore foodyStore : data) {
                 JSONObject object=new JSONObject();
                 object.put("name", foodyStore.getName());
                 object.put("link", foodyStore.getLink());
                 object.put("address", foodyStore.getAddress());
                 object.put("phone", foodyStore.getSdt());
                 arrStore.add(object);
            }
            Files.write(Paths.get(jsonF), arrStore.toJSONString().getBytes());
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
