package mate.academy.repository;

import java.util.List;
import mate.academy.models.Book;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

@Repository
public class BookRepositoryImpl extends AbstractRepository implements BookRepository {
    private final SessionFactory sessionFactory;

    @Override
    public Book save(Book book) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.persist(book);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't save book" + book.getTitle(), e);
        } finally {
            session.close();
        }

        return book;
    }

    @Override
    public List<Book> findAll() {
        Session session = factory.openSession();
        List<Book> allBooks = session.createQuery("SELECT a FROM books a", Book.class).list();

        session.close();
        return allBooks;
    }
}
