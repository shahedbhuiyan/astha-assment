package com.booking.svc.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "TIME_SLOTS")
public class Slot extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CANDIDATE_ID", columnDefinition = "BIGINT")
    private Candidate candidate;

    @Column(name = "BOOKING_DATE", columnDefinition = "DATE")
    private Date bookingDate;

    @Column(name = "INTERVIEW_DATE", columnDefinition = "DATE")
    private Date interviewDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "START_DATE_TIME", columnDefinition = "TIMESTAMP")
    private Date startDateTime;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "END_DATE_TIME", columnDefinition = "TIMESTAMP")
    private Date endDateTime;

    @Column(name = "IS_ACTIVE", columnDefinition = "boolean default true")
    private Boolean isActive;

}

