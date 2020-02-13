package app.servlet;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import app.Util.UsefullF;
import app.dao.Dao;
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
    protected void doPost( HttpServletRequest request,
            HttpServletResponse response ) throws ServletException, IOException
    {
        response.setContentType("text/html");
        try( PrintWriter out = response.getWriter() )
        {

            String name = request.getParameter("name");
            String password = Hashing.sha256().hashString(request.getParameter("password"), StandardCharsets.UTF_8).toString();

            if( name.equals("admin") && password.equals(Hashing.sha256().hashString("admin", StandardCharsets.UTF_8).toString()) )
            {

                HttpSession session = request.getSession();
                session.setAttribute("name", name);

                request.getRequestDispatcher("AdminBlog.html").include(request, response);

                List<Post> listaPost = Dao.getPostDao().findAll();
                out.print(UsefullF.getPostAndComments(listaPost));

            }
            else if( Dao.getUserDao().findUser(name, password) )
            {
                HttpSession session = request.getSession();
                session.setAttribute("name", name);

                request.getRequestDispatcher("PublicBlog.html").include(request, response);

                List<Post> listaPost = Dao.getPostDao().findAll();
                out.print(UsefullF.getPostAndComments(listaPost));
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
