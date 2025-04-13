package mate.academy.demo.service;

import java.util.List;
import mate.academy.demo.dto.BookDto;
import mate.academy.demo.dto.CreateBookRequestDto;

public interface BookService {
    BookDto save(CreateBookRequestDto bookRequestDto);

    List<BookDto> findAll();

    BookDto findById(Long id);

    boolean deleteById(Long id);

    BookDto update(CreateBookRequestDto createBookRequestDto, Long id);
}
