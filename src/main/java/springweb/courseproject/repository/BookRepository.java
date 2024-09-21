package springweb.courseproject.repository;

import springweb.courseproject.model.Book;
import java.util.List;

public interface BookRepository {
    Book save(Book book);

    List<Book> findAll();
}
