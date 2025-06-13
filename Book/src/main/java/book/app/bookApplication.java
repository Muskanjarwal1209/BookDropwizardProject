package book.app;

import book.app.Entity.BookEntity;
import book.app.config.BookConfiguration;

import book.app.dao.authorEntityDAO;
import book.app.dao.bookEntityDAO;
import book.app.resources.AuthorResources;
import book.app.resources.BookResources;
import book.app.service.AuthorService;
import book.app.service.BookService;
import io.dropwizard.core.Application;
import io.dropwizard.core.setup.Bootstrap;
import io.dropwizard.core.setup.Environment;
import io.dropwizard.db.PooledDataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;

public class bookApplication extends Application<BookConfiguration> {
    public static void main(String[] args) throws Exception {
        new bookApplication().run(args);
    }

//
   HibernateBundle<BookConfiguration> hibernateBundle = new HibernateBundle<BookConfiguration>(BookEntity.class) {
        @Override
        public PooledDataSourceFactory getDataSourceFactory(BookConfiguration configuration) {
            return configuration.getDataSourceFactory();
        }
    };

    //-----------compul
    @Override
    public void initialize(Bootstrap<BookConfiguration> bootstrap) {
        bootstrap.addBundle(hibernateBundle);
    }

    @Override
    public void run(BookConfiguration configuration, Environment environment) {
        System.out.println("App is running");

        bookEntityDAO bookEntityDAO = new bookEntityDAO(hibernateBundle.getSessionFactory());
        authorEntityDAO authorEntityDAO = new authorEntityDAO(hibernateBundle.getSessionFactory());

        // Services
        BookService bookService = new BookService(bookEntityDAO);
        AuthorService authorService = new AuthorService(authorEntityDAO);

        // Resources (Register with Jersey)
        environment.jersey().register(new BookResources(bookService));
        environment.jersey().register(new AuthorResources(authorService));
    }
}
