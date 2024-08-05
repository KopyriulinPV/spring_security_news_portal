package com.example.news_portal.repository;

import com.example.news_portal.model.Comment;
import com.example.news_portal.model.News;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    Page<Comment> findAllByNews(News news, Pageable pageable);

    List<Comment> findAllByNews(News news);

}
