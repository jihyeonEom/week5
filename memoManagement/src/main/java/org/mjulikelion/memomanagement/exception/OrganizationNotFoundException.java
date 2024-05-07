package org.mjulikelion.memomanagement.exception;

import org.mjulikelion.memomanagement.errorcode.ErrorCode;

public class OrganizationNotFoundException extends CustomException {
    public OrganizationNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }

}
