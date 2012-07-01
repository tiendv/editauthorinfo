/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uit.pubguru.model;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import uit.pubguru.dbconnection.ConnectionService;
import uit.pubguru.dbconnection.ConnectionServicePubDB;

/**
 *
 * @author ThanhIT
 */
public class FeedBackDetails {

    private int iD;
    private int feedBackId;
    private String name;
    private int status;

    /**
     * @return the feedBackId
     */
    public int getFeedBackId() {
        return feedBackId;
    }

    /**
     * @param feedBackId the feedBackId to set
     */
    public void setFeedBackId(int feedBackId) {
        this.feedBackId = feedBackId;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the status
     */
    public int getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(int status) {
        this.status = status;
    }

    public boolean insert_fbdetail( int feedbackid, String name, int status) {
        try {
            Connection connection = ConnectionService.getConnection();
            String sql = "INSERT INTO feedbackdetail (feedBackId, name, status) " + "VALUES(?,?, ?)";
            PreparedStatement stm = (PreparedStatement) connection.prepareStatement(sql);
            stm.setInt(1,feedbackid);
            stm.setString(2, name);
            stm.setInt(3,status);
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
    // update status

    public boolean update_fbdetail(int status, int feedbackid, String name) {
        try {
            Connection connection = ConnectionService.getConnection();
            String sql = "UPDATE feedbackdetail set status =? where feedBackId = ? and name = ?";
            PreparedStatement stm = (PreparedStatement) connection.prepareStatement(sql);
            stm.setInt(1, status);
            stm.setInt(2, feedbackid);
            stm.setString(3, name);
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
    //get feeback status

    public int getStatus(int feedbackid, String name) {
        int fb = 0;

        try {
            Connection connection = ConnectionService.getConnection();
            String sql = "SELECT * FROM feedbackdetail where feedBackId =? and name =? ";
            PreparedStatement pst = (PreparedStatement) connection.prepareStatement(sql);
            pst.setInt(1, feedbackid);
            pst.setString(2, name);
            ResultSet sr = pst.executeQuery();
            boolean result = sr.next();
            if (result) {
                fb = sr.getInt("status");
            }
            pst.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return fb;
    }
    // get feedbackdetails by feedbackid and name

    public FeedBackDetails getDetail(int feedbackid, String name) {
        FeedBackDetails fb = new FeedBackDetails();
        try {
            Connection connection = ConnectionService.getConnection();
            String sql = "SELECT * FROM feedbackdetail where feedBackId =? and name =? ";
            PreparedStatement pst = (PreparedStatement) connection.prepareStatement(sql);
            pst.setInt(1, feedbackid);
            pst.setString(2, name);
            ResultSet sr = pst.executeQuery();
            boolean result = sr.next();
            if (result) {
                fb.setFeedBackId(sr.getInt("feedBackId"));
                fb.setName(sr.getString("name"));
                fb.setStatus(sr.getInt("status"));
            }
           pst.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return fb;
    }
    // get lis feebackdetails by feebackid

    public FeedBackDetails[] getList_byFeeBackId(int feeBackId) {
        try {
            Connection connection = ConnectionService.getConnection();
            String sql = "SELECT * FROM feedbackdetail where feedBackId =?  ";
            PreparedStatement pst = (PreparedStatement) connection.prepareStatement(sql);
            pst.setInt(1, feeBackId);
            ResultSet sr = pst.executeQuery();
            ArrayList list = new ArrayList();
            while (sr.next()) {
                FeedBackDetails fbd = new FeedBackDetails();
                fbd.setFeedBackId(sr.getInt("feedBackId"));
                fbd.setName(sr.getString("name"));
                fbd.setStatus(sr.getInt("status"));
                list.add(fbd);
            }
            FeedBackDetails[] result = new FeedBackDetails[list.size()];
            list.toArray(result);
            pst.close();
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
