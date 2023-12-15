package com.booking.svc.repositories;

import com.booking.svc.domain.dtos.common.SlotWithDateTimeDto;
import com.booking.svc.domain.entity.Slot;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository
public interface SlotRepository extends JpaRepository<Slot, Long> {

    @Query("SELECT CASE WHEN COUNT(bt) > 0 THEN TRUE ELSE FALSE END FROM Slot bt " +
            "WHERE ((bt.startDateTime < :requestedEnd) AND (bt.endDateTime > :requestedStart))")
    Boolean countOverlappingSlots(@Param("requestedStart") Date startDateTime, @Param("requestedEnd") Date endDateTime);

    Optional<Slot> findByIdAndIsActiveTrue(Long slotId);

    @Query("SELECT new com.booking.svc.domain.dtos.common.SlotWithDateTimeDto(s.id, s.interviewDate, s.startDateTime, s.endDateTime, c.id) " +
            "FROM Slot s LEFT JOIN s.candidate c")
    Page<SlotWithDateTimeDto> getAllSlotWithDateAndTime(Pageable pageable);

    @Modifying
    @Query("UPDATE Slot s SET s.candidate = null, s.bookingDate = null WHERE s.id = :slotId")
    void cancelBooking(@Param("slotId") Long slotId);
}

