package org.mjulikelion.memomanagement.exception;

import org.mjulikelion.memomanagement.errorcode.ErrorCode;

public class UserNotInOrganizationException extends CustomException {
    public UserNotInOrganizationException(ErrorCode errorCode) {
        super(errorCode);
    }
}
