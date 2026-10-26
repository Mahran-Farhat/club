package com.isikef.club.service;



import com.isikef.club.entities.Formation;
import com.isikef.club.forms.FormFormation;

import java.util.List;

public interface FormationService {
    public Formation createFormation(FormFormation form);
    public Formation updateFormation(Long id, FormFormation form);
    public void deleteFormation(Long id);
    public Formation getFormationById(Long id);
    public List<Formation> getAllFormations();
}
