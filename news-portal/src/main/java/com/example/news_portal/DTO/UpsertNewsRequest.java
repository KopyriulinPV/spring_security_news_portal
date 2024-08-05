package com.example.news_portal.DTO;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpsertNewsRequest {

    @NotBlank(message = "Заголовок не может быть пустым!")
    private String title;

    @NotBlank(message = "Текст статьи не может быть пустым!")
    private String content;

    private Long author_id;

    private Long category_id;
}
