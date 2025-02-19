package com.bibliotheque.dao;

import java.util.List;
import java.util.Optional;

public interface IDataAccess<T> {
    // Créer (Create)
    boolean create(T entity);

    // Lire par ID (Read)
    Optional<T> findById(Long id);

    // Lire tous (Read)
    List<T> findAll();

    // Mettre à jour (Update)
    boolean update(T entity);

    // Supprimer (Delete)
    boolean delete(Long id);
} 