package springweb.courseproject.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import springweb.courseproject.config.MapperConfig;
import springweb.courseproject.dto.category.CategoryDto;
import springweb.courseproject.dto.category.CreateCategoryRequestDto;
import springweb.courseproject.model.Category;

@Mapper(config = MapperConfig.class)
public interface CategoryMapper {
    CategoryDto toDto(Category category);

    Category toEntity(CreateCategoryRequestDto categoryDto);

    void updateCategory(CreateCategoryRequestDto categoryDto, @MappingTarget Category category);
}
