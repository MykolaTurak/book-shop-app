package mate.academy.demo.repository;

import java.util.List;
import mate.academy.demo.model.Book;

public interface BookRepository {
    Book save(Book book);

    List<Book> findAll();
}
