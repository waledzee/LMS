package mapper;


import dto.BookDto;
import entity.Book;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface BookMapper {

    @Mapping(target = "bookId", source = "id")
    @Mapping(target = "publisher", ignore = true)
    @Mapping(target = "language", ignore = true)
    @Mapping(target = "authors", ignore = true)
    @Mapping(target = "categories", ignore = true)
    Book toEntity(BookDto dto);

    @Mapping(target = "id", source = "bookId")
    @Mapping(target = "publisherId", source = "publisher.publisherId")
    @Mapping(target = "languageId", source = "language.languageId")
    @Mapping(target = "authorIds", expression = "java(book.getAuthors().stream().map(a -> a.getAuthorId()).toList())")
    @Mapping(target = "categoryIds", expression = "java(book.getCategories().stream().map(c -> c.getCategoryId()).toList())")
    BookDto toDto(Book book);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateBookFromDto(BookDto dto, @MappingTarget Book book);
}
