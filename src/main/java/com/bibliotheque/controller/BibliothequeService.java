package com.bibliotheque.controller;

import com.bibliotheque.model.Livre;
import com.bibliotheque.model.Membre;
import com.bibliotheque.model.Emprunt;
import com.bibliotheque.dao.DatabaseManager;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

public class BibliothequeService {
    private static final Logger LOGGER = Logger.getLogger(BibliothequeService.class.getName());

    // DAOs pour chaque entité
    private DatabaseManager<Livre> livreManager;
    private DatabaseManager<Membre> membreManager;
    private DatabaseManager<Emprunt> empruntManager;

    // Constructeur
    public BibliothequeService(
        DatabaseManager<Livre> livreManager,
        DatabaseManager<Membre> membreManager,
        DatabaseManager<Emprunt> empruntManager
    ) {
        this.livreManager = livreManager;
        this.membreManager = membreManager;
        this.empruntManager = empruntManager;
    }

    // Opérations sur les livres
    public boolean ajouterLivre(Livre livre) {
        return livreManager.create(livre);
    }

    public Optional<Livre> rechercherLivre(Long id) {
        return livreManager.findById(id);
    }

    public List<Livre> listerTousLivres() {
        return livreManager.findAll();
    }

    public boolean modifierLivre(Livre livre) {
        return livreManager.update(livre);
    }

    public boolean supprimerLivre(Long id) {
        return livreManager.delete(id);
    }

    // Opérations sur les membres
    public boolean ajouterMembre(Membre membre) {
        return membreManager.create(membre);
    }

    public Optional<Membre> rechercherMembre(Long id) {
        return membreManager.findById(id);
    }

    public List<Membre> listerTousMembres() {
        return membreManager.findAll();
    }

    public boolean modifierMembre(Membre membre) {
        return membreManager.update(membre);
    }

    public boolean supprimerMembre(Long id) {
        return membreManager.delete(id);
    }

    // Opérations sur les emprunts
    public boolean enregistrerEmprunt(Emprunt emprunt) {
        if (!validerEmprunt(emprunt)) {
            LOGGER.warning("Données d'emprunt invalides");
            return false;
        }
        
        // Vérifier si le livre est déjà emprunté
        List<Emprunt> empruntsActifs = listerEmpruntsActifs(emprunt.getLivreId());
        if (!empruntsActifs.isEmpty()) {
            LOGGER.warning("Le livre est déjà emprunté");
            return false;
        }

        return empruntManager.create(emprunt);
    }

    public boolean retournerLivre(Emprunt emprunt) {
        emprunt.setDateRetour(java.sql.Date.valueOf(LocalDate.now()));
        return empruntManager.update(emprunt);
    }

    public List<Emprunt> listerEmpruntsActifs(Long livreId) {
        // Méthode à implémenter dans EmpruntDatabaseManager
        // Retourne les emprunts non retournés pour un livre donné
        return null; // Placeholder
    }

    public List<Emprunt> listerEmpruntsParMembre(Membre membre) {
        // Méthode à implémenter dans EmpruntDatabaseManager
        // Retourne tous les emprunts d'un membre
        return null; // Placeholder
    }

    // Méthodes de validation
    private boolean validerEmprunt(Emprunt emprunt) {
        return emprunt.getLivreId() != null 
            && emprunt.getMembreId() != null
            && emprunt.getDateEmprunt() != null;
    }

    // Méthode de recherche avancée
    public List<Livre> rechercherLivresParAuteur(String auteur) {
        // Méthode à implémenter dans LivreDatabaseManager
        return null; // Placeholder
    }

    public boolean emprunterLivre(Livre livre, Membre membre) {
        // Implementação simplificada para teste
        return true;
    }
    
    public boolean retournerLivre(Livre livre) {
        // Implementação simplificada para teste
        return true;
    }
} 