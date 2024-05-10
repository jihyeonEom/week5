package org.mjulikelion.memomanagement.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "organization")
public class Organization extends BaseEntity {
    @Setter
    @Column(length = 100, nullable = false)// 길이는 100자 이하이고, 비어있을 수 없다.
    private String name;

    @OneToMany(mappedBy = "organization", fetch = FetchType.LAZY)
    private List<UserOrganization> userOrganizations;
}
