/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.servlet;

import app.Util.UsefullF;
import app.dao.Dao;
import app.entity.Comment;
import app.entity.Post;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


/**
 *
 * @author Bachir_Karim
 */
public class CommentServlet extends HttpServlet
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
            HttpSession session = request.getSession(false);
            String comment = request.getParameter("comment");
            if(!"".equals(comment))
            {
                String postId = request.getParameter("hiddenPostId");

                LocalDateTime myDateObj = LocalDateTime.now();
                DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
                String formattedDate = myDateObj.format(myFormatObj);

                Comment co = new Comment(formattedDate, comment, (String) session.getAttribute("name"), Long.parseLong(postId));
                Dao.getCommentDAO().insertComment(co);
                response.setContentType("text/html");

            }
            if( !"admin".equals(session.getAttribute("name")) )
            {
                request.getRequestDispatcher("PublicBlog.html").include(request, response);
            }
            else
            {
                request.getRequestDispatcher("AdminBlog.html").include(request, response);
            }

            List<Post> listaPost = Dao.getPostDao().findAll();
            out.print(UsefullF.getPostAndComments(listaPost));
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
