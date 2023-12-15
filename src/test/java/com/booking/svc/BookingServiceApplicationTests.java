package com.booking.svc;

import com.booking.svc.common.utils.DateTimeUtils;
import com.booking.svc.domain.dtos.request.SlotInfoRequest;
import com.booking.svc.domain.dtos.response.SlotInfoResponse;
import com.booking.svc.domain.entity.Slot;
import com.booking.svc.repositories.SlotRepository;
import com.booking.svc.services.impl.SlotServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class BookingServiceApplicationTests {

    @Mock
    private SlotRepository slotRepository;

    @InjectMocks
    private SlotServiceImpl slotService;

    //@Test
    void createBookingSlot_Success() {

        SlotInfoRequest request = new SlotInfoRequest();
        request.setInterviewDate("2023-12-31");
        request.setStartTime("14:00:00");
        request.setEndTime("14:30:00");

        String startDateTimeStr = "2023-12-15 09:00:00";
        String endDateTimeStr = "2023-12-15 10:00:00";
        String interviewDateStr = "2023-12-15";

        Optional<Date> startDateTimeOpt = DateTimeUtils.convertDateTime(startDateTimeStr);
        Optional<Date> endDateTimeOpt = DateTimeUtils.convertDateTime(endDateTimeStr);
        Optional<Date> interviewDateOpt = DateTimeUtils.convertDate(interviewDateStr);

        when(slotService.convertDateTime(any(), any())).thenReturn(startDateTimeOpt, endDateTimeOpt);
        when(slotService.convertDate(any())).thenReturn(interviewDateOpt);
        doNothing().when(slotService).checkIfDatesAreValid(any(), any(), any());
        doNothing().when(slotService).checkTimeSlotIsNotOverlapped(any(), any());


        Slot mockedSlot = new Slot();
        //mockedSlot.setBookingDate();
        when(slotService.prepareEntity(any(), any(), any())).thenReturn(mockedSlot);

        when(slotRepository.save(any())).thenReturn(mockedSlot);

        SlotInfoResponse result = slotService.createBookingSlot(request);

        assertNotNull(result);

        verify(slotRepository, times(1)).save(any());
    }

}
