package mate.academy.demo.service;

import java.util.List;
import mate.academy.demo.model.Book;

public interface BookService {
    Book save(Book book);

    List<Book> findAll();
}
