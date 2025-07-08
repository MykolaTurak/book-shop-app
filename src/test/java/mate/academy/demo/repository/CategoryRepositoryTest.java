package mate.academy.demo.repository;

import java.util.Optional;
import mate.academy.demo.config.CustomMySqlContainer;
import mate.academy.demo.model.Category;
import org.junit.Assert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CategoryRepositoryTest {
    @Container
    private static MySQLContainer<?> mysql = CustomMySqlContainer.getInstance()
            .withUsername("user")
            .withPassword("password")
            .withDatabaseName("testdb");

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    @Sql(scripts = "classpath:/db/scripts/create-one-category.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:/db/scripts/delete-single-category-and-book.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("""
            Find category by name
            """)
    void findByName_WithValidName_ShouldReturnValidCategory() {
        Long categoryId = 1L;
        Category category = new Category();
        category.setId(categoryId);
        category.setName("Fantasy");
        Optional<Category> expected = Optional.of(category);

        Optional<Category> actual = categoryRepository.findById(categoryId);

        Assert.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("""
            Find category by non exist name
            """)
    void findByName_ByNonExistId_ShouldReturnEmptyOptional() {
        Optional<Category> expected = Optional.empty();

        Optional<Category> actual = categoryRepository.findByName("Fantasy");

        Assert.assertEquals(expected, actual);
    }
}
