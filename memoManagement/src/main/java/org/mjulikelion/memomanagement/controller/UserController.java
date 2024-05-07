package org.mjulikelion.memomanagement.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.mjulikelion.memomanagement.dto.ResponseDto;
import org.mjulikelion.memomanagement.dto.UserCreateDto;
import org.mjulikelion.memomanagement.dto.UserUpdateDto;
import org.mjulikelion.memomanagement.dto.response.UserOrganizationResponseData;
import org.mjulikelion.memomanagement.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    // 회원가입
    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ResponseDto<Void>> createUser(@RequestBody @Valid UserCreateDto userCreateDto, @RequestHeader("userId") String userId) {
        this.userService.createUser(userCreateDto, userId);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.CREATED, "create user"), HttpStatus.OK);
    }

    // 회원 탈퇴
    @DeleteMapping("/users/{userId}")
    public ResponseEntity<ResponseDto<Void>> deleteUserByUserId(@PathVariable("userId") UUID userId) {
        this.userService.deleteUserByUserId(userId);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK, "delete user"), HttpStatus.OK);
    }

    // 회원 정보 수정
    @PatchMapping("/users/{userId}")
    public ResponseEntity<ResponseDto<Void>> updateMemoByMemoId(@RequestBody @Valid UserUpdateDto userUpdateDto, @PathVariable("userId") UUID userId) {
        this.userService.updateUserByUserId(userUpdateDto, userId);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK, "update user"), HttpStatus.OK);
    }

    // 자신이 가입한 org의 아이디 조회
    @GetMapping("/users/{userId}")
    public ResponseEntity<ResponseDto<UserOrganizationResponseData>> getUserOrganizationIdByUserId(@PathVariable("userId") UUID userId) {
        UserOrganizationResponseData userOrganizationResponseData = this.userService.getUserOrganizationIdByUserId(userId);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK, "get user organization", userOrganizationResponseData), HttpStatus.OK);
    }

}
