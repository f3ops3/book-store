package springweb.courseproject.service;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import springweb.courseproject.dto.book.BookDto;
import springweb.courseproject.dto.book.BookDtoWithoutCategoryIds;
import springweb.courseproject.dto.book.CreateBookRequestDto;
import springweb.courseproject.mapper.BookMapper;
import springweb.courseproject.model.Book;
import springweb.courseproject.repository.book.BookRepository;
import springweb.courseproject.service.book.BookServiceImpl;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {
    @Mock
    private BookRepository bookRepository;
    @Mock
    private BookMapper bookMapper;
    @InjectMocks
    private BookServiceImpl bookService;

    @Test
    @DisplayName("Find all books")
    void findAllBooks_returnBookDtoList() {
        //GIVEN
        Book firstBook = new Book();
        Book secondBook = new Book();
        Page<Book> bookPage = new PageImpl<>(List.of(firstBook, secondBook));
        Pageable pageable = Pageable.ofSize(1);

        BookDto firstBookDto = new BookDto();
        BookDto secondBookDto = new BookDto();
        final List<BookDto> expected = List.of(firstBookDto, secondBookDto);
        //WHEN
        when(bookMapper.toDto(firstBook)).thenReturn(firstBookDto);
        when(bookMapper.toDto(secondBook)).thenReturn(secondBookDto);
        when(bookRepository.findAll(pageable)).thenReturn(bookPage);
        List<BookDto> actual = bookService.findAll(pageable);
        //THEN
        Assertions.assertEquals(expected, actual);
        verify(bookRepository, Mockito.times(1)).findAll(pageable);
        verify(bookMapper, Mockito.times(1)).toDto(firstBook);
        verify(bookMapper, Mockito.times(1)).toDto(secondBook);
    }

    @Test
    @DisplayName("Save valid book")
    void saveBook_validRequest_returnBookDto() {
        //GIVEN
        CreateBookRequestDto bookRequestDto = new CreateBookRequestDto();
        Book book = new Book();
        BookDto expected = new BookDto();
        //WHEN
        when(bookMapper.toEntity(bookRequestDto)).thenReturn(book);
        when(bookRepository.save(book)).thenReturn(book);
        when(bookMapper.toDto(book)).thenReturn(expected);
        BookDto actual = bookService.save(bookRequestDto);
        //THEN
        Assertions.assertEquals(expected, actual);
        verify(bookRepository, Mockito.times(1)).save(book);
        verify(bookMapper, Mockito.times(1)).toEntity(bookRequestDto);
        verify(bookMapper, Mockito.times(1)).toDto(book);
    }

    @Test
    @DisplayName("Update book by valid id")
    void updateBook_validIdAndRequest_Success() {
        //GIVEN
        Long id = 1L;
        CreateBookRequestDto bookRequestDto = new CreateBookRequestDto();
        Book book = new Book();
        BookDto expected = new BookDto();
        //WHEN
        when(bookRepository.findById(id)).thenReturn(Optional.of(book));
        doNothing().when(bookMapper).updateBook(bookRequestDto, book);
        when(bookRepository.save(book)).thenReturn(book);
        when(bookMapper.toDto(book)).thenReturn(expected);
        BookDto actual = bookService.updateBookById(id, bookRequestDto);
        //THEN
        Assertions.assertEquals(expected, actual);
        verify(bookRepository, Mockito.times(1)).findById(id);
        verify(bookMapper, Mockito.times(1)).updateBook(bookRequestDto, book);
        verify(bookMapper, Mockito.times(1)).toDto(book);
    }

    @Test
    @DisplayName("Find book by valid id")
    void findBook_validId_Success() {
        //GIVEN
        Long id = 1L;
        Book book = new Book();
        BookDto expected = new BookDto();
        //WHEN
        when(bookRepository.findById(id)).thenReturn(Optional.of(book));
        when(bookMapper.toDto(book)).thenReturn(expected);
        BookDto actual = bookService.findById(id);
        //THEN
        Assertions.assertEquals(expected, actual);
        verify(bookRepository, Mockito.times(1)).findById(id);
        verify(bookMapper, Mockito.times(1)).toDto(book);
    }

    @Test
    @DisplayName("Find all books by category")
    void getAllBooksByCategory_returnBookDtoList() {
        //GIVEN
        Long categoryId = 1L;
        Book firstBook = new Book();
        Book secondBook = new Book();
        List<Book> bookPage = List.of(firstBook, secondBook);
        Pageable pageable = Pageable.ofSize(1);

        BookDtoWithoutCategoryIds firstBookDto = new BookDtoWithoutCategoryIds();
        BookDtoWithoutCategoryIds secondBookDto = new BookDtoWithoutCategoryIds();
        final List<BookDtoWithoutCategoryIds> expected = List.of(firstBookDto, secondBookDto);
        //WHEN
        when(bookMapper.toDtoWithoutCategories(firstBook)).thenReturn(firstBookDto);
        when(bookMapper.toDtoWithoutCategories(secondBook)).thenReturn(secondBookDto);
        when(bookRepository.findAllByCategoryId(categoryId, pageable)).thenReturn(bookPage);
        List<BookDtoWithoutCategoryIds> actual = bookService.findAllByCategoryId(categoryId,
                pageable);
        //THEN
        Assertions.assertEquals(expected, actual);
        verify(bookRepository, Mockito.times(1)).findAllByCategoryId(categoryId, pageable);
        verify(bookMapper, Mockito.times(1)).toDtoWithoutCategories(firstBook);
        verify(bookMapper, Mockito.times(1)).toDtoWithoutCategories(secondBook);
    }
}
