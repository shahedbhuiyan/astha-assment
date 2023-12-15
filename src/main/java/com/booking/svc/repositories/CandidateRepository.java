package com.booking.svc.repositories;

import com.booking.svc.domain.entity.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CandidateRepository extends JpaRepository<Candidate, Long> {

    /*@Query("SELECT COUNT(c) > 0 FROM Candidate c WHERE c.email = :email OR c.phoneNo = :phoneNo")
    boolean existsByEmailOrPhone(@Param("email") String email, @Param("phoneNo") String phoneNo);*/

    boolean existsCandidateByEmailOrPhoneNo(String email, String phone);

}

