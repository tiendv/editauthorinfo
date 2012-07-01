/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uit.pubguru.model;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
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
public class LogModel {

    private int logId;
    private int feedBackId;
    private String dateEdit;
    private String comment;

    /**
     * @return the logId
     */
    public LogModel() {
    }

    public LogModel(int feedbackid, String date, String comment) {
        this.feedBackId = feedbackid;
        this.dateEdit = date;
        this.comment = comment;
    }

    public int getLogId() {
        return logId;
    }

    /**
     * @param logId the logId to set
     */
    public void setLogId(int logId) {
        this.logId = logId;
    }

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
     * @return the dateEdit
     */
    public String getDateEdit() {
        return dateEdit;
    }

    /**
     * @param dateEdit the dateEdit to set
     */
    public void setDateEdit(String dateEdit) {
        this.dateEdit = dateEdit;
    }

    /**
     * @return the comment
     */
    public String getComment() {
        return comment;
    }

    /**
     * @param comment the comment to set
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

//    public boolean insert_log_feedback(LogModel log) {
//        try {
//            Connection connection = ConnectionService.getConnection();
//            String sql = "INSERT INTO log ( dateEdit, comment) " + "VALUES(?,?,?)";
//            PreparedStatement stm = (PreparedStatement) connection.prepareStatement(sql);
//            stm.setString(1, log.getDateEdit());
//            stm.setString(2, log.getComment());
//            int result = stm.executeUpdate();
//            stm.close();
//            connection.close();
//            if (result > 0) {
//                return true;
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return false;
//    }
    public boolean insert_log() {
        try {
            Connection connection = ConnectionService.getConnection();
            String sql = "INSERT INTO log (dateEdit, comment) " + "VALUES(?,?)";
            PreparedStatement stm = (PreparedStatement) connection.prepareStatement(sql);
            stm.setString(1, this.getDateEdit());
            stm.setString(2, this.getComment());
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

    public String datetime() {
        String dateFormat = "yyyy-MM-dd : HH:mm:ss";
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        return (sdf.format(cal.getTime()));
        // String dateFormat = "yyyy-MM-dd ':' hh:mm:ss";
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
