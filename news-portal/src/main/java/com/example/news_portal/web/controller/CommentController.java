package com.example.news_portal.web.controller;
import com.example.news_portal.DTO.*;
import com.example.news_portal.aopSpringSecurity.DeleteCommentVerification;
import com.example.news_portal.aopSpringSecurity.WhoCreatedCommentCanUpdate;
import com.example.news_portal.mapper.CommentMapper;
import com.example.news_portal.model.Comment;
import com.example.news_portal.service.impl.DataBaseCommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comment")
@RequiredArgsConstructor
public class CommentController {

    private final DataBaseCommentService dataBaseCommentService;

    private final CommentMapper commentMapper;

    @GetMapping("/commentFilter")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER', 'ROLE_MODERATOR')")
    public ResponseEntity<CommentListResponse> findAllByNews(CommentFilter commentFilter) {
        return ResponseEntity.ok(commentMapper.commentListToCommentListResponse(dataBaseCommentService.findAllByNews(commentFilter)));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER', 'ROLE_MODERATOR')")
    public ResponseEntity<CommentResponse> findById(@PathVariable long id) {
        return ResponseEntity.ok(commentMapper.commentToCommentResponse(dataBaseCommentService.findById(id)));
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER', 'ROLE_MODERATOR')")
    public ResponseEntity<CommentResponse> create(@AuthenticationPrincipal UserDetails userDetails, @RequestBody UpsertCommentRequest request) {
        Comment comment = dataBaseCommentService.save(commentMapper.requestToComment(request, userDetails.getUsername()));
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(commentMapper.commentToCommentResponse(comment));
    }

    @PutMapping("/{commentId}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER', 'ROLE_MODERATOR')")
    @WhoCreatedCommentCanUpdate
    public ResponseEntity<CommentResponse> update(@AuthenticationPrincipal UserDetails userDetails, @PathVariable("commentId") Long commentId,
                                                  @RequestBody @Valid UpsertCommentRequest request) {
        Comment updatedComment = dataBaseCommentService.update(commentMapper.requestToComment(commentId, request, userDetails.getUsername()));
        return ResponseEntity.ok(commentMapper.commentToCommentResponse(updatedComment));
    }

    @DeleteMapping("/{commentId}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER', 'ROLE_MODERATOR')")
    @DeleteCommentVerification
    public ResponseEntity<Void> delete(@PathVariable("commentId") Long commentId) {
        dataBaseCommentService.deleteById(commentId);
        return ResponseEntity.noContent().build();
    }

}
