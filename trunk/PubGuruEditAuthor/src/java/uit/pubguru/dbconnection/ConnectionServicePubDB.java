package uit.pubguru.dbconnection;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import java.lang.reflect.Array;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.naming.NamingException;
import uit.pubguru.constant.PubGuruConst;

/**
 *
 * @author ThanhIT
 */
public class ConnectionServicePubDB {
     public static Connection getConnection() throws NamingException, SQLException {
            Connection connection = null;
            String connecttionURL = "jdbc:mysql://localhost:3306/"+PubGuruConst.PUBDATABASE;
          try {
              Class.forName("com.mysql.jdbc.Driver").newInstance();
              connection = (Connection) DriverManager.getConnection(connecttionURL, PubGuruConst.PUBUSER_DB, PubGuruConst.PUBPASS_DB);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return connection;
    }
}
