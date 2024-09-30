package springweb.courseproject.repository.book;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import springweb.courseproject.dto.BookSearchParametersDto;
import springweb.courseproject.model.Book;
import springweb.courseproject.repository.SpecificationBuilder;
import springweb.courseproject.repository.SpecificationProviderManager;

@RequiredArgsConstructor
@Component
public class BookSpecificationBuilder implements SpecificationBuilder<Book> {
    private final SpecificationProviderManager<Book> bookSpecificationProviderManager;

    @Override
    public Specification<Book> build(BookSearchParametersDto searchParametersDto) {
        Specification<Book> spec = Specification.where(null);
        if (searchParametersDto.author() != null && !searchParametersDto.author().isEmpty()) {
            spec = spec.and(bookSpecificationProviderManager.getSpecificationProvider("author")
                    .getSpecification(searchParametersDto.author()));
        }
        if (searchParametersDto.title() != null && !searchParametersDto.title().isEmpty()) {
            spec = spec.and(bookSpecificationProviderManager.getSpecificationProvider("title")
                    .getSpecification(searchParametersDto.title()));
        }
        if (searchParametersDto.isbn() != null && !searchParametersDto.isbn().isEmpty()) {
            spec = spec.and(bookSpecificationProviderManager.getSpecificationProvider("isbn")
                    .getSpecification(searchParametersDto.isbn()));
        }
        return spec;
    }
}
