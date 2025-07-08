package mate.academy.demo.service;

import static org.mockito.ArgumentMatchers.anyLong;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import mate.academy.demo.dto.book.BookDto;
import mate.academy.demo.dto.book.CreateBookRequestDto;
import mate.academy.demo.exeption.EntityNotFoundException;
import mate.academy.demo.mapper.BookMapper;
import mate.academy.demo.model.Book;
import mate.academy.demo.model.Category;
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
        Long categoryId = 1L;
        Category category = new Category();
        category.setId(categoryId);
        category.setName("Fantasy");

        CreateBookRequestDto createBookRequestDto = new CreateBookRequestDto();
        createBookRequestDto.setTitle("The Java Chronicles");
        createBookRequestDto.setAuthor("Elena Novak");
        createBookRequestDto.setIsbn("978-1-56619-909-4");
        createBookRequestDto.setPrice(BigDecimal.valueOf(39.99));
        createBookRequestDto.setDescription("A deep dive into modern Java development.");
        createBookRequestDto.setCoverImage("java_chronicles.jpg");
        createBookRequestDto.setCategoriesId(List.of(categoryId));

        Book book = new Book();
        book.setTitle("The Java Chronicles");
        book.setAuthor("Elena Novak");
        book.setIsbn("978-1-56619-909-4");
        book.setPrice(BigDecimal.valueOf(39.99));
        book.setDescription("A deep dive into modern Java development.");
        book.setCoverImage("java_chronicles.jpg");

        Long bookId = 1L;
        BookDto expected = new BookDto();
        expected.setId(bookId);
        expected.setTitle("The Java Chronicles");
        expected.setAuthor("Elena Novak");
        expected.setIsbn("978-1-56619-909-4");
        expected.setPrice(BigDecimal.valueOf(39.99));
        expected.setDescription("A deep dive into modern Java development.");
        expected.setCoverImage("java_chronicles.jpg");
        expected.setCategoryIds(List.of(bookId));

        Mockito.when(bookRepository.save(book)).thenReturn(book);
        Mockito.when(categoryRepository.findAllById(List.of(categoryId)))
                .thenReturn(List.of(category));
        Mockito.when(bookMapper.toModel(createBookRequestDto)).thenReturn(book);
        Mockito.when(bookMapper.toDto(book)).thenReturn(expected);

        BookDto actual = bookService.save(createBookRequestDto);

        Assert.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("""
            Find all books and return Page
            """)
    void findAll_ShouldReturnPageOfBooks() {
        Long bookId = 1L;
        Book book = new Book();
        book.setId(bookId);
        book.setTitle("The Java Chronicles");
        book.setAuthor("Elena Novak");
        book.setIsbn("978-1-56619-909-4");
        book.setPrice(BigDecimal.valueOf(39.99));
        book.setDescription("A deep dive into modern Java development.");
        book.setCoverImage("java_chronicles.jpg");

        BookDto bookDto = new BookDto();
        bookDto.setId(bookId);
        bookDto.setTitle("The Java Chronicles");
        bookDto.setAuthor("Elena Novak");
        bookDto.setIsbn("978-1-56619-909-4");
        bookDto.setPrice(BigDecimal.valueOf(39.99));
        bookDto.setDescription("A deep dive into modern Java development.");
        bookDto.setCoverImage("java_chronicles.jpg");
        bookDto.setCategoryIds(List.of(bookId));
        Pageable pageable = PageRequest.of(0, 10);
        Page<BookDto> expected = new PageImpl<>(List.of(bookDto), pageable, 1L);

        Page<Book> bookPage = new PageImpl<>(List.of(book), pageable, 1L);
        Mockito.when(bookRepository.findAll(pageable)).thenReturn(bookPage);
        Mockito.when(bookMapper.toDto(book)).thenReturn(bookDto);

        Page<BookDto> actual = bookService.findAll(pageable);

        Assert.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("""
            Find single book by id
            """)
    void findById_WithValidId_ShouldReturnValidBook() {
        Category category = new Category();
        category.setId(1L);
        category.setName("Fantasy");

        Long bookId = 1L;
        Book book = new Book();
        book.setId(bookId);
        book.setTitle("The Java Chronicles");
        book.setAuthor("Elena Novak");
        book.setIsbn("978-1-56619-909-4");
        book.setPrice(BigDecimal.valueOf(39.99));
        book.setDescription("A deep dive into modern Java development.");
        book.setCoverImage("java_chronicles.jpg");
        book.setCategories(Set.of(category));

        BookDto bookDto = new BookDto();
        bookDto.setId(bookId);
        bookDto.setTitle("The Java Chronicles");
        bookDto.setAuthor("Elena Novak");
        bookDto.setIsbn("978-1-56619-909-4");
        bookDto.setPrice(BigDecimal.valueOf(39.99));
        bookDto.setDescription("A deep dive into modern Java development.");
        bookDto.setCoverImage("java_chronicles.jpg");
        bookDto.setCategoryIds(List.of(bookId));
        BookDto expected = bookDto;

        Mockito.when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        Mockito.when(bookMapper.toDto(book)).thenReturn(bookDto);

        BookDto actual = bookService.findById(bookId);

        Assert.assertEquals(expected, actual);
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

        Assert.assertEquals(expectedMessage, actual.getMessage());
    }
}
