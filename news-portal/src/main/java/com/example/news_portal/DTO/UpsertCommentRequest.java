package com.example.news_portal.DTO;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpsertCommentRequest {

    @NotBlank(message = "Комментарий не может быть пустым!")
    private String text;

    private Long author_id;

    private Long news_id;

}
