package mate.academy.demo.repository;

import static mate.academy.demo.util.TestUtil.getFirstBook;
import static mate.academy.demo.util.TestUtil.getSecondBook;
import static org.junit.Assert.assertEquals;

import java.util.Collections;
import java.util.List;
import mate.academy.demo.config.CustomMySqlContainer;
import mate.academy.demo.model.Book;
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
        List<Book> expected = List.of(getFirstBook(), getSecondBook());
        Pageable pageable = PageRequest.of(0, 10);

        List<Book> actual = bookRepository.findAllByCategoryId(1L, pageable).toList();

        assertEquals(expected, actual);
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

        assertEquals(expected, actual);
    }
}
