package app.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-02-18T11:26:53")
@StaticMetamodel(Comment.class)
public class Comment_ { 

    public static volatile SingularAttribute<Comment, String> dataOra;
    public static volatile SingularAttribute<Comment, Long> id;
    public static volatile SingularAttribute<Comment, String> autore;
    public static volatile SingularAttribute<Comment, String> testo;
    public static volatile SingularAttribute<Comment, Long> idPost;

}