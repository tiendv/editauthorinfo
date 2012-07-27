
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uit.pubguru.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.Session;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import uit.pubguru.constant.PubGuruConst;
import uit.pubguru.model.Author;
import uit.pubguru.model.Author_EditModel;
import uit.pubguru.model.FeedBackDetails;
import uit.pubguru.model.FeedBackModel;
import uit.pubguru.model.LogModel;
import uit.pubguru.model.UserModel;
import util.SendEmail;

/**
 *
 * @author ThanhIT
 */
public class AuthorEditController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException, Exception {
        HttpSession session = request.getSession(true);
        String authorID = request.getParameter("authorID");
        String login = null;
        login = (String) session.getAttribute("login");
        String action = request.getParameter("action");
        if (action.equals("complete")) {
            if (login == null) {
                session.setAttribute("authorID", authorID);
                request.setAttribute("pageName", PubGuruConst.LOGIN_PAGE);
                RequestDispatcher dispatcher = request.getRequestDispatcher(PubGuruConst.FRONT_TEMPLATE_PAGE);
                dispatcher.forward(request, response);
                return;
            }
            processComplete(request, response, session);
        } else if (action.equals("edit")) {
            if (login == null) {
                session.setAttribute("authorID", authorID);
                request.setAttribute("pageName", PubGuruConst.LOGIN_PAGE);
                RequestDispatcher dispatcher = request.getRequestDispatcher(PubGuruConst.FRONT_TEMPLATE_PAGE);
                dispatcher.forward(request, response);
                return;
            }
            processEditAuthor(request, response, session);
        } else if (action.equals("info")) {
            processAuthorInfo(request, response, session);
        } else if (action.equals("cancel")) {
            if (login == null) {
                session.setAttribute("authorID", authorID);
                request.setAttribute("pageName", PubGuruConst.LOGIN_PAGE);
                RequestDispatcher dispatcher = request.getRequestDispatcher(PubGuruConst.FRONT_TEMPLATE_PAGE);
                dispatcher.forward(request, response);
                return;
            }
            processCancel(request, response, session);
        }
    }

    /**
     * Handles
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processComplete(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException, ServletException, SQLException, Exception {
        UserModel usermodel = null;
        Author au = null;
        Author_EditModel author = null;
        FeedBackModel feedback = null;
        FeedBackDetails fbd = null;
        try {
            String str_userid = (String) session.getAttribute("userId");
            if (str_userid == null) {
                return;
            }
            int userid = Integer.parseInt(str_userid);
            usermodel = new UserModel();
            au = new Author();
            String user_email = usermodel.name_byId(userid); // get email of user !
            String authorID = request.getParameter("authorID");
            String firstName = request.getParameter("txt_firstName");
            String middleName = request.getParameter("txt_middleName");
            String lastName = request.getParameter("txt_lastName");
            String website = request.getParameter("txt_homepageUrl");
            String imageUrl = request.getParameter("txt_photoUrl");
            String email = request.getParameter("txt_email");
            String comment = request.getParameter("txt_comment");
            String authorName = firstName + " " + middleName + " " + lastName;
            int authorid = Integer.parseInt(authorID);
            // get author infor before edit
            au = au.getAuthorById(authorid);
            String[] array = au.getAuthorName().split(" ");
            String firstName_ = array[0];
            String lastName_ = array[array.length - 1];
            String middleName_ = "";
            String email_=au.getEmailAddress();
            if(email_.equals(null))
                email_="";
            String imageurl_=au.getImageUrl();
             System.out.print("ASDFASDFa234 " + imageurl_);
           if(imageurl_==(null))
               imageurl_="";

            String homepage_=au.getUrl();
            if(homepage_==(null))
                homepage_="";
            for (int i = 1; i < array.length - 1; i++) {
                middleName_ += array[i];
            }
            //if have no info change, don't do any thing
            if (firstName.equals(firstName_) && middleName.equals(middleName_) && lastName.equals(lastName_) && email.equals(au.getEmailAddress()) && website.equals(au.getWebsite()) && imageUrl.equals(au.getImageUrl())) {
                System.out.print("no thing to update");
            } else {
                author = new Author_EditModel(authorid, authorName, imageUrl, email, website, comment);
                boolean result = author.insert(author);
                if (result) {
                    // get curent time
                    LogModel log = new LogModel();
                    String time = log.datetime();
                    String commentlog = "User : ' " + user_email + " ' send edit information about author : ' " + authorName + " '";
                    //Log to database
                    log.setDateEdit(time);
                    log.setComment(commentlog);
                    log.insert_log();
                    //Save to Feeadback list
                    feedback = new FeedBackModel(author.maxId(), userid, 0, time, "", 0);
                    feedback.insert();
                    // get feedback id
                    int feedbackid = feedback.maxId(); // max id is the feedback id have just insert.
                    // compare, if infor edit user input is diff infor before edit =>> insert to feedbackdetails
                    fbd = new FeedBackDetails();
                    if (firstName.equals(firstName_) == false) {
                        fbd.insert_fbdetail(feedbackid, "firstName", 0);
                    }
                    if (middleName.equals(middleName_) == false) {
                        fbd.insert_fbdetail(feedbackid, "middleName", 0);
                    }
                    if (lastName.equals(lastName_) == false) {
                        fbd.insert_fbdetail(feedbackid, "lastName", 0);
                    }
                    if (email.equals(email_) == false ) {
                        fbd.insert_fbdetail(feedbackid, "emailAddress", 0);
                    }
                    if (website.equals(homepage_) == false) {
                        fbd.insert_fbdetail(feedbackid, "website", 0);
                    }
                    if (imageUrl.equals(imageurl_) == false) {
                        fbd.insert_fbdetail(feedbackid, "imageUrl", 0);
                    }
                    request.setAttribute("pageResult", PubGuruConst.RESULT_EDIT);
                    request.setAttribute("pageName", PubGuruConst.RESULT_PAGE);
                    RequestDispatcher dispatcher = request.getRequestDispatcher(PubGuruConst.FRONT_TEMPLATE_PAGE);
                    dispatcher.forward(request, response);
                } else {
                    request.setAttribute("pageName", PubGuruConst.LOGIN_PAGE);
                    RequestDispatcher dispatcher = request.getRequestDispatcher(PubGuruConst.FRONT_TEMPLATE_PAGE);
                    dispatcher.forward(request, response);
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(AuthorEditController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
//            au.closeConnection();
//            fbd.closeConnection();
//            author.closeConnection();
//            feedback.closeConnection();
//            usermodel.closeConnection();
        }
    }

    /**
     * Handles
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processEditAuthor(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException, ServletException {

        String authorID = (String) request.getParameter("authorID");
        session.setAttribute("author_ID", authorID);
        request.setAttribute("pageName", PubGuruConst.AUTHOREDIT_PAGE);
        RequestDispatcher dispatcher = request.getRequestDispatcher(PubGuruConst.FRONT_TEMPLATE_PAGE);
        dispatcher.forward(request, response);
    }

    protected void processAuthorInfo(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException, ServletException {
        String authorID = (String) request.getParameter("authorID");
        request.setAttribute("pageName", PubGuruConst.AUTHORINFO_PAGE);
        RequestDispatcher dispatcher = request.getRequestDispatcher(PubGuruConst.FRONT_TEMPLATE_PAGE);
        dispatcher.forward(request, response);
    }

    protected void processCancel(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException, ServletException {
        String authorID = (String) request.getParameter("authorID");
        request.setAttribute("pageName", PubGuruConst.AUTHORINFO_PAGE);
        RequestDispatcher dispatcher = request.getRequestDispatcher(PubGuruConst.FRONT_TEMPLATE_PAGE);
        dispatcher.forward(request, response);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(AuthorEditController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(AuthorEditController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
