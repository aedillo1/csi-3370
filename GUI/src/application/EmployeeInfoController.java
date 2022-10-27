package application;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Node;

import utils.DBUtils;
import models.Employee;

public class EmployeeInfoController implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private Label empID;

    @FXML
    private Label empJob;

    @FXML
    private Label empName;

    @FXML
    private Label empPass;

    @FXML
    private Label empSSN;

    @FXML
    private Label empSalary;

    String q;
    ResultSet rs;
    PreparedStatement preStmt;
    Connection conn;
    Employee emp;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadData();
    }

    @FXML
    public void goBack(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("mainMenu.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private void loadData() {
        conn = DBUtils.conDB();
        
        try {
            q = "SELECT * FROM employees WHERE EMP_ID = ?";
            preStmt = conn.prepareStatement(q);
            preStmt.setString(1, DBUtils.uname);
            rs = preStmt.executeQuery();

            while (rs.next()) {
                emp = new Employee(
                    rs.getInt("EMP_ID"), 
                    rs.getString("EMP_NAME").toUpperCase(),
                    rs.getString("EMP_PASS"),
                    rs.getString("EMP_JOB").toUpperCase(),
                    rs.getString("EMP_SSN"),
                    rs.getFloat("EMP_SALARY"));
            }

            empID.setText(Integer.toString(emp.getId()));
            empJob.setText(emp.getJob());
            empName.setText(emp.getName());
            empPass.setText(emp.getPass());
            empSSN.setText(emp.getSSN());
            empSalary.setText(Float.toString(emp.getSalary()));

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
