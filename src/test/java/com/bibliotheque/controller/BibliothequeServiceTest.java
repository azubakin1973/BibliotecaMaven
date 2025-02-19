package com.bibliotheque.controller;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import com.bibliotheque.model.Livre;

public class BibliothequeServiceTest {
    
    @Test
    public void testLivre() {
        Livre livre = new Livre();
        livre.setId(1L);
        livre.setTitre("O Senhor dos Anéis");
        livre.setAuteur("J.R.R. Tolkien");
        livre.setAnnee("1954");
        
        assertEquals(1L, livre.getId());
        assertEquals("O Senhor dos Anéis", livre.getTitre());
        assertEquals("J.R.R. Tolkien", livre.getAuteur());
        assertEquals("1954", livre.getAnnee());
    }
    
    @Test
    public void testLivreEquals() {
        Livre livre1 = new Livre();
        livre1.setId(1L);
        livre1.setTitre("O Senhor dos Anéis");
        
        Livre livre2 = new Livre();
        livre2.setId(1L);
        livre2.setTitre("O Senhor dos Anéis");
        
        assertEquals(livre1, livre2);
    }
    
    @Test
    public void testLivreToString() {
        Livre livre = new Livre();
        livre.setId(1L);
        livre.setTitre("O Senhor dos Anéis");
        livre.setAuteur("J.R.R. Tolkien");
        livre.setAnnee("1954");
        
        String expected = "Livre{id=1, titre='O Senhor dos Anéis', auteur='J.R.R. Tolkien', annee='1954', isbn='null'}";
        assertEquals(expected, livre.toString());
    }
} 