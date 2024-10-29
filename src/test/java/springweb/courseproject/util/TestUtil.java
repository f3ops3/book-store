package springweb.courseproject.util;

import java.math.BigDecimal;
import java.util.Set;
import springweb.courseproject.dto.book.BookDto;
import springweb.courseproject.dto.book.CreateBookRequestDto;
import springweb.courseproject.dto.category.CategoryResponseDto;
import springweb.courseproject.dto.category.CreateCategoryRequestDto;

public final class TestUtil {
    private TestUtil() {
    }

    public static String getFirstBookIsbn() {
        return "0000000001";
    }

    public static String getSecondBookIsbn() {
        return "0000000002";
    }

    public static CreateBookRequestDto createBookRequestDto() {
        return new CreateBookRequestDto()
                .setTitle("Book 1")
                .setAuthor("Author")
                .setIsbn(getFirstBookIsbn())
                .setPrice(BigDecimal.valueOf(50.00))
                .setCategoryIds(Set.of(1L));
    }

    public static BookDto createBookResponseDto() {
        return new BookDto()
                .setTitle(createBookRequestDto().getTitle())
                .setAuthor(createBookRequestDto().getAuthor())
                .setIsbn(createBookRequestDto().getIsbn())
                .setPrice(createBookRequestDto().getPrice())
                .setCategoryIds(createBookRequestDto().getCategoryIds());
    }

    public static CreateCategoryRequestDto createCategoryRequestDto() {
        return new CreateCategoryRequestDto()
                .setName("Sci-fi")
                .setDescription("Science fiction");
    }

    public static CategoryResponseDto createCategoryResponseDto() {
        return new CategoryResponseDto()
                .setName(createCategoryRequestDto().getName())
                .setDescription(createCategoryRequestDto().getDescription());
    }

    public static CategoryResponseDto updateCategoryResponseDto() {
        return new CategoryResponseDto()
                .setName(updateCategoryRequestDto().getName())
                .setDescription(updateCategoryRequestDto().getDescription());
    }

    public static CreateCategoryRequestDto updateCategoryRequestDto() {
        return new CreateCategoryRequestDto()
                .setName("Sci-fi")
                .setDescription("Science fiction (sometimes shortened to SF or sci-fi) "
                        + "is a genre of speculative fiction");
    }
}
