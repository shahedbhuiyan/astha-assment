package com.booking.svc.domain.exceptions;

import com.booking.svc.domain.enums.ApiResponseCode;
import com.booking.svc.domain.enums.ResponseMessage;

public class ConcurrentBookingException extends CustomRootException {
    private static final String MESSAGE_CODE = ApiResponseCode.CONCURRENT_DATA_ACCESS_ERROR.getResponseCode();

    public ConcurrentBookingException(ResponseMessage message) {
        super(MESSAGE_CODE, message.getResponseMessage());
    }

    public ConcurrentBookingException(String messageKey) {
        super(MESSAGE_CODE, messageKey);
    }
}
