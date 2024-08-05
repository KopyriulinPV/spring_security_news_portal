package com.example.news_portal.web.controller;
import com.example.news_portal.DTO.*;
import com.example.news_portal.aop.UserVerification;
import com.example.news_portal.mapper.NewsMapper;
import com.example.news_portal.model.News;
import com.example.news_portal.service.impl.DataBaseNewsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/news")
@RequiredArgsConstructor
public class NewsController {

    private final DataBaseNewsService dataBaseNewsService;

    private final NewsMapper newsMapper;

    @GetMapping("/newsFilter")
    public ResponseEntity<NewsListResponse> filterBy(@Valid NewsFilter newsFilter) {
        return ResponseEntity.ok(newsMapper
                .newsListToNewsListResponse(dataBaseNewsService.filterBy(newsFilter)));
    }

    @GetMapping
    public ResponseEntity<NewsListResponse> findAll() {
        return ResponseEntity.ok(newsMapper.newsListToNewsListResponse(dataBaseNewsService.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<NewsResponseForSingleCall> findById(@PathVariable long id) {
        return ResponseEntity.ok(newsMapper.newsToNewsResponseForSingleCall(dataBaseNewsService.findById(id)));
    }

    @PostMapping
    public ResponseEntity<NewsResponseForSingleCall> create(@RequestBody @Valid UpsertNewsRequest request) {
        News news = dataBaseNewsService.save(newsMapper.requestToNews(request));
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(newsMapper.newsToNewsResponseForSingleCall(news));
    }

    @PutMapping("/{newsId}/{authorId}")
    @UserVerification
    public ResponseEntity<NewsResponseForSingleCall> update(@PathVariable("newsId") Long newsId, @PathVariable("authorId") Long authorId,
                                               @RequestBody @Valid UpsertNewsRequest request) {
        News updatedNews = dataBaseNewsService.update(newsMapper.requestToNews(newsId, request));
        return ResponseEntity.ok(newsMapper.newsToNewsResponseForSingleCall(updatedNews));
    }

    @DeleteMapping("/{newsId}/{authorId}")
    @UserVerification
    public ResponseEntity<Void> delete(@PathVariable("newsId") Long newsId, @PathVariable("authorId") Long authorId) {
        dataBaseNewsService.deleteById(newsId);
        return ResponseEntity.noContent().build();
    }

}
