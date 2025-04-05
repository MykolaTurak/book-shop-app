package mate.academy.demo.service;

import java.util.List;
import mate.academy.demo.BookMapper;
import mate.academy.demo.dto.BookDto;
import mate.academy.demo.dto.CreateBookRequestDto;
import mate.academy.demo.exeptions.EntityNotFoundException;
import mate.academy.demo.model.Book;
import mate.academy.demo.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private BookMapper bookMapper;

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
        return bookMapper.toDto(bookRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Cant find book with id: " + id)));
    }
}
