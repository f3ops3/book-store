package springweb.courseproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springweb.courseproject.model.Book;
import springweb.courseproject.repository.BookRepository;
import java.util.List;

@Service
public class BookServiceImpl implements BookService {
    private BookRepository bookRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Book save(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }
}
