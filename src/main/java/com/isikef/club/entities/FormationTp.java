package com.isikef.club.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "FORMATION_TP")
@Getter
@Setter
public class FormationTp {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id; // Identifiant de l'association (Clé primaire)

    @ManyToOne
    @JoinColumn(name = "formation_id", nullable = false)
    private Formation formation; // Référence à la formation

    @ManyToOne
    @JoinColumn(name = "tp_id", nullable = false)
    private TravauxPratiques travauxPratiques; // Référence au TP

    @OneToOne
    @JoinColumn(name = "instructor_id", nullable = false)
    private Instructor instructor; // Référence à l'instructeur

    @Temporal(TemporalType.DATE)
    @Column(name = "date_tp", nullable = false)
    private Date date; // Date de l'association

}
