package com.example.news_portal.repository;

import com.example.news_portal.DTO.NewsFilter;
import com.example.news_portal.DTO.UserResponse;
import com.example.news_portal.model.News;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;

public interface NewsSpecification {

    static Specification<News> withFilter(NewsFilter newsFilter) {
        return Specification.where(byAuthorName(newsFilter.getAuthorName()))
                .and(byCategoryName(newsFilter.getCategoryName()));
    }

    static Specification<News> byAuthorName(String authorName) {
        return (root, query, criteriaBuilder) -> {
            if (authorName == null) {
                return null;
            }
            return criteriaBuilder.equal(root.get("author").get("userName"), authorName);
        };
    }

    static Specification<News> byCategoryName(String categoryName) {
        return (root, query, criteriaBuilder) -> {
            if (categoryName == null) {
                return null;
            }
            return criteriaBuilder.equal(root.get("category").get("categoryName"), categoryName);
        };
    }
}
