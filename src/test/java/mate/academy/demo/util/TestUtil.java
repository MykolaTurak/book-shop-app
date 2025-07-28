package mate.academy.demo.util;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import mate.academy.demo.dto.book.BookDto;
import mate.academy.demo.dto.book.CreateBookRequestDto;
import mate.academy.demo.dto.category.CategoryDto;
import mate.academy.demo.dto.category.CreateCategoryRequestDto;
import mate.academy.demo.model.Book;
import mate.academy.demo.model.Category;

public class TestUtil {
    private TestUtil() {
    }

    public static Category getFirstCategory() {
        Category category = new Category();
        category.setId(1L);
        category.setName("Fantasy");

        return category;
    }

    public static CreateCategoryRequestDto getFirstCategoryRequestDto() {
        return new CreateCategoryRequestDto("Fantasy", null);
    }

    public static CreateCategoryRequestDto getWrongCategoryRequestDto() {
        return new CreateCategoryRequestDto(null, null);
    }

    public static CategoryDto getFirstCategoryDto() {
        return new CategoryDto(1L, "Fantasy", null);
    }

    public static Book getFirstBook() {
        Book firstBook = new Book();
        firstBook.setId(1L);
        firstBook.setTitle("The Java Chronicles");
        firstBook.setAuthor("Elena Novak");
        firstBook.setIsbn("978-1-56619-909-4");
        firstBook.setPrice(BigDecimal.valueOf(39.99));
        firstBook.setDescription("A deep dive into modern Java development.");
        firstBook.setCoverImage("java_chronicles.jpg");
        firstBook.setCategories(Set.of(getFirstCategory()));

        return firstBook;
    }

    public static Book getSecondBook() {
        Book secondBook = new Book();
        secondBook.setId(2L);
        secondBook.setTitle("Spring Boot in Action");
        secondBook.setAuthor("John Doe");
        secondBook.setIsbn("978-0-12345-678-9");
        secondBook.setPrice(BigDecimal.valueOf(29.99));
        secondBook.setDescription("Practical guide for building microservices.");
        secondBook.setCoverImage("spring_boot.jpg");
        secondBook.setCategories(Set.of(getFirstCategory()));

        return secondBook;
    }

    public static BookDto getFirstBookDto() {
        BookDto firstBookDto = new BookDto();
        firstBookDto.setId(getFirstBook().getId());
        firstBookDto.setTitle("The Java Chronicles");
        firstBookDto.setAuthor("Elena Novak");
        firstBookDto.setIsbn("978-1-56619-909-4");
        firstBookDto.setPrice(BigDecimal.valueOf(39.99));
        firstBookDto.setDescription("A deep dive into modern Java development.");
        firstBookDto.setCoverImage("java_chronicles.jpg");
        firstBookDto.setCategoryIds(List.of(getFirstCategory().getId()));

        return firstBookDto;
    }

    public static BookDto getSecondBookDto() {
        BookDto secondBookDto = new BookDto();
        secondBookDto.setId(2L);
        secondBookDto.setTitle("Spring Boot in Action");
        secondBookDto.setAuthor("John Doe");
        secondBookDto.setIsbn("978-0-12345-678-9");
        secondBookDto.setPrice(BigDecimal.valueOf(29.99));
        secondBookDto.setDescription("Practical guide for building microservices.");
        secondBookDto.setCoverImage("spring_boot.jpg");
        secondBookDto.setCategoryIds(List.of(1L));

        return secondBookDto;
    }

    public static BookDto getThirdBookDto() {
        BookDto thirdBookDto = new BookDto();
        thirdBookDto.setId(3L);
        thirdBookDto.setTitle("Docker for Dummies");
        thirdBookDto.setAuthor("Lisa Smith");
        thirdBookDto.setIsbn("978-0-99999-999-9");
        thirdBookDto.setPrice(BigDecimal.valueOf(19.59));
        thirdBookDto.setDescription("An easy introduction to containerization.");
        thirdBookDto.setCoverImage("docker_for_dummies.jpg");
        thirdBookDto.setCategoryIds(List.of(2L));

        return thirdBookDto;
    }

    public static CreateBookRequestDto getFirstCreateBookDto() {
        CreateBookRequestDto createBookRequestDto = new CreateBookRequestDto();
        createBookRequestDto.setTitle("The Java Chronicles");
        createBookRequestDto.setAuthor("Elena Novak");
        createBookRequestDto.setIsbn("978-1-56619-909-4");
        createBookRequestDto.setPrice(BigDecimal.valueOf(39.99));
        createBookRequestDto.setDescription("A deep dive into modern Java development.");
        createBookRequestDto.setCoverImage("java_chronicles.jpg");
        createBookRequestDto.setCategoriesId(List.of(getFirstCategory().getId()));

        return createBookRequestDto;
    }
}
