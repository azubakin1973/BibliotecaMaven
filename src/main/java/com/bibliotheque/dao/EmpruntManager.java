package com.bibliotheque.dao;

import com.bibliotheque.model.Emprunt;
import java.sql.*;
import java.util.List;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EmpruntManager extends DatabaseManager<Emprunt> {
    private static final Logger LOGGER = Logger.getLogger(EmpruntManager.class.getName());

    @Override
    protected String getTableName() {
        return "emprunts";
    }

    @Override
    protected Emprunt mapResultSetToObject(ResultSet rs) throws SQLException {
        Emprunt emprunt = new Emprunt(
            rs.getLong("id"),
            rs.getLong("livre_id"),
            rs.getLong("membre_id"),
            rs.getDate("date_emprunt"),
            rs.getDate("date_retour")
        );
        
        // Adicionar informações do livro e membro se disponíveis
        try {
            emprunt.setTitreLivre(rs.getString("titre"));
            emprunt.setNomMembre(rs.getString("nom"));
        } catch (SQLException e) {
            // Campos podem não estar disponíveis em todas as consultas
        }
        
        return emprunt;
    }

    @Override
    protected void prepareStatement(PreparedStatement pstmt, Emprunt emprunt) throws SQLException {
        pstmt.setLong(1, emprunt.getLivreId());
        pstmt.setLong(2, emprunt.getMembreId());
        pstmt.setDate(3, new java.sql.Date(emprunt.getDateEmprunt().getTime()));
        if (emprunt.getDateRetour() != null) {
            pstmt.setDate(4, new java.sql.Date(emprunt.getDateRetour().getTime()));
        } else {
            pstmt.setNull(4, Types.DATE);
        }
    }

    @Override
    public boolean create(Emprunt emprunt) {
        String query = "INSERT INTO " + getTableName() + 
                      " (livre_id, membre_id, date_emprunt, date_retour) VALUES (?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            prepareStatement(pstmt, emprunt);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Erreur lors de la création de l'emprunt", e);
            return false;
        }
    }

    public List<Object[]> listerEmprunts() {
        List<Object[]> emprunts = new ArrayList<>();
        String query = "SELECT e.*, l.titre, m.nom FROM emprunts e " +
                      "JOIN livres l ON e.livre_id = l.id " +
                      "JOIN membres m ON e.membre_id = m.id";
        
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            while (rs.next()) {
                Object[] emprunt = {
                    rs.getLong("id"),
                    rs.getString("titre"),
                    rs.getString("nom"),
                    rs.getDate("date_emprunt"),
                    rs.getDate("date_retour")
                };
                emprunts.add(emprunt);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Erreur lors de la récupération des emprunts", e);
        }
        return emprunts;
    }

    public boolean ajouterEmprunt(Long livreId, Long membreId, Date dateEmprunt) {
        Emprunt emprunt = new Emprunt(null, livreId, membreId, dateEmprunt, null);
        return create(emprunt);
    }

    public boolean retournerLivre(Long empruntId) {
        String query = "UPDATE emprunts SET date_retour = ? WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setDate(1, new java.sql.Date(new java.util.Date().getTime()));
            pstmt.setLong(2, empruntId);
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Erreur lors du retour du livre", e);
            return false;
        }
    }
} 