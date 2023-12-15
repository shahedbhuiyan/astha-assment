package com.booking.svc.domain.exceptions;


import com.booking.svc.domain.enums.ApiResponseCode;
import com.booking.svc.domain.enums.ResponseMessage;

public class DatabaseException extends CustomRootException {
    private static final String MESSAGE_CODE = ApiResponseCode.DB_OPERATION_FAILED.getResponseCode();

    public DatabaseException(ResponseMessage message) {
        super(MESSAGE_CODE, message.getResponseMessage());
    }

    public DatabaseException(String messageKey) {
        super(MESSAGE_CODE, messageKey);
    }
}
