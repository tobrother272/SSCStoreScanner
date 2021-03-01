/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FormComponent;
import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcons;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
/**
 * @author PC
 */
public class SSCInputProgressDialog {

    private SSCVForm form;

    public SSCVForm getForm() {
        return form;
    }

    public void setForm(SSCVForm form) {
        this.form = form;
    }
    private AnchorPane rootDialog;
    private AnchorPane apContainer;
    private TableView tvProgress;

    public TableView getTvProgress() {
        return tvProgress;
    }

    public void setTvProgress(TableView tvProgress) {
        this.tvProgress = tvProgress;
    }
    
    public SSCInputProgressDialog(Scene scene,String title,String content, String buttonTitle) {
        this.apContainer = (AnchorPane) scene.lookup("#dialogPane");
        
        rootDialog = new AnchorPane();
        rootDialog.setPrefSize(apContainer.getPrefWidth()-100, apContainer.getPrefHeight()-100);
        rootDialog.getStyleClass().setAll("paneDialog");
        rootDialog.setLayoutY(-100);
        rootDialog.setLayoutX((apContainer.getPrefWidth() - rootDialog.getPrefWidth()) / 2);
        apContainer.getChildren().add(rootDialog);
        rootDialog.setOpacity(0);
        rootDialog.setVisible(false);
        Label lbLabelHeader = new Label(title);
        lbLabelHeader.getStyleClass().setAll("lbBreadScrumb");
        lbLabelHeader.setPrefSize(rootDialog.getPrefWidth()-40, 30);
        rootDialog.getChildren().add(lbLabelHeader);
        
        Label lbLabelContent = new Label(content);
        lbLabelContent.getStyleClass().setAll("lbBreadScrumbDes");
        lbLabelContent.setPrefSize(rootDialog.getPrefWidth()-40, 30);
        rootDialog.getChildren().add(lbLabelContent);
        

        Button btnClose = new Button();
        btnClose.getStyleClass().setAll("btn-dialog-close");
        GlyphsDude.setIcon(btnClose, FontAwesomeIcons.CLOSE, "1.5em", ContentDisplay.RIGHT);
        rootDialog.getChildren().add(btnClose);
        btnClose.setLayoutX(rootDialog.getPrefWidth()-40);
        lbLabelHeader.setLayoutX(25);
        lbLabelHeader.setLayoutY(50);
        lbLabelContent.setLayoutX(25);
        lbLabelContent.setLayoutY(85);
        btnClose.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                hide();
            }
        });
        rootDialog.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

            }
        });
        AnchorPane formRoot = new AnchorPane();
        formRoot.setLayoutY(lbLabelContent.getLayoutY()+lbLabelContent.getPrefHeight() + 20);
        formRoot.setPrefSize(rootDialog.getPrefWidth() /3, rootDialog.getPrefHeight()-lbLabelContent.getLayoutY() - lbLabelContent.getPrefHeight() - 45);
        rootDialog.getChildren().add(formRoot);
        formRoot.setLayoutX(20);
        form = new SSCVForm(scene, formRoot, buttonTitle);
        
        
        AnchorPane tableRoot = new AnchorPane();
        tableRoot.setLayoutY(lbLabelContent.getLayoutY()+lbLabelContent.getPrefHeight() + 20);
        tableRoot.setPrefSize(((rootDialog.getPrefWidth()/3*2)-60), rootDialog.getPrefHeight()-lbLabelContent.getLayoutY() - lbLabelContent.getPrefHeight() - 45);
        tableRoot.setLayoutY(lbLabelContent.getLayoutY()+lbLabelContent.getPrefHeight() + 20);
        tableRoot.setLayoutX(formRoot.getLayoutX()+formRoot.getPrefWidth()+20);
      
        rootDialog.getChildren().add(tableRoot);
        tvProgress=new TableView();
        tvProgress.setPrefSize(tableRoot.getPrefWidth(), tableRoot.getPrefHeight());
        tvProgress.getStyleClass().setAll("table-view-material");
        tableRoot.getChildren().add(tvProgress);
        
    }

    

    public void show() {
        this.apContainer.setVisible(true);
        this.rootDialog.setVisible(true);
        FadeTransition fadeInTransition = new FadeTransition(Duration.millis(1000), rootDialog);
        fadeInTransition.setFromValue(0.0);
        fadeInTransition.setToValue(1.0);
        fadeInTransition.play();
        fadeInTransition.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

            }
        });
        TranslateTransition tt = new TranslateTransition(Duration.millis(1000), rootDialog);
        tt.setByY(-100);
        tt.setToY(150);
        tt.play();
    }

    public void hide() {
        if (rootDialog.getOpacity() < 1) {
            this.apContainer.setVisible(false);
            return;
        }
        FadeTransition fadeInTransition = new FadeTransition(Duration.millis(300), rootDialog);
        fadeInTransition.setFromValue(1.0);
        fadeInTransition.setToValue(0.0);
        fadeInTransition.play();
        fadeInTransition.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                apContainer.setVisible(false);
                rootDialog.setVisible(false);
            }
        });
        TranslateTransition tt = new TranslateTransition(Duration.millis(300), rootDialog);
        tt.setByY(150);
        tt.setToY(-100);
        tt.play();
    }
}
