package org.mjulikelion.memomanagement.dto.memodto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class MemoCreateDto {

    @NotBlank(message = "제목이 비어있습니다.")
    private String title;

    @NotBlank(message = "내용이 비어있습니다.")
    private String content;

}
