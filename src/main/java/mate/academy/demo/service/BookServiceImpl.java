package mate.academy.demo.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import mate.academy.demo.dto.BookDto;
import mate.academy.demo.dto.CreateBookRequestDto;
import mate.academy.demo.exeption.EntityNotFoundException;
import mate.academy.demo.mapper.BookMapper;
import mate.academy.demo.model.Book;
import mate.academy.demo.repository.BookRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    @Override
    public BookDto save(CreateBookRequestDto bookRequestDto) {
        Book book = bookMapper.toModel(bookRequestDto);

        return bookMapper.toDto(bookRepository.save(book));
    }

    @Override
    public List<BookDto> findAll() {
        return bookRepository.findAll().stream()
                .map(bookMapper::toDto)
                .toList();
    }

    @Override
    public BookDto findById(Long id) {
        return bookMapper.toDto(bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cant find book with id: " + id)));
    }

    @Override
    public void deleteById(Long id) {
        bookRepository.deleteById(id);
        return true;
    }

    @Override
    public BookDto update(CreateBookRequestDto createBookRequestDto, Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cant update book with id: " + id));

        bookMapper.updateModelFromDto(createBookRequestDto, book);

        return bookMapper.toDto(bookRepository.save(book));
    }
}
