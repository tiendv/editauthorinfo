/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uit.pubguru.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.naming.NamingException;
import uit.pubguru.dbconnection.ConnectionService;

/**
 *
 * @author ThanhIT
 */
public class LogModel {

    private int logId;
    private int feedBackId;
    private String dateEdit;
    private String comment;
    private Connection connection;

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
    public boolean insert_log() throws SQLException, NamingException {

        boolean in = false;
        try {
            openConnection();
            String sql = "INSERT INTO log (dateEdit, comment) " + "VALUES(?,?)";
            PreparedStatement stm = (PreparedStatement) connection.prepareStatement(sql);
            stm.setString(1, this.getDateEdit());
            stm.setString(2, this.getComment());
            int result = stm.executeUpdate();
            stm.close();

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

    public String datetime() {
        String dateFormat = "yyyy-MM-dd : HH:mm:ss";
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        return (sdf.format(cal.getTime()));
        // String dateFormat = "yyyy-MM-dd ':' hh:mm:ss";
    }

    public void openConnection() throws NamingException, SQLException {
        connection = ConnectionService.getConnection();
    }

    public void closeConnection() throws NamingException, SQLException {
        connection.close();
    }
}
