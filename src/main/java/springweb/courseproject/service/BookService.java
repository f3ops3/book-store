package springweb.courseproject.service;

import springweb.courseproject.model.Book;
import java.util.List;

public interface BookService {
    Book save(Book book);

    List<Book> findAll();
}
