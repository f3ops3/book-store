package springweb.courseproject.service;

import java.util.List;
import springweb.courseproject.dto.BookDto;
import springweb.courseproject.dto.CreateBookRequestDto;

public interface BookService {
    BookDto save(CreateBookRequestDto book);

    List<BookDto> findAll();

    BookDto findById(Long id);

    BookDto updateBookById(Long id, CreateBookRequestDto book);

    void deleteBookById(Long id);
}
