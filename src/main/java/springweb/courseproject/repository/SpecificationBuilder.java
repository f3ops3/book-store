package springweb.courseproject.repository;

import org.springframework.data.jpa.domain.Specification;
import springweb.courseproject.dto.book.BookSearchParametersDto;

public interface SpecificationBuilder<T> {
    Specification<T> build(BookSearchParametersDto searchParametersDto);
}
