package Tool;

import java.sql.*;

public class KoneksiDB {
    
    public KoneksiDB(){
    
    }
    
    public Connection getConnection() throws SQLException {
        Connection cn;
        try{
            String server = "jdbc:mysql://localhost/dbsiakad_151530009";
            String drever = "com.mysql.jdbc.Driver";
            Class.forName(drever);
            cn = DriverManager.getConnection (server, "root","");
            return cn;
        } catch (SQLException | ClassNotFoundException se) {
            System.out.println(se.toString());
            return null;
        }
    }
    
}
    
