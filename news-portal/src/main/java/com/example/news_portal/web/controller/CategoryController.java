package com.example.news_portal.web.controller;
import com.example.news_portal.DTO.*;
import com.example.news_portal.model.Category;
import com.example.news_portal.service.impl.DataBaseCategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/category")
@RequiredArgsConstructor
public class CategoryController {

    private final DataBaseCategoryService dataBaseCategoryService;

    private final com.example.news_portal.mapper.CategoryMapper categoryMapper;

    @GetMapping("/categoryFilter")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER', 'ROLE_MODERATOR')")
    public ResponseEntity<CategoryListResponse> findAll(CategoryFilter categoryFilter) {
        return ResponseEntity.ok(categoryMapper.categoryListToCategoryResponseList(dataBaseCategoryService.findAll(categoryFilter)));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER', 'ROLE_MODERATOR')")
    public ResponseEntity<CategoryResponse> findById(@PathVariable long id) {
        return ResponseEntity.ok(categoryMapper.categoryToCategoryResponse(dataBaseCategoryService.findById(id)));
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_MODERATOR')")
    public ResponseEntity<CategoryResponse> create(@RequestBody @Valid UpsertCategoryRequest request) {
        Category category = dataBaseCategoryService.save(categoryMapper.requestToCategory(request));
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(categoryMapper.categoryToCategoryResponse(category));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_MODERATOR')")
    public ResponseEntity<CategoryResponse> update(@PathVariable("id") Long categoryId, @RequestBody @Valid UpsertCategoryRequest request) {
        Category updatedCategory = dataBaseCategoryService.update(categoryMapper.requestToCategory(categoryId, request));
        return ResponseEntity.ok(categoryMapper.categoryToCategoryResponse(updatedCategory));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_MODERATOR')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        dataBaseCategoryService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
