package org.mjulikelion.memomanagement.service;

import lombok.AllArgsConstructor;
import org.mjulikelion.memomanagement.dto.OrganizationCreateDto;
import org.mjulikelion.memomanagement.errorcode.ErrorCode;
import org.mjulikelion.memomanagement.exception.OrganizationNotFoundException;
import org.mjulikelion.memomanagement.exception.UserNotFoundException;
import org.mjulikelion.memomanagement.exception.UserNotInOrganizationException;
import org.mjulikelion.memomanagement.model.Organization;
import org.mjulikelion.memomanagement.model.User;
import org.mjulikelion.memomanagement.model.UserOrganization;
import org.mjulikelion.memomanagement.model.repository.OrganizationRepository;
import org.mjulikelion.memomanagement.model.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class OrganizationService {

    private final OrganizationRepository organizationRepository;
    private final UserRepository userRepository;

    // org 생성
    public void createOrganization(OrganizationCreateDto organizationCreateDto) {
        Organization organization = new Organization();
        organization.setName(organizationCreateDto.getName());
        this.organizationRepository.save(organization);
    }

    // org 삭제
    public void deleteOrganizationById(UUID orgId) {
        if (!this.organizationRepository.existsById(orgId)) {
            throw new OrganizationNotFoundException(ErrorCode.ORGANIZATION_NOT_FOUND);
        }
        this.organizationRepository.deleteOrganizationById(orgId);
    }

    // user의 org 가입
    public void joinOrganization(UUID userId, UUID orgId) {
        if (!this.userRepository.existsById(userId)) {
            throw new UserNotFoundException(ErrorCode.USER_NOT_FOUND);
        }
        if (!this.organizationRepository.existsById(orgId)) {
            throw new OrganizationNotFoundException(ErrorCode.ORGANIZATION_NOT_FOUND);
        }
        User user = this.userRepository.findUserById(userId);
        Organization organization = this.organizationRepository.findOrganizationById(orgId);
        UserOrganization userOrganization = new UserOrganization();

        // org에 유저 등록
        userOrganization.setUser(user);
        userOrganization.setOrganization(organization);

        this.organizationRepository.save(userOrganization);
    }

    // user의 org 탈퇴
    public void leaveOrganization(UUID userOrgId, UUID userId) {
        if (!this.userRepository.existsById(userId)) {
            throw new UserNotFoundException(ErrorCode.USER_NOT_FOUND);
        }
        if (!isUserInOrganization(userId, userOrgId)) {
            throw new UserNotInOrganizationException(ErrorCode.USER_NOT_IN_ORGANIZATION);
        }

        this.organizationRepository.deleteUserOrganizationById(userOrgId);
    }

    // user가 해당 org에 속해있는지 확인
    public boolean isUserInOrganization(UUID userId, UUID userOrgId) {
        User user = this.userRepository.findUserById(userId);
        List<UserOrganization> userOrganization = user.getUserOrganizations();
        for (UserOrganization userOrg : userOrganization) {
            if (userOrg.getOrganization().getId().equals(userOrgId)) {
                return true;
            }
        }
        return false;
    }
}
