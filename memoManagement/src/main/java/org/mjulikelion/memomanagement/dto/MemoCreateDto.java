package org.mjulikelion.memomanagement.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class MemoCreateDto {

    @NotNull(message = "title이 null입니다.")
    private String title;

    @NotNull(message = "content가 null입니다.")
    private String content;

}
