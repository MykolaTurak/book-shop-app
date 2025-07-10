package mate.academy.demo.controller;

import static mate.academy.demo.util.TestUtil.getFirstBookDto;
import static mate.academy.demo.util.TestUtil.getFirstCreateBookDto;
import static mate.academy.demo.util.TestUtil.getSecondBookDto;
import static mate.academy.demo.util.TestUtil.getThirdBookDto;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import mate.academy.demo.dto.book.BookDto;
import mate.academy.demo.dto.book.CreateBookRequestDto;
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
        String jsonRequest = objectMapper.writeValueAsString(getFirstCreateBookDto());
        BookDto expected = getFirstBookDto();

        MvcResult result = mockMvc.perform(
                        post("/books")
                                .content(jsonRequest)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();

        BookDto actual = objectMapper.readValue(result.getResponse().getContentAsString(),
                BookDto.class);

        assertEquals(expected, actual);
    }

    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Test
    @Sql(scripts = "classpath:/db/scripts/create-one-category.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:/db/scripts/delete-single-category-and-book.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("""
            Save book with empty title and get 400 status
            """)
    void createBook_WithWrongDto_ShouldReturnBadRequest() throws Exception {
        CreateBookRequestDto wrongRequestDto = getFirstCreateBookDto();
        wrongRequestDto.setTitle(null);
        String jsonRequest = objectMapper.writeValueAsString(wrongRequestDto);

        mockMvc.perform(
                        post("/books")
                                .content(jsonRequest)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Sql(scripts = "classpath:db/scripts/create-two-books-with-same-category.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:db/scripts/delete-books-and-categories.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("""
            Find all books
            """)
    void getAll_ShouldReturnValidPage() throws Exception {
        List<BookDto> expected = List.of(getFirstBookDto(),
                getSecondBookDto(), getThirdBookDto());

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

        assertEquals(expected, actual);
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
        BookDto expected = getFirstBookDto();

        MvcResult result = mockMvc.perform(get("/books/{id}", expected.getId()))
                .andExpect(status().isOk())
                .andReturn();

        BookDto actual = objectMapper.readValue(result.getResponse().getContentAsString(),
                BookDto.class);

        assertEquals(expected, actual);
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @DisplayName("""
            Find book by not valid id
            """)
    void findBookById_WithNotValidId_ShouldReturnNotFound() throws Exception {
        Long nonExistId = 1L;
        mockMvc.perform(get("/books/{id}", nonExistId))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Sql(scripts = "classpath:db/scripts/create-two-books-with-same-category.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:db/scripts/delete-books-and-categories.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("""
            Delete book with valid id
            """)
    void deleteBook_WithValidId_ShouldReturnOkStatus() throws Exception {
        Long bookId = 1L;
        mockMvc.perform(delete("/books/{id}", bookId))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @DisplayName("""
            Delete book with not valid id
            """)
    void deleteBook_WithNotValidId_ShouldReturnNotFound() throws Exception {
        Long nonExistId = 1L;
        mockMvc.perform(delete("/books/{id}", nonExistId))
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Sql(scripts = "classpath:db/scripts/create-two-books-with-same-category.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:db/scripts/delete-books-and-categories.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("""
            Update book with valid data
            """)
    void updateBook_WithValidId_ShouldReturnUpdatedBookDto() throws Exception {
        BookDto expected = getFirstBookDto();

        String jsonRequest = objectMapper.writeValueAsString(getFirstCreateBookDto());

        Long bookId = 2L;
        expected.setId(bookId);
        MvcResult result = mockMvc.perform(put("/books/{id}", bookId)
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();

        BookDto actual = objectMapper.readValue(result.getResponse()
                .getContentAsString(), BookDto.class);

        assertEquals(expected, actual);
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @DisplayName("""
            Update book with not valid id
            """)
    void updateBook_WithNotValidId_ShouldReturnNotFound() throws Exception {
        Long nonExistId = 1L;
        String jsonRequest = objectMapper.writeValueAsString(getFirstCreateBookDto());
        mockMvc.perform(put("/books/{id}", nonExistId)
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNotFound());
    }
}
