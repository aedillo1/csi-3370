package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import utils.DBUtils;

public class RegisterEmployeeController implements Initializable {

    @FXML
    private TextField addID;

    @FXML
    private TextField addJob;

    @FXML
    private TextField addName;

    @FXML
    private PasswordField addPass;

    @FXML
    private TextField addSSN;

    @FXML
    private TextField addSalary;

    @FXML
    private PasswordField checkPass;

    @FXML
    private Button registerBtn;

    @FXML
    private TextField checkPass_see;

    @FXML
    private TextField addPass_see;

    @FXML
    private CheckBox checkToggle;

    String q;
    ResultSet rs;
    PreparedStatement preStmt;
    Connection conn;

    @Override 
    public void initialize(URL url, ResourceBundle rb) {
        loadData();
    }

    @FXML
    public void registerEmployee(ActionEvent event) {
        if (addID.getText().isEmpty() || addJob.getText().isEmpty()
            || addName.getText().isEmpty() || addPass.getText().isEmpty()
            || addSSN.getText().isEmpty() || addSalary.getText().isEmpty())
        {
            JOptionPane.showMessageDialog(null, "Fill in all the slots");
        }
        else if (!addID.getText().matches("[0-9]+"))
        {
            JOptionPane.showMessageDialog(null, "ID only accepts digits");
        }
        else
        {
            checkPassword();
        }
    }

    private void checkPassword() {
        if (!addPass.getText().equals(checkPass.getText()))
        {
            JOptionPane.showMessageDialog(null, "Passwords do not match.");
        }
        else  
        {
            registerEmp(addID.getText(), addJob.getText(), addName.getText(), addPass.getText(), addSSN.getText(), addSalary.getText());
        }
    }

    private void registerEmp(String id, String job, String name, String pass, String ssn, String salary) {
        try {
            q = "INSERT INTO employees (EMP_ID, EMP_JOB, EMP_NAME, EMP_PASS, EMP_SSN, EMP_SALARY) VALUES (?,?,?,?,?,?)";
            preStmt = conn.prepareStatement(q);
            preStmt.setString(1, id);
            preStmt.setString(2, job.toUpperCase());
            preStmt.setString(3, name);
            preStmt.setString(4, pass);
            preStmt.setString(5, ssn);
            preStmt.setString(6, salary);

            preStmt.executeQuery();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        JOptionPane.showMessageDialog(null, "Employee successfully registered.");
        resetText();
    }

    @FXML
    public void toggleVisiblePasswords(ActionEvent event) {
        if (checkToggle.isSelected()) {
            addPass_see.setText(addPass.getText());
            checkPass_see.setText(checkPass.getText());
            addPass_see.setVisible(true);
            checkPass_see.setVisible(true);
            addPass.setVisible(false);
            checkPass.setVisible(false);
            return;
        }
        addPass.setText(addPass_see.getText());
        checkPass.setText(checkPass_see.getText());
        addPass.setVisible(true);
        checkPass.setVisible(true);
        addPass_see.setVisible(false);
        checkPass_see.setVisible(false);
    }

    private void resetText() {
        addID.setText("");
        addJob.setText("");
        addName.setText("");
        addPass.setText("");
        addSSN.setText("");
        addSalary.setText("");
    }

    private void loadData() {
        conn = DBUtils.conDB();
    }
}
