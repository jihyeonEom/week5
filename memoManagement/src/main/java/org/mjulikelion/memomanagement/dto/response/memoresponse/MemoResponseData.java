package org.mjulikelion.memomanagement.dto.response.memoresponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.mjulikelion.memomanagement.model.Memo;

@Builder
@Getter
@AllArgsConstructor
public class MemoResponseData {
    private Memo memo;
}
