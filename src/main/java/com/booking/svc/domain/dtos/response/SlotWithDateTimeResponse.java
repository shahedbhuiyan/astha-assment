package com.booking.svc.domain.dtos.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SlotWithDateTimeResponse implements Serializable {
    private Long slotId;
    private String interviewDate;
    private String startDateTime;
    private String endDateTime;
    private Character isSlotBooked;
}
