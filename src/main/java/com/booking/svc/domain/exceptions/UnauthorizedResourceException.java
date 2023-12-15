package com.booking.svc.domain.exceptions;

import com.booking.svc.domain.enums.ApiResponseCode;
import com.booking.svc.domain.enums.ResponseMessage;

public class UnauthorizedResourceException extends CustomRootException {
    private static final String MESSAGE_CODE = ApiResponseCode.UNAUTHORIZED_RESOURCE_ACCESS.getResponseCode();

    public UnauthorizedResourceException(ResponseMessage message) {
        super(MESSAGE_CODE, message.getResponseMessage());
    }

    public UnauthorizedResourceException(String messageKey) {
        super(MESSAGE_CODE, messageKey);
    }
}
