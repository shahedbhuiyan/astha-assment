package com.booking.svc.domain.exceptions;

import com.booking.svc.domain.enums.ApiResponseCode;
import com.booking.svc.domain.enums.ResponseMessage;

public class InterServiceCommunicationException extends CustomRootException {
    private static final String MESSAGE_CODE = ApiResponseCode.INTER_SERVICE_COMMUNICATION_ERROR.getResponseCode();

    public InterServiceCommunicationException(ResponseMessage message) {
        super(MESSAGE_CODE, message.getResponseMessage());
    }

    public InterServiceCommunicationException(String messageKey) {
        super(MESSAGE_CODE, messageKey);
    }
}
