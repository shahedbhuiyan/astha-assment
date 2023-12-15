package com.booking.svc.resources;


import com.booking.svc.common.utils.ResponseUtils;
import com.booking.svc.domain.common.ApiResponse;
import com.booking.svc.domain.dtos.request.SlotBookingRequest;
import com.booking.svc.domain.dtos.request.SlotInfoRequest;
import com.booking.svc.domain.dtos.response.SlotBookingResponse;
import com.booking.svc.domain.dtos.response.SlotInfoResponse;
import com.booking.svc.domain.dtos.response.SlotWithDateTimeResponse;
import com.booking.svc.domain.enums.ResponseMessage;
import com.booking.svc.services.SlotService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/booking-svc")
public class SlotResources {

    private final SlotService slotService;

    public SlotResources(SlotService slotService) {
        this.slotService = slotService;
    }

    @PostMapping("/slots")
    public ApiResponse<SlotInfoResponse> createBookingSlot(@RequestBody SlotInfoRequest request) {
        SlotInfoResponse slotInfoResponse = slotService.createBookingSlot(request);
        return ResponseUtils.createSuccessResponseObject(ResponseMessage.OPERATION_SUCCESSFUL.getResponseMessage(), slotInfoResponse);
    }

    @GetMapping("/slots")
    public ApiResponse<Page<SlotWithDateTimeResponse>> getAllSlots(@RequestParam(name = "pageNo", defaultValue = "0") int pageNo,
                                                                   @RequestParam(name = "pageSize", defaultValue = "5") int pageSize) {
        Page<SlotWithDateTimeResponse> slotListResponse = slotService.getAllSlotWithDateAndTime(pageNo, pageSize);
        return ResponseUtils.createSuccessResponseObject(ResponseMessage.OPERATION_SUCCESSFUL.getResponseMessage(), slotListResponse);
    }

    @PutMapping("/slots/{slotId}")
    public ApiResponse<SlotBookingResponse> bookASlot(@PathVariable("slotId") Long slotId,
                                                      @RequestBody SlotBookingRequest request) {
        SlotBookingResponse slotBookingResponse = slotService.bookASlot(slotId, request);
        return ResponseUtils.createSuccessResponseObject(ResponseMessage.OPERATION_SUCCESSFUL.getResponseMessage(), slotBookingResponse);
    }

    @DeleteMapping("/slots/{slotId}")
    public ApiResponse<SlotBookingResponse> cancelBooking(@PathVariable("slotId") Long slotId) {
        slotService.cancelBooking(slotId);
        return ResponseUtils.createSuccessResponseObject(ResponseMessage.OPERATION_SUCCESSFUL.getResponseMessage());
    }

    @PatchMapping("/slots/{slotId}")
    public ApiResponse<SlotBookingResponse> updateBooking(@PathVariable("slotId") Long slotId,
                                                          @RequestBody SlotBookingRequest request) {
        SlotBookingResponse slotBookingResponse = slotService.updateBooking(slotId, request);
        return ResponseUtils.createSuccessResponseObject(ResponseMessage.OPERATION_SUCCESSFUL.getResponseMessage(), slotBookingResponse);
    }

}
