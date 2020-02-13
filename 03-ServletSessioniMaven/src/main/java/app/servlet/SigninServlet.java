/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.servlet;

import app.dao.Dao;
import app.entity.BlogUser;
import com.google.common.hash.Hashing;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 *
 * @author Bachir_Karim
 */
public class SigninServlet extends HttpServlet
{

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet( HttpServletRequest request, HttpServletResponse response )
            throws ServletException, IOException
    {

    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost( HttpServletRequest request, HttpServletResponse response )
            throws ServletException, IOException
    {
        try( PrintWriter out = response.getWriter() )
        {
            response.setContentType("text/html");
            String name = request.getParameter("name");
            String password = request.getParameter("password");
            if( Dao.getUserDao().findUserByName(name) )
            {
                request.getRequestDispatcher("signIn.html").include(request, response);
                out.print("<h2>This user already exists!</h2>");
            }
            else
            {

                BlogUser u = new BlogUser(name, Hashing.sha256().hashString(password, StandardCharsets.UTF_8).toString());
                Dao.getUserDao().insertUser(u);
                out.print("<h2>Correctly signed in!</h2>");
                request.getRequestDispatcher("index.html").include(request, response);
            }
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo()
    {
        return "Short description";
    }// </editor-fold>

}
