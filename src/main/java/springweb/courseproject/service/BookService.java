package springweb.courseproject.service;

import java.util.List;
import org.springframework.data.domain.Pageable;
import springweb.courseproject.dto.BookDto;
import springweb.courseproject.dto.BookSearchParametersDto;
import springweb.courseproject.dto.CreateBookRequestDto;

public interface BookService {
    BookDto save(CreateBookRequestDto book);

    List<BookDto> findAll(Pageable pageable);

    BookDto findById(Long id);

    BookDto updateBookById(Long id, CreateBookRequestDto book);

    void deleteBookById(Long id);

    List<BookDto> search(BookSearchParametersDto searchParametersDto, Pageable pageable);
}
