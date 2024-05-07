package org.mjulikelion.memomanagement.exception;

import org.mjulikelion.memomanagement.errorcode.ErrorCode;

public class MemoNotFoundException extends CustomException {
    public MemoNotFoundException(ErrorCode errorCode, String detail) {
        super(errorCode, detail);
    }
}
