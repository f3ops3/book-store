package springweb.courseproject.dto.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateCategoryRequestDto {
    @NotBlank
    @Size(min = 4, max = 50)
    private String name;
    @Size(min = 8, max = 255)
    private String description;
}
