package com.isikef.club.service;

import com.isikef.club.entities.Club;
import com.isikef.club.exceptions.ResourceNotFound;
import com.isikef.club.repository.ClubRepository;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import java.util.Date;
import java.util.List;

@Service
@Validated
public class ClubServiceImpl implements IClubService{


    @Autowired
    ClubRepository clubRepository;

    @Override
    public Club createClub(@NotNull(message = "name can not be null") String name,
                           @NotNull(message = "description can not be null")
                           @Size(max = 1000, message = "description max length is 1000 characters") String description) {
        Club club = null;
        boolean exist = clubRepository.existsByNameIgnoreCase(name);
        if(!exist){
            club = new Club();
            club.setName( name);
            club.setDescription(description);
            club.setCreateDate(new Date());
            club.setUpdateDate(new Date());
            club = clubRepository.save(club);
        }
        return club;
    }

    @Override
    public Club updateClub(Long id, Club club) throws ResourceNotFound {
        Club existingClub = getClubById(id);
        existingClub.setName(club.getName());
        existingClub.setDescription(club.getDescription());
        existingClub.setUpdateDate(new Date());
        return clubRepository.save(existingClub);
    }

    @Override
    public void deleteClub(Long id) throws ResourceNotFound {
        Club club = getClubById(id);
        clubRepository.delete(club);
    }

    @Override
    public Club getClubById(Long id) throws ResourceNotFound {
        return clubRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFound("The club with id : "+id+" not found or has been deleted."));
    }

    @Override
    public List<Club> getAllClubs() {
        return clubRepository.findAll();
    }
}
