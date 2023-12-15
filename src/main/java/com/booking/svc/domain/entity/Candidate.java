package com.booking.svc.domain.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "CANDIDATES")
public class Candidate extends BaseEntity {

    @Column(name = "NAME", columnDefinition = "varchar(50)")
    private String name;

    @Column(name = "EMAIL", columnDefinition = "varchar(100)")
    private String email;

    @Column(name = "PHONE_NO", columnDefinition = "varchar(15)")
    private String phoneNo;

    @Column(name = "ADDRESS", columnDefinition = "varchar(250)")
    private String address;

    @OneToMany(
            mappedBy = "candidate",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Slot> slots = new ArrayList<>();

}

