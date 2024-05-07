package org.mjulikelion.memomanagement.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Builder
@AllArgsConstructor
@Getter
@NoArgsConstructor
@Entity(name = "UserOrganization")
public class UserOrganization {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "uuid2")// UUID로 자동 생성되며, uuid2라는 이름의 생성기를 사용한다.
    @Column(updatable = false, unique = true, nullable = false)// 업데이트가 불가능하고, 고유하며, 비어있을 수 없다.
    private UUID id;

    @Setter
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Organization organization;

    @Setter
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private User user;

}
