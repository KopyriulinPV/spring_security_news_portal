package com.example.news_portal.repository;
import com.example.news_portal.model.Category;
import com.example.news_portal.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Page<Category> findAll(Pageable pageable);

}
