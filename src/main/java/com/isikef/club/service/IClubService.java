package com.isikef.club.service;

import com.isikef.club.entities.Club;
import com.isikef.club.exceptions.ResourceNotFound;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public interface IClubService {
    public Club createClub(@NotNull(message = "name can not be null") String name,
                           @NotNull(message = "description can not be null")
                           @Size(max = 1000, message = "description max length is 1000 characters") String description);
    public Club updateClub(Long id, Club club) throws ResourceNotFound;
    public void deleteClub(Long id) throws ResourceNotFound;
    public Club getClubById(Long id) throws ResourceNotFound;
    public List<Club> getAllClubs();
}
