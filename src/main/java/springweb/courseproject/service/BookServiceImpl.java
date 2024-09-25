package springweb.courseproject.service;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import springweb.courseproject.dto.BookDto;
import springweb.courseproject.dto.CreateBookRequestDto;
import springweb.courseproject.mapper.BookMapper;
import springweb.courseproject.model.Book;
import springweb.courseproject.repository.BookRepository;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    @Override
    public BookDto save(CreateBookRequestDto book) {
        Book bookToSave = bookMapper.toBook(book);
        return bookMapper.toDto(bookRepository.save(bookToSave));
    }

    @Override
    public List<BookDto> findAll() {
        return bookRepository.findAll().stream()
                .map(bookMapper::toDto)
                .toList();
    }

    @Override
    public BookDto findById(long id) {
        return bookMapper.toDto(bookRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Couldn't find book with id = " + id))
        );
    }
}
