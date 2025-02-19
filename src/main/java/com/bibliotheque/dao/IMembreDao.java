package com.bibliotheque.dao;

import com.bibliotheque.model.Membre;
import java.util.List;

public interface IMembreDao extends IDataAccess<Membre> {
    // Méthodes spécifiques aux membres
    Membre findByEmail(String email);
    List<Membre> findByNom(String nom);
} 