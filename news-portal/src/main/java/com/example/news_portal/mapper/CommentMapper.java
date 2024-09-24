package com.example.news_portal.mapper;
import com.example.news_portal.DTO.*;
import com.example.news_portal.model.Comment;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import java.util.List;
import java.util.stream.Collectors;

@DecoratedWith(CommentMapperDelegate.class)
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CommentMapper {

    Comment requestToComment (UpsertCommentRequest request, String username);

    @Mapping(source = "commentId", target = "id")
    Comment requestToComment(Long commentId, UpsertCommentRequest request, String username);

    CommentResponse commentToCommentResponse(Comment comment);

    default CommentListResponse commentListToCommentListResponse(List<Comment> commentList) {
        CommentListResponse response = new CommentListResponse();
        response.setComments(commentList.stream().map(this::commentToCommentResponse).collect(Collectors.toList()));
        return response;
    }

}
