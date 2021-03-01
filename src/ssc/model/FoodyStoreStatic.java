/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ssc.model;

import static FormComponent.SSCTableColumStatic.getTableColInteger;
import static FormComponent.SSCTableColumStatic.getTableColString;
import javafx.collections.ObservableList;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableView;
/**
 * @author PC
 */
public class FoodyStoreStatic {
    public static void initTable(TableView tv,ObservableList<FoodyStore> data){
         tv.getColumns().addAll(
                getTableColInteger("STT", "stt", 70, false),
                getTableColString("Tên", "name", 350, false),
                getTableColString("Địa chỉ", "address", 450, false),
                getTableColString("SDT", "sdt", 140, false));
        tv.setItems(data);
        tv.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }
}
