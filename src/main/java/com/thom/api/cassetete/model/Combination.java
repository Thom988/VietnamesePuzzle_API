package com.thom.api.cassetete.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="combinaison")
public class Combination {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="combinaison_id")
    private int id;
    
    @Column(name="valeur")
    private String value;
    
    @Column(name = "valide")
    private boolean valid;
    
}
