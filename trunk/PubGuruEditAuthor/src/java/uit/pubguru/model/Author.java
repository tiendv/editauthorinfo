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
public class Author {

    private int author_Id;
    private String authorName;
    private String imageUrl;
    private String emailAddress;
    private String website;
    private int idOrg;
    private String url;
    private String comment;

    public Author() {
        this.author_Id = 0;
        this.authorName = "";
        this.emailAddress = "";
        this.imageUrl = "";
        this.website = "";
        this.url = "";

    }

    public Author(String authorname, String imageurl, String emailaddress, String website) {
        this.authorName = authorname;
        this.imageUrl = imageurl;
        this.emailAddress = emailaddress;
        this.website = website;

    }

    /**
     * @return the authorEdit_Id
     */
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

    // get author by authorEdit_Id
    public Author getAuthorById(int authorid) {
        Author author = new Author();
        try {
            Connection connection = ConnectionServicePubDB.getConnection();
            String sql = "Select * from author where idAuthor = ? ";
            PreparedStatement stm = (PreparedStatement) connection.prepareStatement(sql);
            stm.setInt(1, authorid);
            ResultSet rs = stm.executeQuery();
            boolean resultq = rs.next();
            if (resultq) {
                int authorId = rs.getInt("idAuthor");
                String authorName = rs.getString("authorName");
                String imageUrl = rs.getString("image");
                String emailAddress = rs.getString("emailAddress");
                String website = rs.getString("website");
                String url = rs.getString("url");
                author.setAuthor_Id(authorId);
                author.setAuthorName(authorName);
                author.setEmailAddress(emailAddress);
                author.setImageUrl(imageUrl);
                author.setWebsite(website);
                author.setUrl(url);
            }
            rs.close();
            stm.close();          
        } catch (Exception e) {
            e.printStackTrace();
        }
        return author;
    }
    // get list author_edit by author id

    public Author[] list_Author(int start, int limit) {
        try {
           Connection connection = ConnectionServicePubDB.getConnection();
            String sql = "Select * from author limit ?,? ";
            PreparedStatement stm = (PreparedStatement) connection.prepareStatement(sql);
            stm.setInt(1, start);
            stm.setInt(2, limit);
            System.out.print(sql);
            ResultSet rs = stm.executeQuery();
            ArrayList list = new ArrayList();
            while (rs.next()) {

                int authorId = rs.getInt("idAuthor");
                System.out.print(authorId);
                String authorName = rs.getString("authorName");
                String imageUrl = rs.getString("image");
                String emailAddess = rs.getString("emailAddress");
                String website = rs.getString("website");
                //String url = rs.getString("url");
                Author author = new Author();
                author.setAuthor_Id(authorId);
                author.setAuthorName(authorName);
                author.setImageUrl(imageUrl);
                author.setEmailAddress(emailAddress);
                author.setWebsite(website);
                list.add(author);
            }
            Author[] result = new Author[list.size()];
            list.toArray(result);
            rs.close();
            stm.close();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean updateAuthor(String authorName, String image, String emailAddress, String website, int idAuthor) {
        try {
           Connection connection = ConnectionServicePubDB.getConnection();
            String sql = "Update author set authorName = ?,image = ?,emailAddress =?, website = ? where idAuthor = ?";
            PreparedStatement stm = (PreparedStatement) connection.prepareStatement(sql);
            stm.setString(1, authorName);
            stm.setString(2, image);
            stm.setString(3, emailAddress);
            stm.setString(4, website);
            stm.setInt(5, idAuthor);
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
    public void closeConnection() throws NamingException, SQLException
    {
        try {
            ConnectionServicePubDB.getConnection().close();
        } catch (NamingException ex) {
            Logger.getLogger(Author.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
