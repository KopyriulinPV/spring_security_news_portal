package com.example.news_portal.DTO;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserFilter {
    Integer pageNumber;
    Integer pageSize;
}
