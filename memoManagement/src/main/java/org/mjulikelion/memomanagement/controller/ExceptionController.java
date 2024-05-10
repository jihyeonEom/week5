package org.mjulikelion.memomanagement.controller;

import lombok.extern.slf4j.Slf4j;
import org.mjulikelion.memomanagement.dto.errordto.ErrorResponseDto;
import org.mjulikelion.memomanagement.errorcode.ErrorCode;
import org.mjulikelion.memomanagement.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionController {

    //UserNotFoundException 핸들러
    @ResponseStatus(HttpStatus.NOT_FOUND) //response HTTP 상태 코드를 404(NOT_FOUND)로 설정
    @ExceptionHandler(UserNotFoundException.class) //UserNotFoundException 예외를 처리하는 핸들러
    public ResponseEntity<ErrorResponseDto> handleUserNotFoundException(
            UserNotFoundException userNotFoundException) {

        this.writeLog(userNotFoundException);
        return new ResponseEntity<>(ErrorResponseDto.res(userNotFoundException), HttpStatus.NOT_FOUND);
    }

    //MemoNotFoundException 핸들러
    @ResponseStatus(HttpStatus.NOT_FOUND) //response HTTP 상태 코드를 404(NOT_FOUND)로 설정
    @ExceptionHandler(MemoNotFoundException.class) // MemoNotFoundException 예외를 처리하는 핸들러
    public ResponseEntity<ErrorResponseDto> handleMemoNotFoundException(
            MemoNotFoundException memoNotFoundException) {

        this.writeLog(memoNotFoundException);
        return new ResponseEntity<>(ErrorResponseDto.res(memoNotFoundException), HttpStatus.NOT_FOUND);
    }

    // UserAlreadyExistsException 핸들러
    @ResponseStatus(HttpStatus.CONFLICT) // response HTTP 상태코드를 409(CONFLICT)로 설정
    @ExceptionHandler(EmailAlreadyExistsException.class) // UserAlreadyExistsException 예외를 처리하는 핸들러
    public ResponseEntity<ErrorResponseDto> handleUserAlreadyExistsException(EmailAlreadyExistsException userAlreadyExistsException) {
        this.writeLog(userAlreadyExistsException);
        return new ResponseEntity<>(ErrorResponseDto.res(userAlreadyExistsException), HttpStatus.CONFLICT);
    }

    // UserDoesNotHaveAccessException 핸들러
    @ResponseStatus(HttpStatus.UNAUTHORIZED) // response HTTP 상태코드를 401(UNAUTHORIZED)로 설정
    @ExceptionHandler(UserDoesNotHaveAccessException.class) // UserDoesNotHaveAccessException 예외를 처리하는 핸들러
    public ResponseEntity<ErrorResponseDto> handleUserDoesNotHaveAccessException(UserDoesNotHaveAccessException userDoesNotHaveAccessException) {
        this.writeLog(userDoesNotHaveAccessException);
        return new ResponseEntity<>(ErrorResponseDto.res(userDoesNotHaveAccessException), HttpStatus.UNAUTHORIZED);
    }

    // 원인을 알 수 없는 예외 처리
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleException(Exception exception) {
        this.writeLog(exception);
        return new ResponseEntity<>(
                ErrorResponseDto.res(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()), exception),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> handleMethodArgumentNotValidException(MethodArgumentNotValidException methodArgumentNotValidException) {
        FieldError fieldError = methodArgumentNotValidException.getBindingResult().getFieldError();
        if (fieldError == null) {
            return new ResponseEntity<>(ErrorResponseDto.res(String.valueOf(HttpStatus.BAD_REQUEST.value()),
                    methodArgumentNotValidException), HttpStatus.BAD_REQUEST);
        }
        ErrorCode validationErrorCode = ErrorCode.resolveValidationErrorCode(fieldError.getCode());
        String detail = fieldError.getDefaultMessage();
        DtoValidationException dtoValidationException = new DtoValidationException(validationErrorCode, detail);
        this.writeLog(dtoValidationException);
        return new ResponseEntity<>(ErrorResponseDto.res(dtoValidationException), HttpStatus.BAD_REQUEST);
    }

    private void writeLog(CustomException customException) {
        String exceptionName = customException.getClass().getSimpleName();
        ErrorCode errorCode = customException.getErrorCode();
        String detail = customException.getDetail();

        log.error("({}){}: {}", exceptionName, errorCode.getMessage(), detail);
    }

    private void writeLog(Exception exception) {
        log.error("({}){}", exception.getClass().getSimpleName(), exception.getMessage());
    }
}
