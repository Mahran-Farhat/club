package com.isikef.club.repository;

import com.isikef.club.entities.TpCorrection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TpCorrectionRepository extends JpaRepository<TpCorrection, Long> {
}
