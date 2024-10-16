package springweb.courseproject.service.book;

import java.util.List;
import org.springframework.data.domain.Pageable;
import springweb.courseproject.dto.book.BookDto;
import springweb.courseproject.dto.book.BookDtoWithoutCategoryIds;
import springweb.courseproject.dto.book.BookSearchParametersDto;
import springweb.courseproject.dto.book.CreateBookRequestDto;

public interface BookService {
    BookDto save(CreateBookRequestDto book);

    List<BookDto> findAll(Pageable pageable);

    List<BookDtoWithoutCategoryIds> findAllByCategoryId(Long categoryId, Pageable pageable);

    BookDto findById(Long id);

    BookDto updateBookById(Long id, CreateBookRequestDto book);

    void deleteBookById(Long id);

    List<BookDto> search(BookSearchParametersDto searchParametersDto, Pageable pageable);
}
