package org.invest.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterUserDto {
    @Size(max = 255, message = "Имя должно быть не больше 255 символов")
    private String name;

    @Size(min = 5, max = 255, message = "Адрес электронной почты должен содержать от 5 до 255 символов")
    @NotBlank(message = "Имя пользователя не может быть пустыми")
    private String email;

    @Size(min = 5, max = 50, message = "Длина пароля должна быть от 5 до 50 символов")
    @NotBlank(message = "Пароль не должен быть пустым")
    private String password;
}
