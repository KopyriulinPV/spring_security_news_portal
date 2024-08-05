package com.example.news_portal.DTO;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CommentFilter {

    Integer pageNumber;
    Integer pageSize;

    Long news_id;
}
