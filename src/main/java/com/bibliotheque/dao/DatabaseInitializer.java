package com.bibliotheque.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseInitializer {
    private static final Logger LOGGER = Logger.getLogger(DatabaseInitializer.class.getName());
    private static final String DB_URL = "jdbc:sqlite:BibliotecaMaven.db";

    public static void initializeDatabase() {
        try {
            // Carregar o driver JDBC
            Class.forName("org.sqlite.JDBC");

            // Estabelecer conexão (isso criará o arquivo se não existir)
            try (Connection conn = DriverManager.getConnection(DB_URL);
                 Statement stmt = conn.createStatement()) {
                
                // Criar tabela Livres
                stmt.execute("CREATE TABLE IF NOT EXISTS livres (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "titre TEXT NOT NULL, " +
                        "auteur TEXT NOT NULL, " +
                        "annee INTEGER NOT NULL, " +
                        "isbn TEXT)");
                
                // Criar tabela Membres
                stmt.execute("CREATE TABLE IF NOT EXISTS membres (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "nom TEXT NOT NULL, " +
                        "email TEXT UNIQUE)");
                
                // Criar tabela Emprunts
                stmt.execute("CREATE TABLE IF NOT EXISTS emprunts (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "livre_id INTEGER, " +
                        "membre_id INTEGER, " +
                        "date_emprunt DATE, " +
                        "date_retour DATE, " +
                        "FOREIGN KEY(livre_id) REFERENCES livres(id), " +
                        "FOREIGN KEY(membre_id) REFERENCES membres(id))");
                
                LOGGER.info("Base de données initialisée avec succès.");
            }
        } catch (ClassNotFoundException e) {
            LOGGER.log(Level.SEVERE, "Driver SQLite non trouvé", e);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Erreur lors de l'initialisation de la base de données", e);
        }
    }

    // Méthode main pour tester la création du fichier
    public static void main(String[] args) {
        initializeDatabase();
        System.out.println("Fichier de base de données créé : BibliotecaMaven.db");
    }
} 