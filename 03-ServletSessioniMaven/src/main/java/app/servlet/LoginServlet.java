package app.servlet;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import app.dao.Dao;
import app.entity.Comment;
import app.entity.Post;
import com.google.common.hash.Hashing;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Bachir Karim
 */
public class LoginServlet extends HttpServlet
{

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     *
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException
    {
        response.setContentType("text/html");
        try (PrintWriter out = response.getWriter())
        {

            String name = request.getParameter("name");
            String password = Hashing.sha256().hashString(request.getParameter("password"), StandardCharsets.UTF_8).toString();

            if (name.equals("admin") && password.equals(Hashing.sha256().hashString("admin", StandardCharsets.UTF_8).toString()))
            {
              
                HttpSession session = request.getSession();
                session.setAttribute("name", name);

                request.getRequestDispatcher("LoggedBlog.html").include(request, response);
                
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
            else if(Dao.getUserDao().findUser(name, password))
            {
                HttpSession session = request.getSession();
                session.setAttribute("name", name);

                request.getRequestDispatcher("GuestBlog.html").include(request, response);
                
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
            else
            {
                out.print("Sorry, username or password error!");
                request.getRequestDispatcher("login.html").include(request, response);
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
