package org.mjulikelion.memomanagement.exception;

import org.mjulikelion.memomanagement.errorcode.ErrorCode;

public class EmailAlreadyExistsException extends CustomException {
    public EmailAlreadyExistsException(ErrorCode errorCode) {
        super(errorCode);
    }
}
