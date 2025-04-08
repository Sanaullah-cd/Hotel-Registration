
package hotelregistration;

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

public class FXMLDocumentController implements Initializable {
    
    @FXML
    private Button btnSave;
    
    @FXML
    private Circle profileCircle;
    
    @FXML
    private Pane btnDashboard, btnReserve, btnCustomer, btnRoom, btnAbout, btnExit;
    
    @FXML
    private AnchorPane paneDashboard, paneReserve, paneCustomer;
    
    int selectedIndex = 0;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      Image image = new Image(getClass().getResource("icons/hotel.jpg").toExternalForm());
    profileCircle.setFill(new ImagePattern(image));
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
          btnReserve.setStyle("-fx-background-color: #E8F0FF; -fx-background-radius: 10px; -fx-border-color: #0455A7; fx-border-radius: 10px;");
          btnDashboard.setStyle("-fx-background-color: white; -fx-background-radius: 10px; -fx-border-color: none; fx-border-radius: 10px;");
          btnCustomer.setStyle("-fx-background-color: white; -fx-background-radius: 10px; -fx-border-color: none; fx-border-radius: 10px;");
          btnRoom.setStyle("-fx-background-color: white; -fx-background-radius: 10px; -fx-border-color: none; fx-border-radius: 10px;");
          btnAbout.setStyle("-fx-background-color: white; -fx-background-radius: 10px; -fx-border-color: none; fx-border-radius: 10px;");
      });
      btnCustomer.setOnMouseClicked(event -> {
          paneDashboard.setVisible(false);
          paneReserve.setVisible(false);
          paneCustomer.setVisible(true);
          btnCustomer.setStyle("-fx-background-color: #E8F0FF; -fx-background-radius: 10px; -fx-border-color: #0455A7; fx-border-radius: 10px;");
          btnDashboard.setStyle("-fx-background-color: white; -fx-background-radius: 10px; -fx-border-color: none; fx-border-radius: 10px;");
          btnReserve.setStyle("-fx-background-color: white; -fx-background-radius: 10px; -fx-border-color: none; fx-border-radius: 10px;");
          btnRoom.setStyle("-fx-background-color: white; -fx-background-radius: 10px; -fx-border-color: none; fx-border-radius: 10px;");
          btnAbout.setStyle("-fx-background-color: white; -fx-background-radius: 10px; -fx-border-color: none; fx-border-radius: 10px;");
      });
    }
    
}
