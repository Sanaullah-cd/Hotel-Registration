
package hotelregistration;

import hotelregistration.model.Customers;
import hotelregistration.model.Reservation;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class FXMLDocumentController implements Initializable {
    
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    
    @FXML
    private Button btnSave, btnCustomerSave, btnCustomerNew, btnRoomSave,btnRoomNew,btnResSave,btnResNew;
    
    @FXML
    private Circle profileCircle;
    
    @FXML
    private TextField txtCustomerName, txtCustomerFName, txtCustomerAddress, txtCustomerPhone, txtResDuration,txtResPrice,txtResTotal,txtResPayments,txtResRemine;
    
    @FXML
    private ComboBox<String> txtRoomCategory,txtResCustomer,txtResRoom;
    
    @FXML
    private DatePicker txtResDate;
            
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
    private TableView<Reservation> tblRes;

    @FXML
    private TableColumn<Reservation, String> colResCustomer,colResRoom,colResDate,colResDuration, colResPrice,colResTotal,colResPayment,colResRemine,colResCreated;
    
    @FXML
    private TableColumn<Customers, String> colCustomerName, colCustomerFName, colCustomerAddress, colCustomerPhone, colCustomerCreatedAt;
    
    @FXML
    private TableColumn<Room, String> colRoomNo, colRoomName, colRoomCategory, colRoomPrice, colRoomCreatedAt;
    
    ObservableList<Customers> customerList = FXCollections.observableArrayList();
    
    ObservableList<Room> roomList = FXCollections.observableArrayList();
    
    ObservableList<Reservation> reserveList = FXCollections.observableArrayList();

    
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
    loadCustomerRoom();
    loadResColumns();
    loadReserveData();
    
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
          txtResCustomer.getItems().removeAll();
          txtResRoom.getItems().removeAll();
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
      btnResSave.setOnAction(event -> {
          String customer = txtResCustomer.getValue();
          String room = txtResRoom.getValue();
          String reserve_date = txtResDate.getValue().toString();
          String day_duration = txtResDuration.getText();
          String price = txtResPrice.getText();
          String total = txtResTotal.getText();
          String payment = txtResPayments.getText();
          String remine = txtResRemine.getText();
          LocalDate today = LocalDate.now();
          if(customer.isEmpty()){
              showAlertDialog("Please type a room no", "Required");
              txtResCustomer.requestFocus();
          }else{
            try {
              ps = con.prepareStatement("insert into reservation (customer,room,reserve_date,day_duration,price,total,payment,remine,created)values(?,?,?,?,?,?,?,?,?)");
              ps.setString(1, customer);
              ps.setString(2, room);
              ps.setString(3, reserve_date);
              ps.setString(4, day_duration);
              ps.setString(5, price);
              ps.setString(6, total);
              ps.setString(7, payment);
              ps.setString(8, remine);
              ps.setString(9, today.toString());
              ps.executeUpdate();
              showAlertInfo("Successfully Saved", "Done Message");
              emptyReserveField();
              loadReserveData();
            } catch (Exception e) {
                showAlertDialog(e.getMessage().toString(), "Error");
            }
          }
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
        void emptyReserveField(){
                txtResDuration.setText("");
                txtResPayments.setText("");
                txtResPrice.setText("");
                txtResTotal.setText("");
                txtResRemine.setText("");
                txtResCustomer.requestFocus();
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
        
        void loadReserveData(){
            reserveList.clear();
            try {
                ps = con.prepareStatement("select * from reservation order by created desc");
                rs = ps.executeQuery();
                while(rs.next()){
                    reserveList.add(new Reservation(
                            rs.getString("customer"),
                            rs.getString("room"),
                            rs.getString("reserve_date"),
                            rs.getInt("day_duration"),
                            rs.getDouble("price"),
                            rs.getDouble("total"),
                            rs.getDouble("payment"),
                            rs.getDouble("remine"),
                            rs.getString("created")
                    ));
                    tblRes.setItems(reserveList);
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
        private void loadResColumns(){
            colResCustomer.setCellValueFactory(new PropertyValueFactory<>("customer"));
            colResRoom.setCellValueFactory(new PropertyValueFactory<>("room_no"));
            colResDate.setCellValueFactory(new PropertyValueFactory<>("reserved_date"));
            colResDuration.setCellValueFactory(new PropertyValueFactory<>("day_duration"));
            colResPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
            colResTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
            colResPayment.setCellValueFactory(new PropertyValueFactory<>("payment"));
            colResRemine.setCellValueFactory(new PropertyValueFactory<>("remine"));
            colResCreated.setCellValueFactory(new PropertyValueFactory<>("created"));
        }
        
        void loadCustomerRoom(){
            try {
                ps = con.prepareStatement("select * from customer");
                rs = ps.executeQuery();
                while(rs.next()){
                    txtResCustomer.getItems().addAll(rs.getString(1));
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            try {
                ps = con.prepareStatement("select * from room");
                rs = ps.executeQuery();
                while(rs.next()){
                    txtResRoom.getItems().addAll(rs.getString(1));
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

}
