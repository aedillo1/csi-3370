package application;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Node;

import utils.DBUtils;

public class SceneController {
    private Stage stage;
    private Scene scene;
    private Parent root;
    private boolean allowAccess;

    @FXML
    private CheckBox checkBox;

    @FXML
    private Button loginBtn;

    @FXML
    private TextField pass_text;

    @FXML
    private TextField userID;

    @FXML
    private PasswordField userPass;

    @FXML 
    private Button man_employee;

    private Connection conn;
    private PreparedStatement pst;
    private ResultSet rs;;

    private boolean bypass = false;

    //Created to bypass the login, sets bypass to true and ignores the login code
    public void bypass_login(ActionEvent event) throws IOException{
        bypass = true;
        switchTo_mainMenu(event);
    }

    //Switches to main menu when the login has successfully pass, otherwise bypass will switch to it
    public void switchTo_mainMenu(ActionEvent event) throws IOException {
        allowAccess = false;
        DBUtils.employee_type = "IT MANAGER";
        
        LoginPageController login = new LoginPageController();
        allowAccess = login.checkLogin(event, userID, userPass, conn, pst, rs, bypass);
        DBUtils.employee_type = login.getJobType(event, conn, pst, rs, userID);
        if (allowAccess == true || bypass == true){
            root = FXMLLoader.load(getClass().getResource("mainMenu.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }

    @FXML
    public void showPassword(ActionEvent event) {
        if (checkBox.isSelected()) {
            pass_text.setText(userPass.getText());
            pass_text.setVisible(true);
            userPass.setVisible(false);
            return;
        }
        userPass.setText(pass_text.getText());
        userPass.setVisible(true);
        pass_text.setVisible(false);
    }

    public static String getEmployee_Type(){
        return DBUtils.employee_type;
    }
}
