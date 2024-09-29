package springweb.courseproject.service;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
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
    public BookDto findById(Long id) {
        return bookMapper.toDto(bookRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Couldn't find book with id = " + id))
        );
    }

    @Override
    public BookDto updateBookById(Long id, CreateBookRequestDto bookDto) {
        Book book = bookRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Couldn't find book with id = " + id));
        bookMapper.updateBook(bookDto, book);
        return bookMapper.toDto(bookRepository.save(book));
    }

    @Override
    public void deleteBookById(Long id) {
        Optional<Book> book = bookRepository.findById(id);
        bookRepository.delete(book.get());
    }
}
