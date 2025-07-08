package mate.academy.demo.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import jdk.jfr.Description;
import mate.academy.demo.dto.category.CategoryDto;
import mate.academy.demo.dto.category.CreateCategoryRequestDto;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

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
        CategoryDto expected = new CategoryDto(1L, "Fantasy", null);
        CreateCategoryRequestDto categoryRequestDto = new CreateCategoryRequestDto("Fantasy", null);

        String jsonRequest = objectMapper.writeValueAsString(categoryRequestDto);

        MvcResult result = mockMvc.perform(
                        MockMvcRequestBuilders.post("/categories")
                                .content(jsonRequest)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();

        CategoryDto actual = objectMapper.readValue(
                result.getResponse().getContentAsString(), CategoryDto.class);

        Assert.assertEquals(expected, actual);
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
        Long categoryId = 1L;
        CreateCategoryRequestDto categoryRequestDto = new CreateCategoryRequestDto(
                "Science fiction", "description");
        String jsonRequest = objectMapper.writeValueAsString(categoryRequestDto);

        CategoryDto expected = new CategoryDto(categoryId, "Science fiction", "description");

        MvcResult result = mockMvc.perform(
                        MockMvcRequestBuilders.put("/categories/{id}", categoryId)
                                .content(jsonRequest)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();

        CategoryDto actual = objectMapper.readValue(
                result.getResponse().getContentAsString(), CategoryDto.class);

        Assert.assertEquals(expected, actual);
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
        CategoryDto expected = new CategoryDto(categoryId, "Fantasy", null);

        MvcResult result = mockMvc.perform(
                        MockMvcRequestBuilders.get("/categories/{id}", categoryId)
                )
                .andExpect(status().isOk())
                .andReturn();

        CategoryDto actual = objectMapper.readValue(result.getResponse().getContentAsString(),
                CategoryDto.class);

        Assert.assertEquals(expected, actual);
    }
}
