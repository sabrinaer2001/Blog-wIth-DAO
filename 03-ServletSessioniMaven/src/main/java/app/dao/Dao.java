/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.dao;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;


/**
 *
 * @author Bachir_Karim
 */
public class Dao
{

    String PERSISTENCE_UNIT_NAME = "persistence";
    private final EntityManager em = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME)
            .createEntityManager();
    static PostDao postDao = new PostDao();
    static BlogUserDao userDao = new BlogUserDao();
    static CommentDao commentDAO = new CommentDao();

    public static CommentDao getCommentDAO()
    {
        return Dao.commentDAO;
    }

    public static PostDao getPostDao()
    {
        return Dao.postDao;
    }

    public static BlogUserDao getUserDao()
    {
        return Dao.userDao;
    }

}
