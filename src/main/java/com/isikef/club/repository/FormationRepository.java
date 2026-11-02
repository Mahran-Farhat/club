package com.isikef.club.repository;

import com.isikef.club.entities.Formation;
import com.isikef.club.entities.ThemeFormation;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;



@Repository
public interface FormationRepository extends JpaRepository<Formation, Long> {

    @Query("SELECT f FROM Formation f WHERE LOWER(f.theme) LIKE LOWER(CONCAT('%', :theme, '%'))")
    public List<Formation> findByThemeContains(String theme);

    public List<Formation> findByTheme(ThemeFormation theme);

    @Modifying
    @Transactional
    @Query("DELETE FROM Formation f WHERE f.club.id = :clubId")
    void deleteByClubId(Long clubId);
}
