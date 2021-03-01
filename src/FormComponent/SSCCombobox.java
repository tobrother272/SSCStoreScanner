/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FormComponent;

import com.jfoenix.controls.JFXComboBox;
import java.util.List;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 *
 * @author PC
 */
public class SSCCombobox {

    private JFXComboBox combobox;

    public JFXComboBox getCombobox() {
        return combobox;
    }

    public void setCombobox(JFXComboBox combobox) {
        this.combobox = combobox;
    }
    private List<String> arrValidate;

    public List<String> getArrValidate() {
        return arrValidate;
    }

    public void setArrValidate(List<String> arrValidate) {
        this.arrValidate = arrValidate;
    }

    public boolean isValidate() {
        for (String string : arrValidate) {
            if (string.contains("require") && combobox.getValue() == null) {
                SSCMessage.showError(combobox.getScene(), lableText);
                return true;
            }
        }
        return false;
    }

    private String lableText;

    public SSCCombobox(String label, String hintText, HBox form, int child, String userData, List<String> arrValidate) {
        lableText = label;
        this.arrValidate = arrValidate;
        VBox vb = new VBox();
        vb.getStyleClass().setAll("formGroupItem");
        Label lb = new Label(label);
        lb.getStyleClass().setAll("labelFor");
        combobox = new JFXComboBox();
        combobox.setPromptText(hintText);
        combobox.setUserData(userData);
        combobox.setPrefHeight(30);
        combobox.setPrefWidth(form.getPrefWidth() / child);
        combobox.getStyleClass().setAll("my-checbox");
        vb.getChildren().add(lb);
        vb.getChildren().add(combobox);
        form.getChildren().add(vb);
    }

    public SSCCombobox(String label, String hintText, VBox form, String userData, List<String> arrValidate) {
        lableText = label;
        this.arrValidate = arrValidate;
        VBox vb = new VBox();
        vb.getStyleClass().setAll("formGroupItem");
        Label lb = new Label(label);
        lb.getStyleClass().setAll("labelFor");
        combobox = new JFXComboBox();
        combobox.setUserData(userData);
        combobox.setPrefHeight(30);
        combobox.getStyleClass().setAll("my-checbox");
        combobox.setPromptText(hintText);
        vb.getChildren().add(lb);
        vb.getChildren().add(combobox);
        form.getChildren().add(vb);
    }

    public String getValue() {
        return combobox.getValue()==null?"":combobox.getValue().toString();
    }

    public void requestFocus() {
        combobox.requestFocus();
    }

    public ObservableList<String> getItems() {
        return combobox.getItems();
    }

    public void setItems(ObservableList<String> items) {
        combobox.setItems(items);
    }

    public void setPromptText(String hintText) {
        combobox.setPromptText(hintText);
    }

    public SingleSelectionModel getSelectionModel() {
        return combobox.getSelectionModel();
    }
}
