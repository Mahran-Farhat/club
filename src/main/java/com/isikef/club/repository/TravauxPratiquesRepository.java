package com.isikef.club.repository;

import com.isikef.club.entities.TravauxPratiques;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TravauxPratiquesRepository extends JpaRepository<TravauxPratiques, Long> {
}
