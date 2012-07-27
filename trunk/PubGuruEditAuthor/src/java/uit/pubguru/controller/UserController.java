//
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uit.pubguru.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import uit.pubguru.constant.PubGuruConst;
import uit.pubguru.model.CountryModel;
import uit.pubguru.model.LogModel;
import uit.pubguru.model.UserModel;
import util.SendEmail;

/**
 *
 * @since 02/02/2012: add similiar publication function
 */
public class UserController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */

    public String md5Password(String password) throws NoSuchAlgorithmException// generate md5 code from password
    {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(password.getBytes());
        byte byteData[] = md.digest();
        //convert the byte to hex format method 1
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < byteData.length; i++) {
            sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, Exception {
        response.setContentType("text/html;charset=UTF-8");
         HttpSession session = request.getSession(true);
        String action = request.getParameter("action");
        PrintWriter out = response.getWriter();
        LogModel log =new LogModel();
        UserModel user = new UserModel();
         UserModel user1 = new UserModel();
        CountryModel ct = new CountryModel();
        try {
            if (action == null) { // default
            } else if (action.equals("login")) {
                processLogin(request, response);
            } else if (action.equals("changePass")) {
                processChangePass(request, response);
            } else if (action.equals("register")) {
                processRegister(request, response);
            } else if (action.equals("getList")) {
                processGetList(request, response);
            } else if (action.equals("logout")) {
                log.setComment("User " + session.getAttribute("username") + " logout !");
                log.setDateEdit(log.datetime());
                log.insert_log();
                session.removeAttribute("username");
                session.removeAttribute("permissionId");
                session.removeAttribute("userId");
                session.removeAttribute("login");
                session.removeAttribute("authorEditId");
                session.removeAttribute("pageResult_");
                session.removeAttribute("author_ID");
                request.setAttribute("pageName", PubGuruConst.LOGIN_PAGE);
                RequestDispatcher dispatcher = request.getRequestDispatcher(PubGuruConst.FRONT_TEMPLATE_PAGE);
                dispatcher.forward(request, response);
            } else if (action.equals("Active")) {
                String authorid = request.getParameter("auhorid");
                if (authorid != null) {
                    session.setAttribute("authorID", authorid);
                }
                String userid = request.getParameter("id");
                // user = new UserModel();
                user.activeUser(Integer.parseInt(userid));
                request.setAttribute("pageName", PubGuruConst.LOGIN_PAGE);
                RequestDispatcher dispatcher = request.getRequestDispatcher(PubGuruConst.FRONT_TEMPLATE_PAGE);
                dispatcher.forward(request, response);
            } else if (action.equals("Enable")) {
                int userId = Integer.parseInt(request.getParameter("userId"));
                // user = new UserModel();
                user.updatePer(userId, PubGuruConst.ENABLE_USER);
                String url = "UserController?action=getList&active=" + PubGuruConst.ACTIVED + "&page=1";
                RequestDispatcher rd = request.getRequestDispatcher(url);
                rd.forward(request, response);
            } else if (action.equals("Disable")) {
                int userId = Integer.parseInt(request.getParameter("userId"));
                //    user = new UserModel();
                user.updatePer(userId, PubGuruConst.DISENABLE_USER);
                String url = "UserController?action=getList&active=" + PubGuruConst.ACTIVED + "&page=1";
                RequestDispatcher rd = request.getRequestDispatcher(url);
                rd.forward(request, response);
            } else if (action.equals("registerform")) {
                //  ct = new CountryModel();
                CountryModel[] listcountry = ct.getAllCountry();
                request.setAttribute("listCountry", listcountry);
                request.setAttribute("pageName", PubGuruConst.REGISTER_PAGE);
                RequestDispatcher rd = request.getRequestDispatcher(PubGuruConst.FRONT_TEMPLATE_PAGE);
                rd.forward(request, response);
            } else if (action.equals("loginform")) {
                RequestDispatcher rd = request.getRequestDispatcher(PubGuruConst.FRONT_TEMPLATE_PAGE);
                request.setAttribute("pageName", PubGuruConst.LOGIN_PAGE);
                rd.forward(request, response);
            } else if (action.equals("success")) {
                request.setAttribute("pageResult", PubGuruConst.RESULT_REGISTER);
                request.setAttribute("pageName", PubGuruConst.RESULT_PAGE);
                RequestDispatcher rd = request.getRequestDispatcher(PubGuruConst.FRONT_TEMPLATE_PAGE);
                rd.forward(request, response);
            } else if (action.equals("changepass")) {
                request.setAttribute("pageName", "changepassword.jsp");
                RequestDispatcher rd = request.getRequestDispatcher(PubGuruConst.BACKEND_TEMPLATE_PAGE);
                rd.forward(request, response);
            } else if (action.equals("Continue")) {
                String pageResult = (String) session.getAttribute("pageResult_");
                String authorId = (String) session.getAttribute("author_ID");
                String url = "";
                if (pageResult == null) {
                    pageResult = PubGuruConst.RESULT_REGISTER;
                    request.setAttribute("pageName", PubGuruConst.REGISTER_PAGE);
                    url = PubGuruConst.FRONT_TEMPLATE_PAGE;
                } else if (pageResult.equals(PubGuruConst.RESULT_REGISTER)) {
                    request.setAttribute("pageName", PubGuruConst.REGISTER_PAGE);
                    url = PubGuruConst.FRONT_TEMPLATE_PAGE;
                } else if (pageResult.equals(PubGuruConst.RESULT_EDIT)) {//note.
                    url = "/AuthorEditController?action=info&authorID=" + authorId;
                } else if (pageResult.equals("loginfail")) {
                    url = "/UserController?action=loginform";
                }
                RequestDispatcher rd = request.getRequestDispatcher(url);
                rd.forward(request, response);
            }
        } catch (Exception e) {
            out.print(e.getMessage());
        } finally {           
//          user.closeConnection();
//          user1.closeConnection();
//          ct.closeConnection();
//           log.closeConnection();
           out.close();
        }
    }

    protected void processLogin(HttpServletRequest request, HttpServletResponse response) throws NoSuchAlgorithmException, IOException, ServletException, Exception {
        PrintWriter out = response.getWriter();
        String submit = request.getParameter("submit");
        String username = request.getParameter("txt_User"); // login by email
        HttpSession session = request.getSession(true);
        String password = request.getParameter("txt_Password");
        String password_md5 = md5Password(password);
        UserModel user = new UserModel();
        LogModel log = new LogModel();
        try {
            if (submit != null && submit.equals("Login")) {
//                user = new UserModel();
                boolean result = user.checkLogin(username, password_md5);
                String url = PubGuruConst.LOGIN_PAGE;
                if (result) {
                    user = user.getUser_byId(username);
                    session.setAttribute("username", username);
                    session.setAttribute("permissionId", String.valueOf(user.getPermissionId()));
                    session.setAttribute("userId", String.valueOf(user.getId()));
                    session.setAttribute("login", "true");
                    String authorID = (String) session.getAttribute("authorID");
                    if (user.getPermissionId() != PubGuruConst.DISENABLE_USER) {
                        if (user.getPermissionId() == PubGuruConst.ADMIN_PERMISSION) {
                            // if user is amdin, redirect to backend.jsp
                            //  request.setAttribute("pageName", PubGuruConst.BACKEND_TEMPLATE_PAGE);
                            url = PubGuruConst.BACKEND_TEMPLATE_PAGE;
                        } else if (authorID != null) {
                            url = "AuthorEditController?action=edit&authorID=" + authorID;
                        } else {
                            url = "AuthorController?action=getList";
                        }                     
                        out.print(url);                    
                    }
                    log.setComment("User " + user.getEmailAddress() + " login !");
                    log.setDateEdit(log.datetime());
                    log.insert_log();
                } else {
                    out.print(-1);                   
                }
            } else {
                RequestDispatcher rd = request.getRequestDispatcher(PubGuruConst.FRONT_TEMPLATE_PAGE);
                request.setAttribute("pageName", PubGuruConst.LOGIN_PAGE);
                rd.forward(request, response);
                out.print(PubGuruConst.FRONT_TEMPLATE_PAGE);
                //return;
            }
        } catch (Exception ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
//            user.closeConnection();
//            log.closeConnection();
        }
    }

    protected void processGetList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, Exception {
        UserModel user = new UserModel();
        try {
//            user = new UserModel();
            int rows = user.rowsResult();
            int rowsperpage = 0;
            if (rows % PubGuruConst.ROW_PERPAGE == 0) {
                rowsperpage = rows / PubGuruConst.ROW_PERPAGE;
            } else {
                rowsperpage = (int) rows / PubGuruConst.ROW_PERPAGE + 1;
            }
            request.setAttribute("Rows", rowsperpage);
            int active = 0; // status =0 : have not edited yet, status = 1 : have edited.
            String sta = (String) request.getParameter("active");
            int page = Integer.parseInt((String) request.getParameter("page"));
            if (sta == null) {
                active = 1;
            } else {
                active = Integer.parseInt(sta);
                if (active == 2) {
                    // status == 2 : get all user
                    UserModel[] result = user.getList_All((page - 1) * 5, 5);
                    request.setAttribute("ListUser", result);
                } else {
                    UserModel[] result = user.getList_byActived(active, (page - 1) * 5, 5);
                    request.setAttribute("ListUser", result);
                }
            }
            request.setAttribute("pageName", PubGuruConst.USER_PAGE);
            RequestDispatcher rd = request.getRequestDispatcher(PubGuruConst.BACKEND_TEMPLATE_PAGE);
            rd.forward(request, response);
        } catch (Exception ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
//            user.closeConnection();
        }
    }

    protected void processRegister(HttpServletRequest request, HttpServletResponse response) throws IOException, NoSuchAlgorithmException, ServletException, Exception {
        //Get parameter
        String email = request.getParameter("txt_email");
        String fullname = request.getParameter("txt_fullname");
        String password = request.getParameter("txt_password");
        String password_md5 = md5Password(password);
        String country = request.getParameter("country");
        String org = request.getParameter("txt_org");
        UserModel user = new UserModel(email, password_md5, fullname, country, 0, 0, "");
        LogModel log = new LogModel();
        try {
            String submit = request.getParameter("submit");
            if (submit != null && submit.equals("Complete")) {
                String code = request.getParameter("txt_code");
                PrintWriter out = response.getWriter();
                HttpSession session = request.getSession(true);
                String captcha = (String) session.getAttribute("captcha");
                System.out.print(captcha + code);
                if (!code.equals(captcha)) {
                    out.print(0); // captcha don't match.
                    return;
                }
                user.setOrganization(org);
                boolean isEmailExist = user.checkEmail(email);
                if (isEmailExist) {
                    out.print(-1); // email exist
                    return;
                }
                if (fullname.length() < 3) {
                    out.print(-2);
                    return; // full name too short
                }
                if (password.length() < 6) {
                    out.print(-3);
                    return; // password too short
                }
                boolean result = user.insert(user);
                if (result) {
                    //Register successfull, send email to reqire active account
                    out.print(1); // successfull
                    out.flush();
                    String userid = String.valueOf(user.maxId());
                    String authorid = (String) session.getAttribute("author_ID");
                    String linkActive;
                    if (authorid != null) {
                        linkActive = PubGuruConst.HTTP_SERVER + "/UserController?action=Active&id=" + userid + "&authorId=" + authorid;
                    } else {
                        linkActive = PubGuruConst.HTTP_SERVER + "/UserController?action=Active&id=" + userid;
                    }
                    String subject = PubGuruConst.EMAIL_ACTIVE_SUBJECT;
                    String content = PubGuruConst.CONTENT_ACTIVE(linkActive);
                    SendEmail.sendMail(email, subject, content);
                    log = new LogModel();
                    String time = log.datetime();
                    String comment = "Insert user : ' " + user.getEmailAddress() + " ' to database ";
                    log.setDateEdit(time);
                    log.setComment(comment);
                    return;
                }
            } else {
                //  RequestDispatcher rd = request.getRequestDispatcher(PubGuruConst.FRONT_TEMPLATE_PAGE);
                request.setAttribute("pageName", PubGuruConst.REGISTER_PAGE);
                RequestDispatcher rd = request.getRequestDispatcher(PubGuruConst.FRONT_TEMPLATE_PAGE);
                rd.forward(request, response);
            }
        } catch (Exception ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
//            user.closeConnection();
//            log.closeConnection();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="processChangePass methods">
    void processChangePass(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, NoSuchAlgorithmException, Exception {
       HttpSession session = request.getSession(true);
        int userId = Integer.parseInt((String) session.getAttribute("userId"));
        UserModel user = new UserModel();
        LogModel  log = new LogModel();
        PrintWriter out = response.getWriter();
        try {
//            user = new UserModel();
            user = user.getUser_byId(userId);
            String oldPass = user.getPassword(); // get old pass from database
            String oldPass_ = request.getParameter("txt_oldPassword"); // get oldpass input by user
            String newPass = request.getParameter("txt_newPassword");
            String newPassConfirm = request.getParameter("txt_newPasswordConfirm");
            if (oldPass.equals(oldPass_)) {
                if (newPass.equals(newPassConfirm)) {
                    // update password
                    if (user.changePass(userId, md5Password(newPass))) {
                        out.print(1); // success
//                        log = new LogModel();
                        log.setComment("User " + session.getAttribute("username") + " changepassword !");
                        log.setDateEdit(log.datetime());
                        log.insert_log();
                    } else {
                        out.print(2); // new pass doesn't match.
                    }
                } else {
                    // new pass doesn't match.
                }
            } else {
                out.print(-1);
            }
            RequestDispatcher rd = request.getRequestDispatcher("");
            rd.forward(request, response);
        } // </editor-fold>
        catch (Exception ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
//            user.closeConnection();
//            log.closeConnection();
        }
    }
    // </editor-fold>

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
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
