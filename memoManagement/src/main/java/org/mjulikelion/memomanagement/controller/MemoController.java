package org.mjulikelion.memomanagement.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.mjulikelion.memomanagement.dto.ResponseDto;
import org.mjulikelion.memomanagement.dto.memodto.MemoCreateDto;
import org.mjulikelion.memomanagement.dto.memodto.MemoUpdateDto;
import org.mjulikelion.memomanagement.dto.response.memoresponse.MemoListResponseData;
import org.mjulikelion.memomanagement.dto.response.memoresponse.MemoResponseData;
import org.mjulikelion.memomanagement.dto.response.userlikeresponse.UserLikeResponseData;
import org.mjulikelion.memomanagement.service.MemoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@AllArgsConstructor
public class MemoController {

    private final MemoService memoService;

    // 메모 작성하기
    @PostMapping("/memos")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ResponseDto<Void>> createMemo(@RequestBody @Valid MemoCreateDto memoCreateDto, @RequestHeader("userId") UUID userId) {
        this.memoService.createMemo(memoCreateDto, userId);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK, "create memo"), HttpStatus.OK);
    }

    // 유저가 작성한 모든 메모 조회하기
    @GetMapping("/memos/userId")
    public ResponseEntity<ResponseDto<MemoListResponseData>> getAllMemoByUserId(@RequestHeader("userId") UUID userId) {
        MemoListResponseData memoListResponseData = memoService.getAllMemoByUserId(userId);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK, "get memos", memoListResponseData), HttpStatus.OK);
    }

    // 유저가 작성한 메모를 메모 아이디를 통해 조회
    @GetMapping("/memos/memoId")
    public ResponseEntity<ResponseDto<MemoResponseData>> getMemoByMemoId(@RequestHeader("memoId") UUID memoId, @RequestHeader("userId") UUID userId) {
        MemoResponseData memoResponseData = memoService.getMemoByMemoId(userId, memoId);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK, "get memo", memoResponseData), HttpStatus.OK);
    }

    // 메모에 좋아요를 누르는 기능
    @PostMapping("/memos/likes")
    public ResponseEntity<ResponseDto<MemoResponseData>> addLikeByMemoId(@RequestHeader("memoId") UUID memoId, @RequestHeader("userId") UUID userId) {
        this.memoService.addLikeByMemoId(userId, memoId);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.CREATED, "add like"), HttpStatus.OK);
    }

    // 특정 메모의 좋아요 리스트 반환
    // 메모의 좋아요 갯수와 좋아요를 누른 유저의 이름을 보여준다.
    @GetMapping("/memos/likes")
    public ResponseEntity<ResponseDto<UserLikeResponseData>> getLikeByMemoId(@RequestHeader("memoId") UUID memoId) {
        UserLikeResponseData likeListResponseData = memoService.getLikeByMemoId(memoId);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK, "get like list", likeListResponseData), HttpStatus.OK);
    }

    // 해당 유저가 작성한 메모를 메모 아이디를 통해 삭제
    @DeleteMapping("/memos")
    public ResponseEntity<ResponseDto<Void>> deleteMemoByMemoId(@RequestHeader("memoId") UUID memoId, @RequestHeader("userId") UUID userId) {
        this.memoService.deleteMemoByMemoId(userId, memoId);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK, "delete memo"), HttpStatus.OK);
    }

    // 해당 유저가 작성한 메모를 메모 아이디를 통해 수정
    @PatchMapping("/memos")
    public ResponseEntity<ResponseDto<Void>> updateMemoByMemoId(@RequestHeader("memoId") UUID memoId, @RequestBody @Valid MemoUpdateDto memoUpdateDto, @RequestHeader("userId") UUID userId) {
        this.memoService.updateMemoByMemoId(userId, memoUpdateDto, memoId);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK, "update memo"), HttpStatus.OK);
    }

}
