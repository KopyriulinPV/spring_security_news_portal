package com.example.news_portal.service.impl;
import com.example.news_portal.DTO.CategoryFilter;
import com.example.news_portal.exception.EntityNotFoundException;
import com.example.news_portal.model.Category;
import com.example.news_portal.repository.CategoryRepository;
import com.example.news_portal.service.CategoryService;
import com.example.news_portal.utils.BeanUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DataBaseCategoryService implements CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public List<Category> findAll(CategoryFilter categoryFilter) {
        return categoryRepository.findAll(PageRequest.of(
                categoryFilter.getPageNumber(), categoryFilter.getPageSize()
        )).getContent();
    }

    @Override
    public Category findById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(MessageFormat.format(
                        "Категория с ID {0} не найден", id
                )));
    }

    @Override
    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Category update(Category category) {
        Category existedCategory = categoryRepository.findById(category.getId()).get();
        BeanUtils.copyNonNullProperties(category, existedCategory);
        return categoryRepository.save(existedCategory);
    }

    @Override
    public void deleteById(Long id) {
        categoryRepository.deleteById(id);
    }
}
