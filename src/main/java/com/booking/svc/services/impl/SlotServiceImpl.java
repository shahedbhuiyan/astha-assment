package com.booking.svc.services.impl;

import com.booking.svc.common.logggers.BookingServiceLogger;
import com.booking.svc.common.utils.DateTimeUtils;
import com.booking.svc.domain.dtos.common.SlotWithDateTimeDto;
import com.booking.svc.domain.dtos.request.SlotBookingRequest;
import com.booking.svc.domain.dtos.request.SlotInfoRequest;
import com.booking.svc.domain.dtos.response.SlotBookingResponse;
import com.booking.svc.domain.dtos.response.SlotInfoResponse;
import com.booking.svc.domain.dtos.response.SlotWithDateTimeResponse;
import com.booking.svc.domain.entity.Candidate;
import com.booking.svc.domain.entity.Slot;
import com.booking.svc.domain.enums.ResponseMessage;
import com.booking.svc.domain.exceptions.ConcurrentBookingException;
import com.booking.svc.domain.exceptions.InvalidRequestDataException;
import com.booking.svc.domain.exceptions.RecordAlreadyExistException;
import com.booking.svc.domain.exceptions.RecordNotFoundException;
import com.booking.svc.repositories.SlotRepository;
import com.booking.svc.services.CandidateService;
import com.booking.svc.services.SlotService;
import jakarta.persistence.OptimisticLockException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Objects;
import java.util.Optional;

@Service
public class SlotServiceImpl implements SlotService {

    private final BookingServiceLogger logger;
    private final CandidateService candidateService;
    private final SlotRepository slotRepository;

    public SlotServiceImpl(BookingServiceLogger logger,
                           CandidateService candidateService,
                           SlotRepository slotRepository) {
        this.logger = logger;
        this.candidateService = candidateService;
        this.slotRepository = slotRepository;
    }

    @Transactional
    @Override
    public SlotInfoResponse createBookingSlot(SlotInfoRequest request) {
        validateRequestPayload(request);

        Optional<Date> startDateTimeOpt = convertDateTime(request.getInterviewDate(), request.getStartTime());
        Optional<Date> endDateTimeOpt = convertDateTime(request.getInterviewDate(), request.getEndTime());
        Optional<Date> interviewDateOpt = convertDate(request.getInterviewDate());

        checkIfDatesAreValid(startDateTimeOpt, endDateTimeOpt, interviewDateOpt);

        checkTimeSlotIsNotOverlapped(startDateTimeOpt.get(), endDateTimeOpt.get());

        Slot slot = prepareEntity(interviewDateOpt.get(), startDateTimeOpt.get(), endDateTimeOpt.get());

        slotRepository.save(slot);

        return createBookingSlotInfoResponse(slot);
    }

    public Slot prepareEntity(Date interviewDate, Date startDateTime, Date endDateTime) {
        Slot slot = new Slot();
        slot.setInterviewDate(interviewDate);
        slot.setStartDateTime(startDateTime);
        slot.setEndDateTime(endDateTime);
        slot.setIsActive(Boolean.TRUE);
        return slot;
    }

    private void validateRequestPayload(SlotInfoRequest request) {
        if (StringUtils.isBlank(request.getInterviewDate())
                || StringUtils.isBlank(request.getStartTime())
                || StringUtils.isBlank(request.getEndTime()))
            throw new InvalidRequestDataException(ResponseMessage.INVALID_REQUEST_PAYLOAD);
    }

    public void checkTimeSlotIsNotOverlapped(Date startDateTime, Date endDateTime) {
        Boolean hasOverlappingSlots = slotRepository.countOverlappingSlots(startDateTime, endDateTime);
        if (Boolean.TRUE.equals(hasOverlappingSlots))
            throw new RecordAlreadyExistException(ResponseMessage.RECORD_ALREADY_EXIST);
    }

    public Optional<Date> convertDateTime(String date, String time) {
        return DateTimeUtils.convertDateTime(DateTimeUtils.concatenateDateAndTimeInISOFormat(date, time));
    }

    public Optional<Date> convertDate(String date) {
        return DateTimeUtils.convertDate(date);
    }

    public void checkIfDatesAreValid(Optional<Date> startDateTimeOpt, Optional<Date> endDateTimeOpt, Optional<Date> interviewDateOpt) {
        if (startDateTimeOpt.isEmpty()
                || endDateTimeOpt.isEmpty()
                || interviewDateOpt.isEmpty())
            throw new InvalidRequestDataException(ResponseMessage.INVALID_REQUEST_PAYLOAD);
    }


