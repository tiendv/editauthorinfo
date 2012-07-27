package uit.pubguru.dbconnection;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;


/**
 *
 * @author ThanhIT
 */
public class ConnectionService {

    public static Connection getConnection() throws NamingException, SQLException {
        Connection connection = null;
        try {
            DataSource ds = (DataSource) new InitialContext().lookup("jdbc/author_edit");
            connection = ds.getConnection();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return connection;
    }
}
