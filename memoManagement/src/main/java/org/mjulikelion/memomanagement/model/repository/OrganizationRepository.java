package org.mjulikelion.memomanagement.model.repository;

import jakarta.transaction.Transactional;
import org.mjulikelion.memomanagement.model.Organization;
import org.mjulikelion.memomanagement.model.UserOrganization;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrganizationRepository extends JpaRepository<Organization, UUID> {
    Organization save(Organization organization);

    void save(UserOrganization userOrganization);

    @Transactional
    void deleteOrganizationById(UUID organizationId);

    Organization findOrganizationById(UUID organizationId);

    @Transactional
    void deleteUserOrganizationById(UUID userOrgId);
}
