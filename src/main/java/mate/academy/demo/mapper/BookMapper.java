package mate.academy.demo.mapper;

import mate.academy.demo.config.MapperConfig;
import mate.academy.demo.dto.BookDto;
import mate.academy.demo.dto.CreateBookRequestDto;
import mate.academy.demo.model.Book;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface BookMapper {
    BookDto toDto(Book book);

    Book toModel(CreateBookRequestDto createBookRequestDto);
}
