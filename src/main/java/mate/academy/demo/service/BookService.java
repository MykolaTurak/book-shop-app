package mate.academy.demo.service;

import mate.academy.demo.dto.BookDto;
import mate.academy.demo.dto.CreateBookRequestDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookService {
    BookDto save(CreateBookRequestDto bookRequestDto);

    Page<BookDto> findAll(Pageable pageable);

    BookDto findById(Long id);

    void deleteById(Long id);

    BookDto update(CreateBookRequestDto createBookRequestDto, Long id);
}
