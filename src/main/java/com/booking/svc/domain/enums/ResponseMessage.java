package com.booking.svc.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ResponseMessage {

    OPERATION_SUCCESSFUL("Operation went smoothly."),
    RECORD_NOT_FOUND("Record is not found."),
    INTER_SERVICE_COMMUNICATION_ERROR("Inter service communication error."),
    INTERNAL_SERVICE_EXCEPTION("Internal service exception"),
    DATABASE_EXCEPTION("Database exception"),
    INVALID_REQUEST_PAYLOAD("Requested payload is not ok."),
    RECORD_ALREADY_EXIST("Record is already in store."),
    SLOT_ALREADY_BOOKED("Your requested slot is already booked."),
    CONCURRENT_BOOKING("Some is already booking the slot."),
    NO_CANDIDATE_FOUND("There is no candidate with the give ID."),
    NO_SLOT_FOUND("There is no Slot with the given ID."),
    EMAIL_ADDRESS_NOT_VALID("Please provide valid email address."),
    CANDIDATE_ALREADY_IN_DB("This candidate is already in the system.");

    private String responseMessage;
}
