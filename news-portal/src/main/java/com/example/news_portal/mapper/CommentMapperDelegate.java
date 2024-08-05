package com.example.news_portal.mapper;
import com.example.news_portal.DTO.CommentResponse;
import com.example.news_portal.DTO.UpsertCommentRequest;
import com.example.news_portal.model.Comment;
import com.example.news_portal.service.impl.DataBaseNewsService;
import com.example.news_portal.service.impl.DataBaseUserService;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class CommentMapperDelegate implements CommentMapper {

    @Autowired
    private DataBaseUserService dataBaseUserService;
    @Autowired
    private DataBaseNewsService dataBaseNewsService;

    @Override
    public Comment requestToComment(UpsertCommentRequest request) {
        Comment comment = new Comment();
        comment.setText(request.getText());
        comment.setAuthor(dataBaseUserService.findById(request.getAuthor_id()));
        comment.setNews(dataBaseNewsService.findById(request.getNews_id()));
        return comment;
    }

    @Override
    public Comment requestToComment(Long CommentId, UpsertCommentRequest request) {
        Comment comment = requestToComment(request);
        comment.setId(CommentId);
        return comment;
    }

    @Override
    public CommentResponse commentToCommentResponse(Comment comment) {
        CommentResponse commentResponse = new CommentResponse();
        commentResponse.setId(comment.getId());
        commentResponse.setText(comment.getText());
        commentResponse.setAuthor_id(comment.getAuthor().getId());
        commentResponse.setNews_id(comment.getNews().getId());
        return commentResponse;
    }
}