    private SlotInfoResponse createBookingSlotInfoResponse(Slot slot) {
        return new SlotInfoResponse(
                slot.getId(),
                DateTimeUtils.convertDateTime(slot.getStartDateTime()),
                DateTimeUtils.convertDateTime(slot.getEndDateTime())
        );
    }

    @Transactional
    @Override
    public SlotBookingResponse bookASlot(Long slotId, SlotBookingRequest request) {

        validateRequestPayload(slotId, request);

        Slot slot = findSlotById(slotId);

        checkIfSlotIdIsAlreadyBooked(slot);

        Candidate candidate = findCandidateById(request.getCandidateId());

        bookSlot(slot, candidate);

        return new SlotBookingResponse(candidate.getId(), slot.getId());
    }

    @Transactional
    @Override
    public SlotBookingResponse updateBooking(Long slotId, SlotBookingRequest request) {
        validateRequestPayload(slotId, request);

        Slot slot = findSlotById(slotId);

        Candidate candidate = findCandidateById(request.getCandidateId());

        bookSlot(slot, candidate);

        return new SlotBookingResponse(candidate.getId(), slot.getId());
    }

    private Slot findSlotById(Long slotId) {
        return slotRepository.findByIdAndIsActiveTrue(slotId)
                .orElseThrow(() -> new RecordNotFoundException(ResponseMessage.NO_SLOT_FOUND));
    }

    private Candidate findCandidateById(Long candidateId) {
        return candidateService.getCandidateById(candidateId)
                .orElseThrow(() -> new RecordNotFoundException(ResponseMessage.NO_CANDIDATE_FOUND));
    }

    private void bookSlot(Slot slot, Candidate candidate) {
        slot.setCandidate(candidate);
        slot.setBookingDate(new Date());

        try {
            slotRepository.save(slot);
        } catch (OptimisticLockException ex) {
            logger.error(ex.getMessage(), ex);
            throw new ConcurrentBookingException(ResponseMessage.CONCURRENT_BOOKING);
        }
    }

    private void checkIfSlotIdIsAlreadyBooked(Slot slot) {
        if (Objects.nonNull(slot.getCandidate()))
            throw new RecordAlreadyExistException(ResponseMessage.SLOT_ALREADY_BOOKED);
    }

    private void validateRequestPayload(Long slotId, SlotBookingRequest request) {
        if (slotId <= 0 || request.getCandidateId() <= 0)
            throw new InvalidRequestDataException(ResponseMessage.INVALID_REQUEST_PAYLOAD);
    }

    @Override
    public Page<SlotWithDateTimeResponse> getAllSlotWithDateAndTime(int pageNumber, int pageSize) {

        if(pageSize <= 0)
            throw new InvalidRequestDataException(ResponseMessage.INVALID_REQUEST_PAYLOAD);

        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("interviewDate").ascending());
        Page<SlotWithDateTimeDto> slotList = slotRepository.getAllSlotWithDateAndTime(pageable);

        return slotList.map(this::mapDtoToResponse);
    }

    private SlotWithDateTimeResponse mapDtoToResponse(SlotWithDateTimeDto slotWithDateTimeDto) {
        SlotWithDateTimeResponse slotWithDateTimeResponse = new SlotWithDateTimeResponse();
        slotWithDateTimeResponse.setSlotId(slotWithDateTimeDto.getSlotId());
        slotWithDateTimeResponse.setInterviewDate(DateTimeUtils.convertDate(slotWithDateTimeDto.getInterviewDate()));
        slotWithDateTimeResponse.setStartDateTime(DateTimeUtils.convertDateTime(slotWithDateTimeDto.getStartDateTime()));
        slotWithDateTimeResponse.setEndDateTime(DateTimeUtils.convertDateTime(slotWithDateTimeDto.getEndDateTime()));
        slotWithDateTimeResponse.setIsSlotBooked(isSlotBooked(slotWithDateTimeDto));
        return slotWithDateTimeResponse;
    }

    private char isSlotBooked(SlotWithDateTimeDto slotWithDateTimeDto) {
        return Objects.nonNull(slotWithDateTimeDto.getCandidateId()) && slotWithDateTimeDto.getCandidateId() > 0 ? 'Y' : 'N';
    }

    @Transactional
    @Override
    public void cancelBooking(Long slotId) {
        slotRepository.cancelBooking(slotId);
    }
}
