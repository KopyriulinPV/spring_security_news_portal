package com.example.news_portal.DTO;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpsertUserRequest {
    @NotBlank(message = "Имя категории должно быть заполнено!")
    private String userName;

    private String password;
}
