package com.booking.svc.services;

import com.booking.svc.domain.dtos.request.SlotBookingRequest;
import com.booking.svc.domain.dtos.request.SlotInfoRequest;
import com.booking.svc.domain.dtos.response.SlotBookingResponse;
import com.booking.svc.domain.dtos.response.SlotInfoResponse;
import com.booking.svc.domain.dtos.response.SlotWithDateTimeResponse;
import org.springframework.data.domain.Page;

public interface SlotService {
    SlotInfoResponse createBookingSlot(SlotInfoRequest request);

    SlotBookingResponse bookASlot(Long slotId, SlotBookingRequest request);

    Page<SlotWithDateTimeResponse> getAllSlotWithDateAndTime(int pageNumber, int pageSize);

    void cancelBooking(Long slotId);

    SlotBookingResponse updateBooking(Long slotId, SlotBookingRequest request);
}
