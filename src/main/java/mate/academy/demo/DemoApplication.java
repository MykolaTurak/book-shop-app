package mate.academy.demo;

import java.math.BigDecimal;
import mate.academy.demo.model.Book;
import mate.academy.demo.service.BookService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(scanBasePackages = "mate.academy")
public class DemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Bean
    public CommandLineRunner demo(BookService bookService) {
        return args -> {
            Book book = new Book();
            book.setTitle("title");
            book.setAuthor("author");
            book.setIsbn("nibs");
            book.setPrice(BigDecimal.valueOf(987));
            bookService.save(book);

            System.out.println(bookService.findAll());
        };
    }
}
