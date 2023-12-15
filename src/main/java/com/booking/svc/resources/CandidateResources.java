package com.booking.svc.resources;

import com.booking.svc.common.utils.ResponseUtils;
import com.booking.svc.domain.common.ApiResponse;
import com.booking.svc.domain.dtos.request.CreatingCandidateRequest;
import com.booking.svc.domain.dtos.response.CreatingCandidateResponse;
import com.booking.svc.domain.enums.ResponseMessage;
import com.booking.svc.services.CandidateService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/booking-svc")
public class CandidateResources {

    private final CandidateService candidateService;

    public CandidateResources(CandidateService candidateService) {
        this.candidateService = candidateService;
    }

    @PostMapping("/candidates")
    public ApiResponse<CreatingCandidateResponse> createNewCandidate(@RequestBody CreatingCandidateRequest request) {
        CreatingCandidateResponse creatingCandidateResponse = candidateService.createNewCandidate(request);
        return ResponseUtils.createSuccessResponseObject(ResponseMessage.OPERATION_SUCCESSFUL.getResponseMessage(), creatingCandidateResponse);
    }

}
