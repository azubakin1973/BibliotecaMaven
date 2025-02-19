package com.bibliotheque;

import com.bibliotheque.dao.DatabaseInitializer;
import com.bibliotheque.dao.LivreManager;
import com.bibliotheque.dao.MembreManager;
import com.bibliotheque.dao.EmpruntManager;
import com.bibliotheque.view.BibliothequeGUI;

public class BibliothequeApp {
    public static void main(String[] args) {
        // Initialiser la base de données
        DatabaseInitializer.initializeDatabase();

        // Créer les gestionnaires
        LivreManager livreManager = new LivreManager();
        MembreManager membreManager = new MembreManager();
        EmpruntManager empruntManager = new EmpruntManager();

        // Lancer l'interface graphique
        javax.swing.SwingUtilities.invokeLater(() -> {
            new BibliothequeGUI(livreManager, membreManager, empruntManager).setVisible(true);
        });
    }
} 