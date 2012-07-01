/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uit.pubguru.model;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
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
public class Author_EditModel {

    private int authorEdit_Id;
    private int author_Id;
    private String authorName;
    private String imageUrl;
    private String emailAddress;
    private String website;
    private int idOrg;
    private String url;
    private String comment;

    public Author_EditModel() {
        this.authorEdit_Id=0;
          this.author_Id=0;
        this.authorName="";
        this.emailAddress="";
        this.imageUrl="";
        this.website="";
        this.url="";
        this.comment="";
    }


    public Author_EditModel(int authorId, String authorname, String imageurl, String emailaddress, String website, String comment) {
        this.author_Id = authorId;
        this.authorName = authorname;
        this.imageUrl = imageurl;
        this.emailAddress = emailaddress;
        this.website = website;
        this.comment = comment;
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
     * @return the author_Id
     */
    public int getAuthor_Id() {
        return author_Id;
    }

    /**
     * @param author_Id the author_Id to set
     */
    public void setAuthor_Id(int author_Id) {
        this.author_Id = author_Id;
    }

    /**
     * @return the authorName
     */
    public String getAuthorName() {
        return authorName;
    }

    /**
     * @param authorName the authorName to set
     */
    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    /**
     * @return the imageUrl
     */
    public String getImageUrl() {
        return imageUrl;
    }

    /**
     * @param imageUrl the imageUrl to set
     */
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
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
     * @return the website
     */
    public String getWebsite() {
        return website;
    }

    /**
     * @param website the website to set
     */
    public void setWebsite(String website) {
        this.website = website;
    }

    /**
     * @return the idOrg
     */
    public int getIdOrg() {
        return idOrg;
    }

    /**
     * @param idOrg the idOrg to set
     */
    public void setIdOrg(int idOrg) {
        this.idOrg = idOrg;
    }

    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url the url to set
     */
    public void setUrl(String url) {
        this.url = url;
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

    public boolean insert(Author_EditModel author) {
        try {
            Connection connection = ConnectionService.getConnection();
            String sql = "INSERT INTO author_edit (authorId, authorName, imageUrl, emailAddress, website, comment) " + "VALUES(?,?,?,?,?,?)";
            PreparedStatement stm = (PreparedStatement) connection.prepareStatement(sql);
            stm.setInt(1, author.getAuthor_Id());
            stm.setString(2, author.getAuthorName());
            stm.setString(3, author.getImageUrl());
            stm.setString(4, author.getEmailAddress());
            stm.setString(5, author.getWebsite());
            stm.setString(6, author.getComment());
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
    // get author by authorEdit_Id
    public Author_EditModel getAuthor_byId(int author_editid) {
        Author_EditModel author = new Author_EditModel();
        try {
            Connection connection = ConnectionService.getConnection();
            String sql = "Select * from author_edit where authorEdit_Id = ? ";
            PreparedStatement stm = (PreparedStatement) connection.prepareStatement(sql);
            stm.setInt(1, author_editid);
            ResultSet rs = stm.executeQuery();
            boolean resultq = rs.next();
            if (resultq) {
                    int authorEdit_id = rs.getInt("authorEdit_Id");
                    int authorId = rs.getInt("authorId");
                    String authorName = rs.getString("authorName");
                    String imageUrl = rs.getString("imageUrl");
                    String emailAddress = rs.getString("emailAddress");
                    String website = rs.getString("website");
                    String url = rs.getString("url");
                    String comment = rs.getString("comment");
                    author.setAuthorEdit_Id(authorEdit_id);
                    author.setAuthor_Id(authorId);
                    author.setAuthorName(authorName);
                    author.setEmailAddress(emailAddress);
                    author.setImageUrl(imageUrl);
                    author.setWebsite(website);
                    author.setUrl(url);
                    author.setComment(comment);
                 }
            rs.close();
            stm.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return author;
    }
    // get list author_edit by author id
     public Author_EditModel[] list_byAuthorId(int author_Id) {
        try {
            Connection connection = ConnectionService.getConnection();
            String sql = "Select * from author_edit where author_Id = ? ";
            PreparedStatement stm = (PreparedStatement) connection.prepareStatement(sql);
            stm.setInt(1, author_Id);
             ResultSet rs = stm.executeQuery();
            ArrayList list = new ArrayList();
            while (rs.next()) {
                int authorEdit_id = rs.getInt("authorEdit_Id");
                int authorId = rs.getInt("authorId");
                String authorName = rs.getString("authorName");
                String imageUrl = rs.getString("imageUrl");
                String emailAddess = rs.getString("emailAddress");
                String website = rs.getString("website");
                //String url = rs.getString("url");
                String comment = rs.getString("comment");

                Author_EditModel author = new Author_EditModel(authorId,authorName,imageUrl,emailAddess,website,comment);
                author.setAuthorEdit_Id(authorEdit_Id);
                list.add(author);
            }
              Author_EditModel[] result = new Author_EditModel[list.size()];
            list.toArray(result);
            rs.close();
            stm.close();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
      //select max id from authorEdit ; return 0 : don't have any row in database, else : maxid
    public int maxId() {
        int maxId = 0;
        try {
            Connection connection = ConnectionService.getConnection();
            String sql = "SELECT max(authorEdit_Id) FROM author_edit ";
            PreparedStatement pst = (PreparedStatement) connection.prepareStatement(sql);
            ResultSet sr = pst.executeQuery();
             while (sr.next()) {
                maxId=sr.getInt("max(authorEdit_Id)");
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
