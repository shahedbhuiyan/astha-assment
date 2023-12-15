package com.booking.svc.services;

import com.booking.svc.domain.dtos.request.CreatingCandidateRequest;
import com.booking.svc.domain.dtos.response.CreatingCandidateResponse;
import com.booking.svc.domain.entity.Candidate;

import java.util.Optional;

public interface CandidateService {
    Optional<Candidate> getCandidateById(Long candidateId);

    CreatingCandidateResponse createNewCandidate(CreatingCandidateRequest request);
}
