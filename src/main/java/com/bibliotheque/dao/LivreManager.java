package com.bibliotheque.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.stream.Collectors;

import com.bibliotheque.model.Livre;

public class LivreManager extends DatabaseManager<Livre> {
    @Override
    protected String getTableName() {
        return "livres";
    }

    @Override
    protected Livre mapResultSetToObject(ResultSet rs) throws SQLException {
        return new Livre(
            rs.getLong("id"),
            rs.getString("titre"),
            rs.getString("auteur"),
            rs.getString("annee")
        );
    }

    @Override
    protected void prepareStatement(PreparedStatement pstmt, Livre livre) throws SQLException {
        pstmt.setString(1, livre.getTitre());
        pstmt.setString(2, livre.getAuteur());
        pstmt.setString(3, livre.getAnnee());
        pstmt.setString(4, livre.getIsbn());
    }

    public void initializeDatabase() {
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            
            stmt.execute("CREATE TABLE IF NOT EXISTS livres (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "titre TEXT NOT NULL, " +
                    "auteur TEXT NOT NULL, " +
                    "annee INTEGER NOT NULL, " +
                    "isbn TEXT)");
            
        } catch (SQLException e) {
            // Log or handle exception
            e.printStackTrace();
        }
    }

    // Additional method to support GUI operations
    public boolean ajouterLivre(String titre, String auteur, String annee) {
        Livre livre = new Livre(null, titre, auteur, annee);
        return create(livre);
    }

    public List<Object[]> listerLivres() {
        List<Livre> livres = findAll();
        return livres.stream()
            .map(livre -> new Object[]{
                livre.getId(), 
                livre.getTitre(), 
                livre.getAuteur(), 
                livre.getAnnee()
            })
            .collect(Collectors.toList());
    }
} 