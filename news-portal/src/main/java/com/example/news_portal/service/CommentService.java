package com.example.news_portal.service;

import com.example.news_portal.DTO.CommentFilter;
import com.example.news_portal.model.Category;
import com.example.news_portal.model.Comment;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CommentService {

    List<Comment> findAllByNews(CommentFilter commentFilter);

    Comment findById(Long id);

    Comment save(Comment comment);

    Comment update(Comment comment);

    void deleteById(Long id);

}
