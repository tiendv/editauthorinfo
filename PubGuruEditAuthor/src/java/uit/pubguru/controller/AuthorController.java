
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uit.pubguru.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import uit.pubguru.constant.PubGuruConst;
import uit.pubguru.model.Author;
import org.json.simple.JSONObject;

/**
 *
 * @author ThanhIT
 */
public class AuthorController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, Exception {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        //ConnDb connDb = new ConnDb();
        Author au = new Author();
        Author[] result = null;
        try {           
            String action = request.getParameter("action");
            if (action.equals("getList")) {
                result = au.list_Author(15, 15);
                request.setAttribute("ListAuthor", result);
                request.setAttribute("pageName", PubGuruConst.AUTHORLIST_PAGE);
                RequestDispatcher rd = request.getRequestDispatcher(PubGuruConst.FRONT_TEMPLATE_PAGE);
                rd.forward(request, response);
            } else if (action.equals("getInfo")) {
//                // return Json info on Author ID
//                // Input: authorID
                String authorID = request.getParameter("authorID");
                au = au.getAuthorById(Integer.parseInt(authorID));
                String[] array = au.getAuthorName().split(" ");
                String firstName = array[0];
                String lastName = array[array.length - 1];
                String middleName = "";
                for (int i = 1; i < array.length - 1; i++) {
                    middleName += array[i];
                }

                JSONObject obj = new JSONObject();
                obj.put("authorID", au.getAuthor_Id());
                obj.put("authorName", au.getAuthorName());
                obj.put("firstName", firstName);
                obj.put("lastName", lastName);
                obj.put("middleName", middleName);
                obj.put("imageUrl", au.getImageUrl());
                obj.put("website", au.getWebsite());
                obj.put("emailAddress", au.getEmailAddress());
                out.print(obj);
                out.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //connDb.closeConnection();
            out.close();
        }
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
            Logger.getLogger(AuthorController.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(AuthorController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
