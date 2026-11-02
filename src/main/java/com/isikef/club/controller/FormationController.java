package com.isikef.club.controller;

import com.isikef.club.entities.Formation;
import com.isikef.club.entities.ThemeFormation;
import com.isikef.club.forms.FormFormation;
import com.isikef.club.forms.PageParams;
import com.isikef.club.query.FormationSearchQuery;
import com.isikef.club.service.FormationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/formations")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class FormationController {

    @Autowired
    FormationService formationService;

    @PostMapping("/create")
    public Formation createFormation(@Valid @ModelAttribute FormFormation form) {
        return formationService.createFormation(form);
    }

    @GetMapping("/{id}")
    public Formation getFormationById(@PathVariable Long id) {
        return formationService.getFormationById(id);
    }

    @GetMapping
    public List<Formation> getAllFormations() {
        return formationService.getAllFormations();
    }

    @DeleteMapping("/{id}")
    public void deleteFormation(@PathVariable Long id) {
        formationService.deleteFormation(id);
    }

    @GetMapping("/theme/{theme}")
    public List<Formation> findByTheme(@PathVariable ThemeFormation theme) {
        return formationService.findByTheme(theme);
    }

    @GetMapping("/search/{theme}")
    public List<Formation> searchByTheme(@PathVariable String theme) {
        return formationService.searchByTheme(theme);
    }

    @GetMapping("/paginated")
    public ResponseEntity<?> getFormationsWithPagination(
            @ModelAttribute PageParams pageParams) {
        return ResponseEntity.ok(formationService.getFormationsWithPagination(pageParams));
    }

    @GetMapping("/sortedAndPaginated")
    public ResponseEntity<?> getPaginatedAndSortedFormations(
            @ModelAttribute FormationSearchQuery query) {
        return ResponseEntity.ok(null);
    }
}
