package application;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Node;

public class MenuController {
    private Stage stage;
    private Scene scene;
    private Parent root;

    private static String employee_type;
    
    @FXML 
    private Button man_employee;

    @FXML
    private void initialize() {
        employee_type = getEmployee_Type();

        if (employee_type == null) {
            // do nothing
        } else if(employee_type.toUpperCase().equals("EMPLOYEE")){
            man_employee.setVisible(false);
        }
        
    }

    public void switchTo_loginPage(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("loginPage.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    //Switches to management inventory screen
    public void switchTo_manageInventory(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("manageInventory.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    //Switches to Employee management screen. 
    public void switchTo_manageEmployee(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("manageEmployee.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchTo_employeeInfo(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("employeeInfo.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public static String getEmployee_Type(){
        employee_type = SceneController.getEmployee_Type();
        return employee_type;

    }
}
