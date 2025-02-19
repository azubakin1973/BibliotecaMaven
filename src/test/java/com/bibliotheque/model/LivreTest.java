package com.bibliotheque.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LivreTest {
    
    private Livre livre;
    
    @BeforeEach
    public void setUp() {
        livre = new Livre();
        livre.setId(1L);
        livre.setTitre("O Senhor dos Anéis");
        livre.setAuteur("J.R.R. Tolkien");
        livre.setAnnee("1954");
    }
    
    @Test
    public void testConstructorAndGetters() {
        assertEquals(1L, livre.getId());
        assertEquals("O Senhor dos Anéis", livre.getTitre());
        assertEquals("J.R.R. Tolkien", livre.getAuteur());
        assertEquals("1954", livre.getAnnee());
    }
    
    @Test
    public void testSetters() {
        livre.setId(2L);
        livre.setTitre("O Hobbit");
        livre.setAuteur("J.R.R. Tolkien");
        livre.setAnnee("1937");
        
        assertEquals(2L, livre.getId());
        assertEquals("O Hobbit", livre.getTitre());
        assertEquals("J.R.R. Tolkien", livre.getAuteur());
        assertEquals("1937", livre.getAnnee());
    }
    
    @Test
    public void testEquals() {
        Livre autreLivre = new Livre();
        autreLivre.setId(1L);
        autreLivre.setTitre("O Senhor dos Anéis");
        autreLivre.setAuteur("J.R.R. Tolkien");
        autreLivre.setAnnee("1954");
        
        assertTrue(livre.equals(autreLivre));
        
        Livre livreDifferent = new Livre();
        livreDifferent.setId(2L);
        livreDifferent.setTitre("O Hobbit");
        livreDifferent.setAuteur("J.R.R. Tolkien");
        livreDifferent.setAnnee("1937");
        
        assertFalse(livre.equals(livreDifferent));
    }
    
    @Test
    public void testToString() {
        String expected = "Livre{id=1, titre='O Senhor dos Anéis', auteur='J.R.R. Tolkien', annee='1954', isbn='null'}";
        assertEquals(expected, livre.toString());
    }
} 