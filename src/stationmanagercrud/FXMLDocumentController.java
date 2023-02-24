/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stationmanagercrud;

//import StationManager.utils.ConnectionDB;
import StationManager.entities.Station;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import java.sql.*;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableRow;

public class FXMLDocumentController implements Initializable {
    

    @FXML
    private Label label;

    @FXML
    private TextField txtLoc_ID;

    @FXML
    private TextField txtZone;

    @FXML
    private TextField txtName;

    @FXML
    private Button btnUpdate;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnAdd;

    @FXML
    private TableView<Station> table;

    @FXML
    private TableColumn<Station, String> IDColmn;

    @FXML
    private TableColumn<Station, String> LocationColmn;

    @FXML
    private TableColumn<Station, String> NameColmn;

    @FXML
    private TableColumn<Station, String> ZoneColmn;

    @FXML
//////////////////////////////////////////      Ajout Station       /////////////////////////////////////////////////////////////////            
void Add(ActionEvent event) {
    String Name,Location,Zone;
    Location = txtLoc_ID.getText();
    Name = txtName.getText();
    Zone = txtZone.getText();
    if (Location.isEmpty() || Name.isEmpty() || Zone.isEmpty()) {
        // Si l'un des champs est vide, afficher un message d'erreur
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Gestion Station");
        alert.setHeaderText("Erreur lors de l'ajout de la station");
        alert.setContentText("Veuillez remplir tous les champs.");
        alert.showAndWait();
    } else if (!Location.startsWith("@")) {
        // Si Location ne commence pas par le caractère "@", afficher un message d'erreur
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Gestion Station");
        alert.setHeaderText("Erreur lors de l'ajout de la station");
        alert.setContentText("L'emplacement doit commencer par le caractère '@'.");
        alert.showAndWait();
    } else {
        try {
            // Vérifier si la station existe déjà dans la table
            pst = con.prepareStatement("SELECT * FROM registation WHERE Name=?");
            pst.setString(1, Name);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                // Si la station existe déjà, afficher un message d'erreur
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Gestion Station");
                alert.setHeaderText("Erreur lors de l'ajout de la station");
                alert.setContentText("Une station avec ce nom existe déjà.");
                alert.showAndWait();
            } else {
                // Sinon, insérer une nouvelle entrée dans la table
                pst = con.prepareStatement("INSERT INTO registation(Location,Name,Zone) VALUES (?,?,?)");
                pst.setString(1, Location);
                pst.setString(2, Name);
                pst.setString(3, Zone);
                pst.executeUpdate();
              
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Gestion Station");
                alert.setHeaderText("Enregistrement Station En Cours....");
                alert.setContentText("Station Ajoutée!");
                alert.showAndWait();
              
                table();
                txtLoc_ID.setText("");
                txtName.setText("");
                txtZone.setText("");
                txtName.requestFocus();
            }
        } catch (SQLException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}


/////////////////////////////////////// Supprimer Station /////////////////////////////////////////////////////////////////
    @FXML
    void Delete(ActionEvent event) {
    myIndex = table.getSelectionModel().getSelectedIndex();
    id = Integer.parseInt(String.valueOf(table.getItems().get(myIndex).getId()));

    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle("Gestion Station");
    alert.setHeaderText("Suppression Station");
    alert.setContentText("Voulez-vous vraiment supprimer la station ?");

    Optional<ButtonType> result = alert.showAndWait();
    if (result.get() == ButtonType.OK) {
        try {
            pst = con.prepareStatement("delete from registation where id = ? ");
            pst.setInt(1, id);
            pst.executeUpdate();

            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setTitle("Gestion Station");
            successAlert.setHeaderText("Suppression Station");
            successAlert.setContentText("La station a été supprimée.");
            successAlert.showAndWait();

            table();
        } catch (SQLException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

 
/////////////////////////////////////////////////////Modifier Station //////////////////////////////////////////////////
    @FXML
    void Update(ActionEvent event) {
    String Name, Location, Zone;
    myIndex = table.getSelectionModel().getSelectedIndex();
    id = Integer.parseInt(String.valueOf(table.getItems().get(myIndex).getId()));

    Location = txtLoc_ID.getText();
    Name = txtName.getText();
    Zone = txtZone.getText();
    
    if (Location.isEmpty() || Name.isEmpty() || Zone.isEmpty()) {
        // Si l'un des champs est vide, afficher un message d'erreur
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Gestion Station");
        alert.setHeaderText("Erreur lors de la mise à jour de la station");
        alert.setContentText("Veuillez remplir tous les champs.");
        alert.showAndWait();
    } else if (!Location.startsWith("@")) {
        // Si Location ne contient pas le caractère "@", afficher un message d'erreur
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Gestion Station");
        alert.setHeaderText("Erreur lors de la mise à jour de la station");
        alert.setContentText("L'emplacement doit commencer avec le caractère '@'.");
        alert.showAndWait();
    } else {
        try {
            pst = con.prepareStatement("SELECT * FROM registation WHERE Name=? AND id != ?");
            pst.setString(1, Name);
            pst.setInt(2, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                // Si la station existe déjà, afficher un message d'erreur
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Gestion Station");
                alert.setHeaderText("Erreur lors de la mise à jour de la station");
                alert.setContentText("Une station avec ce nom existe déjà.");
                alert.showAndWait();
            } else {
                // Afficher une boîte de dialogue de confirmation
                Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
                    confirm.setTitle("Gestion Station");
                    confirm.setHeaderText("Confirmation de la mise à jour de la station");
                    confirm.setContentText("Voulez-vous vraiment modifier cette station ?");

                    Optional<ButtonType> result = confirm.showAndWait();
                    if (result.get() == ButtonType.OK){
                    // Exécuter la mise à jour
                        pst = con.prepareStatement("update registation set Location = ?,Name = ? ,Zone = ? where id = ? ");
                        pst.setString(1, Location);
                        pst.setString(2, Name);
                        pst.setString(3, Zone);
                        pst.setInt(4, id);
                        pst.executeUpdate();
              
    
              
    table();
} else {
    // Annuler la mise à jour
    return;
}

                // Sinon, mettre à jour l'entrée dans la table
                pst = con.prepareStatement("UPDATE registation SET Location = ?, Name = ?, Zone = ? WHERE id = ?");
                pst.setString(1, Location);
                pst.setString(2, Name);
                pst.setString(3, Zone);
                pst.setInt(4, id);
                pst.executeUpdate();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Gestion Station");
                alert.setHeaderText("Mise à jour En Cours....");
                alert.setContentText("Station Modifiée!");
                alert.showAndWait();

                table();
                txtLoc_ID.setText("");
                txtName.setText("");
                txtZone.setText("");
                txtName.requestFocus();
            }
        } catch (SQLException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

////////////////////////////////////////Table Management ///////////////////////////////////////////////////////////////////
public void table()
      {
          Connect();
          ObservableList<Station> stations = FXCollections.observableArrayList();
       try
       {
           pst = con.prepareStatement("select id,Location,Name,Zone from registation");  
           ResultSet rs = pst.executeQuery();
      //{
        while (rs.next())
        {
            Station st = new Station();
            st.setId(rs.getString("id"));
            st.setLocation(rs.getString("Location"));
            st.setName(rs.getString("Name"));
            st.setZone(rs.getString("Zone"));
            stations.add(st);
       }
    //}
                table.setItems(stations);
                IDColmn.setCellValueFactory(f -> f.getValue().idProperty());
                LocationColmn.setCellValueFactory(f -> f.getValue().locationProperty());                
                NameColmn.setCellValueFactory(f -> f.getValue().nameProperty());
                ZoneColmn.setCellValueFactory(f -> f.getValue().zoneProperty());
                
              
 
       }
      
       catch (SQLException ex)
       {
           Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
       }
 
                table.setRowFactory( tv -> {
                    TableRow<Station> myRow = new TableRow<>();
                    myRow.setOnMouseClicked (event ->
     {
        if (event.getClickCount() == 1 && (!myRow.isEmpty()))
        {
            myIndex =  table.getSelectionModel().getSelectedIndex();
           id = Integer.parseInt(String.valueOf(table.getItems().get(myIndex).getId()));
           txtLoc_ID.setText(table.getItems().get(myIndex).getLocation());          
           txtName.setText(table.getItems().get(myIndex).getName());
           txtZone.setText(table.getItems().get(myIndex).getZone());
                            
                          
                        
                          
        }
     });
        return myRow;
                   });
    
    
      }    
//////////////////////////////////////////////////////// connection à la Base ////////////////////////////////////////////////////////////////////////    
    Connection con;
    PreparedStatement pst;
    int myIndex;
    int id;
    
    
    
     public void Connect()
    {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/station","root","");
        } catch (ClassNotFoundException | SQLException ex) {
          
        }
    }
    
  
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Connect();
        table();
    }    
    
}
