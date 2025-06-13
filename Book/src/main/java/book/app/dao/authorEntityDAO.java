package book.app.dao;

import book.app.Entity.AuthorEntity;
import book.app.Entity.BookEntity;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

public class authorEntityDAO extends AbstractDAO<AuthorEntity> {
    public authorEntityDAO(SessionFactory sessionFactory){
        super(sessionFactory);
    }
    public AuthorEntity find(int id) {
        return get(id);
    }

    public AuthorEntity save(AuthorEntity authorEntity) {
        return persist(authorEntity);
    }
}
