package org.mjulikelion.memomanagement.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.mjulikelion.memomanagement.model.Memo;

import java.util.List;

@Builder
@Getter
@AllArgsConstructor
public class MemoListResponseData {
    private List<Memo> memos;
}
