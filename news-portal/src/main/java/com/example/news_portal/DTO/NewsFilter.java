package com.example.news_portal.DTO;
import com.example.news_portal.validation.NewsFilterValid;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@NewsFilterValid
public class NewsFilter {

    Integer pageNumber;
    Integer pageSize;

    String authorName;
    String categoryName;
}
