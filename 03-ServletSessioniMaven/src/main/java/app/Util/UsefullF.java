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
            out += "<form action=\"CommentServlet\" method=\"post\">\n"
                    + "            Comment: <input type=\"text\" name=\"comment\"> \n"
                    + "            <input type=\"hidden\" name=\"hiddenPostId\" value=\"" + po.getId() + "\">\n"
                    + "            <input type=\"submit\" value=\"send\">\n"
                    + "</form>"
                    + "<button class=\"collapsible\">View comments</button>\n"
                    + "        <div class=\"content\">\n"
                    + "            <ul>\n";
            List<Comment> listaCommenti = Dao.getCommentDAO().findCommentsByPostId(po.getId());
            for( Comment c : listaCommenti )
            {
                out += "<dt>" + c.getDataOra() + " " + c.getAutore() + "</dt>";
                out += "<dd>-" + c.getTesto() + "</dd>";
            }
            out +=    "        </ul>\n"
                    + "        </div>\n";
            out += "</div>";
        }
        return out;
    }
}
