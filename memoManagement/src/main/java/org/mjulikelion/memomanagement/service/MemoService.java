package org.mjulikelion.memomanagement.service;

import lombok.AllArgsConstructor;
import org.mjulikelion.memomanagement.dto.memodto.MemoCreateDto;
import org.mjulikelion.memomanagement.dto.memodto.MemoUpdateDto;
import org.mjulikelion.memomanagement.dto.response.memoresponse.MemoListResponseData;
import org.mjulikelion.memomanagement.dto.response.memoresponse.MemoResponseData;
import org.mjulikelion.memomanagement.dto.response.userlikeresponse.UserLikeResponseData;
import org.mjulikelion.memomanagement.errorcode.ErrorCode;
import org.mjulikelion.memomanagement.exception.EmailAlreadyExistsException;
import org.mjulikelion.memomanagement.exception.MemoNotFoundException;
import org.mjulikelion.memomanagement.exception.UserNotFoundException;
import org.mjulikelion.memomanagement.model.Memo;
import org.mjulikelion.memomanagement.model.MemoLikes;
import org.mjulikelion.memomanagement.model.User;
import org.mjulikelion.memomanagement.model.repository.MemoRepository;
import org.mjulikelion.memomanagement.model.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class MemoService {

    private final MemoRepository memoRepository;
    private final UserRepository userRepository;

    // 메모 작성하기
    public void createMemo(MemoCreateDto memoCreateDto, UUID userId) {
        if (!userRepository.existsById(userId)) {
            throw new EmailAlreadyExistsException(ErrorCode.EMAIL_ALREADY_EXISTS);
        }
        User user = userRepository.findUserById(userId);
        List<MemoLikes> memoLikes = new ArrayList<>(); // 빈 좋아요 리스트 생성
        Memo memo = new Memo(memoCreateDto.getTitle(), memoCreateDto.getContent(), user, memoLikes);
        this.memoRepository.save(memo);
    }

    // 유저가 작성한 모든 메모 조회하기
    public MemoListResponseData getAllMemoByUserId(UUID userId) {
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException(ErrorCode.USER_NOT_FOUND);
        }
        return new MemoListResponseData(memoRepository.findAllMemoByUserId(userId));
    }

    // 유저가 해당 메모에 접근할 권한이 있는지 검사
    public boolean isUserHaveAccessTo(UUID userId, UUID memoId) {
        for (Memo memo : memoRepository.findAllMemoByUserId(userId)) {
            if (memo.getId().equals(memoId)) {
                return true;
            }
        }
        throw new UserNotFoundException(ErrorCode.USER_DOES_NOT_HAVE_ACCESS);
    }

    // 메모를 메모 아이디를 통해 조회
    // 다른 사람이 작성한 메모도 조회할 수 있다.
    public MemoResponseData getMemoByMemoId(UUID userId, UUID memoId) {
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException(ErrorCode.USER_NOT_FOUND);
        }
        if (!memoRepository.existsById(memoId)) {
            throw new MemoNotFoundException(ErrorCode.MEMO_NOT_FOUND, "Memo not found");
        }

        return new MemoResponseData(memoRepository.findMemoById(memoId));
    }

    // 메모에 좋아요 누르기
    public void addLikeByMemoId(UUID userId, UUID memoId) {
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException(ErrorCode.USER_NOT_FOUND);
        }
        if (!memoRepository.existsById(memoId)) {
            throw new MemoNotFoundException(ErrorCode.MEMO_NOT_FOUND, "Memo not found");
        }

        // 메모, 유저 받아오기
        Memo memo = this.memoRepository.findMemoById(memoId);
        User user = this.userRepository.findUserById(userId);

        // 새로운 좋아요 생성
        MemoLikes memoLikes = new MemoLikes(memo, user);

        memo.getMemoLikes().add(memoLikes);
        this.memoRepository.save(memo);
    }

    // 해당 메모의 좋아요를 누른 유저의 이름과 좋아요 갯수 반환
    public UserLikeResponseData getLikeByMemoId(UUID memoId) {
        if (!memoRepository.existsById(memoId)) {
            throw new MemoNotFoundException(ErrorCode.MEMO_NOT_FOUND, "Memo not found");
        }
        List<MemoLikes> memoLike = this.memoRepository.findMemoById(memoId).getMemoLikes();
        List<String> likeUserList = new ArrayList<>(); // 메모에 좋아요를 누른 유저의 아이디 리스트

        for (MemoLikes likes : memoLike) {
            likeUserList.add(likes.getUser().getName());
        }

        int count = likeUserList.size();
        return new UserLikeResponseData(likeUserList, count);
    }

    // 해당 유저가 작성한 메모를 메모 아이디를 통해 삭제
    public void deleteMemoByMemoId(UUID userId, UUID memoId) {
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException(ErrorCode.USER_NOT_FOUND);
        }
        if (!memoRepository.existsById(memoId)) {
            throw new MemoNotFoundException(ErrorCode.MEMO_NOT_FOUND, "Memo not found");
        }
        isUserHaveAccessTo(userId, memoId); // 접근권한 검사

        this.memoRepository.deleteMemoById(memoId);
    }

    // 해당 유저가 작성한 메모를 메모 아이디를 통해 수정
    public void updateMemoByMemoId(UUID userId, MemoUpdateDto memoUpdateDto, UUID memoId) {
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException(ErrorCode.USER_NOT_FOUND);
        }
        if (!memoRepository.existsById(memoId)) {
            throw new MemoNotFoundException(ErrorCode.MEMO_NOT_FOUND, "Memo not found");
        }
        isUserHaveAccessTo(userId, memoId); // 접근권한 검사

        Memo memo = this.memoRepository.findMemoById(memoId);
        memo.setTitle(memoUpdateDto.getTitle());
        memo.setContent(memoUpdateDto.getContent());
        this.memoRepository.save(memo);
    }
}
