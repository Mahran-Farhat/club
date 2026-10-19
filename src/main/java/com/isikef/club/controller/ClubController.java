package com.isikef.club.controller;

import com.isikef.club.entities.Club;
import com.isikef.club.exceptions.ResourceNotFound;
import com.isikef.club.forms.ClubForm;
import com.isikef.club.service.IClubService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity createClub(@Valid @RequestBody ClubForm clubForm) {
        Club club = clubService.createClub(clubForm.getName(), clubForm.getDescription());
        if (club != null)
            return ResponseEntity.ok(club);
        else
            return ResponseEntity.badRequest().body("Check provided data.");
    }

    @GetMapping
    public ResponseEntity getClubs(){
        List<Club> clubList = clubService.getAllClubs();
        return ResponseEntity.ok().body(clubList);
    }

    @GetMapping("/get")
    public ResponseEntity getClub(@RequestParam Long id) throws ResourceNotFound {
        Club club = clubService.getClubById(id);
        return ResponseEntity.ok().body(club);
    }

    @DeleteMapping
    public ResponseEntity deleteClub(@RequestParam Long id) throws ResourceNotFound {
        clubService.deleteClub(id);
        Map<String, Boolean> resp = new HashMap<>();
        resp.put("deleted",Boolean.TRUE);
        return ResponseEntity.ok().body(resp);
    }
}
