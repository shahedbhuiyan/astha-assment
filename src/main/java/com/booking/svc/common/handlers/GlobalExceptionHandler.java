package com.booking.svc.common.handlers;

import com.booking.svc.common.logggers.BookingServiceLogger;
import com.booking.svc.common.utils.ResponseUtils;
import com.booking.svc.domain.common.ApiResponse;
import com.booking.svc.domain.enums.ApiResponseCode;
import com.booking.svc.domain.enums.ResponseMessage;
import com.booking.svc.domain.exceptions.*;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler extends BaseExceptionHandler {

    private final BookingServiceLogger logger;

    @ExceptionHandler({
            InvalidRequestDataException.class,
            RecordNotFoundException.class,
            RecordAlreadyExistException.class
    })
    public ResponseEntity<ApiResponse<Void>> handleCustomException(CustomRootException ex) {
        logger.error(ex.getLocalizedMessage(), ex);
        ApiResponse<Void> apiResponse = ResponseUtils.createApiResponse(ex.getMessageCode(), ex.getMessage());
        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({DatabaseException.class, DataAccessException.class})
    public ResponseEntity<ApiResponse<Void>> handleDatabaseException(CustomRootException ex) {
        logger.error(ex.getLocalizedMessage(), ex);
        ApiResponse<Void> apiResponse = ResponseUtils.createApiResponse(ex.getMessageCode(), ex.getMessage());
        return new ResponseEntity<>(apiResponse, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler({InterServiceCommunicationException.class})
    public ResponseEntity<ApiResponse<Void>> handleFeignClientException(CustomRootException ex) {
        logger.error(ex.getLocalizedMessage(), ex);
        ApiResponse<Void> apiResponse = ResponseUtils.createApiResponse(ex.getMessageCode(), ex.getMessage());
        return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({FeignException.class})
    public ResponseEntity<ApiResponse<Void>> handleFeignException(FeignException ex) {
        logger.error(ex.getLocalizedMessage(), ex);
        ApiResponse<Void> apiResponse = ResponseUtils.createApiResponse(ApiResponseCode.INTER_SERVICE_COMMUNICATION_ERROR.getResponseCode(), ResponseMessage.INTER_SERVICE_COMMUNICATION_ERROR.getResponseMessage());
        return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ApiResponse<Void>> commonException(Exception ex) {
        errorLogger.error(ex.getLocalizedMessage(), ex);
        ApiResponse<Void> apiResponse = ResponseUtils.createApiResponse(ApiResponseCode.UNHANDLED_EXCEPTION.getResponseCode(), ResponseMessage.INTERNAL_SERVICE_EXCEPTION.getResponseMessage());
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

}
