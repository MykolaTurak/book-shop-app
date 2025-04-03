package mate.academy.demo.repository;

import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import mate.academy.demo.exeptions.DataProcessingException;
import mate.academy.demo.model.Book;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BookRepositoryImpl implements BookRepository{
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Book save(Book book) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()){
            transaction = session.beginTransaction();
            session.persist(book);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can't save book in db: " + book.getTitle());
        }

        return book;
    }

    @Override
    public List<Book> findAll() {
        List<Book> bookList = null;
        try (Session session = sessionFactory.openSession()) {
            bookList = session.createQuery("SELECT a FROM Book a", Book.class).getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can't get all books");
        }

        return bookList;
    }
}
