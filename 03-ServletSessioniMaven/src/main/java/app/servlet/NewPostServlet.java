/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.servlet;

import app.dao.Dao;
import app.entity.Comment;
import app.entity.Post;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 *
 * @author Bachir_Karim
 */
@WebServlet(name = "NewPostServlet", urlPatterns =
{
    "/NewPostServlet"
})
public class NewPostServlet extends HttpServlet
{

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest( HttpServletRequest request, HttpServletResponse response )
            throws ServletException, IOException
    {
        response.setContentType("text/html;charset=UTF-8");
        try( PrintWriter out = response.getWriter() )
        {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet NewPostServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet NewPostServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
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
        response.setContentType("text/html");
        try (PrintWriter out = response.getWriter())
        {
            request.getRequestDispatcher("LoggedBlog.html").include(request, response);
            
            String title = request.getParameter("title");
            String text = request.getParameter("text");
            
            LocalDateTime myDateObj = LocalDateTime.now();
            DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
            String formattedDate = myDateObj.format(myFormatObj);
            
            Post p = new Post(title,(String)request.getSession(false).getAttribute("name"),formattedDate,text);
            Dao.getPostDao().insertPost(p);
            
            List<Post> listaPost = Dao.getPostDao().findAll();
            for(Post po : listaPost)
            {
                out.print("<div class=\"card\">");
                out.print("<h2>" + po.getTitolo() + "</h2>");
                out.print("<h5> Author: " + po.getAutore()+ "</h5>");
                out.print("<h5> Date & Time: " + po.getDataOra() + "</h5>");
                out.print("<p>" + po.getTesto() + "</p><hr>");
                out.print(  "<form action=\"CommentServlet\" method=\"post\">\n" +
                            "            Comment: <input type=\"text\" name=\"comment\"> \n" +
                            "            <input type=\"hidden\" name=\"hiddenPostId\" value=\""+ po.getId() +"\">\n" +             
                            "            <input type=\"submit\" value=\"send\">\n" +
                            "</form>");
                List<Comment> listaCommenti = Dao.getCommentDAO().findCommentsByPostId(po.getId());
                for(Comment c : listaCommenti)
                {
                    out.print("<h5>" + c.getAutore() + ": " + c.getTesto() + " " + c.getDataOra() + "</h5>");
                }
                out.print("</div>");
            }
            
        }
    }

    

}
