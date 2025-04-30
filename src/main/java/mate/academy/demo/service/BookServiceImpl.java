package mate.academy.demo.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import mate.academy.demo.dto.book.BookDto;
import mate.academy.demo.dto.book.CreateBookRequestDto;
import mate.academy.demo.exeption.EntityNotFoundException;
import mate.academy.demo.mapper.BookMapper;
import mate.academy.demo.model.Book;
import mate.academy.demo.model.Category;
import mate.academy.demo.repository.BookRepository;
import mate.academy.demo.repository.CategoryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;
    private final BookMapper bookMapper;

    @Override
    public BookDto save(CreateBookRequestDto bookRequestDto) {
        Book book = bookMapper.toModel(bookRequestDto);
        Category category = categoryRepository.findByName(bookRequestDto.getCategoryName())
                .orElseThrow(
                        () -> new EntityNotFoundException(
                                "Can't find category with id: " + bookRequestDto.getCategoryName())
                );
        book.getCategories().add(category);

        return bookMapper.toDto(bookRepository.save(book));
    }

    @Override
    public Page<BookDto> findAll(Pageable pageable) {
        return bookRepository.findAll(pageable)
                .map(bookMapper::toDto);
    }

    @Override
    public BookDto findById(Long id) {
        return bookMapper.toDto(bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Can't find book with id: " + id)));
    }

    @Override
    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public BookDto update(CreateBookRequestDto createBookRequestDto, Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Can't update book with id: " + id));

        bookMapper.updateModelFromDto(createBookRequestDto, book);

        return bookMapper.toDto(bookRepository.save(book));
    }

    @Override
    public List<BookDto> findAllByCategoryId(Long id) {
        return bookRepository.findAllByCategoryId(id).stream()
                .map(bookMapper::toDto)
                .toList();
    }
}
