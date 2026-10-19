package com.isikef.club.repository;

import com.isikef.club.entities.Formation;
import com.isikef.club.entities.FormationTp;
import com.isikef.club.entities.Instructor;
import com.isikef.club.entities.TravauxPratiques;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FormationTpRepository extends JpaRepository<FormationTp, Long> {

    public List<FormationTp> findByFormationId(Long formationId);

    public List<FormationTp> findByFormation(Formation formation);

    public List<FormationTp> findByTravauxPratiquesId(Long tpId);

    public List<FormationTp> findByTravauxPratiques(TravauxPratiques travauxPratiques);

    public List<FormationTp> findByInstructorId(Long instructorId);

    public List<FormationTp> findByInstructor(Instructor instructor);


}
