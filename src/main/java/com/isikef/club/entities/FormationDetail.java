package com.isikef.club.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table
@Getter
@Setter
public class FormationDetail implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="FD_ID")
    private Long fdId;

    @Column(name="FD_TABLE_MATIERE")
    private String fdTableMatiere;

    @Column(name="FD_SUPPORT_PDF")
    private String fdSupportPDF;

    @OneToOne(mappedBy = "formationDetail")
    private Formation formation;
}
