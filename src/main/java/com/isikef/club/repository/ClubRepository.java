package com.isikef.club.repository;

import com.isikef.club.entities.Club;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClubRepository extends JpaRepository<Club, Long> {
    // assert that the name is unique
    public boolean existsByNameIgnoreCase(String name);
    // Custom query method to find clubs by name containing a specific string (case-insensitive)
    public List<Club> findByNameContainingIgnoreCase(String name);
    // exact match
    public Club findByName(String name);
    // start with
    public List<Club> findByNameStartingWithIgnoreCase(String prefix);
    // end with
    public List<Club> findByNameEndingWithIgnoreCase(String suffix);
    // find by name and description
    public List<Club> findByNameContainingIgnoreCaseAndDescriptionContainingIgnoreCase(String name, String description);
    // find by name or description
}
