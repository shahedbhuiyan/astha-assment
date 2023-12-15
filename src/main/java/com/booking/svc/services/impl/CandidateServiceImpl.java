package com.booking.svc.services.impl;

import com.booking.svc.domain.dtos.request.CreatingCandidateRequest;
import com.booking.svc.domain.dtos.response.CreatingCandidateResponse;
import com.booking.svc.domain.entity.Candidate;
import com.booking.svc.domain.enums.ResponseMessage;
import com.booking.svc.domain.exceptions.InvalidRequestDataException;
import com.booking.svc.domain.exceptions.RecordAlreadyExistException;
import com.booking.svc.repositories.CandidateRepository;
import com.booking.svc.services.CandidateService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class CandidateServiceImpl implements CandidateService {

    private final CandidateRepository candidateRepository;

    public CandidateServiceImpl(CandidateRepository candidateRepository) {
        this.candidateRepository = candidateRepository;
    }

    @Override
    public Optional<Candidate> getCandidateById(Long candidateId) {
        return candidateRepository.findById(candidateId);
    }

    @Transactional
    @Override
    public CreatingCandidateResponse createNewCandidate(CreatingCandidateRequest request) {

        validateRequestPayload(request);

        checkIfCandidateIsExistOrNot(request);

        Candidate candidateEntity = prepareCandidateEntity(request);

        candidateRepository.save(candidateEntity);

        return new CreatingCandidateResponse(candidateEntity.getId(), candidateEntity.getName(), candidateEntity.getPhoneNo());
    }

    private void checkIfCandidateIsExistOrNot(CreatingCandidateRequest request) {
        if(candidateRepository.existsCandidateByEmailOrPhoneNo(request.getEmail(), request.getPhoneNo()))
            throw new RecordAlreadyExistException(ResponseMessage.CANDIDATE_ALREADY_IN_DB);
    }

    private Candidate prepareCandidateEntity(CreatingCandidateRequest request) {
        Candidate candidate = new Candidate();
        candidate.setName(request.getName());
        candidate.setEmail(request.getEmail());
        candidate.setAddress(request.getAddress());
        candidate.setPhoneNo(request.getPhoneNo());
        candidate.setCreatedDate(new Date());
        return candidate;
    }

    private void validateRequestPayload(CreatingCandidateRequest request) {
        if (StringUtils.isBlank(request.getName())
                || StringUtils.isBlank(request.getPhoneNo())
                || StringUtils.isBlank(request.getEmail())
                || StringUtils.isBlank(request.getAddress()))
            throw new InvalidRequestDataException(ResponseMessage.INVALID_REQUEST_PAYLOAD);

        if (isContentNotValid(request.getEmail(), "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"))
            throw new InvalidRequestDataException(ResponseMessage.EMAIL_ADDRESS_NOT_VALID);

        if (isContentNotValid(request.getPhoneNo(), "^(?:\\+?88)?01[3-9]\\d{8}$"))
            throw new InvalidRequestDataException(ResponseMessage.EMAIL_ADDRESS_NOT_VALID);
    }

    public static boolean isContentValid(String email, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static boolean isContentNotValid(String email, String regex){
        return !isContentValid(email, regex);
    }
}
