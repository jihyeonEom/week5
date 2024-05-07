package org.mjulikelion.memomanagement.exception;

import org.mjulikelion.memomanagement.errorcode.ErrorCode;

public class UserNotFoundException extends CustomException {
    public UserNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
