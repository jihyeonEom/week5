package org.mjulikelion.memomanagement.exception;

import org.mjulikelion.memomanagement.errorcode.ErrorCode;

public class UserDoesNotHaveAccessException extends CustomException {
    public UserDoesNotHaveAccessException(ErrorCode errorCode) {
        super(errorCode);
    }
}
