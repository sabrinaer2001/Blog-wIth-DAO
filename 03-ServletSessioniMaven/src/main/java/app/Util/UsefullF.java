/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.Util;

import app.dao.Dao;
import app.entity.Comment;
import app.entity.Post;
import java.util.List;


/**
 *
 * @author Bachir_Karim
 */
public class UsefullF
{

    public static String getPostAndComments( List<Post> listaPost )
    {
        String out = "";
        for( Post po : listaPost )
        {
            out += "<div class=\"card\">";
            out += "<h2>" + po.getTitolo() + "</h2>";
            out += "<h5> Author: " + po.getAutore() + "</h5>";
            out += "<h5> Date & Time: " + po.getDataOra() + "</h5>";
            out += "<p>" + po.getTesto() + "</p><hr>";
            out += "<form action=\"CommentServlet\" method=\"post\">"
                    + "Comment: <input type=\"text\" name=\"comment\">"
                    + "<input type=\"hidden\" name=\"hiddenPostId\" value=\"" + po.getId() + "\">"
                    + "<input type=\"submit\" value=\"send\">"
                    + "</form>"
                    + "<button class=\"collapsible\">View comments</button>"
                    + "<div class=\"content\">"
                    + "<dl>";
            List<Comment> listaCommenti = Dao.getCommentDAO().findCommentsByPostId(po.getId());
            for( Comment c : listaCommenti )
            {
                out +=    "<div class=\"comment\">"
                        + "<dt>" + c.getAutore() + ":</dt>";
                out +=    "<dd>" + c.getTesto() + "</dd>"
                        + "<h6>" + c.getDataOra() + "</h6>"
                        + "</div>";
                //out += "<p>" + "<span style=\"text-align: right;\">" + c.getDataOra() + "</span>" + " " + c.getAutore() + ":\n" + c.getTesto() + "</p>";
            }
            out +=    "</dl>"
                    + "</div>"
                    + "</div>"
                    + "<script src=\"ViewComments.js\"></script>";
        }
        return out;
    }
}
