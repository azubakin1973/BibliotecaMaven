package com.bibliotheque.model;

import java.util.Date;

public class Emprunt {
    private Long id;
    private Long livreId;
    private Long membreId;
    private Date dateEmprunt;
    private Date dateRetour;
    
    // Campos adicionais para exibição
    private String titreLivre;
    private String nomMembre;

    // Constructeur par défaut
    public Emprunt() {}

    // Constructeur avec paramètres
    public Emprunt(Long id, Long livreId, Long membreId, Date dateEmprunt, Date dateRetour) {
        this.id = id;
        this.livreId = livreId;
        this.membreId = membreId;
        this.dateEmprunt = dateEmprunt;
        this.dateRetour = dateRetour;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Long getLivreId() { return livreId; }
    public void setLivreId(Long livreId) { this.livreId = livreId; }
    
    public Long getMembreId() { return membreId; }
    public void setMembreId(Long membreId) { this.membreId = membreId; }
    
    public Date getDateEmprunt() { return dateEmprunt; }
    public void setDateEmprunt(Date dateEmprunt) { this.dateEmprunt = dateEmprunt; }
    
    public Date getDateRetour() { return dateRetour; }
    public void setDateRetour(Date dateRetour) { this.dateRetour = dateRetour; }
    
    public String getTitreLivre() { return titreLivre; }
    public void setTitreLivre(String titreLivre) { this.titreLivre = titreLivre; }
    
    public String getNomMembre() { return nomMembre; }
    public void setNomMembre(String nomMembre) { this.nomMembre = nomMembre; }

    // Méthode toString pour faciliter l'affichage
    @Override
    public String toString() {
        return "Emprunt{" +
                "id=" + id +
                ", livreId=" + livreId +
                ", membreId=" + membreId +
                ", dateEmprunt=" + dateEmprunt +
                ", dateRetour=" + dateRetour +
                '}';
    }
} 