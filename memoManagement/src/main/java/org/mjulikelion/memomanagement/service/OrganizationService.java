package org.mjulikelion.memomanagement.service;

import lombok.AllArgsConstructor;
import org.mjulikelion.memomanagement.dto.organizationdto.OrganizationCreateDto;
import org.mjulikelion.memomanagement.dto.organizationdto.OrganizationUpdateDto;
import org.mjulikelion.memomanagement.errorcode.ErrorCode;
import org.mjulikelion.memomanagement.exception.OrganizationNotFoundException;
import org.mjulikelion.memomanagement.exception.UserNotFoundException;
import org.mjulikelion.memomanagement.exception.UserNotInOrganizationException;
import org.mjulikelion.memomanagement.model.Organization;
import org.mjulikelion.memomanagement.model.User;
import org.mjulikelion.memomanagement.model.UserOrganization;
import org.mjulikelion.memomanagement.model.repository.OrganizationRepository;
import org.mjulikelion.memomanagement.model.repository.UserOrganizationRepository;
import org.mjulikelion.memomanagement.model.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class OrganizationService {

    private final OrganizationRepository organizationRepository;
    private final UserRepository userRepository;
    private final UserOrganizationRepository userOrganizationRepository;

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

    // org 이름 변경
    public void updateOrganizationById(UUID orgId, OrganizationUpdateDto organizationUpdateDto) {
        if (!this.organizationRepository.existsById(orgId)) {
            throw new OrganizationNotFoundException(ErrorCode.ORGANIZATION_NOT_FOUND);
        }
        Organization organization = this.organizationRepository.findOrganizationById(orgId);
        organization.setName(organizationUpdateDto.getName());
        this.organizationRepository.save(organization);
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

        // org에 유저 등록
        UserOrganization userOrganization = new UserOrganization(organization, user);

        this.userOrganizationRepository.save(userOrganization);
    }

    // user의 org 탈퇴
    public void leaveOrganization(UUID orgId, UUID userId) {
        if (!this.userRepository.existsById(userId)) {
            throw new UserNotFoundException(ErrorCode.USER_NOT_FOUND);
        }
        if (!this.organizationRepository.existsById(orgId)) {
            throw new OrganizationNotFoundException(ErrorCode.ORGANIZATION_NOT_FOUND);
        }
        UUID userOrgId = this.isUserInOrganization(orgId, userId);

        System.out.println("-------------------");
        this.userOrganizationRepository.deleteUserOrganizationById(userOrgId);
        System.out.println("-------------------");

    }

    // user가 해당 org에 속해있는지 확인
    // 있으면 UserOrganiztion의 Id를 반환
    public UUID isUserInOrganization(UUID orgId, UUID userId) {
        User user = this.userRepository.findUserById(userId);
        List<UserOrganization> userOrganizations = user.getUserOrganizations();

        for (UserOrganization userOrg : userOrganizations) {

            if (userOrg.getOrganization().getId().equals(orgId)) {
                if (userOrg.getUser().getId().equals(userId)) {
                    return userOrg.getId();
                }
                throw new UserNotInOrganizationException(ErrorCode.USER_NOT_IN_ORGANIZATION);
            }
        }
        throw new UserNotInOrganizationException(ErrorCode.USER_NOT_IN_ORGANIZATION);
    }
}
