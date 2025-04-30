package mate.academy.demo.mapper;

import java.util.List;
import java.util.stream.Collectors;
import mate.academy.demo.config.MapperConfig;
import mate.academy.demo.dto.book.BookDto;
import mate.academy.demo.dto.book.BookDtoWithoutCategoryIds;
import mate.academy.demo.dto.book.CreateBookRequestDto;
import mate.academy.demo.model.Book;
import mate.academy.demo.model.Category;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class)
public interface BookMapper {
    BookDto toDto(Book book);

    Book toModel(CreateBookRequestDto createBookRequestDto);

    void updateModelFromDto(CreateBookRequestDto createBookRequestDto, @MappingTarget Book book);

    BookDtoWithoutCategoryIds toDtoWithoutCategories(Book book);

    @AfterMapping
    default void setCategoryIds(@MappingTarget BookDto bookDto, Book book) {
        if (book.getCategories() != null) {
            List<Long> ids = book.getCategories().stream()
                    .map(Category::getId)
                    .collect(Collectors.toList());
            bookDto.setCategoryIds(ids);
        }
    }
}
