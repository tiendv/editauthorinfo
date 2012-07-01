/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uit.pubguru.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import uit.pubguru.constant.PubGuruConst;
import uit.pubguru.dbconnection.ConnectionService;
import uit.pubguru.dbconnection.ConnectionServicePubDB;

/**
 *
 * @author ThanhIT
 */
public class UserModel {

    private int id;
    private String emailAddress;
    private String password;
    private String fullName;
    private String country;
    private boolean gender;
    private int permissionId;
    private int actived;
    private String dateRegister;
    private String organization;

    public UserModel(String email, String pass, String fullname, String country, int permission, int actived, String org) {
        this.emailAddress = email;
        this.password = pass;
        this.fullName = fullname;
        this.country = country;
        this.permissionId = permission;
        this.actived = actived;
        this.organization = org;
    }

    public UserModel() {
    }

    /**
     * @return the emailAddress
     */
    public String getEmailAddress() {
        return emailAddress;
    }

    /**
     * @param emailAddress the emailAddress to set
     */
    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the fullName
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * @param fullName the fullName to set
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

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

    /**
     * @return the gender
     */
    public boolean isGender() {
        return gender;
    }

    /**
     * @param gender the gender to set
     */
    public void setGender(boolean gender) {
        this.gender = gender;
    }

    /**
     * @return the permissionId
     */
    public int getPermissionId() {
        return permissionId;
    }

    /**
     * @param permissionId the permissionId to set
     */
    public void setPermissionId(int permissionId) {
        this.permissionId = permissionId;
    }

    /**
     * @return the actived
     */
    public int getActived() {
        return actived;
    }

    /**
     * @param actived the actived to set
     */
    public void setActived(int actived) {
        this.actived = actived;
    }
    // Check login with email and password,if actived = 1( user have already actived account through email) =>> can login!

