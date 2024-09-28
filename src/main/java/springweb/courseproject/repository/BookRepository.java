package springweb.courseproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import springweb.courseproject.model.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
}
