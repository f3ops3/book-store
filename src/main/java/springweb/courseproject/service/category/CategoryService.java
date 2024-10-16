package springweb.courseproject.service.category;

import java.util.List;
import springweb.courseproject.dto.category.CategoryResponseDto;
import springweb.courseproject.dto.category.CreateCategoryRequestDto;

public interface CategoryService {
    List<CategoryResponseDto> findAll();

    CategoryResponseDto getById(Long id);

    CategoryResponseDto save(CreateCategoryRequestDto categoryDto);

    CategoryResponseDto update(Long id, CreateCategoryRequestDto categoryDto);

    void deleteById(Long id);
}
