package com.isikef.club.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table
@Getter
@Setter
public class TpCorrection implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TC_ID")

    private Long tcId;
    @Column(name = "TC_CORRECTION")
    private String tcCorrection;

    @ManyToOne
    @JoinColumn(name = "TP_ID")
    private TravauxPratiques travauxPratiques;
}
