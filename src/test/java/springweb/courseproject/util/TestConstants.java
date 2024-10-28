package springweb.courseproject.util;

import java.math.BigDecimal;
import java.util.Set;
import springweb.courseproject.dto.book.BookDto;
import springweb.courseproject.dto.book.CreateBookRequestDto;
import springweb.courseproject.dto.category.CategoryResponseDto;
import springweb.courseproject.dto.category.CreateCategoryRequestDto;

public final class TestConstants {
    public static final String FIRST_BOOK_ISBN = "0000000001";
    public static final String SECOND_BOOK_ISBN = "0000000002";
    public static final CreateBookRequestDto CREATE_BOOK_REQUEST_DTO = new CreateBookRequestDto()
                .setTitle("Book 1")
                .setAuthor("Author")
                .setIsbn(FIRST_BOOK_ISBN)
                .setPrice(BigDecimal.valueOf(50.00))
                .setCategoryIds(Set.of(1L));
    public static final BookDto CREATE_BOOK_RESPONSE_DTO = new BookDto()
            .setTitle(CREATE_BOOK_REQUEST_DTO.getTitle())
            .setAuthor(CREATE_BOOK_REQUEST_DTO.getAuthor())
            .setIsbn(CREATE_BOOK_REQUEST_DTO.getIsbn())
            .setPrice(CREATE_BOOK_REQUEST_DTO.getPrice())
            .setCategoryIds(CREATE_BOOK_REQUEST_DTO.getCategoryIds());
    public static final CreateCategoryRequestDto CREATE_CATEGORY_REQUEST_DTO =
            new CreateCategoryRequestDto()
                    .setName("Sci-fi")
                    .setDescription("Science fiction");
    public static final CategoryResponseDto CREATE_CATEGORY_RESPONSE_DTO = new CategoryResponseDto()
            .setName(CREATE_CATEGORY_REQUEST_DTO.getName())
            .setDescription(CREATE_CATEGORY_REQUEST_DTO.getDescription());
    public static final CreateCategoryRequestDto UPDATE_CATEGORY_REQUEST_DTO =
            new CreateCategoryRequestDto()
                    .setName("Sci-fi")
                    .setDescription("Science fiction (sometimes "
                            + "shortened to SF or sci-fi) is a genre of speculative fiction");

    private TestConstants() {
    }
}
