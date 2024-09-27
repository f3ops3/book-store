package springweb.courseproject.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import springweb.courseproject.config.MapperConfig;
import springweb.courseproject.dto.BookDto;
import springweb.courseproject.dto.CreateBookRequestDto;
import springweb.courseproject.model.Book;

@Mapper(config = MapperConfig.class)
public interface BookMapper {
    BookDto toDto(Book book);

    Book toBook(CreateBookRequestDto dto);

    Book updateBook(CreateBookRequestDto dto, @MappingTarget Book book);
}
