package com.example.news_portal.service.impl;
import com.example.news_portal.DTO.CommentFilter;
import com.example.news_portal.exception.EntityNotFoundException;
import com.example.news_portal.model.Comment;
import com.example.news_portal.repository.CommentRepository;
import com.example.news_portal.repository.NewsRepository;
import com.example.news_portal.service.CommentService;
import com.example.news_portal.utils.BeanUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DataBaseCommentService implements CommentService {

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    NewsRepository newsRepository;

    @Override
    public List<Comment> findAllByNews(CommentFilter commentFilter) {
        return commentRepository.findAllByNews(newsRepository.findById(commentFilter.getNews_id()).get(), PageRequest.of(
                commentFilter.getPageNumber(), commentFilter.getPageSize()
        )).getContent();
    }

    @Override
    public Comment findById(Long id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(MessageFormat.format(
                        "Комментарий с ID {0} не найдена", id
                )));
    }

    @Override
    public Comment save(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    public Comment update(Comment comment) {
        Comment existedComment = commentRepository.findById(comment.getId()).get();
        BeanUtils.copyNonNullProperties(comment, existedComment);
        return commentRepository.save(existedComment);
    }

    @Override
    public void deleteById(Long id) {
         commentRepository.deleteById(id);
    }
}
