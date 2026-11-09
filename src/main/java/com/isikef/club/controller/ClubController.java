package com.isikef.club.controller;

import com.isikef.club.entities.Club;
import com.isikef.club.exceptions.ResourceNotFound;
import com.isikef.club.forms.ClubForm;
import com.isikef.club.response.CMResponse;
import com.isikef.club.service.IClubService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/clubs")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ClubController {

    @Autowired
    IClubService clubService;

    @PostMapping("/create")
    public CMResponse createClub(@Valid @RequestBody ClubForm clubForm) {
        Club club = clubService.createClub(clubForm.getName(), clubForm.getDescription());
        if (club != null)
            return CMResponse.success(club);
        else
            return CMResponse.failed("Check provided data.");
    }

    @GetMapping
    public CMResponse getClubs(){
        List<Club> clubList = clubService.getAllClubs();
        return CMResponse.success(clubList);
    }

    @GetMapping("/get")
    public CMResponse getClub(@RequestParam Long id) throws ResourceNotFound {
        Club club = clubService.getClubById(id);
        return CMResponse.success(club);
    }

    @DeleteMapping
    public CMResponse deleteClub(@RequestParam Long id) throws ResourceNotFound {
        clubService.deleteClub(id);
        Map<String, Boolean> resp = new HashMap<>();
        resp.put("deleted", Boolean.TRUE);
        return CMResponse.success(resp);
    }
}
