package springweb.courseproject.repository.book;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import springweb.courseproject.model.Book;
import springweb.courseproject.repository.SpecificationProvider;

@Component
public class IsbnSpecificationProvider implements SpecificationProvider<Book> {
    @Override
    public String getKey() {
        return "isbn";
    }

    @Override
    public Specification<Book> getSpecification(String parameter) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("isbn"), parameter);
    }
}
