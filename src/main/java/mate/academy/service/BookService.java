package mate.academy.service;

import mate.academy.models.Book;

import java.util.List;

public interface BookService {
    Book save(Book book);

    List<Book> findAll();
}
