package springweb.courseproject.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import lombok.Data;
import springweb.courseproject.validation.Author;
import springweb.courseproject.validation.Isbn;

@Data
public class CreateBookRequestDto {
    @NotBlank
    private String title;
    @Author
    private String author;
    @Isbn
    private String isbn;
    @NotNull
    @Min(0)
    private BigDecimal price;
    private String description;
    private String coverImage;
}
