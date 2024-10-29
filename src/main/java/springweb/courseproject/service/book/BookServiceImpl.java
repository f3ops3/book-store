package springweb.courseproject.service.book;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import springweb.courseproject.dto.book.BookDto;
import springweb.courseproject.dto.book.BookDtoWithoutCategoryIds;
import springweb.courseproject.dto.book.BookSearchParametersDto;
import springweb.courseproject.dto.book.CreateBookRequestDto;
import springweb.courseproject.exception.EntityNotFoundException;
import springweb.courseproject.mapper.BookMapper;
import springweb.courseproject.model.Book;
import springweb.courseproject.repository.book.BookRepository;
import springweb.courseproject.repository.book.BookSpecificationBuilder;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final BookSpecificationBuilder bookSpecificationBuilder;

    @Override
    public BookDto save(CreateBookRequestDto book) {
        Book bookToSave = bookMapper.toEntity(book);
        return bookMapper.toDto(bookRepository.save(bookToSave));
    }

    @Override
    public List<BookDto> findAll(Pageable pageable) {
        return bookRepository.findAll(pageable).stream()
                .map(bookMapper::toDto)
                .toList();
    }

    @Override
    public List<BookDtoWithoutCategoryIds> findAllByCategoryId(Long categoryId,
                                                               Pageable pageable) {
        return bookRepository.findAllByCategoryId(categoryId, pageable).stream()
                .map(bookMapper::toDtoWithoutCategories)
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
        bookRepository.deleteById(id);
    }

    @Override
    public List<BookDto> search(BookSearchParametersDto searchParametersDto, Pageable pageable) {
        Specification<Book> bookSpecification = bookSpecificationBuilder
                .build(searchParametersDto);
        return bookRepository.findAll(bookSpecification, pageable).stream()
                .map(bookMapper::toDto)
                .toList();
    }
}
