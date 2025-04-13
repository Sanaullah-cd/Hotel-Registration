
package hotelregistration;

import hotelregistration.model.Customers;
import hotelregistration.model.Room;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class FXMLDocumentController implements Initializable {
    
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    
    @FXML
    private Button btnSave, btnCustomerSave, btnCustomerNew, btnRoomSave, btnRoomNew;
    
    @FXML
    private Circle profileCircle;
    
    @FXML
    private TextField txtCustomerName, txtCustomerFName, txtCustomerAddress, txtCustomerPhone;
    
    @FXML
    private ComboBox<String> txtRoomCategory;
    
    @FXML
    private TextField txtRoomNo, txtRoomName, txtRoomPrice;
    
    @FXML
    private Pane btnDashboard, btnReserve, btnCustomer, btnRoom, btnAbout, btnExit;
    
    @FXML
    private AnchorPane paneDashboard, paneReserve, paneCustomer, paneRoom, paneAbout;
    
    @FXML
    private TableView<Customers> tblCustomer;
    
    @FXML
    private TableView<Room> tblRoom;

    @FXML
    private TableColumn<Customers, String> colCustomerName, colCustomerFName, colCustomerAddress, colCustomerPhone, colCustomerCreatedAt;
    
    @FXML
    private TableColumn<Room, String> colRoomNo, colRoomName, colRoomCategory, colRoomPrice, colRoomCreatedAt;
    
    ObservableList<Customers> customerList = FXCollections.observableArrayList();
    
    ObservableList<Room> roomList = FXCollections.observableArrayList();
    
    void connect() throws ClassNotFoundException{
         try {
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection("jdbc:sqlite:database/hotel regis.db");
            System.out.println("Connect");
        } catch (SQLException ex) {
             showAlertDialog(ex.getMessage().toString(), "Databse error");
        }
    }
    
    void showAlertDialog(String message, String subTitle){
        Alert alertInfo = new Alert(Alert.AlertType.WARNING, message, ButtonType.OK);
        alertInfo.setHeaderText(message);
        alertInfo.setTitle(subTitle);
        alertInfo.showAndWait();
    }
    void showAlertInfo(String message, String subTitle){
        Alert alertInfo = new Alert(Alert.AlertType.INFORMATION, message, ButtonType.OK);
        alertInfo.setHeaderText(message);
        alertInfo.setTitle(subTitle);
        alertInfo.showAndWait();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        try {
            connect();
        } catch (ClassNotFoundException ex) {
            showAlertDialog(ex.getMessage().toString(), "Databse error");
        }
        
    Image image = new Image(getClass().getResource("icons/hotel.jpg").toExternalForm());
    profileCircle.setFill(new ImagePattern(image));
    
    txtRoomCategory.getItems().addAll("Economy", "Standard", "Deluxe", "Luxury / Premium", "VIP");
    
    loadCustomerColumns();
    loadCustomerData();
    loadRoomColumns();
    loadRoomData();
    
    btnExit.setOnMouseClicked(event -> {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit Confrom");
        alert.setHeaderText("Exit?");
        alert.setContentText("Are you sure exit?");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            System.exit(0);
        }
    });
      btnDashboard.setOnMouseClicked(event -> {
          paneDashboard.setVisible(true);
          paneReserve.setVisible(false);
          paneCustomer.setVisible(false);
          paneRoom.setVisible(false);
          paneAbout.setVisible(false);
          btnDashboard.setStyle("-fx-background-color: #E8F0FF; -fx-background-radius: 10px; -fx-border-color: #0455A7; fx-border-radius: 10px;");
          btnReserve.setStyle("-fx-background-color: white; -fx-background-radius: 10px; -fx-border-color: none; fx-border-radius: 10px;");
          btnCustomer.setStyle("-fx-background-color: white; -fx-background-radius: 10px; -fx-border-color: none; fx-border-radius: 10px;");
          btnRoom.setStyle("-fx-background-color: white; -fx-background-radius: 10px; -fx-border-color: none; fx-border-radius: 10px;");
          btnAbout.setStyle("-fx-background-color: white; -fx-background-radius: 10px; -fx-border-color: none; fx-border-radius: 10px;");
          
      });
      btnReserve.setOnMouseClicked(event -> {
          paneDashboard.setVisible(false);
          paneReserve.setVisible(true);
          paneCustomer.setVisible(false);
          paneRoom.setVisible(false);
          paneAbout.setVisible(false);
          btnReserve.setStyle("-fx-background-color: #E8F0FF; -fx-background-radius: 10px; -fx-border-color: #0455A7; fx-border-radius: 10px;");
          btnDashboard.setStyle("-fx-background-color: white; -fx-background-radius: 10px; -fx-border-color: none; fx-border-radius: 10px;");
          btnCustomer.setStyle("-fx-background-color: white; -fx-background-radius: 10px; -fx-border-color: none; fx-border-radius: 10px;");
          btnRoom.setStyle("-fx-background-color: white; -fx-background-radius: 10px; -fx-border-color: none; fx-border-radius: 10px;");
          btnAbout.setStyle("-fx-background-color: white; -fx-background-radius: 10px; -fx-border-color: none; fx-border-radius: 10px;");
      });
      btnCustomer.setOnMouseClicked(event -> {
          loadCustomerData();
          txtCustomerName.requestFocus();
          paneDashboard.setVisible(false);
          paneReserve.setVisible(false);
          paneCustomer.setVisible(true);
          paneRoom.setVisible(false);
          paneAbout.setVisible(false);
          btnCustomer.setStyle("-fx-background-color: #E8F0FF; -fx-background-radius: 10px; -fx-border-color: #0455A7; fx-border-radius: 10px;");
          btnDashboard.setStyle("-fx-background-color: white; -fx-background-radius: 10px; -fx-border-color: none; fx-border-radius: 10px;");
          btnReserve.setStyle("-fx-background-color: white; -fx-background-radius: 10px; -fx-border-color: none; fx-border-radius: 10px;");
          btnRoom.setStyle("-fx-background-color: white; -fx-background-radius: 10px; -fx-border-color: none; fx-border-radius: 10px;");
          btnAbout.setStyle("-fx-background-color: white; -fx-background-radius: 10px; -fx-border-color: none; fx-border-radius: 10px;");
      });
      btnRoom.setOnMouseClicked(event -> {
          loadRoomData();
          paneDashboard.setVisible(false);
          paneReserve.setVisible(false);
          paneCustomer.setVisible(false);
          paneRoom.setVisible(true);
          paneAbout.setVisible(false);
          btnRoom.setStyle("-fx-background-color: #E8F0FF; -fx-background-radius: 10px; -fx-border-color: #0455A7; fx-border-radius: 10px;");
          btnDashboard.setStyle("-fx-background-color: white; -fx-background-radius: 10px; -fx-border-color: none; fx-border-radius: 10px;");
          btnReserve.setStyle("-fx-background-color: white; -fx-background-radius: 10px; -fx-border-color: none; fx-border-radius: 10px;");
          btnCustomer.setStyle("-fx-background-color: white; -fx-background-radius: 10px; -fx-border-color: none; fx-border-radius: 10px;");
          btnAbout.setStyle("-fx-background-color: white; -fx-background-radius: 10px; -fx-border-color: none; fx-border-radius: 10px;");
      });
      btnAbout.setOnMouseClicked(event -> {
          paneDashboard.setVisible(false);
          paneReserve.setVisible(false);
          paneCustomer.setVisible(false);
          paneRoom.setVisible(false);
          paneAbout.setVisible(true);
          btnAbout.setStyle("-fx-background-color: #E8F0FF; -fx-background-radius: 10px; -fx-border-color: #0455A7; fx-border-radius: 10px;");
          btnDashboard.setStyle("-fx-background-color: white; -fx-background-radius: 10px; -fx-border-color: none; fx-border-radius: 10px;");
          btnReserve.setStyle("-fx-background-color: white; -fx-background-radius: 10px; -fx-border-color: none; fx-border-radius: 10px;");
          btnCustomer.setStyle("-fx-background-color: white; -fx-background-radius: 10px; -fx-border-color: none; fx-border-radius: 10px;");
          btnRoom.setStyle("-fx-background-color: white; -fx-background-radius: 10px; -fx-border-color: none; fx-border-radius: 10px;");
      });
      
      
      // Action Button Events
      //---------------
      
      btnCustomerSave.setOnAction(event -> {
          String name = txtCustomerName.getText();
          String fname = txtCustomerFName.getText();
          String address = txtCustomerAddress.getText();
          String phone = txtCustomerPhone.getText();
          LocalDate today = LocalDate.now();
          if(name.isEmpty()){
              showAlertDialog("Please type a name", "Required");
              txtCustomerName.requestFocus();
          }else{
            try {
              ps = con.prepareStatement("insert into customer (name,fname,address,phone,created_at)values(?,?,?,?,?)");
              ps.setString(1, name);
              ps.setString(2, fname);
              ps.setString(3, address);
              ps.setString(4, phone);
              ps.setString(5, today.toString());
              ps.executeUpdate();
              showAlertInfo("Successfully Saved", "Done Message");
              emptyCustomerField();
              loadCustomerData();
            } catch (Exception e) {
                showAlertDialog(e.getMessage().toString(), "Error");
            }
          }
      });
      btnCustomerNew.setOnAction(event -> {
        emptyCustomerField();
      });
      btnRoomSave.setOnAction(event -> {
          String roomNo = txtRoomNo.getText();
          String roomName = txtRoomName.getText();
          String category = txtRoomCategory.getValue();
          String price = txtRoomPrice.getText();
          LocalDate today = LocalDate.now();
          if(roomNo.isEmpty()){
              showAlertDialog("Please type a room no", "Required");
              txtRoomNo.requestFocus();
          }else{
            try {
              ps = con.prepareStatement("insert into room (room_no,room_name,category,price,created_at)values(?,?,?,?,?)");
              ps.setString(1, roomNo);
              ps.setString(2, roomName);
              ps.setString(3, category);
              ps.setString(4, price);
              ps.setString(5, today.toString());
              ps.executeUpdate();
              showAlertInfo("Successfully Saved", "Done Message");
              emptyRoomField();
              loadRoomData();
            } catch (Exception e) {
                showAlertDialog(e.getMessage().toString(), "Error");
            }
          }
      });
      btnRoomNew.setOnAction(event -> {
          emptyRoomField();
      });
      tblCustomer.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                Customers selectedCustomer= tblCustomer.getSelectionModel().getSelectedItem();
                if (selectedCustomer != null) {
                    try {
                        ps = con.prepareStatement("delete from customer where name = ?");
                        ps.setString(1, selectedCustomer.getName());
                        
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle("Delete Message");
                        alert.setHeaderText("Delete?");
                        alert.setContentText("Are you sure delete, item undone?");

                        Optional<ButtonType> result = alert.showAndWait();

                        if (result.isPresent() && result.get() == ButtonType.OK) {
                           int r = ps.executeUpdate();
                        
                            if(r > 0){
                                showAlertInfo("Successfully deleted.", "Done Message");
                                loadCustomerData();
                            }else{
                                showAlertInfo("No date", "Message");
                            }
                        }
                    } catch (Exception e) {
                        showAlertDialog(e.getMessage().toString(), "Database Error");
                    }
                }
            }
      });
      tblRoom.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                Room selectedRoom = tblRoom.getSelectionModel().getSelectedItem();
                if (selectedRoom != null) {
                    try {
                        ps = con.prepareStatement("delete from room where room_no = ?");
                        ps.setString(1, selectedRoom.getRoomNo());
                        
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle("Delete Message");
                        alert.setHeaderText("Delete?");
                        alert.setContentText("Are you sure delete, item undone?");

                        Optional<ButtonType> result = alert.showAndWait();

                        if (result.isPresent() && result.get() == ButtonType.OK) {
                           int r = ps.executeUpdate();
                        
                            if(r > 0){
                                showAlertInfo("Successfully deleted.", "Done Message");
                                loadRoomData();
                            }else{
                                showAlertInfo("No date", "Message");
                            }  
                        }
                    } catch (Exception e) {
                        showAlertDialog(e.getMessage().toString(), "Database Error");
                    }
                }
            }
      });
      
    }
   
        void emptyCustomerField(){
                txtCustomerAddress.setText("");
                txtCustomerFName.setText("");
                txtCustomerName.setText("");
                txtCustomerPhone.setText("");
                txtCustomerName.requestFocus();
        }
        
        void emptyRoomField(){
                txtRoomNo.setText("");
                txtRoomName.setText("");
                txtRoomPrice.setText("");
                txtRoomNo.requestFocus();
        }
        
        void loadCustomerData(){
            customerList.clear();
            try {
                ps = con.prepareStatement("select * from customer order by created_at desc");
                rs = ps.executeQuery();
                while(rs.next()){
                    customerList.add(new Customers(
                            rs.getString("name"),
                            rs.getString("fname"),
                            rs.getString("address"),
                            rs.getString("phone"),
                            rs.getString("created_at")
                    ));
                    tblCustomer.setItems(customerList);
                }
            } catch (Exception e) {
                showAlertDialog(e.getMessage().toString(), "Database Error");
            }
        }
        
        void loadRoomData(){
            roomList.clear();
            try {
                ps = con.prepareStatement("select * from room order by created_at desc");
                rs = ps.executeQuery();
                while(rs.next()){
                    roomList.add(new Room(
                            rs.getString("room_no"),
                            rs.getString("room_name"),
                            rs.getString("category"),
                            rs.getString("price")+"$",
                            rs.getString("created_at")
                    ));
                    tblRoom.setItems(roomList);
                }
            } catch (Exception e) {
                showAlertDialog(e.getMessage().toString(), "Database Error");
            }
        }
        
        private void loadCustomerColumns(){
            colCustomerName.setCellValueFactory(new PropertyValueFactory<>("name"));
            colCustomerFName.setCellValueFactory(new PropertyValueFactory<>("fname"));
            colCustomerAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
            colCustomerPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
            colCustomerCreatedAt.setCellValueFactory(new PropertyValueFactory<>("createdAt"));
        }
        private void loadRoomColumns(){
            colRoomNo.setCellValueFactory(new PropertyValueFactory<>("roomNo"));
            colRoomName.setCellValueFactory(new PropertyValueFactory<>("roomName"));
            colRoomCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
            colRoomPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
            colRoomCreatedAt.setCellValueFactory(new PropertyValueFactory<>("createdAt"));
        }

}
