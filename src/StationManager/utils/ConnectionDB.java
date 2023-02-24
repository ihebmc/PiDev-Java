package StationManager.utils;

//import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
//import java.util.ResourceBundle;

public class ConnectionDB {
    
    Connection con;
    PreparedStatement pst;
    int myIndex;
    int id;

    public void setCon(Connection con) {
        this.con = con;
    }

    public void setPst(PreparedStatement pst) {
        this.pst = pst;
    }

    public void setMyIndex(int myIndex) {
        this.myIndex = myIndex;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Connection getCon() {
        return con;
    }

    public PreparedStatement getPst() {
        return pst;
    }

    public int getMyIndex() {
        return myIndex;
    }

    public int getId() {
        return id;
    }
    
    
    
     public void Connect()
    {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/station","root","");
        } catch (ClassNotFoundException | SQLException ex) {
          
        }
    }

}
