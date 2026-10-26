package com.isikef.club.service;

import com.isikef.club.entities.Club;
import com.isikef.club.entities.Formation;
import com.isikef.club.entities.ThemeFormation;
import com.isikef.club.forms.FormFormation;
import com.isikef.club.repository.ClubRepository;
import com.isikef.club.repository.FormationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FormationServiceImpl implements FormationService{

    @Autowired
    FormationRepository formationRepository;

    @Autowired
    ClubRepository clubRepository;

    private Club getClubById(Long clubId) {
        return clubRepository.findById(clubId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Club not found with id: " + clubId));
    }

    public Formation createFormation(Club club, ThemeFormation themeFormation) {
        Formation formation = new Formation();
        formation.setTheme(themeFormation);
        formation.setClub(club);
        return formationRepository.save(formation);
    }

    @Override
    public Formation createFormation(FormFormation form) {
        Club club = getClubById(form.getClubId());
        Formation formation = createFormation(club, form.getTheme());
        return formation;
    }

    @Override
    public Formation updateFormation(Long id, FormFormation form) {
        Formation formation = getFormationById(id);
        formation.setTheme(form.getTheme());
        return formationRepository.save(formation);
    }

    @Override
    public void deleteFormation(Long id) {
        Formation formation = getFormationById(id);
        formationRepository.delete(formation);
    }

    @Override
    public Formation getFormationById(Long id) {
        return formationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Formation not found with id: " + id));
    }

    @Override
    public List<Formation> getAllFormations() {
        return formationRepository.findAll();
    }
}
