package com.bibliotheque.dao;

import com.bibliotheque.model.Livre;
import java.util.List;

public interface ILivreDao extends IDataAccess<Livre> {
    // Méthodes spécifiques aux livres
    List<Livre> findByAuteur(String auteur);
    List<Livre> findByTitre(String titre);
    Livre findByIsbn(String isbn);
} 