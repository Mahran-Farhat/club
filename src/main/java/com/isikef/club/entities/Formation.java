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
public class Formation implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="FORMATION_ID")
    private Long id; // Identifiant formation (Clé primaire)

    @Column(name="FORMATION_THEME")
    private String theme; // Thème formation
    @OneToOne
    @JoinColumn(name = "FORMATION_DETAIL_ID",
            referencedColumnName = "FD_ID")
    private FormationDetail formationDetail;

    // association sans données supplémentaires
   /* @ManyToMany
    @JoinTable(
            name = "FORMATION_TP",
            joinColumns = @JoinColumn(name = "FORMATION_ID"),
            inverseJoinColumns = @JoinColumn(name = "TP_ID")
    )
    private List<TravauxPratiques> travauxPratiques;
    */

}
