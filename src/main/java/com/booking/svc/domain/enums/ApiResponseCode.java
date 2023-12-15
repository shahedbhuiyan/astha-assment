package com.booking.svc.domain.enums;

import com.booking.svc.domain.common.ApiResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

@AllArgsConstructor
@Getter
public enum ApiResponseCode {

    OPERATION_SUCCESSFUL("S200"),
    RECORD_NOT_FOUND("E404"),
    INVALID_REQUEST_DATA("E400"),
    INTER_SERVICE_COMMUNICATION_ERROR("E503"),
    DB_OPERATION_FAILED("E422"),
    UNHANDLED_EXCEPTION("E500"),
    METHOD_NOT_ALLOWED("E405"),
    UNAUTHORIZED_RESOURCE_ACCESS("E401"),
    CONCURRENT_DATA_ACCESS_ERROR("E405"),
    ;

    private final String responseCode;

    public static boolean isOperationSuccessful(ApiResponse apiResponse) {
        return Objects.nonNull(apiResponse) && apiResponse.getResponseCode().equals(ApiResponseCode.OPERATION_SUCCESSFUL.getResponseCode());
    }

    public static boolean isNotOperationSuccessful(ApiResponse apiResponse) {
        return !isOperationSuccessful(apiResponse);
    }

}
