/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MainView.SubView.Login;

import FormComponent.SSCLoginForm;
import FormComponent.SSCMessage;
import FormComponent.SSCPasswdField;
import FormComponent.SSCTextField;
import Setup.LoginTask;
import java.io.File;
import java.util.Arrays;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

/**
 * @author PC
 */
public class LoginView {
    public void LoginView() {

    }
    private Button btnSignIn;
    private Button btnSignUp;
    private Button btnLoginPage;
    private Button btnViewPage;
    private Button btnPricePage;
    private AnchorPane apLogin;
    private AnchorPane paneLogin;
    private AnchorPane paneContentContainer;
    private AnchorPane apInitAppViewContainer;
    private SSCLoginForm loginForm;
    private SSCTextField txtUsername;
    private SSCPasswdField txtPassword;
    public void initView(Scene scene) {
        this.paneLogin = ((AnchorPane) scene.lookup("#paneLogin"));
        this.paneContentContainer = ((AnchorPane) scene.lookup("#paneContentContainer"));
        Image image = new Image(new File("assets/login_bg.jpg").toURI().toString());
        ((ImageView) scene.lookup("#imgLogin")).setImage(image);
        paneLogin.setVisible(true);
        paneContentContainer.setVisible(false);
        loginForm = new SSCLoginForm(scene, "apLogin", "Đăng nhập");

        txtUsername = new SSCTextField("Tên đăng nhập", "Nhập tên đăng nhập ...", loginForm.getFormElement(), "txtUsername", Arrays.asList("require"));
        txtPassword = new SSCPasswdField("Mật khẩu", "Nhập mật khẩu ...", loginForm.getFormElement(), "txtPassword", Arrays.asList("require"));

        paneContentContainer.setVisible(true);
        
    }
}
