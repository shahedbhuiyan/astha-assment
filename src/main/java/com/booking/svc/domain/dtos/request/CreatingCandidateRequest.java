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
public class CreatingCandidateRequest implements Serializable {
    private String name;
    private String email;
    private String phoneNo;
    private String address;
}
