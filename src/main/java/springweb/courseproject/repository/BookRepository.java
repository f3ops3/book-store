package springweb.courseproject.repository;

import java.util.List;
import springweb.courseproject.model.Book;

public interface BookRepository {
    Book save(Book book);

    List<Book> findAll();
}
