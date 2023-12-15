package com.booking.svc.domain.exceptions;

import com.booking.svc.domain.enums.ApiResponseCode;
import com.booking.svc.domain.enums.ResponseMessage;

public class RecordNotFoundException extends CustomRootException {
    private static final String MESSAGE_CODE = ApiResponseCode.RECORD_NOT_FOUND.getResponseCode();

    public RecordNotFoundException(ResponseMessage message) {
        super(MESSAGE_CODE, message.getResponseMessage());
    }

    public RecordNotFoundException(String messageKey) {
        super(MESSAGE_CODE, messageKey);
    }
}
