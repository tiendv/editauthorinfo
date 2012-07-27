
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uit.pubguru.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import uit.pubguru.constant.PubGuruConst;
import uit.pubguru.model.*;
import util.SendEmail;

/**
 *
 * @author ThanhIT
 */
public class FeedBackController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, NamingException, SQLException, Exception {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession(true);
        String action = request.getParameter("action");
        String login = null;
        login = (String) session.getAttribute("login");
        if (login == null) {
            response.sendRedirect("UserController?action=loginform");
            return;
        }

        if (action.equals("getList")) {
            processGetList(request, response);
        } else if (action.equals("Edit")) {
            processEdit(request, response, session);
        } else if (action.equals("Complete")) {
            try {
                processComplete(request, response, session);
            } catch (Exception ex) {
                Logger.getLogger(FeedBackController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (action.equals("Ignore")) {
            try {
                processIgnore(request, response, session);
            } catch (Exception ex) {
                Logger.getLogger(FeedBackController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    /**
     * Handles
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processGetList(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, Exception {
        FeedBackModel feed = null;
        FeedBackModel[] result = null;
        int status = 0; // status =0 : have not edited yet, status = 1 : have edited.
        String sta = request.getParameter("status");
        request.setAttribute("status_", sta);
        String pa = (String) request.getParameter("page");
        request.setAttribute("page_", pa);
        String sort = request.getParameter("sort");
        request.setAttribute("sort", sort);
        try {
            feed = new FeedBackModel();
            int page = Integer.parseInt(pa);
            String sortby = request.getParameter("sortby");
            if (sort != null) {
                request.setAttribute("sortby", sortby);
                if (sortby.equals("dateedit")) {
                    sortby = "dateEdit";
                } else {
                    sortby = "dateEdit_Confirm";
                }
            }
            int rows;
            int rowsperpage = 0;
            if (sta == null) {
                status = PubGuruConst.CONFIRM_STATUS;
            } else {
                status = Integer.parseInt(sta);
                if (status == PubGuruConst.ALL_STATUS) {
                    if (sort == null) {
                        result = feed.list_AllfeedBack((page - 1) * PubGuruConst.ROW_PERPAGE, PubGuruConst.ROW_PERPAGE);
                    } else {
                        result = feed.list_AllfeedBack_Sort((page - 1) * PubGuruConst.ROW_PERPAGE, PubGuruConst.ROW_PERPAGE, sortby, sort);
                    }
                    rows = feed.row_AllfeedBack();
                    request.setAttribute("ListFeed", result);
                } else {
                    rows = feed.row_byStatus(status);
                    if (sort == null) {
                        result = feed.list_byStatus(status, (page - 1) * PubGuruConst.ROW_PERPAGE, PubGuruConst.ROW_PERPAGE);
                    } else {
                        result = feed.list_byStatus_Sort(status, (page - 1) * PubGuruConst.ROW_PERPAGE, PubGuruConst.ROW_PERPAGE, sortby, sort);
                    }
                    request.setAttribute("ListFeed", result);
                }
                if (rows % PubGuruConst.ROW_PERPAGE == 0) {
                    rowsperpage = rows / PubGuruConst.ROW_PERPAGE;
                } else {
                    rowsperpage = (int) rows / PubGuruConst.ROW_PERPAGE + 1;
                }
                request.setAttribute("Rows", rowsperpage);
                request.setAttribute("pageName", PubGuruConst.FEEDBACK_MANAGER_PAGE);
                request.setAttribute("Controller", "FeedBackController");
                RequestDispatcher rd = request.getRequestDispatcher(PubGuruConst.BACKEND_TEMPLATE_PAGE);
                rd.forward(request, response);
            }
        } catch (Exception ex) {
            Logger.getLogger(FeedBackController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
//            feed.closeConnection();
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
    protected void processEdit(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException, ServletException, NamingException, SQLException, Exception {
        String username = (String) session.getAttribute("username");
        String login = (String) session.getAttribute("login");
        String permission = (String) session.getAttribute("permissionId");
        // get value of author when user send edit!
        String authorName = " ";
        String photoUrl = " ";
        String emailAdderss = " ";
        String website = " ";
        String firstName = " ";
        String lastName = " ";
        String middleName = "";
        String comment = "";
        // get value of original author
        String authorNameD = " ";
        String photoUrlD = " ";
        String emailAdderssD = " ";
        String websiteD = " ";
        String firstNameD = " ";
        String lastNameD = " ";
        String middleNameD = " ";
        String homepageD = "";
        FeedBackModel feed = null;
        FeedBackDetails feeddetails = null;
        Author_EditModel author = null;
        Author authorD = null;
        if (login == null) {
            //request.setAttribute("pageName", PubGuruConst.LOGIN_PAGE);
            RequestDispatcher dispatcher = request.getRequestDispatcher("UserController?action=loginform");
            dispatcher.forward(request, response);
            //response.sendRedirect("UserController?action=loginform");
        } else {
            if (login.equals("true")) {
                if (permission.equals(String.valueOf(PubGuruConst.ADMIN_PERMISSION))) {
                    try {
                        int authorEdit_id = Integer.parseInt(request.getParameter("authorEdit_Id"));
                        session.setAttribute("authorEditId", authorEdit_id);
                        feed = new FeedBackModel();
                        feeddetails = new FeedBackDetails();
                        int feedid = feed.getfeedbackId(authorEdit_id);
                        FeedBackDetails[] feeddt = feeddetails.getList_byFeeBackId(feedid);
                        for (int i = 0; i < feeddt.length; i++) {
                            if (feeddt[i].getName().equals("firstName")) {
                                request.setAttribute("firstNamedt", "firstName");
                                if (feeddt[i].getStatus() == 0) {
                                    request.setAttribute("firstNameSTT", "");
                                } else {
                                    request.setAttribute("firstNameSTT", "disabled checked");
                                }
                            }
                            if (feeddt[i].getName().equals("middleName")) {
                                request.setAttribute("middleNamedt", "middleName");
                                if (feeddt[i].getStatus() == 0) {
                                    request.setAttribute("middleNameSTT", "");
                                } else {
                                    request.setAttribute("middleNameSTT", "disabled checked");
                                }
                            }
                            if (feeddt[i].getName().equals("lastName")) {
                                request.setAttribute("lastNamedt", "lastName");
                                if (feeddt[i].getStatus() == 0) {
                                    request.setAttribute("lastNameSTT", "");
                                } else {
                                    request.setAttribute("lastNameSTT", "disabled checked");
                                }
                            }
                            if (feeddt[i].getName().equals("website")) {
                                request.setAttribute("websitedt", "website");
                                if (feeddt[i].getStatus() == 0) {
                                    request.setAttribute("websiteSTT", "");
                                } else {
                                    request.setAttribute("websiteSTT", "disabled checked");
                                }
                            }
                            if (feeddt[i].getName().equals("imageUrl")) {
                                request.setAttribute("imageUrldt", "imageUrl");
                                if (feeddt[i].getStatus() == 0) {
                                    request.setAttribute("imageUrlSTT", "");
                                } else {
                                    request.setAttribute("imageUrlSTT", "disabled checked");
                                }
                            }
                            if (feeddt[i].getName().equals("emailAddress")) {
                                request.setAttribute("emaildt", "email");
                                if (feeddt[i].getStatus() == 0) {
                                    request.setAttribute("emailSTT", "");
                                } else {
                                    request.setAttribute("emailSTT", "disabled checked");
                                }
                            }
                        }
                        author = new Author_EditModel();
                        author = author.getAuthor_byId(authorEdit_id);
                        authorName = author.getAuthorName();
                        photoUrl = author.getImageUrl();
                        emailAdderss = author.getEmailAddress();
                        website = author.getWebsite();
                        comment = author.getComment();
                        String[] array = authorName.split(" ");
                        firstName = array[0];
                        lastName = array[array.length - 1];
                        middleName = "";
                        for (int i = 1; i < array.length - 1; i++) {
                            middleName += array[i];
                        }
                        authorD = new Author();
                        authorD = authorD.getAuthorById(author.getAuthor_Id());
                        authorNameD = authorD.getAuthorName();
                        photoUrlD = authorD.getImageUrl();
                        emailAdderssD = authorD.getEmailAddress();
                        websiteD = authorD.getWebsite();
                        String[] array1 = authorNameD.split(" ");
                        homepageD = authorD.getWebsite();
                        firstNameD = array1[0];
                        lastNameD = array1[array1.length - 1];
                        middleNameD = "";
                        for (int i = 1; i < array1.length - 1; i++) {
                            middleNameD += array1[i];
                        }
                        // set attribute
                        request.setAttribute("authorID", String.valueOf(author.getAuthor_Id()));
                        request.setAttribute("authorName", authorName);
                        request.setAttribute("photoUrl", photoUrl);
                        request.setAttribute("emailAdderss", emailAdderss);
                        request.setAttribute("website", website);
                        request.setAttribute("firstName", firstName);
                        request.setAttribute("lastName", lastName);
                        request.setAttribute("middleName", middleName);
                        request.setAttribute("authorNameD", authorNameD);
                        request.setAttribute("photoUrlD", photoUrlD);
                        request.setAttribute("emailAdderssD", emailAdderssD);
                        request.setAttribute("websiteD", websiteD);
                        request.setAttribute("firstNameD", firstNameD);
                        request.setAttribute("lastNameD", lastNameD);
                        request.setAttribute("middleNameD", middleNameD);
                        request.setAttribute("comment", comment);
                        request.setAttribute("Controller", "FeedBackControlle");
                    } catch (Exception ex) {
                        Logger.getLogger(FeedBackController.class.getName()).log(Level.SEVERE, null, ex);
                    } finally {
//                        feeddetails.closeConnection();
//                         feed.closeConnection();
//                        author.closeConnection();
//                        authorD.closeConnection();
                    }
                } else {
                    request.setAttribute("pageName", PubGuruConst.PER_ERror_PAGE);
                    RequestDispatcher rd = request.getRequestDispatcher(PubGuruConst.FRONT_TEMPLATE_PAGE);
                    rd.forward(request, response);
                }
            } else {
                request.setAttribute("pageName", PubGuruConst.LOGIN_PAGE);
                RequestDispatcher rd = request.getRequestDispatcher(PubGuruConst.FRONT_TEMPLATE_PAGE);
                rd.forward(request, response);
            }

        }
        RequestDispatcher rd = request.getRequestDispatcher(PubGuruConst.BACKEND_TEMPLATE_PAGE);
        request.setAttribute("pageName", PubGuruConst.COFIRMEDIT_PAGE);
        rd.forward(request, response);

    }
    // <editor-fold defaultstate="collapsed" desc="Click on the + sign on the left to edit the code.">

    /**
     * Handles
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processComplete(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException, ServletException, NamingException, SQLException, Exception {
        PrintWriter out = response.getWriter();
        String chkfirstName = request.getParameter("chkFirstName");
        String chkmiddleName = request.getParameter("chkMiddleName");
        String chklastName = request.getParameter("chkLastName");
        String chkhomePage = request.getParameter("chkHomePage");
        String chkphotoUrl = request.getParameter("chkPhotoUrl");
        String chkemail = request.getParameter("chkEmail");
        String firstName = "";
        String middleName = "";
        String lastName = "";
        String homePage = "";
        String photoUrl = "";
        String email = "";
        FeedBackModel fee = null;
        FeedBackDetails fbdt = null;
        UserModel user = null;
        Author_EditModel author = null;
        Author au = null;
        try {
            fee = new FeedBackModel();
            if (chkemail == null && chkfirstName == null && chkhomePage == null && chklastName == null && chkmiddleName == null && chkphotoUrl == null) {
                out.print(0); // nothing to update
            } else {
                fbdt = new FeedBackDetails();
                // get feedback id
                int feedbackid = fee.getfeedbackId(Integer.parseInt(String.valueOf(session.getAttribute("authorEditId"))));
                if (chkfirstName != null) {
                    firstName = request.getParameter("e_firstName");
                    fbdt.update_fbdetail(1, feedbackid, "firstName"); //update status =1 in feedback detail when user improve infor
                } else {
                    firstName = request.getParameter("firstName");
                }
                if (chkmiddleName != null) {
                    middleName = request.getParameter("e_middleName");
                    fbdt.update_fbdetail(1, feedbackid, "middleName");
                } else {
                    middleName = request.getParameter("middleName");
                }
                if (chklastName != null) {
                    lastName = request.getParameter("e_lastName");
                    fbdt.update_fbdetail(1, feedbackid, "lastName");
                } else {
                    lastName = request.getParameter("lastName");
                }
                if (chkhomePage != null) {
                    homePage = request.getParameter("e_homepageUrl");
                    fbdt.update_fbdetail(1, feedbackid, "website");
                } else {
                    homePage = request.getParameter("homepageUrl");
                }
                if (chkphotoUrl != null) {
                    photoUrl = request.getParameter("e_photoUrl");
                    fbdt.update_fbdetail(1, feedbackid, "imageUrl");
                } else {
                    photoUrl = request.getParameter("photoUrl");
                }
                if (chkemail != null) {
                    email = request.getParameter("e_txtEmail");
                    fbdt.update_fbdetail(1, feedbackid, "emailAddress");
                } else {
                    email = request.getParameter("txtEmail");
                }
                String comment = request.getParameter("comment");
                user = new UserModel();
                au = new Author();
                author = new Author_EditModel();
                author = author.getAuthor_byId(Integer.parseInt(String.valueOf(session.getAttribute("authorEditId")))); // get author by authorEdit_Id
                // update author infor
                boolean a = au.updateAuthor(firstName + " " + middleName + " " + lastName, photoUrl, email, homePage, author.getAuthor_Id());
                if (a == true) {
                    request.setAttribute("pageResult", PubGuruConst.RESULT_COMPETE);
                    LogModel log = new LogModel();
                    String time = log.datetime();
                    String authorname = request.getParameter("firstName") + request.getParameter("middleName") + request.getParameter("lastName"); // get authorname in original database( before edit )
                    String subject = PubGuruConst.EMAIL_EDITED_SUBJECT;
                    boolean result = fee.updateFeedBack(Integer.parseInt((String) session.getAttribute("userId")), time, 1, Integer.parseInt(String.valueOf(session.getAttribute("authorEditId")))); // update feedback, status is set = 1
                    log.setDateEdit(time);
                    log.setComment("Admin : " + session.getAttribute("username") + " have improved info of author " + author.getAuthorName());
                    log.insert_log();
                    String url =  PubGuruConst.HTTP_PUBGURU_SERVER + "/author.guru?aid=" + author.getAuthor_Id();
                    String content = PubGuruConst.CONTENT_IMPROVE(user.name_byId(fee.getuserId(Integer.parseInt(String.valueOf(session.getAttribute("authorEditId"))))), authorname, url);
                    SendEmail.sendMail(user.name_byId(fee.getuserId(Integer.parseInt(String.valueOf(session.getAttribute("authorEditId"))))), subject, content);
                    out.print(1);
                } else {
                    out.print(-1); //update fail
                }
            }

        } catch (Exception ex) {
            Logger.getLogger(FeedBackController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            out.close();
//            fee.closeConnection();
//            fbdt.closeConnection();
//            user.closeConnection();
//            author.closeConnection();
//            au.closeConnection();
        }
    }

    protected void processIgnore(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException, ServletException, NamingException, SQLException, Exception {
        FeedBackModel fee = null;
        LogModel log = null;
        UserModel user = null;
        Author au = null;
        Author_EditModel author = null;
        PrintWriter out = response.getWriter();
        try {
            fee = new FeedBackModel();
            log = new LogModel();
            String time = log.datetime();
            boolean result = fee.updateStatus(PubGuruConst.IGNORE_STATUS, Integer.parseInt(String.valueOf(session.getAttribute("authorEditId"))), time);
            if (result) {
                request.setAttribute("pageResult", PubGuruConst.RESULT_IGNORE);
                // duplicate code, ... process later
                user = new UserModel();
                au = new Author();
                author = new Author_EditModel();
                author = author.getAuthor_byId(Integer.parseInt(String.valueOf(session.getAttribute("authorEditId")))); // get author by authorEdit_Id
                // update author infor
                String authorname = request.getParameter("firstName") + request.getParameter("middleName") + request.getParameter("lastName"); // get authorname in original database( before edit )
                String subject = PubGuruConst.EMAIL_EDITED_SUBJECT;
                log.setDateEdit(time);
                log.setComment("Admin : " + session.getAttribute("username") + " have  ignored edit author " + author.getAuthorName());
                log.insert_log();
                String content = PubGuruConst.CONTENT_IGNORE(user.name_byId(fee.getuserId(Integer.parseInt(String.valueOf(session.getAttribute("authorEditId"))))), author.getAuthorName());
                SendEmail.sendMail(user.name_byId(fee.getuserId(Integer.parseInt(String.valueOf(session.getAttribute("authorEditId"))))), subject, content);
                out.print(1); //ignore success
            } else {
                out.print(0); // update fail
            }
        } catch (Exception ex) {
            Logger.getLogger(FeedBackController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            out.close();
//            fee.closeConnection();
//            user.closeConnection();
//            au.closeConnection();
//            author.closeConnection();

        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
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
        } catch (NamingException ex) {
            Logger.getLogger(FeedBackController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(FeedBackController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(FeedBackController.class.getName()).log(Level.SEVERE, null, ex);
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
        } catch (NamingException ex) {
            Logger.getLogger(FeedBackController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(FeedBackController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(FeedBackController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
