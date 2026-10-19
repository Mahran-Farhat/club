package com.isikef.club.repository;

import com.isikef.club.entities.Formation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FormationRepository extends JpaRepository<Formation, Long> {

    public List<Formation> findByThemeContainsIgnoreCase(String theme);
}
