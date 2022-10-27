package application;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import javafx.event.ActionEvent;


import utils.DBUtils;
public class LoginPageController {

    public boolean checkLogin(ActionEvent event, TextField userID, TextField userPass, Connection conn,
    PreparedStatement pst, ResultSet rs, boolean bypass) throws IOException {
        if (bypass){
            return true;

        }
        String uname = userID.getText();
        String pass = userPass.getText();

        if(uname.equals("") && pass.equals(""))
        {
            JOptionPane.showMessageDialog(null, "Username and Password Blank");
        }
        else
        {
            try {
                conn = DBUtils.conDB();
                String st = "employees";

                pst = conn.prepareStatement("SELECT * FROM " + st + " WHERE EMP_ID=? AND EMP_PASS=?");

                pst.setString(1, uname);
                pst.setString(2, pass);

                rs = pst.executeQuery();

                if(rs.next())
                {
                    JOptionPane.showMessageDialog(null, "Login Success");
                    DBUtils.uname = uname;
                    System.out.println(DBUtils.uname);
                    return true;
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "Login Failed");
                    userID.setText("");
                    userPass.setText("");
                    userID.requestFocus();
                }
            } catch (SQLException e) {
                System.out.println("wrong sql command");
                e.printStackTrace();
            }
        }
        return false;

    }
    public String getJobType(ActionEvent event, Connection conn, 
    PreparedStatement pst, ResultSet rs, TextField userID) throws IOException{
        String table = "employees";
        String column = "EMP_JOB";
        String employee_type;
        try{
            conn = DBUtils.conDB();
            pst = conn.prepareStatement("SELECT " + column + " FROM " + table + " WHERE EMP_ID=?");
            pst.setString(1, userID.getText());
            rs = pst.executeQuery();
            while(rs.next()){
                employee_type = rs.getString(column);
                return employee_type;
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Employee Type cannot be found");
        }
        return null;
    }
}
