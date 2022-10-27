package application;

import javafx.scene.Scene;
import javafx.scene.control.Button;
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

public class ManageEmployeeController implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;
    private Parent root1;

    @FXML
    private Button registerBtn;

    @FXML
    private TableColumn<Employee, String> col_ID;

    @FXML
    private TableColumn<Employee, String> col_job;

    @FXML
    private TableColumn<Employee, String> col_name;

    @FXML
    private TextField searchField;

    @FXML
    private TableView<Employee> tableView;

    @FXML
    private TextField text_ID;

    @FXML
    private TextField text_job;

    @FXML
    private TextField text_name;

    String q;
    ResultSet rs;
    PreparedStatement preStmt;
    Connection conn;
    Employee emp;

    ObservableList<Employee> empList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadData();
        
        registerBtn.setVisible(false);
        if (DBUtils.employee_type.toUpperCase().equals("IT MANAGER"))
        {
            registerBtn.setVisible(true);
        }

        searchField.setOnKeyPressed((EventHandler<? super KeyEvent>) new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent ke) {
                if (ke.getCode().equals(KeyCode.ENTER)) {
                    displaySearchResult();
                }
            }
        });
    }

    @FXML
    public void goBack(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("mainMenu.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void addEmployee(ActionEvent event) {
        if (text_ID.getText().isEmpty() || text_job.getText().isEmpty() 
        || text_name.getText().isEmpty())
        {
            JOptionPane.showMessageDialog(null, "fill in all the slots");
        }
        else
        {
            saveData();
        }
    }

    @FXML
    public void refreshTable() {
        try {
            empList.clear();

            q = "SELECT * FROM employees WHERE NOT EMP_ID = 1000";
            preStmt = conn.prepareStatement(q);
            rs = preStmt.executeQuery();

            while (rs.next()) {
                empList.add(new Employee(
                    rs.getInt("EMP_ID"), 
                    rs.getString("EMP_NAME").toUpperCase(),
                    rs.getString("EMP_PASS").toUpperCase(),
                    rs.getString("EMP_JOB").toUpperCase(),
                    rs.getString("EMP_SSN"),
                    rs.getFloat("EMP_SALARY")));

                tableView.setItems(empList);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    public void removeEmployee(ActionEvent event) {
        if (text_ID.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "fill in id slot to delete");
        }
        else {
            try {
                String st = "DELETE FROM employees WHERE EMP_ID = ?";
                preStmt = conn.prepareStatement(st);
                preStmt.setString(1, text_ID.getText());
    
                preStmt.executeUpdate();
    
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }

            resetText();
            refreshTable();
        }
    }

    @FXML
    public void updateEmployee(ActionEvent event) {

    }

    @FXML
    public void registerEmployee(ActionEvent event) throws IOException {
        root1 = FXMLLoader.load(getClass().getResource("registerEmployee.fxml"));
        Stage stage = new Stage();
        stage.setTitle("what");
        stage.setScene(new Scene(root1));
        stage.show();
    }

    public void displaySearchResult() {
        try {
            empList.clear();
            String st = searchField.getText();
            if (st.isEmpty()) {
                q = "SELECT * FROM employees WHERE NOT EMP_ID = 1000";
            } else {
                q = "SELECT * FROM employees WHERE EMP_ID = " + st + " AND NOT EMP_ID = 1000";
            }

            preStmt = conn.prepareStatement(q);
            rs = preStmt.executeQuery();

            while (rs.next()) {

                empList.add(new Employee(
                    rs.getInt("EMP_ID"), 
                    rs.getString("EMP_NAME").toUpperCase(),
                    rs.getString("EMP_PASS").toUpperCase(),
                    rs.getString("EMP_JOB").toUpperCase(),
                    rs.getString("EMP_SSN"),
                    rs.getFloat("EMP_SALARY")));

                tableView.setItems(empList);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void saveData() {
        if (!text_ID.getText().matches("[0-9]+")) {
            JOptionPane.showMessageDialog(null, "ID only accepts digits");
        }
        else {
            try {
                String st = "INSERT INTO employees (EMP_ID, EMP_NAME, EMP_JOB) VALUES (?,?,?)";
                preStmt = conn.prepareStatement(st);
                preStmt.setString(1, text_ID.getText());
                preStmt.setString(2, text_name.getText().toUpperCase());
                preStmt.setString(3, text_job.getText().toUpperCase());

                preStmt.executeUpdate();

            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }

            resetText();
            refreshTable();
        }
    }

    private void loadData() {
        conn = DBUtils.conDB();
        refreshTable();

        col_ID.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        col_job.setCellValueFactory(new PropertyValueFactory<>("job"));
    }

    public void resetText()
    {
        text_ID.setText("");
        text_name.setText("");
        text_job.setText("");
    }

}
