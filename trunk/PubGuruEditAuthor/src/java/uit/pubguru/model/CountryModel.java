/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uit.pubguru.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.naming.NamingException;
import uit.pubguru.dbconnection.ConnectionService;

/**
 *
 * @author ThanhIT
 */
public class CountryModel {

    private String country;
    private Connection connection;

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

    public CountryModel[] getAllCountry() throws SQLException, NamingException {
        CountryModel[] result = null;
        try {
            openConnection();
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

            result = new CountryModel[list.size()];
            list.toArray(result);
            rs.close();
            stm.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return result;
    }

    public void openConnection() throws NamingException, SQLException {
        connection = ConnectionService.getConnection();
    }

    public void closeConnection() throws NamingException, SQLException {
        connection.close();
    }
}
