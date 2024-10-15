package springweb.courseproject.repository.category;

import org.springframework.data.jpa.repository.JpaRepository;
import springweb.courseproject.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
