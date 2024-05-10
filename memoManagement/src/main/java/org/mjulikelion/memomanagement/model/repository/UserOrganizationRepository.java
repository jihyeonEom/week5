package org.mjulikelion.memomanagement.model.repository;

import jakarta.transaction.Transactional;
import org.mjulikelion.memomanagement.model.UserOrganization;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserOrganizationRepository extends JpaRepository<UserOrganization, UUID> {
    @Transactional
    void deleteUserOrganizationById(UUID userOrgId);
}
