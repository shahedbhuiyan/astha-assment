package com.booking.svc.domain.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SlotInfoRequest implements Serializable {
    private String interviewDate;
    private String startTime;
    private String endTime;
}
