package com.example.news_portal.web.controller;
import com.example.news_portal.DTO.*;
import com.example.news_portal.aopSpringSecurity.AdminUserModeratorVerification;
import com.example.news_portal.aopSpringSecurity.DeleteNewsVerification;
import com.example.news_portal.mapper.NewsMapper;
import com.example.news_portal.model.News;
import com.example.news_portal.service.impl.DataBaseNewsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
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
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER', 'ROLE_MODERATOR')")
    public ResponseEntity<NewsListResponse> findAll() {
        return ResponseEntity.ok(newsMapper.newsListToNewsListResponse(dataBaseNewsService.findAll()));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER', 'ROLE_MODERATOR')")
    public ResponseEntity<NewsResponseForSingleCall> findById(@PathVariable long id) {
        return ResponseEntity.ok(newsMapper.newsToNewsResponseForSingleCall(dataBaseNewsService.findById(id)));
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER', 'ROLE_MODERATOR')")
    public ResponseEntity<NewsResponseForSingleCall> create(@AuthenticationPrincipal UserDetails userDetails, @RequestBody @Valid UpsertNewsRequest request) {
        News news = dataBaseNewsService.save(newsMapper.requestToNews(request, userDetails.getUsername()));
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(newsMapper.newsToNewsResponseForSingleCall(news));
    }

    @PutMapping("/{Id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER', 'ROLE_MODERATOR')")
    @AdminUserModeratorVerification
    public ResponseEntity<NewsResponseForSingleCall> update(@AuthenticationPrincipal UserDetails userDetails, @PathVariable("newsId") Long newsId,
                                               @RequestBody UpsertNewsRequest request) {
        News updatedNews = dataBaseNewsService.update(newsMapper.requestToNews(newsId, request, userDetails.getUsername()));
        return ResponseEntity.ok(newsMapper.newsToNewsResponseForSingleCall(updatedNews));
    }

    @DeleteMapping("/{newsId}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER', 'ROLE_MODERATOR')")
    @DeleteNewsVerification
    public ResponseEntity<Void> delete(@AuthenticationPrincipal UserDetails userDetails, @PathVariable("newsId") Long newsId) {
        dataBaseNewsService.deleteById(newsId);
        return ResponseEntity.noContent().build();
    }

}
