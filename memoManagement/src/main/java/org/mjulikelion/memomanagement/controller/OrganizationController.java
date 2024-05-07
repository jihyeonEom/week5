package org.mjulikelion.memomanagement.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.mjulikelion.memomanagement.dto.OrganizationCreateDto;
import org.mjulikelion.memomanagement.dto.ResponseDto;
import org.mjulikelion.memomanagement.service.OrganizationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@AllArgsConstructor
public class OrganizationController {

    private final OrganizationService organizationService;

    // org 생성
    @PostMapping("/organizations")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ResponseDto<Void>> createOrganization(@RequestBody @Valid OrganizationCreateDto organizationCreateDto) {
        this.organizationService.createOrganization(organizationCreateDto);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.CREATED, "create organization"), HttpStatus.OK);
    }

    // org 삭제
    @DeleteMapping("/organizations/{orgId}")
    public ResponseEntity<ResponseDto<Void>> deleteOrganizationById(@PathVariable("orgId") UUID orgId) {
        this.organizationService.deleteOrganizationById(orgId);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK, "delete organization"), HttpStatus.OK);
    }

    // user의 org 가입
    @PostMapping("/organizations/{orgId}/join")
    public ResponseEntity<ResponseDto<Void>> joinOrganization(@PathVariable("orgId") UUID orgId, @RequestHeader("userId") UUID userId) {
        this.organizationService.joinOrganization(userId, orgId);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK, "join organization"), HttpStatus.OK);
    }

    // user의 org 탈퇴
    @DeleteMapping("/organizations/{userOrgId}/leave")
    public ResponseEntity<ResponseDto<Void>> leaveOrganization(@PathVariable("userOrgId") UUID userOrgId, @RequestHeader("userId") UUID userId) {
        this.organizationService.leaveOrganization(userOrgId, userId);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK, "leave organization"), HttpStatus.OK);
    }
}
