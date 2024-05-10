package org.mjulikelion.memomanagement.model.repository;

import jakarta.transaction.Transactional;
import org.mjulikelion.memomanagement.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    User save(User user);

    @Transactional
    void deleteUserById(UUID userId);

    @Transactional
    void deleteUserOrganizationById(UUID userOrgId);

    User findUserById(UUID id);

    User findUserByEmail(String email);

}
