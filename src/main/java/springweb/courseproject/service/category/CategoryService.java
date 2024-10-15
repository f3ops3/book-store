package springweb.courseproject.service.category;

import java.util.List;
import springweb.courseproject.dto.category.CategoryDto;
import springweb.courseproject.dto.category.CreateCategoryRequestDto;

public interface CategoryService {
    List<CategoryDto> findAll();

    CategoryDto getById(Long id);

    CategoryDto save(CreateCategoryRequestDto categoryDto);

    CategoryDto update(Long id, CreateCategoryRequestDto categoryDto);

    void deleteById(Long id);
}
