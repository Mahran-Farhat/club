package com.isikef.club.controller;

import com.isikef.club.entities.Formation;
import com.isikef.club.entities.ThemeFormation;
import com.isikef.club.forms.FormFormation;
import com.isikef.club.forms.PageParams;
import com.isikef.club.query.FormationSearchQuery;
import com.isikef.club.response.CMResponse;
import com.isikef.club.service.FormationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/formations")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class FormationController {

    @Autowired
    FormationService formationService;

    @PostMapping("/create")
    public CMResponse createFormation(@Valid @ModelAttribute FormFormation form) {
        Formation f =  formationService.createFormation(form);
        return CMResponse.success(f);
    }

    @GetMapping("/{id}")
    public CMResponse getFormationById(@PathVariable Long id) {
        return CMResponse.success(formationService.getFormationById(id));
    }

    @GetMapping
    public CMResponse getAllFormations() {
        return CMResponse.success(formationService.getAllFormations());
    }

    @DeleteMapping("/{id}")
    public CMResponse deleteFormation(@PathVariable Long id) {
        formationService.deleteFormation(id);
        Map<String, Boolean> resp = new HashMap<>();
        resp.put("deleted", Boolean.TRUE);
        return CMResponse.success(resp);
    }

    @GetMapping("/theme/{theme}")
    public CMResponse findByTheme(@PathVariable ThemeFormation theme) {
        return CMResponse.success(formationService.findByTheme(theme));
    }

    @GetMapping("/search/{theme}")
    public CMResponse searchByTheme(@PathVariable String theme) {
        return CMResponse.success(formationService.searchByTheme(theme));
    }

    @GetMapping("/paginated")
    public CMResponse getFormationsWithPagination(
            @ModelAttribute PageParams pageParams) {
        return CMResponse.of(formationService.getFormationsWithPagination(pageParams));
    }

    @GetMapping("/sortedAndPaginated")
    public CMResponse getPaginatedAndSortedFormations(
            @ModelAttribute FormationSearchQuery query) {
        Page<Formation> rpage =
                formationService.getPaginatedAndSortedFormations(query);
        return CMResponse.of(rpage);
    }
}
