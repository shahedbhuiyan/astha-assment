package com.booking.svc.domain.exceptions;

import com.booking.svc.domain.enums.ApiResponseCode;
import com.booking.svc.domain.enums.ResponseMessage;

public class RecordAlreadyExistException extends CustomRootException {
    private static final String MESSAGE_CODE = ApiResponseCode.INVALID_REQUEST_DATA.getResponseCode();

    public RecordAlreadyExistException(ResponseMessage message) {
        super(MESSAGE_CODE, message.getResponseMessage());
    }

    public RecordAlreadyExistException(String messageKey) {
        super(MESSAGE_CODE, messageKey);
    }
}
