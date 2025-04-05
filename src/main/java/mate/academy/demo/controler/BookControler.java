package mate.academy.demo.controler;

import java.util.List;
import mate.academy.demo.dto.BookDto;
import mate.academy.demo.dto.CreateBookRequestDto;
import mate.academy.demo.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/books")
public class BookControler {
    @Autowired
    private BookService bookService;

    @GetMapping
    public List<BookDto> getAll() {
        return bookService.findAll();
    }

    @GetMapping(path = "/{id}")
    public BookDto getBookById(@PathVariable Long id) {
        return bookService.findById(id);
    }

    @PostMapping
    public BookDto createBook(@RequestBody CreateBookRequestDto bookRequestDto) {
        return bookService.save(bookRequestDto);
    }
}
