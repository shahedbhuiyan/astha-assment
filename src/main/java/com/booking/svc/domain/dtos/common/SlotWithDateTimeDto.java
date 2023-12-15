package com.booking.svc.domain.dtos.common;

import com.booking.svc.domain.entity.Candidate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SlotWithDateTimeDto implements Serializable {
    private Long slotId;
    private Date interviewDate;
    private Date startDateTime;
    private Date endDateTime;
    private Long candidateId;
}
