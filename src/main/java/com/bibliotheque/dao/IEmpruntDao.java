package com.bibliotheque.dao;

import com.bibliotheque.model.Emprunt;
import com.bibliotheque.model.Livre;
import com.bibliotheque.model.Membre;
import java.time.LocalDate;
import java.util.List;

public interface IEmpruntDao extends IDataAccess<Emprunt> {
    // Méthodes spécifiques aux emprunts
    List<Emprunt> findByMembre(Membre membre);
    List<Emprunt> findByLivre(Livre livre);
    List<Emprunt> findByDateEmprunt(LocalDate date);
    List<Emprunt> findEmpruntNonRetournes();
} 