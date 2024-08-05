package com.example.news_portal.web.controller;
import com.example.news_portal.DTO.*;
import com.example.news_portal.aop.CommentVerification;
import com.example.news_portal.mapper.CommentMapper;
import com.example.news_portal.model.Comment;
import com.example.news_portal.service.impl.DataBaseCommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comment")
@RequiredArgsConstructor
public class CommentController {

    private final DataBaseCommentService dataBaseCommentService;

    private final CommentMapper commentMapper;

    @GetMapping("/commentFilter")
    public ResponseEntity<CommentListResponse> findAllByNews(CommentFilter commentFilter) {
        return ResponseEntity.ok(commentMapper.commentListToCommentListResponse(dataBaseCommentService.findAllByNews(commentFilter)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommentResponse> findById(@PathVariable long id) {
        return ResponseEntity.ok(commentMapper.commentToCommentResponse(dataBaseCommentService.findById(id)));
    }

    @PostMapping
    public ResponseEntity<CommentResponse> create(@RequestBody @Valid UpsertCommentRequest request) {
        Comment comment = dataBaseCommentService.save(commentMapper.requestToComment(request));
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(commentMapper.commentToCommentResponse(comment));
    }

    @PutMapping("/{commentId}/{authorId}")
    @CommentVerification
    public ResponseEntity<CommentResponse> update(@PathVariable("commentId") Long commentId, @PathVariable("authorId") Long authorId,
                                                  @RequestBody @Valid UpsertCommentRequest request) {
        Comment updatedComment = dataBaseCommentService.update(commentMapper.requestToComment(commentId, request));
        return ResponseEntity.ok(commentMapper.commentToCommentResponse(updatedComment));
    }

    @DeleteMapping("/{commentId}/{authorId}")
    @CommentVerification
    public ResponseEntity<Void> delete(@PathVariable("commentId") Long commentId, @PathVariable("authorId") Long authorId) {
        dataBaseCommentService.deleteById(commentId);
        return ResponseEntity.noContent().build();
    }

}
