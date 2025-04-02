package mate.academy.service;

import java.util.List;
import mate.academy.models.Book;

public interface BookService {
    Book save(Book book);

    List<Book> findAll();
}
