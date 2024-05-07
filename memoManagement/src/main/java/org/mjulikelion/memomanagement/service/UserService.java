package org.mjulikelion.memomanagement.service;

import lombok.AllArgsConstructor;
import org.mjulikelion.memomanagement.dto.UserCreateDto;
import org.mjulikelion.memomanagement.dto.UserUpdateDto;
import org.mjulikelion.memomanagement.dto.response.UserOrganizationResponseData;
import org.mjulikelion.memomanagement.errorcode.ErrorCode;
import org.mjulikelion.memomanagement.exception.EmailAlreadyExistsException;
import org.mjulikelion.memomanagement.exception.UserNotFoundException;
import org.mjulikelion.memomanagement.model.Memo;
import org.mjulikelion.memomanagement.model.MemoLikes;
import org.mjulikelion.memomanagement.model.User;
import org.mjulikelion.memomanagement.model.UserOrganization;
import org.mjulikelion.memomanagement.model.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    // 유저의 회원가입
    public void createUser(UserCreateDto userCreateDto, String userId) {

        List<Memo> memos = new ArrayList<>(); // 빈 메모 리스트
        List<MemoLikes> memoLikes = new ArrayList<>(); // 빈 좋아요 리스트
        List<UserOrganization> userOrganizations = new ArrayList<>();
        User user = new User(userCreateDto.getEmail(), userCreateDto.getPassword(), userCreateDto.getName(), memos, memoLikes, userOrganizations);
        if (isEmailExist(userCreateDto.getEmail())) {
            throw new EmailAlreadyExistsException(ErrorCode.EMAIL_ALREADY_EXISTS);
        }
        this.userRepository.save(user);
    }

    // 이메일 중복 검사
    public boolean isEmailExist(String email) {
        User user = this.userRepository.findUserByEmail(email);
        return user != null;
    }

    // 유저 탈퇴
    public void deleteUserByUserId(UUID userId) {
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException(ErrorCode.USER_NOT_FOUND);
        }
        this.userRepository.deleteUserById(userId);
    }

    // 유저 정보 업데이트
    public void updateUserByUserId(UserUpdateDto userUpdateDto, UUID userId) {
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException(ErrorCode.USER_NOT_FOUND);
        }
        User user = this.userRepository.findUserById(userId);
        user.setEmail(userUpdateDto.getEmail());
        user.setName(userUpdateDto.getName());
        this.userRepository.save(user);
    }

    // user의 UserOrganizationId 조회하기
    public UserOrganizationResponseData getUserOrganizationIdByUserId(UUID userId) {
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException(ErrorCode.USER_NOT_FOUND);
        }
        User user = this.userRepository.findUserById(userId);
        List<UserOrganization> userOrganization = user.getUserOrganizations();
        return new UserOrganizationResponseData(userOrganization);
    }
}
