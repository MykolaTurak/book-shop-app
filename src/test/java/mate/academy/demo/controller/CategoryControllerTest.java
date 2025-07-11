package mate.academy.demo.controller;

import static mate.academy.demo.util.TestUtil.getFirstBookDto;
import static mate.academy.demo.util.TestUtil.getFirstCategory;
import static mate.academy.demo.util.TestUtil.getFirstCategoryDto;
import static mate.academy.demo.util.TestUtil.getFirstCategoryRequestDto;
import static mate.academy.demo.util.TestUtil.getSecondBookDto;
import static mate.academy.demo.util.TestUtil.getWrongCategoryRequestDto;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import jdk.jfr.Description;
import mate.academy.demo.dto.book.BookDto;
import mate.academy.demo.dto.category.CategoryDto;
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
public class CategoryControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Description("""
            Create category with valid data
            """)
    @Sql(scripts = "classpath:/db/scripts/delete-single-category.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void create_WithValidData_ShouldReturnValidCategory() throws Exception {
        CategoryDto expected = getFirstCategoryDto();

        String jsonRequest = objectMapper.writeValueAsString(getFirstCategoryRequestDto());

        MvcResult result = mockMvc.perform(
                        post("/categories")
                                .content(jsonRequest)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();

        CategoryDto actual = objectMapper.readValue(
                result.getResponse().getContentAsString(), CategoryDto.class);

        assertEquals(expected, actual);
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Description("""
            Create category with not valid data
            """)
    void create_WithNotValidData_ShouldReturnBadRequest() throws Exception {
        String jsonRequest = objectMapper.writeValueAsString(getWrongCategoryRequestDto());
        mockMvc.perform(
                        post("/categories")
                                .content(jsonRequest)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Sql(scripts = "classpath:/db/scripts/create-one-category.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:/db/scripts/delete-single-category.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("""
            Update category with new name
            """)
    void update_WithValidRequest_ShouldReturnUpdatedData() throws Exception {

        String jsonRequest = objectMapper.writeValueAsString(getFirstCategoryRequestDto());

        CategoryDto expected = getFirstCategoryDto();

        Long categoryId = 1L;
        MvcResult result = mockMvc.perform(
                        put("/categories/{id}", categoryId)
                                .content(jsonRequest)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();

        CategoryDto actual = objectMapper.readValue(
                result.getResponse().getContentAsString(), CategoryDto.class);

        assertEquals(expected, actual);
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @DisplayName("""
            Update category with not valid id
            """)
    void update_WithNotValidRequest_ShouldReturnNotFound() throws Exception {
        String jsonRequest = objectMapper.writeValueAsString(getFirstCategoryRequestDto());
        Long nonExistId = 1L;
        mockMvc.perform(
                        put("/categories/{id}", nonExistId)
                                .content(jsonRequest)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Sql(scripts = "classpath:/db/scripts/create-one-category.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:/db/scripts/delete-single-category.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("""
            Find category by id
            """)
    void findById_WithValidId_ShouldReturnValidDto() throws Exception {
        Long categoryId = 1L;
        CategoryDto expected = getFirstCategoryDto();

        MvcResult result = mockMvc.perform(
                        get("/categories/{id}", categoryId)
                )
                .andExpect(status().isOk())
                .andReturn();

        CategoryDto actual = objectMapper.readValue(result.getResponse().getContentAsString(),
                CategoryDto.class);

        assertEquals(expected, actual);
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @DisplayName("""
            Find category by not valid id
            """)
    void findById_WithNotValidId_ShouldReturnValidDto() throws Exception {
        Long nonExistId = 1L;
        mockMvc.perform(
                get("/categories/{id}", nonExistId))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Sql(scripts = "classpath:/db/scripts/create-one-category.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:/db/scripts/delete-single-category.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("""
            Delete category by valid id
            """)
    void delete_WithValidId_ShouldReturnOkStatus() throws Exception {
        Long categoryId = 1L;
        mockMvc.perform(delete("/categories/{id}", categoryId))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @DisplayName("""
            Delete category by not valid id
            """)
    void delete_WithNotValidId_ShouldReturnNotFound() throws Exception {
        Long nonExistId = 1L;
        mockMvc.perform(delete("/categories/{id}", nonExistId))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Sql(scripts = "classpath:/db/scripts/create-one-category.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:/db/scripts/delete-single-category.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("""
            Find all categories
            """)
    void findAll_ShouldReturnValidDto() throws Exception {
        List<CategoryDto> expected = List.of(getFirstCategoryDto());

        MvcResult result = mockMvc.perform(get("/categories"))
                .andExpect(status().isOk())
                .andReturn();

        JavaType type = objectMapper.getTypeFactory()
                .constructParametricType(List.class, CategoryDto.class);
        String content = objectMapper.readTree(result.getResponse().getContentAsString())
                .get("content").toString();
        List<CategoryDto> actual = objectMapper.readValue(content, type);

        assertEquals(expected, actual);
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Sql(scripts = "classpath:/db/scripts/create-two-books-with-same-category.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:/db/scripts/delete-books-and-categories.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("""
            Find books by category id
            """)
    void getBookByCategoryId_WithValidId_ShouldReturnValidDto() throws Exception {
        List<BookDto> expected = List.of(getFirstBookDto(), getSecondBookDto());

        MvcResult result = mockMvc.perform(get("/categories/{id}/books",
                        getFirstCategory().getId()))
                .andExpect(status().isOk())
                .andReturn();

        JavaType type = objectMapper.getTypeFactory()
                .constructParametricType(List.class, BookDto.class);
        String content = objectMapper.readTree(result.getResponse().getContentAsString())
                .get("content").toString();
        List<BookDto> actual = objectMapper.readValue(content, type);
    }
}
