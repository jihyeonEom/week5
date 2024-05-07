package org.mjulikelion.memomanagement.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class UserUpdateDto {
    @NotNull(message = "email이 null입니다.")
    private String email;

    @NotNull(message = "password가 null입니다.")
    private String password;

    @NotNull(message = "name이 null입니다.")
    private String name; // 유저 이름
}
