package org.mjulikelion.memomanagement.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "memo")
public class Memo extends BaseEntity {
    @Setter
    @Column(length = 100, nullable = false)// 길이는 100자 이하이고, 비어있을 수 없다.
    private String title;

    @Setter
    @Column(length = 2000, nullable = false)// 길이는 2000자 이하이고, 비어있을 수 없다.
    private String content;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private User user;

    @Setter
    @OneToMany(mappedBy = "memo", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    // memo 필드를 기준으로 One To Many 관계를 맺는다. memo가 삭제되면 연관된 memo_like도 삭제된다. memo가 null이 되면 memo_like도 삭제된다. 지연로딩을 사용한다.
    private List<MemoLikes> memoLikes;
}
