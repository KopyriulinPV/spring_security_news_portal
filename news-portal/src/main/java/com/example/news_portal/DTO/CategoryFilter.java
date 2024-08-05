package com.example.news_portal.DTO;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CategoryFilter {
    Integer pageNumber;
    Integer pageSize;
}
