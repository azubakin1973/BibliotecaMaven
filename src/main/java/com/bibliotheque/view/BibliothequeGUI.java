package com.bibliotheque.view;

import com.bibliotheque.dao.LivreManager;
import com.bibliotheque.dao.MembreManager;
import com.bibliotheque.dao.EmpruntManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class BibliothequeGUI extends JFrame {
    private JTable livresTable;
    private JTable membresTable;
    private JTable empruntsTable;
    private DefaultTableModel livresTableModel;
    private DefaultTableModel membresTableModel;
    private DefaultTableModel empruntsTableModel;
    private JTextField titreField;
    private JTextField auteurField;
    private JTextField anneeField;
    private LivreManager livreManager;
    private MembreManager membreManager;
    private EmpruntManager empruntManager;
    private JTabbedPane tabbedPane;
    private JComboBox<ComboItem> livreComboBox;
    private JComboBox<ComboItem> membreComboBox;

    public BibliothequeGUI(LivreManager livreManager, MembreManager membreManager, EmpruntManager empruntManager) {
        this.livreManager = livreManager;
        this.membreManager = membreManager;
        this.empruntManager = empruntManager;
        initComponents();
    }

    private void initComponents() {
        // Initialiser le gestionnaire de base de données
        livreManager.initializeDatabase();

        // Configuration de la fenêtre principale
        setTitle("Bibliothèque");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Création de la barre de menu
        JMenuBar menuBar = new JMenuBar();
        
        // Menu Livres
        JMenu livresMenu = new JMenu("Livres");
        JMenuItem listerLivresItem = new JMenuItem("Lister Livres");
        listerLivresItem.addActionListener(e -> chargerLivres());
        JMenuItem ajouterLivreItem = new JMenuItem("Ajouter Livre");
        livresMenu.add(listerLivresItem);
        livresMenu.add(ajouterLivreItem);
        
        // Menu Membres
        JMenu membresMenu = new JMenu("Membres");
        JMenuItem listerMembresItem = new JMenuItem("Lister Membres");
        JMenuItem ajouterMembreItem = new JMenuItem("Ajouter Membre");
        membresMenu.add(listerMembresItem);
        membresMenu.add(ajouterMembreItem);
        
        // Menu Emprunts
        JMenu empruntsMenu = new JMenu("Emprunts");
        JMenuItem listerEmpruntsItem = new JMenuItem("Lister Emprunts");
        JMenuItem ajouterEmpruntItem = new JMenuItem("Ajouter Emprunt");
        empruntsMenu.add(listerEmpruntsItem);
        empruntsMenu.add(ajouterEmpruntItem);
        
        // Ajout des menus à la barre de menu
        menuBar.add(livresMenu);
        menuBar.add(membresMenu);
        menuBar.add(empruntsMenu);
        
        setJMenuBar(menuBar);

        // Création du panneau principal
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Criar TabbedPane
        tabbedPane = new JTabbedPane();
        
        // Painel de Livros
        JPanel livresPanel = new JPanel(new BorderLayout());
        String[] livresColonnes = {"ID", "Titre", "Auteur", "Année"};
        livresTableModel = new DefaultTableModel(livresColonnes, 0);
        livresTable = new JTable(livresTableModel);
        livresPanel.add(new JScrollPane(livresTable), BorderLayout.CENTER);
        livresPanel.add(creerFormulaireLivres(), BorderLayout.SOUTH);
        
        // Painel de Membros
        JPanel membresPanel = new JPanel(new BorderLayout());
        String[] membresColonnes = {"ID", "Nom", "Email"};
        membresTableModel = new DefaultTableModel(membresColonnes, 0);
        membresTable = new JTable(membresTableModel);
        membresPanel.add(new JScrollPane(membresTable), BorderLayout.CENTER);
        membresPanel.add(creerFormulaireMembres(), BorderLayout.SOUTH);
        
        // Painel de Emprunts
        JPanel empruntsPanel = new JPanel(new BorderLayout());
        String[] empruntsColonnes = {"ID", "Livre", "Membre", "Date Emprunt", "Date Retour"};
        empruntsTableModel = new DefaultTableModel(empruntsColonnes, 0);
        empruntsTable = new JTable(empruntsTableModel);
        empruntsPanel.add(new JScrollPane(empruntsTable), BorderLayout.CENTER);
        empruntsPanel.add(creerFormulaireEmprunts(), BorderLayout.SOUTH);
        
        // Adicionar painéis ao TabbedPane
        tabbedPane.addTab("Livres", livresPanel);
        tabbedPane.addTab("Membres", membresPanel);
        tabbedPane.addTab("Emprunts", empruntsPanel);
        
        // Adicionar TabbedPane ao painel principal
        mainPanel.add(tabbedPane, BorderLayout.CENTER);

        // Ajout du panneau principal à la fenêtre
        add(mainPanel);

        // Carregar dados iniciais
        chargerLivres();
        chargerMembres();
        chargerEmprunts();
    }

    private JPanel creerFormulaireLivres() {
        JPanel panel = new JPanel(new GridLayout(4, 2));
        panel.setBorder(BorderFactory.createTitledBorder("Ajouter un Livre"));

        panel.add(new JLabel("Titre:"));
        titreField = new JTextField();
        panel.add(titreField);

        panel.add(new JLabel("Auteur:"));
        auteurField = new JTextField();
        panel.add(auteurField);

        panel.add(new JLabel("Année:"));
        anneeField = new JTextField();
        panel.add(anneeField);

        JButton ajouterButton = new JButton("Ajouter");
        ajouterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ajouterLivre();
            }
        });
        panel.add(ajouterButton);

        return panel;
    }

    private void ajouterLivre() {
        String titre = titreField.getText();
        String auteur = auteurField.getText();
        String annee = anneeField.getText();

        if (!titre.isEmpty() && !auteur.isEmpty() && !annee.isEmpty()) {
            // Ajouter le livre à la base de données
            boolean succes = livreManager.ajouterLivre(titre, auteur, annee);
            
            if (succes) {
                // Actualiser le tableau
                chargerLivres();

                // Effacer les champs après ajout
                titreField.setText("");
                auteurField.setText("");
                anneeField.setText("");

                JOptionPane.showMessageDialog(this, "Livre ajouté avec succès!");
            } else {
                JOptionPane.showMessageDialog(this, "Erreur lors de l'ajout du livre", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void chargerLivres() {
        // Effacer les lignes existantes
        livresTableModel.setRowCount(0);
        
        // Récupérer la liste des livres depuis la base de données
        List<Object[]> livres = livreManager.listerLivres();
        
        // Ajouter chaque livre au modèle
        for (Object[] livre : livres) {
            livresTableModel.addRow(livre);
        }
    }

    private JPanel creerFormulaireMembres() {
        JPanel panel = new JPanel(new GridLayout(3, 2));
        panel.setBorder(BorderFactory.createTitledBorder("Ajouter un Membre"));

        JTextField nomField = new JTextField();
        JTextField emailField = new JTextField();

        panel.add(new JLabel("Nom:"));
        panel.add(nomField);
        panel.add(new JLabel("Email:"));
        panel.add(emailField);

        JButton ajouterButton = new JButton("Ajouter");
        ajouterButton.addActionListener(e -> {
            String nom = nomField.getText();
            String email = emailField.getText();
            
            if (!nom.isEmpty() && !email.isEmpty()) {
                if (membreManager.ajouterMembre(nom, email)) {
                    chargerMembres();
                    nomField.setText("");
                    emailField.setText("");
                    JOptionPane.showMessageDialog(this, "Membre ajouté avec succès!");
                } else {
                    JOptionPane.showMessageDialog(this, "Erreur lors de l'ajout du membre", 
                        "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs", 
                    "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });

        panel.add(ajouterButton);
        return panel;
    }

    private void chargerMembres() {
        membresTableModel.setRowCount(0);
        List<Object[]> membres = membreManager.listerMembres();
        for (Object[] membre : membres) {
            membresTableModel.addRow(membre);
        }
    }

    private JPanel creerFormulaireEmprunts() {
        JPanel panel = new JPanel(new GridLayout(4, 2));
        panel.setBorder(BorderFactory.createTitledBorder("Ajouter un Emprunt"));

        // ComboBox para Livros
        panel.add(new JLabel("Livre:"));
        livreComboBox = new JComboBox<>();
        panel.add(livreComboBox);

        // ComboBox para Membros
        panel.add(new JLabel("Membre:"));
        membreComboBox = new JComboBox<>();
        panel.add(membreComboBox);

        // Botões
        JButton ajouterButton = new JButton("Emprunter");
        JButton retournerButton = new JButton("Retourner");

        panel.add(ajouterButton);
        panel.add(retournerButton);

        // Atualizar ComboBoxes
        updateComboBoxes();

        // Action Listeners
        ajouterButton.addActionListener(e -> ajouterEmprunt());
        retournerButton.addActionListener(e -> retournerLivre());

        return panel;
    }

    private void updateComboBoxes() {
        // Atualizar ComboBox de Livros
        livreComboBox.removeAllItems();
        List<Object[]> livres = livreManager.listerLivres();
        for (Object[] livre : livres) {
            livreComboBox.addItem(new ComboItem((Long)livre[0], (String)livre[1]));
        }

        // Atualizar ComboBox de Membros
        membreComboBox.removeAllItems();
        List<Object[]> membres = membreManager.listerMembres();
        for (Object[] membre : membres) {
            membreComboBox.addItem(new ComboItem((Long)membre[0], (String)membre[1]));
        }
    }

    private void ajouterEmprunt() {
        ComboItem livre = (ComboItem)livreComboBox.getSelectedItem();
        ComboItem membre = (ComboItem)membreComboBox.getSelectedItem();

        if (livre != null && membre != null) {
            boolean succes = empruntManager.ajouterEmprunt(
                livre.getId(),
                membre.getId(),
                new java.sql.Date(new java.util.Date().getTime())
            );

            if (succes) {
                chargerEmprunts();
                JOptionPane.showMessageDialog(this, "Emprunt ajouté avec succès!");
            } else {
                JOptionPane.showMessageDialog(this, "Erreur lors de l'ajout de l'emprunt",
                    "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un livre et un membre",
                "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void retournerLivre() {
        int selectedRow = empruntsTable.getSelectedRow();
        if (selectedRow >= 0) {
            Long empruntId = (Long)empruntsTable.getValueAt(selectedRow, 0);
            if (empruntManager.retournerLivre(empruntId)) {
                chargerEmprunts();
                JOptionPane.showMessageDialog(this, "Livre retourné avec succès!");
            } else {
                JOptionPane.showMessageDialog(this, "Erreur lors du retour du livre",
                    "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un emprunt",
                "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void chargerEmprunts() {
        empruntsTableModel.setRowCount(0);
        List<Object[]> emprunts = empruntManager.listerEmprunts();
        for (Object[] emprunt : emprunts) {
            empruntsTableModel.addRow(emprunt);
        }
    }

    // Classe auxiliar para os ComboBoxes
    private class ComboItem {
        private Long id;
        private String label;

        public ComboItem(Long id, String label) {
            this.id = id;
            this.label = label;
        }

        public Long getId() { return id; }

        @Override
        public String toString() {
            return label;
        }
    }

    public static void main(String[] args) {
        // Exécuter l'interface graphique sur le thread de dispatch d'événements
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new BibliothequeGUI(new LivreManager(), new MembreManager(), new EmpruntManager()).setVisible(true);
            }
        });
    }
} 