package mate.academy.demo.repository;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import mate.academy.demo.config.CustomMySqlContainer;
import mate.academy.demo.model.Book;
import mate.academy.demo.model.Category;
import org.junit.Assert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BookRepositoryTest {
    @Container
    private static MySQLContainer<?> mysql = CustomMySqlContainer.getInstance()
            .withUsername("user")
            .withPassword("password")
            .withDatabaseName("testdb");

    @Autowired
    private BookRepository bookRepository;

    @Test
    @Sql(scripts = "classpath:db/scripts/create-two-books-with-same-category.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:db/scripts/delete-books-and-categories.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("""
            Find two books by single category
            """)
    void findAllByCategoryId_TwoBooksByCategory_Ok() {
        Category category = new Category();
        category.setId(1L);
        category.setName("Fantasy");

        Book book1 = new Book();
        book1.setId(1L);
        book1.setTitle("The Java Chronicles");
        book1.setAuthor("Elena Novak");
        book1.setIsbn("978-1-56619-909-4");
        book1.setPrice(BigDecimal.valueOf(39.99));
        book1.setDescription("A deep dive into modern Java development.");
        book1.setCoverImage("java_chronicles.jpg");
        book1.setCategories(Set.of(category));

        Book book2 = new Book();
        book2.setId(2L);
        book2.setTitle("Spring Boot in Action");
        book2.setAuthor("John Doe");
        book2.setIsbn("978-0-12345-678-9");
        book2.setPrice(BigDecimal.valueOf(29.99));
        book2.setDescription("Practical guide for building microservices.");
        book2.setCoverImage("spring_boot.jpg");
        book2.setCategories(Set.of(category));

        List<Book> expected = List.of(book1, book2);
        Pageable pageable = PageRequest.of(0, 10);

        List<Book> actual = bookRepository.findAllByCategoryId(1L, pageable).toList();

        Assert.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("""
            Find books by non existing category
            """)
    @Sql(scripts = "classpath:db/scripts/create-two-books-with-same-category.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:db/scripts/delete-books-and-categories.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void findAllByCategoryId_NonExistCategory_ExpectNull() {
        Pageable pageable = PageRequest.of(0, 10);
        List<Book> expected = Collections.emptyList();
;
        List<Book> actual = bookRepository.findAllByCategoryId(3L, pageable).toList();

        Assert.assertEquals(expected, actual);
    }
}
