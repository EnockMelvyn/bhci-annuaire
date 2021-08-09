package com.example.AnnuaireBHCI.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table
@Getter
@Setter
@AllArgsConstructor
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String nom;
    private String fonction;
    private String posteTel;
    private String direction;
    private String matricule;

    public Contact() {
    }

    public Contact(String nom, String fonction, String posteTel, String direction, String matricule) {
        this.nom = nom;
        this.fonction = fonction;
        this.posteTel = posteTel;
        this.matricule = matricule;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getFonction() {
        return fonction;
    }

    public void setFonction(String fonction) {
        this.fonction = fonction;
    }

    public String getPosteTel() {
        return posteTel;
    }

    public void setPosteTel(String posteTel) {
        this.posteTel = posteTel;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }
}
