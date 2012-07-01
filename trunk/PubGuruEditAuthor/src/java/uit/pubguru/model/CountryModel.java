/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uit.pubguru.model;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import uit.pubguru.dbconnection.ConnectionService;
import uit.pubguru.dbconnection.ConnectionServicePubDB;
import uit.pubguru.model.Author_EditModel;

/**
 *
 * @author ThanhIT
 */
public class CountryModel {

    private String country;

    /**
     * @return the country
     */
    public String getCountry() {
        return country;
    }

    /**
     * @param country the country to set
     */
    public void setCountry(String country) {
        this.country = country;
    }

    public CountryModel[] getAllCountry() {
        try {
            Connection connection = ConnectionService.getConnection();
            String sql = "Select * from country ";
            PreparedStatement stm = (PreparedStatement) connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            ArrayList list = new ArrayList();
            while (rs.next()) {

                String country = rs.getString("countryname");
                CountryModel ct = new CountryModel();
                ct.setCountry(country);
                list.add(ct);
            }
            CountryModel[] result = new  CountryModel[list.size()];
            list.toArray(result);
            rs.close();
            stm.close();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
     public void closeConnection() throws NamingException, SQLException
    {
        try {
            ConnectionServicePubDB.getConnection().close();
        } catch (NamingException ex) {
            Logger.getLogger(Author.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
