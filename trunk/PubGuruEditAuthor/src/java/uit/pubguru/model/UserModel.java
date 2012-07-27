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
    private Connection connection;

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

    public boolean checkLogin(String email, String password) throws SQLException, NamingException {
        try {
            openConnection();
            String sql = "SELECT * FROM user Where emailAddress= ? and passWord = ? and actived = '" + PubGuruConst.ACTIVED + "'";
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setString(1, email);
            pst.setString(2, password);
            ResultSet sr = pst.executeQuery();
            boolean result = sr.next();
            if (result) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return false;

    }
    // Insert user to database

    public boolean insert(UserModel user) throws SQLException, NamingException {
        boolean in = false;
        try {
            LogModel lg = new LogModel();
            openConnection();
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
                in = true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return in;
    }
    // check exist email,

    public boolean checkEmail(String email) throws SQLException, NamingException {

        boolean check = false;
        try {
            openConnection();
            String sql = "SELECT * FROM user Where emailAddress= ? ";
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setString(1, email);
            ResultSet sr = pst.executeQuery();
            boolean result = sr.next();
            pst.close();
            if (result) {
                check = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return check;

    }

    // active user acount ( update actived value to 1  : actived )
    public boolean activeUser(int id) throws SQLException, NamingException {
        boolean ac = false;
        try {
            openConnection();
            String sql = "Update user set actived = '" + PubGuruConst.ACTIVED + "' Where id = ?";
            PreparedStatement stm = (PreparedStatement) connection.prepareStatement(sql);
            stm.setInt(1, id);
            int result = stm.executeUpdate();
            stm.close();
            if (result > 0) {
                ac = true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return ac;
    }

    public boolean updatePer(int id, int per) throws SQLException, NamingException { // update user, if per =2 : user is disenable, else : per#2
        boolean up = false;
        try {
            openConnection();
            String sql = "Update user set permissionId = ? Where id = ?";
            PreparedStatement stm = (PreparedStatement) connection.prepareStatement(sql);
            stm.setInt(1, per);
            stm.setInt(2, id);
            int result = stm.executeUpdate();
            stm.close();
            if (result > 0) {
                up = true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return up;
    }

    public boolean changePass(int id, String pass) throws SQLException, NamingException { ///change password
        boolean cha = false;
        try {
            openConnection();
            String sql = "Update user set passWord = ? Where id = ?";
            PreparedStatement stm = (PreparedStatement) connection.prepareStatement(sql);
            stm.setString(1, pass);
            stm.setInt(2, id);
            int result = stm.executeUpdate();
            stm.close();
            if (result > 0) {
                cha = true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return cha;
    }
    //select max id from user ; return 0 : don't have any row in database, else : maxid

    public int maxId() throws SQLException, NamingException {
        int maxId = 0;
        try {
            openConnection();
            String sql = "SELECT max(id) FROM user ";
            PreparedStatement pst = connection.prepareStatement(sql);
            ResultSet sr = pst.executeQuery();
            while (sr.next()) {
                maxId = sr.getInt("max(id)");
            }
            pst.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return maxId;

    }
    // get username(email) by id

    public String name_byId(int id) throws SQLException, NamingException {
        String emailaddress = "";
        try {
            openConnection();
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
        } finally {
            closeConnection();
        }
        return emailaddress;
    }
    // get username(email) by id

    public UserModel getUser_byId(int id) throws SQLException, NamingException {
        UserModel user = new UserModel();
        try {
            openConnection();
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
        } finally {
            closeConnection();
        }
        return user;
    }
    // get all user 

    public UserModel[] getList_All(int start, int limit) throws SQLException, NamingException {
        UserModel[] result = null;
        try {
            openConnection();
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
            result = new UserModel[list.size()];
            list.toArray(result);
            pst.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return result;
    }
    //count row

    public int rowsResult() throws SQLException, NamingException {
        int count = 0;
        try {
            openConnection();
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
        } finally {
            closeConnection();
        }
        return count;
    }
    // get all user by active ,
    // actived = 0 : hanvent actied yet
    // actived = 1 : have already actived!

    public UserModel[] getList_byActived(int actived, int start, int limit) throws SQLException, NamingException {
        UserModel[] result = null;
        try {
            openConnection();
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
            result = new UserModel[list.size()];
            list.toArray(result);
            pst.close();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return result;
    }
    // get username by email

    public UserModel getUser_byId(String email) throws SQLException, NamingException {
        UserModel user = new UserModel();

        try {
            openConnection();
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
        } finally {
            closeConnection();
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

    public void openConnection() throws NamingException, SQLException {
        connection = ConnectionService.getConnection();
    }

    public void closeConnection() throws NamingException, SQLException {
        connection.close();
    }
}
