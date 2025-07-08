package mate.academy.demo.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.util.List;
import mate.academy.demo.dto.book.BookDto;
import mate.academy.demo.dto.book.CreateBookRequestDto;
import mate.academy.demo.model.Category;
import org.junit.Assert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class BookControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Test
    @Sql(scripts = "classpath:/db/scripts/create-one-category.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:/db/scripts/delete-single-category-and-book.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("""
            Save book and get DTO
            """)
    void createBook_WithValidRequest_ShouldReturnValidBookDto() throws Exception {
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
        String jsonRequest = objectMapper.writeValueAsString(createBookRequestDto);

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

        MvcResult result = mockMvc.perform(
                        post("/books")
                                .content(jsonRequest)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();

        BookDto actual = objectMapper.readValue(result.getResponse().getContentAsString(),
                BookDto.class);

        Assert.assertEquals(expected, actual);
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Sql(scripts = "classpath:db/scripts/create-two-books-with-same-category.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:db/scripts/delete-books-and-categories.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("""
            Get all books
            """)
    void getAll_ShouldReturnValidPage() throws Exception {
        BookDto bookDto1 = new BookDto();
        bookDto1.setId(1L);
        bookDto1.setTitle("The Java Chronicles");
        bookDto1.setAuthor("Elena Novak");
        bookDto1.setIsbn("978-1-56619-909-4");
        bookDto1.setPrice(BigDecimal.valueOf(39.99));
        bookDto1.setDescription("A deep dive into modern Java development.");
        bookDto1.setCoverImage("java_chronicles.jpg");
        bookDto1.setCategoryIds(List.of(1L));

        BookDto bookDto2 = new BookDto();
        bookDto2.setId(2L);
        bookDto2.setTitle("Spring Boot in Action");
        bookDto2.setAuthor("John Doe");
        bookDto2.setIsbn("978-0-12345-678-9");
        bookDto2.setPrice(BigDecimal.valueOf(29.99));
        bookDto2.setDescription("Practical guide for building microservices.");
        bookDto2.setCoverImage("spring_boot.jpg");
        bookDto2.setCategoryIds(List.of(1L));

        BookDto bookDto3 = new BookDto();
        bookDto3.setId(3L);
        bookDto3.setTitle("Docker for Dummies");
        bookDto3.setAuthor("Lisa Smith");
        bookDto3.setIsbn("978-0-99999-999-9");
        bookDto3.setPrice(BigDecimal.valueOf(19.59));
        bookDto3.setDescription("An easy introduction to containerization.");
        bookDto3.setCoverImage("docker_for_dummies.jpg");
        bookDto3.setCategoryIds(List.of(2L));

        List<BookDto> expected = List.of(bookDto1, bookDto2, bookDto3);

        MvcResult result = mockMvc.perform(
                        get("/books")
                                .param("page", "0")
                                .param("size", "10")

                ).andExpect(status().isOk())
                .andReturn();

        JavaType type = objectMapper.getTypeFactory()
                .constructParametricType(List.class, BookDto.class);
        String content = objectMapper.readTree(result.getResponse().getContentAsString())
                .get("content").toString();
        List<BookDto> actual = objectMapper.readValue(content, type);

        Assert.assertEquals(expected, actual);
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Sql(scripts = "classpath:db/scripts/create-two-books-with-same-category.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:db/scripts/delete-books-and-categories.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("""
            Find book by id
            """)
    void findBookById_WithValidId_ShouldReturnValidBookDto() throws Exception {
        Long bookId = 1L;
        Long categoryId = 1L;
        BookDto expected = new BookDto();
        expected.setId(bookId);
        expected.setTitle("The Java Chronicles");
        expected.setAuthor("Elena Novak");
        expected.setIsbn("978-1-56619-909-4");
        expected.setPrice(BigDecimal.valueOf(39.99));
        expected.setDescription("A deep dive into modern Java development.");
        expected.setCoverImage("java_chronicles.jpg");
        expected.setCategoryIds(List.of(1L));

        MvcResult result = mockMvc.perform(get("/books/{id}", bookId))
                .andExpect(status().isOk())
                .andReturn();

        BookDto actual = objectMapper.readValue(result.getResponse().getContentAsString(),
                BookDto.class);

        Assert.assertEquals(expected, actual);
    }
}

