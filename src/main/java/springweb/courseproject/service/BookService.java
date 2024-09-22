package springweb.courseproject.service;

import java.util.List;
import springweb.courseproject.model.Book;

public interface BookService {
    Book save(Book book);

    List<Book> findAll();
}
