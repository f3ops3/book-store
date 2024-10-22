package springweb.courseproject.dto.order;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import springweb.courseproject.model.Status;

@Data
public class UpdateOrderRequestDto {
    @NotNull
    private Status status;
}