    public boolean checkLogin(String email, String password) {
        try {
            Connection connection = ConnectionService.getConnection();
            String sql = "SELECT * FROM user Where emailAddress= ? and passWord = ? and actived = '" + PubGuruConst.ACTIVED + "'";
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setString(1, email);
            pst.setString(2, password);
            ResultSet sr = pst.executeQuery();
            boolean result = sr.next();

            connection.close();
            if (result) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;

    }
    // Insert user to database

    public boolean insert(UserModel user) {
        try {
            LogModel lg = new LogModel();
            Connection connection = ConnectionService.getConnection();
            String sql = "INSERT INTO user (emailAddress, passWord, fullName, permissionId, actived, country, dateRegister, organization) " + "VALUES(?,?,?,?,?,?,?,?)";
            PreparedStatement stm = (PreparedStatement) connection.prepareStatement(sql);
            stm.setString(1, user.getEmailAddress());
            stm.setString(2, user.getPassword());
            stm.setString(3, user.getFullName());
            stm.setInt(4, user.getPermissionId());
            stm.setInt(5, user.getActived());
            stm.setString(6, user.getCountry());
            stm.setString(7, lg.datetime());
            stm.setString(8, this.organization);
            int result = stm.executeUpdate();
            stm.close();
            connection.close();
            if (result > 0) {
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    // check exist email,

    public boolean checkEmail(String email) {
        try {
            Connection connection = ConnectionService.getConnection();
            String sql = "SELECT * FROM user Where emailAddress= ? ";
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setString(1, email);
            ResultSet sr = pst.executeQuery();
            boolean result = sr.next(); pst.close();
            if (result) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;

    }

    // active user acount ( update actived value to 1  : actived )
    public boolean activeUser(int id) {
        try {
            Connection connection = ConnectionService.getConnection();
            String sql = "Update user set actived = '" + PubGuruConst.ACTIVED + "' Where id = ?";
            PreparedStatement stm = (PreparedStatement) connection.prepareStatement(sql);
            stm.setInt(1, id);
            int result = stm.executeUpdate();
            stm.close();
            if (result > 0) {
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updatePer(int id, int per) { // update user, if per =2 : user is disenable, else : per#2
        try {
            Connection connection = ConnectionService.getConnection();
            String sql = "Update user set permissionId = ? Where id = ?";
            PreparedStatement stm = (PreparedStatement) connection.prepareStatement(sql);
            stm.setInt(1, per);
            stm.setInt(2, id);
            int result = stm.executeUpdate();
            stm.close();
            if (result > 0) {
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean changePass(int id, String pass) { ///change password
        try {
            Connection connection = ConnectionService.getConnection();
            String sql = "Update user set passWord = ? Where id = ?";
            PreparedStatement stm = (PreparedStatement) connection.prepareStatement(sql);
            stm.setString(1, pass);
            stm.setInt(2, id);
            int result = stm.executeUpdate();
            stm.close();
            if (result > 0) {
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    //select max id from user ; return 0 : don't have any row in database, else : maxid

    public int maxId() {
        int maxId = 0;
        try {
            Connection connection = ConnectionService.getConnection();
            String sql = "SELECT max(id) FROM user ";
            PreparedStatement pst = connection.prepareStatement(sql);
            ResultSet sr = pst.executeQuery();
            while (sr.next()) {
                maxId = sr.getInt("max(id)");
            }
          pst.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return maxId;

    }
    // get username(email) by id

    public String name_byId(int id) {
        String emailaddress = "";
        try {
            Connection connection = ConnectionService.getConnection();
            String sql = "SELECT emailAddress FROM user where id =? ";
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setInt(1, id);
            ResultSet sr = pst.executeQuery();
            while (sr.next()) {
                emailaddress = sr.getString("emailAddress");
            }
            pst.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return emailaddress;
    }
    // get username(email) by id

    public UserModel getUser_byId(int id) {
        UserModel user = new UserModel();
        try {
            Connection connection = ConnectionService.getConnection();
            String sql = "SELECT * FROM user where id =? ";
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setInt(1, id);
            ResultSet sr = pst.executeQuery();
            boolean result = sr.next();
            if (result) {
                user.setActived(sr.getInt("actived"));
                user.setEmailAddress(sr.getString("emailAddress"));
                user.setId(sr.getInt("id"));
                user.setPermissionId(sr.getInt("permissionId"));
                user.setPassword(sr.getString("passWord"));
            }
            pst.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }
    // get all user 

    public UserModel[] getList_All(int start, int limit) {
        try {
            Connection connection = ConnectionService.getConnection();
            String sql = "SELECT * FROM user limit ?,? ";
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setInt(1, start);
            pst.setInt(2, limit);
            ResultSet sr = pst.executeQuery();
            ArrayList list = new ArrayList();
            while (sr.next()) {
                UserModel user = new UserModel();
                user.setActived(sr.getInt("actived"));
                user.setEmailAddress(sr.getString("emailAddress"));
                user.setId(sr.getInt("id"));
                user.setFullName(sr.getString("fullName"));
                user.setCountry(sr.getString("country"));
                user.setDateRegister(sr.getString("dateRegister"));
                user.setPermissionId(sr.getInt("permissionId"));
                user.setOrganization(sr.getString("organization"));
                list.add(user);
            }
            UserModel[] result = new UserModel[list.size()];
            list.toArray(result);pst.close();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    //count row

    public int rowsResult() {
        int count = 0;
        try {
            Connection connection = ConnectionService.getConnection();
            String sql = "SELECT * FROM user";
            PreparedStatement pst = connection.prepareStatement(sql);
            ResultSet sr = pst.executeQuery();
            ArrayList list = new ArrayList();
            while (sr.next()) {
                count++;
            }
            pst.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }
    // get all user by active ,
    // actived = 0 : hanvent actied yet
    // actived = 1 : have already actived!

    public UserModel[] getList_byActived(int actived, int start, int limit) {
        try {
            Connection connection = ConnectionService.getConnection();
            String sql = "SELECT * FROM user where actived = ? limit ?,? ";
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setInt(1, actived);
            pst.setInt(2, start);
            pst.setInt(3, limit);
            ResultSet sr = pst.executeQuery();
            ArrayList list = new ArrayList();
            while (sr.next()) {
                UserModel user = new UserModel();
                user.setActived(sr.getInt("actived"));
                user.setEmailAddress(sr.getString("emailAddress"));
                user.setId(sr.getInt("id"));
                user.setFullName(sr.getString("fullName"));
                user.setCountry(sr.getString("country"));
                user.setDateRegister(sr.getString("dateRegister"));
                user.setPermissionId(sr.getInt("permissionId"));
                user.setOrganization(sr.getString("organization"));
                list.add(user);
            }
            UserModel[] result = new UserModel[list.size()];
            list.toArray(result);
            pst.close();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    // get username by email

    public UserModel getUser_byId(String email) {
        UserModel user = new UserModel();
        try {
            Connection connection = ConnectionService.getConnection();
            String sql = "SELECT * FROM user where emailAddress =? ";
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setString(1, email);
            ResultSet sr = pst.executeQuery();
            boolean result = sr.next();
            if (result) {
                user.setActived(sr.getInt("actived"));
                user.setEmailAddress(sr.getString("emailAddress"));
                user.setId(sr.getInt("id"));
                user.setFullName(sr.getString("fullName"));
                user.setCountry(sr.getString("country"));
                user.setDateRegister(sr.getString("dateRegister"));
                user.setPermissionId(sr.getInt("permissionId"));
                user.setOrganization(sr.getString("organization"));
            }
            pst.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the dateRegister
     */
    public String getDateRegister() {
        return dateRegister;
    }

    /**
     * @param dateRegister the dateRegister to set
     */
    public void setDateRegister(String dateRegister) {
        this.dateRegister = dateRegister;
    }

    /**
     * @return the organization
     */
    public String getOrganization() {
        return organization;
    }

    /**
     * @param organization the organization to set
     */
    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public void closeConnection() throws NamingException, SQLException {
        try {
            ConnectionServicePubDB.getConnection().close();
        } catch (NamingException ex) {
            Logger.getLogger(Author.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
