/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uit.pubguru.model;

import com.mysql.jdbc.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import uit.pubguru.dbconnection.ConnectionService;
import uit.pubguru.dbconnection.ConnectionServicePubDB;

/**
 *
 * @author ThanhIT
 */
public class FeedBackModel {

    private int feedBackId;
    private int authorEdit_Id;
    private int userId;
    private int userId_Confirm;
    private String dateEdit;
    private String dateEdit_Confirm;
    private int status;

    public FeedBackModel() {
    }

    public FeedBackModel(int authorEditId, int userId, int userIdConfirm, String dateEdit, String dateEditConfirm, int status) {
        this.authorEdit_Id = authorEditId;
        this.userId = userId;
        this.userId_Confirm = userIdConfirm;
        this.dateEdit = dateEdit;
        this.dateEdit_Confirm = dateEditConfirm;
        this.status = status;
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
     * @return the authorEdit_Id
     */
    public int getAuthorEdit_Id() {
        return authorEdit_Id;
    }

    /**
     * @param authorEdit_Id the authorEdit_Id to set
     */
    public void setAuthorEdit_Id(int authorEdit_Id) {
        this.authorEdit_Id = authorEdit_Id;
    }

    /**
     * @return the userId
     */
    public int getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * @return the userId_Confirm
     */
    public int getUserId_Confirm() {
        return userId_Confirm;
    }

    /**
     * @param userId_Confirm the userId_Confirm to set
     */
    public void setUserId_Confirm(int userId_Confirm) {
        this.userId_Confirm = userId_Confirm;
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
     * @return the dateEdit_Confirm
     */
    public String getDateEdit_Confirm() {
        return dateEdit_Confirm;
    }

    /**
     * @param dateEdit_Confirm the dateEdit_Confirm to set
     */
    public void setDateEdit_Confirm(String dateEdit_Confirm) {
        this.dateEdit_Confirm = dateEdit_Confirm;
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
    //insert int to feedback

    public boolean insert() {
        Connection connection = null;
        PreparedStatement stm = null;
        try {
            connection = ConnectionService.getConnection();
            String sql = "INSERT INTO feedback (authorEdit_Id, userId, dateEdit, status) " + "VALUES(?,?,?,?)";
            stm = (PreparedStatement) connection.prepareStatement(sql);
            stm.setInt(1, this.authorEdit_Id);
            stm.setInt(2, this.userId);
            stm.setString(3, this.dateEdit);
            stm.setInt(4, this.status);
            int result = stm.executeUpdate();
            if (result > 0) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                stm.close();                
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        return false;
    }
    // update feed back . when admin edit the feedback, we will update the userID_Confirm, dateEdit_Cofirm
    //insert int to feedback

    public boolean updateFeedBack(int userId_Cofirm, String dateEdit_Cofirm, int status, int feedBackID) {
        try {
            Connection connection = ConnectionService.getConnection();
            String sql = "UPDATE feedback set userId_Confirm = ?, dateEdit_Confirm = ?, status = ? where authorEdit_Id = ?";
            PreparedStatement stm = (PreparedStatement) connection.prepareStatement(sql);
            stm.setInt(1, userId_Cofirm);
            stm.setString(2, dateEdit_Cofirm);
            stm.setInt(3, status);
            stm.setInt(4, feedBackID);
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

    public boolean updateStatus(int status, int feedBackID, String dateEdit_Cofirm) {
        try {
            Connection connection = ConnectionService.getConnection();
            String sql = "UPDATE feedback set status = ?, dateEdit_Confirm = ? where authorEdit_Id = ?";
            PreparedStatement stm = (PreparedStatement) connection.prepareStatement(sql);
            stm.setInt(1, status);
             stm.setString(2,dateEdit_Cofirm);
            stm.setInt(3, feedBackID);
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
    // get authorId from feedbackId , return 0 : can find author id from feedbackId, else : can find authorId

    public int getauthorId(int feedbackId) throws NamingException, SQLException {
        int authorid = 0;
        try {
            Connection connection = ConnectionService.getConnection();
            String sql = "select authorId from author_edit where authorEdit_id in "
                    + "( select authorEdit_Id from feedback where authorEdit_Id=?)";
            PreparedStatement stm = (PreparedStatement) connection.prepareStatement(sql);
            stm.setInt(1, feedbackId);
            ResultSet rs = stm.executeQuery(sql);
            while (rs.next()) {
                authorid = rs.getInt("authorId");
            }
             stm.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return authorid;
    }
    // get user id by feedbackId ( userEditId)

    public int getuserId(int authorEditId) throws NamingException, SQLException {
        int userid = 0;
        try {
            Connection connection = ConnectionService.getConnection();
            String sql = "select * from feedback where authorEdit_Id = " + authorEditId;
            PreparedStatement stm = connection.prepareStatement(sql);
            // stm.setInt(1, authorEditId);
            ResultSet rs = stm.executeQuery(sql);
            while (rs.next()) {
                userid = rs.getInt("userId");
            }
            stm.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userid;
    }

    public int getuserId_Confirm(int authorEditId) throws NamingException, SQLException {
        int userid = 0;
        try {
            Connection connection = ConnectionService.getConnection();
            String sql = "SELECT * FROM feedback where authorEdit_Id = " + authorEditId;
            PreparedStatement stm = (PreparedStatement) connection.prepareStatement(sql);
            // stm.setInt(1, authorEditId);
            ResultSet rs = stm.executeQuery(sql);
            while (rs.next()) {
                userid = rs.getInt("userId_Confirm");
            }
             stm.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userid;
    }
    // get feed back id from authore edit id
     public int getfeedbackId(int authorEditId) throws NamingException, SQLException {
        int userid = 0;
        try {
            Connection connection = ConnectionService.getConnection();
            String sql = "SELECT * FROM feedback where authorEdit_Id = " + authorEditId;
            PreparedStatement stm = (PreparedStatement) connection.prepareStatement(sql);
            // stm.setInt(1, authorEditId);
            ResultSet rs = stm.executeQuery(sql);
            while (rs.next()) {
                userid = rs.getInt("feedBackId");
            }
            stm.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userid;
    }
    //get list feedback by status :
    // status = 0 : haven't edit yet
    // status = 1 : have alread edited
    //status = 2 : inoge
    // get list author_edit by author id

    public FeedBackModel[] list_byStatus(int status_, int start, int limit) {
        try {
            Connection connection = ConnectionService.getConnection();
            String sql = "Select * from feedback where status = ? limit ?,? ";
            PreparedStatement stm = (PreparedStatement) connection.prepareStatement(sql);
            stm.setInt(1, status_);
            stm.setInt(2, start);
            stm.setInt(3, limit);
            ResultSet rs = stm.executeQuery();
            ArrayList list = new ArrayList();
            while (rs.next()) {
                int feedBackId = rs.getInt("feedBackId");
                int authorEdit_id = rs.getInt("authorEdit_Id");
                int userId = rs.getInt("userId");
                int userId_Confirm = rs.getInt("userId_Confirm");
                String dateEdit = rs.getString("dateEdit");
                String dateEdit_Confirm = rs.getString("dateEdit_Confirm");
                int status = rs.getInt("status");
                FeedBackModel fb = new FeedBackModel(authorEdit_id, userId, userId_Confirm, dateEdit, dateEdit_Confirm, status);
                fb.setFeedBackId(feedBackId);
                list.add(fb);
            }
            FeedBackModel[] result = new FeedBackModel[list.size()];
            list.toArray(result);
            rs.close();
            stm.close();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public FeedBackModel[] list_byStatus_Sort(int status_, int start, int limit,String sortby, String orderby) {
        try {
            Connection connection = ConnectionService.getConnection();
            String sql = "Select * from feedback where status = ? order by " +sortby+ " " +orderby+" limit ?,?" ;
            PreparedStatement stm = (PreparedStatement) connection.prepareStatement (sql);
            stm.setInt(1, status_);
           // stm.setString(2,"");
            stm.setInt(2, start);
            stm.setInt(3, limit);
          
            ResultSet rs = stm.executeQuery();
            ArrayList list = new ArrayList();
            while (rs.next()) {
                int feedBackId = rs.getInt("feedBackId");
                int authorEdit_id = rs.getInt("authorEdit_Id");
                int userId = rs.getInt("userId");
                int userId_Confirm = rs.getInt("userId_Confirm");
                String dateEdit = rs.getString("dateEdit");
                String dateEdit_Confirm = rs.getString("dateEdit_Confirm");
                int status = rs.getInt("status");
                FeedBackModel fb = new FeedBackModel(authorEdit_id, userId, userId_Confirm, dateEdit, dateEdit_Confirm, status);
                fb.setFeedBackId(feedBackId);
                list.add(fb);
            }
            FeedBackModel[] result = new FeedBackModel[list.size()];
            list.toArray(result);
            rs.close();
            stm.close();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    // get row result of feeback dephen on status
     public int row_byStatus(int status_) {
         int row=0;
        try {
            Connection connection = ConnectionService.getConnection();
            String sql = "Select * from feedback where status = ? ";
            PreparedStatement stm = (PreparedStatement) connection.prepareStatement(sql);
            stm.setInt(1, status_);
             ResultSet rs = stm.executeQuery();
            ArrayList list = new ArrayList();
            while (rs.next()) {
               row++;
            }

            rs.close();
            stm.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return row;
    }
// get all feedback

    public FeedBackModel[] list_byStatusDateedit(String datetime, int start, int limit) {
        try {
            Connection connection = ConnectionService.getConnection();
            String sql = "Select * from feedback where dateEdit = ? limit ?,? ";
            PreparedStatement stm = (PreparedStatement) connection.prepareStatement(sql);
            stm.setString(1, datetime);
            stm.setInt(2, start);
            stm.setInt(3, limit);
            ResultSet rs = stm.executeQuery();
            ArrayList list = new ArrayList();
            while (rs.next()) {
                int feedBackId = rs.getInt("feedBackId");
                int authorEdit_id = rs.getInt("authorEdit_Id");
                int userId = rs.getInt("userId");
                int userId_Confirm = rs.getInt("userId_Confirm");
                String dateEdit = rs.getString("dateEdit");
                String dateEdit_Confirm = rs.getString("dateEdit_Confirm");
                int status = rs.getInt("status");
                FeedBackModel fb = new FeedBackModel(authorEdit_id, userId, userId_Confirm, dateEdit, dateEdit_Confirm, status);
                fb.setFeedBackId(feedBackId);
                list.add(fb);
            }
            FeedBackModel[] result = new FeedBackModel[list.size()];
            list.toArray(result);
            rs.close();
            stm.close();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public FeedBackModel[] list_AllfeedBack(int start, int limit) {
        try {
            Connection connection = ConnectionService.getConnection();
            String sql = "Select * from feedback limit ?,? ";
            PreparedStatement stm = (PreparedStatement) connection.prepareStatement(sql);
            stm.setInt(1, start);
            stm.setInt(2, limit);
            ResultSet rs = stm.executeQuery();
            ArrayList list = new ArrayList();
            while (rs.next()) {
                int feedBackId = rs.getInt("feedBackId");
                int authorEdit_id = rs.getInt("authorEdit_Id");
                int userId = rs.getInt("userId");
                int userId_Confirm = rs.getInt("userId_Confirm");
                String dateEdit = rs.getString("dateEdit");
                String dateEdit_Confirm = rs.getString("dateEdit_Confirm");
                int status = rs.getInt("status");
                FeedBackModel fb = new FeedBackModel(authorEdit_id, userId, userId_Confirm, dateEdit, dateEdit_Confirm, status);
                fb.setFeedBackId(feedBackId);
                list.add(fb);
            }
            FeedBackModel[] result = new FeedBackModel[list.size()];
            list.toArray(result);
            rs.close();
            stm.close();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    //sort by veedbackid
     public FeedBackModel[] list_AllfeedBack_Sort(int start, int limit,String sortby, String sort) {
        try {
            Connection connection = ConnectionService.getConnection();
            String sql = "Select * from feedback  order by " +sortby+ " " +sort+" limit ?,?";
            PreparedStatement stm = (PreparedStatement) connection.prepareStatement(sql);
            // stm.setString(1,sort);
            stm.setInt(1, start);
            stm.setInt(2, limit);
          
            ResultSet rs = stm.executeQuery();
            ArrayList list = new ArrayList();
            while (rs.next()) {
                int feedBackId = rs.getInt("feedBackId");
                int authorEdit_id = rs.getInt("authorEdit_Id");
                int userId = rs.getInt("userId");
                int userId_Confirm = rs.getInt("userId_Confirm");
                String dateEdit = rs.getString("dateEdit");
                String dateEdit_Confirm = rs.getString("dateEdit_Confirm");
                int status = rs.getInt("status");
                FeedBackModel fb = new FeedBackModel(authorEdit_id, userId, userId_Confirm, dateEdit, dateEdit_Confirm, status);
                fb.setFeedBackId(feedBackId);
                list.add(fb);
            }
            FeedBackModel[] result = new FeedBackModel[list.size()];
            list.toArray(result);
            rs.close();
            stm.close();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
// get row result of all feedback
    public int row_AllfeedBack() {
        int row = 0;
        try {
            Connection connection = ConnectionService.getConnection();
            String sql = "Select * from feedback";
            PreparedStatement stm = (PreparedStatement) connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            ArrayList list = new ArrayList();
            while (rs.next()) {
                row++;
            }
            rs.close();
            stm.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return row;
    }
      //select max id from feedback ; return 0 : don't have any row in database, else : maxid

    public int maxId() {
        int maxId = 0;
        try {
            Connection connection = ConnectionService.getConnection();
            String sql = "SELECT max(feedBackId) FROM feedback ";
            PreparedStatement pst = connection.prepareStatement(sql);
            ResultSet sr = pst.executeQuery();
            while (sr.next()) {
                maxId = sr.getInt("max(feedBackId)");
            }
           pst.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return maxId;

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
