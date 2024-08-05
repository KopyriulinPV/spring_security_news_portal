package com.example.news_portal.mapper;
import com.example.news_portal.DTO.*;
import com.example.news_portal.model.Category;
import com.example.news_portal.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CategoryMapper {

    Category requestToCategory (UpsertCategoryRequest request);

    @Mapping(source = "categoryId", target = "id")
    Category requestToCategory(Long categoryId, UpsertCategoryRequest request);

    CategoryResponse categoryToCategoryResponse(Category category);

    default CategoryListResponse categoryListToCategoryResponseList(List<Category> categories) {
        CategoryListResponse response = new CategoryListResponse();
        response.setCategories(categories.stream().map(this::categoryToCategoryResponse).collect(Collectors.toList()));
        return response;
    }

}
