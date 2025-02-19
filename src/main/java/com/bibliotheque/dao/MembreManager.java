package com.bibliotheque.dao;

import com.bibliotheque.model.Membre;
import java.sql.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.logging.Level;

public class MembreManager extends DatabaseManager<Membre> {
    @Override
    protected String getTableName() {
        return "membres";
    }

    @Override
    protected Membre mapResultSetToObject(ResultSet rs) throws SQLException {
        return new Membre(
            rs.getLong("id"),
            rs.getString("nom"),
            rs.getString("email")
        );
    }

    @Override
    protected void prepareStatement(PreparedStatement pstmt, Membre membre) throws SQLException {
        pstmt.setString(1, membre.getNom());
        pstmt.setString(2, membre.getEmail());
    }

    public boolean ajouterMembre(String nom, String email) {
        Membre membre = new Membre(null, nom, email);
        return create(membre);
    }

    public List<Object[]> listerMembres() {
        List<Membre> membres = findAll();
        return membres.stream()
            .map(membre -> new Object[]{
                membre.getId(),
                membre.getNom(),
                membre.getEmail()
            })
            .collect(Collectors.toList());
    }

    @Override
    public boolean create(Membre membre) {
        String query = "INSERT INTO " + getTableName() + " (nom, email) VALUES (?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            prepareStatement(pstmt, membre);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Erreur lors de la création du membre", e);
            return false;
        }
    }
} 