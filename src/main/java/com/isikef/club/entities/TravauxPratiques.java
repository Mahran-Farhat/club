package com.isikef.club.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Entity
@Table
@Getter
@Setter
public class TravauxPratiques implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="TP_ID")
    private Long tpId;

    @Column(name="TP_SUJET")
    private String sujet;

    @Column(name="TP_DUREE")
    private Long tpDuree;

    @OneToMany(mappedBy = "travauxPratiques")
    private List<TpCorrection> tpCorrections;

    // sens complémentaire de l'association ManyToMany avec Formation
   /* @ManyToMany(mappedBy = "travauxPratiques")
    private List<Formation> formations;
    */

}
