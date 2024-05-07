package org.mjulikelion.memomanagement.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter

public class UserCreateDto {
    @NotNull(message = "email이 null입니다.")
    @Email
    private String email;

    @NotNull(message = "password가 null입니다.")
    private String password;

    @NotNull(message = "name이 null입니다.")
    private String name; // 유저 이름
}
