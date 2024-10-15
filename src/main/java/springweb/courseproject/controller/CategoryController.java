package springweb.courseproject.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import springweb.courseproject.dto.book.BookDtoWithoutCategoryIds;
import springweb.courseproject.dto.category.CategoryResponseDto;
import springweb.courseproject.dto.category.CreateCategoryRequestDto;
import springweb.courseproject.service.book.BookService;
import springweb.courseproject.service.category.CategoryService;

@Tag(name = "Books store", description = "Endpoints for managing categories")
@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;
    private final BookService bookService;

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Create a new category", description = "Create a new category")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public CategoryResponseDto createCategory(@RequestBody CreateCategoryRequestDto categoryDto) {
        return categoryService.save(categoryDto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update a category", description = "Update a particular category by id")
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}")
    public CategoryResponseDto updateCategory(@PathVariable Long id,
                                              @RequestBody CreateCategoryRequestDto categoryDto) {
        return categoryService.update(id, categoryDto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete a category", description = "Delete a particular category by id")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable Long id) {
        categoryService.deleteById(id);
    }

    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Get all categories")
    @GetMapping
    public List<CategoryResponseDto> getAll() {
        return categoryService.findAll();
    }

    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Get a category", description = "Get a particular category by id")
    @GetMapping("/{id}")
    public CategoryResponseDto getCategoryById(@PathVariable Long id) {
        return categoryService.getById(id);
    }

    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Search books", description = "Search books by category")
    @GetMapping("/{id}/books")
    public List<BookDtoWithoutCategoryIds> getBooksByCategoryId(@PathVariable Long id,
                                                                Pageable pageable) {
        return bookService.findAllByCategoryId(id, pageable);
    }
}
