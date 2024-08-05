package com.example.news_portal.service;
import com.example.news_portal.DTO.CategoryFilter;
import com.example.news_portal.model.Category;
import com.example.news_portal.model.User;
import java.util.List;

public interface CategoryService {

    List<Category> findAll(CategoryFilter categoryFilter);

    Category findById(Long id);

    Category save(Category category);

    Category update(Category category);

    void deleteById(Long id);

}
