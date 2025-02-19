package com.bibliotheque.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class DatabaseManager<T> implements IDataAccess<T> {
    protected static final Logger LOGGER = Logger.getLogger(DatabaseManager.class.getName());

    // Configuration de la base de données
    private static final String DB_URL = "jdbc:sqlite:BibliotecaMaven.db";

    // Bloc statique pour initialiser la connexion
    static {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            LOGGER.log(Level.SEVERE, "Driver SQLite non trouvé", e);
        }
        
        // Initialisation des tables
        initDatabase();
    }

    // Méthode privée pour initialiser la base de données
    private static void initDatabase() {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {
            
            // Création de la table Livre
            stmt.execute("CREATE TABLE IF NOT EXISTS livres (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "titre TEXT NOT NULL, " +
                    "auteur TEXT NOT NULL, " +
                    "annee TEXT NOT NULL)");
            
            // Création de la table Membre
            stmt.execute("CREATE TABLE IF NOT EXISTS membres (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "nom TEXT NOT NULL, " +
                    "email TEXT UNIQUE)");
            
            // Création de la table Emprunt
            stmt.execute("CREATE TABLE IF NOT EXISTS emprunts (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "livre_id INTEGER, " +
                    "membre_id INTEGER, " +
                    "date_emprunt DATE, " +
                    "date_retour DATE, " +
                    "FOREIGN KEY(livre_id) REFERENCES livres(id), " +
                    "FOREIGN KEY(membre_id) REFERENCES membres(id))");
            
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Erreur lors de l'initialisation de la base de données", e);
        }
    }

    // Méthode utilitaire pour obtenir une connexion
    protected Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }

    // Méthodes abstraites à implémenter par les sous-classes
    protected abstract String getTableName();
    protected abstract T mapResultSetToObject(ResultSet rs) throws SQLException;
    protected abstract void prepareStatement(PreparedStatement pstmt, T entity) throws SQLException;

    @Override
    public boolean create(T entity) {
        String query = "INSERT INTO " + getTableName() + " (titre, auteur, annee, isbn) VALUES (?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            prepareStatement(pstmt, entity);
            return pstmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Erreur lors de la création", e);
            return false;
        }
    }

    @Override
    public Optional<T> findById(Long id) {
        String query = "SELECT * FROM " + getTableName() + " WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setLong(1, id);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return Optional.of(mapResultSetToObject(rs));
            }
            
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Erreur lors de la recherche par ID", e);
        }
        return Optional.empty();
    }

    @Override
    public List<T> findAll() {
        List<T> list = new ArrayList<>();
        String query = "SELECT * FROM " + getTableName();
        
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            while (rs.next()) {
                list.add(mapResultSetToObject(rs));
            }
            
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Erreur lors de la récupération de tous les éléments", e);
        }
        
        return list;
    }

    @Override
    public boolean update(T entity) {
        String query = "UPDATE " + getTableName() + " SET ... WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            prepareStatement(pstmt, entity);
            return pstmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Erreur lors de la mise à jour", e);
            return false;
        }
    }

    @Override
    public boolean delete(Long id) {
        String query = "DELETE FROM " + getTableName() + " WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setLong(1, id);
            return pstmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Erreur lors de la suppression", e);
            return false;
        }
    }

    // Fermeture propre des ressources
    protected void closeResources(Connection conn, PreparedStatement pstmt, ResultSet rs) {
        try {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Erreur lors de la fermeture des ressources", e);
        }
    }
} 