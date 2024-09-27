package springweb.courseproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import springweb.courseproject.model.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    //Book updateBookById(Long id, Book book);
}
