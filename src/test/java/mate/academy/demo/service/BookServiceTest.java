package mate.academy.demo.service;

import static mate.academy.demo.util.TestUtil.getFirstBook;
import static mate.academy.demo.util.TestUtil.getFirstBookDto;
import static mate.academy.demo.util.TestUtil.getFirstCategory;
import static mate.academy.demo.util.TestUtil.getFirstCreateBookDto;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;

import java.util.List;
import java.util.Optional;
import mate.academy.demo.dto.book.BookDto;
import mate.academy.demo.exeption.EntityNotFoundException;
import mate.academy.demo.mapper.BookMapper;
import mate.academy.demo.model.Book;
import mate.academy.demo.repository.BookRepository;
import mate.academy.demo.repository.CategoryRepository;
import org.junit.Assert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {
    @Mock
    private BookRepository bookRepository;
    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private BookMapper bookMapper;

    @InjectMocks
    private BookServiceImpl bookService;

    @Test
    @DisplayName("""
            Save single book to db
            """)
    void save_ValidObject_ShouldReturnBookWithId() {
        BookDto expected = getFirstBookDto();

        Mockito.when(bookRepository.save(getFirstBook())).thenReturn(getFirstBook());
        Mockito.when(categoryRepository.findAllById(List.of(getFirstCategory().getId())))
                .thenReturn(List.of(getFirstCategory()));
        Mockito.when(bookMapper.toModel(getFirstCreateBookDto())).thenReturn(getFirstBook());
        Mockito.when(bookMapper.toDto(getFirstBook())).thenReturn(expected);

        BookDto actual = bookService.save(getFirstCreateBookDto());

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("""
            Find all books and return Page
            """)
    void findAll_ShouldReturnPageOfBooks() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<BookDto> expected = new PageImpl<>(List.of(getFirstBookDto()), pageable, 1L);

        Page<Book> bookPage = new PageImpl<>(List.of(getFirstBook()), pageable, 1L);
        Mockito.when(bookRepository.findAll(pageable)).thenReturn(bookPage);
        Mockito.when(bookMapper.toDto(getFirstBook())).thenReturn(getFirstBookDto());

        Page<BookDto> actual = bookService.findAll(pageable);

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("""
            Find single book by id
            """)
    void findById_WithValidId_ShouldReturnValidBook() {
        BookDto expected = getFirstBookDto();

        Mockito.when(bookRepository.findById(getFirstBook().getId()))
                .thenReturn(Optional.of(getFirstBook()));
        Mockito.when(bookMapper.toDto(getFirstBook())).thenReturn(getFirstBookDto());

        BookDto actual = bookService.findById(getFirstBook().getId());

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("""
            Throw EntityNotFoundException by non exist id
            """)
    void findById_NonExistId_ShouldReturnException() {
        String expectedMessage = "Can't find book with id: 0";

        Mockito.when(bookRepository.findById(anyLong())).thenReturn(Optional.empty());

        Exception actual = Assert.assertThrows(EntityNotFoundException.class,
                () -> bookService.findById(anyLong()));

        assertEquals(expectedMessage, actual.getMessage());
    }
}
