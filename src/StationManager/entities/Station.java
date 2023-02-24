package StationManager.entities;



import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Station
{
    private final StringProperty id;
    private final StringProperty Location;
    private final StringProperty Name;
    private final StringProperty  Zone;
    
    public Station()
    {
        id = new SimpleStringProperty(this, "id");
        Location = new SimpleStringProperty(this, "Location");
        Name = new SimpleStringProperty(this, "Name");
        Zone = new SimpleStringProperty(this, "Zone");
    }
 
    public StringProperty idProperty() { return id; }
    public String getId() { return id.get(); }
    public void setId(String newId) { id.set(newId); }
 
    public StringProperty locationProperty() { return Location; }
    public String getLocation() { return Location.get(); }
    public void setLocation(String newLocation) { Location.set(newLocation); }
 
    public StringProperty nameProperty() { return Name; }
    public String getName() { return Name.get(); }
    public void setName(String newName) { Name.set(newName); }
    
    public StringProperty zoneProperty() { return Zone; }
    public String getZone() { return Zone.get(); }
    public void setZone(String newZone) { Zone.set(newZone); }
    


}