package mate.academy.demo.mapper;

import mate.academy.demo.config.MapperConfig;
import mate.academy.demo.dto.book.BookDto;
import mate.academy.demo.dto.book.CreateBookRequestDto;
import mate.academy.demo.model.Book;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class)
public interface BookMapper {
    BookDto toDto(Book book);

    Book toModel(CreateBookRequestDto createBookRequestDto);

    void updateModelFromDto(CreateBookRequestDto createBookRequestDto, @MappingTarget Book book);
}
