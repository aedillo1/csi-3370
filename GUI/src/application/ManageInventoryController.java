package application;

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
import models.Product;

public class ManageInventoryController implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private TableColumn<Product, String> col_ID;

    @FXML
    private TableColumn<Product, String> col_category;

    @FXML
    private TableColumn<Product, String> col_name;

    @FXML
    private TableColumn<Product, String> col_stock;

    @FXML
    private TableColumn<Product, String> col_status;

    @FXML
    private TableView<Product> tableView;

    @FXML
    private TextField searchField;

    @FXML
    private TextField text_ID;

    @FXML
    private TextField text_category;

    @FXML
    private TextField text_name;

    @FXML
    private TextField text_stock;

    @FXML
    private TextField text_status;

    String q;
    ResultSet rs;
    PreparedStatement preStmt;
    Connection conn;
    Product pt;

    ObservableList<Product> ptList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadData();

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
    public void lowStock(ActionEvent event) {
        try {
            ptList.clear();
            String st = "SELECT * FROM products WHERE PT_STATUS = 'LOW'";

            preStmt = conn.prepareStatement(st); 
            rs = preStmt.executeQuery();

            while (rs.next()) {
                ptList.add(new Product(
                    rs.getInt("PT_ID"), 
                    rs.getString("PT_NAME"), 
                    rs.getString("PT_DESC"), 
                    rs.getInt("PT_STOCK"), 
                    rs.getString("PT_STATUS")));
                tableView.setItems(ptList);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
    public void addItem(ActionEvent event) {
        //add item to the database
        if (text_ID.getText().isEmpty() || text_category.getText().isEmpty() 
            || text_name.getText().isEmpty() || text_stock.getText().isEmpty() || text_status.getText().isEmpty())
        {
            //you have to fill in all the slots
            JOptionPane.showMessageDialog(null, "fill in all the slots");
        }
        else
        {
            saveData();
        }
    }

    public void resetText()
    {
        text_ID.setText("");
        text_name.setText("");
        text_category.setText("");
        text_stock.setText("");
        text_status.setText("");
    }

    public void saveData()
    {
        // checks if the ID or stock only contains digits
        if (!text_ID.getText().matches("[0-9]+") || !text_stock.getText().matches("[0-9]+"))
        {
            JOptionPane.showMessageDialog(null, "ID/Stock only accepts digits");
        }
        else {
            try {
                String st = "INSERT INTO products (PT_ID, PT_NAME, PT_DESC, PT_STOCK, PT_STATUS) VALUES (?,?,?,?,?)";
                preStmt = conn.prepareStatement(st);
                preStmt.setString(1, text_ID.getText());
                preStmt.setString(2, text_name.getText());
                preStmt.setString(3, text_category.getText());
                preStmt.setString(4, text_stock.getText());
                preStmt.setString(5, text_status.getText());
    
                preStmt.executeUpdate();
    
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }

            resetText();
            refreshTable();
        }
    }

    public void displaySearchResult() {
        try {
            ptList.clear();
            String st = searchField.getText();
            if (st.isEmpty()) {
                q = "SELECT * FROM products";
            } else {
                q = "SELECT * FROM products WHERE PT_ID = " + st;
            }

            preStmt = conn.prepareStatement(q);
            rs = preStmt.executeQuery();

            while (rs.next()) {
                ptList.add(new Product(
                    rs.getInt("PT_ID"), 
                    rs.getString("PT_NAME"), 
                    rs.getString("PT_DESC"), 
                    rs.getInt("PT_STOCK"), 
                    rs.getString("PT_STATUS")));

                tableView.setItems(ptList);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void removeItem(ActionEvent event) {
        if (text_ID.getText().isEmpty())
        {
            JOptionPane.showMessageDialog(null, "fill in id slot to delete");
        }
        else {
            try {
                String st = "DELETE FROM products WHERE PT_ID = ?";
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
    public void refreshTable() {

        try {
            ptList.clear();

            q = "SELECT * FROM products";
            preStmt = conn.prepareStatement(q);
            rs = preStmt.executeQuery();

            while (rs.next()) {
                //when the sql starts, it's going to add everything from the products table
                //and into the tableview of the fxml
                //it is added by going to the models class and getting every variable that each product has.
                ptList.add(new Product(
                    rs.getInt("PT_ID"), 
                    rs.getString("PT_NAME"),  
                    rs.getString("PT_DESC"),  
                    rs.getInt("PT_STOCK"),  
                    rs.getString("PT_STATUS")));

                tableView.setItems(ptList);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void updateItem(ActionEvent event) {
        if (text_ID.getText().isEmpty() || text_stock.getText().isEmpty())
        {
            JOptionPane.showMessageDialog(null, "fill in id slot and stock/status slots to update");
        }
        else if (!text_stock.getText().isEmpty() && text_status.getText().isEmpty())
        {
            //if the stock is not empty and the status is, then update only the stock
            try {
                String st = "UPDATE products SET PT_STOCK = ? WHERE PT_ID = ?";
                preStmt = conn.prepareStatement(st);
                preStmt.setString(1, text_stock.getText());
                preStmt.setString(2, text_ID.getText());
    
                preStmt.executeUpdate();
    
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        else if (text_stock.getText().isEmpty() && !text_status.getText().isEmpty())
        {
            //if status is not empty and stock is, then update only the status
            try {
                String st = "UPDATE products SET PT_STATUS = ? WHERE PT_ID = ?";
                preStmt = conn.prepareStatement(st);
                preStmt.setString(1, text_status.getText());
                preStmt.setString(2, text_ID.getText());
    
                preStmt.executeUpdate();
    
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        else
        {
            //update both status and stock if they have values
            try {
                String st = "UPDATE products SET PT_STATUS = ?, PT_STOCK = ? WHERE PT_ID = ?";
                preStmt = conn.prepareStatement(st);
                preStmt.setString(1, text_status.getText());
                preStmt.setString(2, text_stock.getText());
                preStmt.setString(3, text_ID.getText());
    
                preStmt.executeUpdate();
    
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        resetText();
        refreshTable();
    }

    private void loadData() {

        conn = DBUtils.conDB();
        refreshTable();

        col_ID.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        col_category.setCellValueFactory(new PropertyValueFactory<>("desc"));
        col_stock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        col_status.setCellValueFactory(new PropertyValueFactory<>("status"));
    }

}
