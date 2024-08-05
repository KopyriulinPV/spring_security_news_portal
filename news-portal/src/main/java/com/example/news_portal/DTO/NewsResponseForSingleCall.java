package com.example.news_portal.DTO;

import com.example.news_portal.model.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewsResponseForSingleCall {

    private Long id;

    private String title;

    private String content;

    private Long author_id;

    private Long category_id;

    private List<CommentResponse> comments;
}
