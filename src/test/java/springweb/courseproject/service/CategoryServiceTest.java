package springweb.courseproject.service;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import springweb.courseproject.dto.category.CategoryResponseDto;
import springweb.courseproject.dto.category.CreateCategoryRequestDto;
import springweb.courseproject.mapper.CategoryMapper;
import springweb.courseproject.model.Category;
import springweb.courseproject.repository.category.CategoryRepository;
import springweb.courseproject.service.category.CategoryServiceImpl;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {
    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private CategoryMapper categoryMapper;
    @InjectMocks
    private CategoryServiceImpl categoryService;

    @Test
    @DisplayName("Find all categories")
    void findAllBooks_returnBookDtoList() {
        //GIVEN
        Category firstCategory = new Category();
        Category secondCategory = new Category();

        CategoryResponseDto firstCategoryDto = new CategoryResponseDto();
        CategoryResponseDto secondCategoryDto = new CategoryResponseDto();
        final List<CategoryResponseDto> expected = List.of(firstCategoryDto, secondCategoryDto);
        //WHEN
        when(categoryMapper.toDto(firstCategory)).thenReturn(firstCategoryDto);
        when(categoryMapper.toDto(secondCategory)).thenReturn(secondCategoryDto);
        when(categoryRepository.findAll()).thenReturn(List.of(firstCategory, secondCategory));
        List<CategoryResponseDto> actual = categoryService.findAll();
        //THEN
        Assertions.assertEquals(expected, actual);
        verify(categoryRepository, Mockito.times(1)).findAll();
        verify(categoryMapper, Mockito.times(1)).toDto(firstCategory);
        verify(categoryMapper, Mockito.times(1)).toDto(secondCategory);
    }

    @Test
    @DisplayName("Save valid category")
    void saveCategory_validRequest_returnCategoryDto() {
        //GIVEN
        CreateCategoryRequestDto categoryRequestDto = new CreateCategoryRequestDto();
        Category category = new Category();
        CategoryResponseDto expected = new CategoryResponseDto();
        //WHEN
        when(categoryMapper.toEntity(categoryRequestDto)).thenReturn(category);
        when(categoryRepository.save(category)).thenReturn(category);
        when(categoryMapper.toDto(category)).thenReturn(expected);
        CategoryResponseDto actual = categoryService.save(categoryRequestDto);
        //THEN
        Assertions.assertEquals(expected, actual);
        verify(categoryRepository, Mockito.times(1)).save(category);
        verify(categoryMapper, Mockito.times(1)).toEntity(categoryRequestDto);
        verify(categoryMapper, Mockito.times(1)).toDto(category);
    }

    @Test
    @DisplayName("Update category by valid id")
    void updateCategory_validIdAndRequest_Success() {
        //GIVEN
        Long id = 1L;
        CreateCategoryRequestDto categoryRequestDto = new CreateCategoryRequestDto();
        Category category = new Category();
        CategoryResponseDto expected = new CategoryResponseDto();
        //WHEN
        when(categoryRepository.findById(id)).thenReturn(Optional.of(category));
        doNothing().when(categoryMapper).updateCategory(categoryRequestDto, category);
        when(categoryRepository.save(category)).thenReturn(category);
        when(categoryMapper.toDto(category)).thenReturn(expected);
        CategoryResponseDto actual = categoryService.update(id, categoryRequestDto);
        //THEN
        Assertions.assertEquals(expected, actual);
        verify(categoryRepository, Mockito.times(1)).findById(id);
        verify(categoryMapper, Mockito.times(1)).updateCategory(categoryRequestDto, category);
        verify(categoryMapper, Mockito.times(1)).toDto(category);
    }
}
