package it.uniroma3.diadia.ambienti;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.io.*;
import it.uniroma3.diadia.personaggi.*;

class CaricatoreLabirintoTest {
	
	@Test
    public void testMonolocale() throws Exception {
        String labirinto = """
            Stanze: N10
            Inizio: N10
            Vincente: N10
            Magica:
            Buia:
            Bloccata:
            Mago:
            Strega:
            Cane:
            Attrezzi: osso 1 N10
            Uscite:
            """;
        CaricatoreLabirinto c = new CaricatoreLabirinto(new StringReader(labirinto));
        c.carica();

        Stanza iniziale = c.getStanzaIniziale();
        assertNotNull(iniziale);
        assertEquals("N10", iniziale.getNome());
        assertTrue(iniziale.hasAttrezzo("osso"));
        assertEquals(c.getStanzaIniziale(), c.getStanzaVincente());
    }

    @Test
    public void testBilocaleConUscite() throws Exception {
        String labirinto = """
            Stanze: N10, Biblioteca
            Inizio: N10
            Vincente: Biblioteca
            Magica:
            Buia:
            Bloccata:
            Mago:
            Strega:
            Cane:
            Attrezzi: libro 2 Biblioteca
            Uscite: N10 nord Biblioteca
            """;
        CaricatoreLabirinto c = new CaricatoreLabirinto(new StringReader(labirinto));
        c.carica();

        Stanza n10 = c.getStanzaIniziale();
        Stanza biblioteca = c.getStanzaVincente();

        assertEquals("Biblioteca", biblioteca.getNome());
        assertEquals("N10", n10.getNome());

        assertNotNull(n10.getStanzaAdiacente(Direzione.NORD));
        assertEquals(biblioteca, n10.getStanzaAdiacente(Direzione.NORD));

        assertTrue(biblioteca.hasAttrezzo("libro"));
    }

    @Test
    public void testAttrezzoInStanzaInesistenteGeneraErrore() {
    	String labirinto = """
    		Stanze: N10
    		Inizio: N10
    		Vincente: N10
    		Magica:
            Buia:
            Bloccata:
            Mago:
            Strega:
            Cane:
    		Attrezzi: chiave 1 StanzaFalsa
    		Uscite:
    		""";

    	Exception e = assertThrows(FormatoFileNonValidoException.class, () -> {
    		new CaricatoreLabirinto(new StringReader(labirinto)).carica();
    	});

    	assertTrue(e.getMessage().contains("non collocabile"), "Errore atteso non rilevato: " + e.getMessage());
    }


    @Test
    void testUscitaConStanzaDestinazioneInesistente() {
        String labirintoNonValido = """
            Stanze: biblioteca
            Inizio: biblioteca
            Vincente: biblioteca
            Magica:
            Buia:
            Bloccata:
            Mago:
            Strega:
            Cane:
            Attrezzi:
            Uscite: biblioteca nord stanzaInesistente
            """;

        StringReader reader = new StringReader(labirintoNonValido);

        assertThrows(FormatoFileNonValidoException.class, () -> {
            CaricatoreLabirinto c = new CaricatoreLabirinto(reader);
            c.carica();
        });
    }


    @Test
    public void testStanzaInizialeNonDefinitaGeneraErrore() {
        String labirinto = """
            Stanze: A
            Inizio: B
            Vincente: A
            Magica:
            Buia:
            Bloccata:
            Mago:
            Strega:
            Cane:
            Attrezzi:
            Uscite:
            """;

        Exception e = assertThrows(FormatoFileNonValidoException.class, () -> {
            new CaricatoreLabirinto(new StringReader(labirinto)).carica();
        });

        assertTrue(e.getMessage().contains("non definita"));
    }

    @Test
    public void testStanzaVincenteNonDefinitaGeneraErrore() {
        String labirinto = """
            Stanze: A
            Inizio: A
            Vincente: B
            Magica:
            Buia:
            Bloccata:
            Mago:
            Strega:
            Cane:
            Attrezzi:
            Uscite:
            """;

        Exception e = assertThrows(FormatoFileNonValidoException.class, () -> {
            new CaricatoreLabirinto(new StringReader(labirinto)).carica();
        });

        assertTrue(e.getMessage().contains("non definita"));
    }

    @Test
    public void testFormatoPesoNonNumericoGeneraErrore() {
        String labirinto = """
            Stanze: A
            Inizio: A
            Vincente: A
            Magica:
            Buia:
            Bloccata:
            Mago:
            Strega:
            Cane:
            Attrezzi: libro xyz A
            Uscite:
            """;

        Exception e = assertThrows(FormatoFileNonValidoException.class, () -> {
            new CaricatoreLabirinto(new StringReader(labirinto)).carica();
        });

        assertTrue(e.getMessage().contains("non valido"));
    }
    
    @Test
    public void testStanzaBuia() throws Exception {
        String labirinto = """
            Stanze: Buia1 
            Inizio: Buia1
            Vincente: Buia1
            Magica:
            Buia: Buia1 lanterna
            Bloccata:
            Mago:
            Strega:
            Cane:
            Attrezzi: lanterna 1 Buia1
            Uscite:
            """;

        CaricatoreLabirinto c = new CaricatoreLabirinto(new StringReader(labirinto));
        c.carica();

        Stanza stanza = c.getStanze().get("Buia1");
        assertNotNull(stanza);
        assertTrue(stanza instanceof StanzaBuia);
        StanzaBuia buia = (StanzaBuia) stanza;
        assertEquals("lanterna", buia.getAttrezzoPerVedere());
    }

    @Test
    public void testStanzaMagica() throws Exception {
        String labirinto = """
            Stanze: Magica1
            Inizio: Magica1
            Vincente: Magica1
            Magica: Magica1
            Buia:
            Bloccata:
            Mago:
            Strega:
            Cane:
            Attrezzi:
            Uscite:
            """;

        CaricatoreLabirinto c = new CaricatoreLabirinto(new StringReader(labirinto));
        c.carica();

        Stanza stanza = c.getStanze().get("Magica1");
        assertNotNull(stanza);
        assertTrue(stanza instanceof StanzaMagica);
    }

    @Test
    public void testStanzaBloccata() throws Exception {
        String labirinto = """
            Stanze: Bloccata1 
            Inizio: Bloccata1
            Vincente: Bloccata1
            Magica:
            Buia:
            Bloccata: Bloccata1 nord chiave
            Mago:
            Strega:
            Cane:
            Attrezzi: chiave 1 Bloccata1
            Uscite:
            """;

        CaricatoreLabirinto c = new CaricatoreLabirinto(new StringReader(labirinto));
        c.carica();

        Stanza stanza = c.getStanze().get("Bloccata1");
        assertNotNull(stanza);
        assertTrue(stanza instanceof StanzaBloccata);
        StanzaBloccata bloccata = (StanzaBloccata) stanza;
        assertEquals(Direzione.NORD, bloccata.getDirezioneBloccata());
        assertEquals("chiave", bloccata.getAttrezzoPerSbloccare());
    }

    @Test
    public void testMaghiInStanza() throws Exception {
        String labirinto = """
            Stanze: Magica1
            Inizio: Magica1
            Vincente: Magica1
            Magica: Magica1
            Buia:
            Bloccata:
            Mago: Magica1 Gandalf 'Il grande mago' bacchetta
            Strega:
            Cane:
            Attrezzi:
            Uscite:
            """;

        CaricatoreLabirinto c = new CaricatoreLabirinto(new StringReader(labirinto));
        c.carica();

        Stanza stanza = c.getStanze().get("Magica1");
        assertNotNull(stanza);
        AbstractPersonaggio p = stanza.getPersonaggio();
        assertNotNull(p);
        assertTrue(p instanceof Mago);
        assertEquals("Gandalf", p.getNome());
        assertEquals("Il grande mago", p.getPresentazione());
        // Supponendo che getAttrezzo() sia un metodo di Mago
        assertEquals("bacchetta", ((Mago)p).getAttrezzo().getNome());
    }

    @Test
    public void testStregheInStanza() throws Exception {
        String labirinto = """
            Stanze: Stregata1
            Inizio: Stregata1
            Vincente: Stregata1
            Magica:
            Buia:
            Bloccata:
            Mago:
            Strega: Stregata1 Morgana 'La strega cattiva'
            Cane:
            Attrezzi:
            Uscite:
            """;

        CaricatoreLabirinto c = new CaricatoreLabirinto(new StringReader(labirinto));
        c.carica();

        Stanza stanza = c.getStanze().get("Stregata1");
        assertNotNull(stanza);
        AbstractPersonaggio p = stanza.getPersonaggio();
        assertNotNull(p);
        assertTrue(p instanceof Strega);
        assertEquals("Morgana", p.getNome());
        assertEquals("La strega cattiva", p.getPresentazione());
    }

    @Test
    public void testCaniInStanza() throws Exception {
        String labirinto = """
            Stanze: Cani1
            Inizio: Cani1
            Vincente: Cani1
            Magica:
            Buia:
            Bloccata:
            Mago:
            Strega:
            Cane: Cani1 Fido 'Il fedele cane'
            Attrezzi:
            Uscite:
            """;

        CaricatoreLabirinto c = new CaricatoreLabirinto(new StringReader(labirinto));
        c.carica();

        Stanza stanza = c.getStanze().get("Cani1");
        assertNotNull(stanza);
        AbstractPersonaggio p = stanza.getPersonaggio();
        assertNotNull(p);
        assertTrue(p instanceof Cane);
        assertEquals("Fido", p.getNome());
        assertEquals("Il fedele cane", p.getPresentazione());
    }

    @Test
    public void testErroreStanzaBuiaSenzaAttrezzo() {
        String labirinto = """
            Stanze: Buia1
            Inizio: Buia1
            Vincente: Buia1
            Magica:
            Buia: Buia1
            Bloccata:
            Mago:
            Strega:
            Cane:
            Attrezzi:
            Uscite:
            """;

        Exception e = assertThrows(FormatoFileNonValidoException.class, () -> {
            new CaricatoreLabirinto(new StringReader(labirinto)).carica();
        });

        assertTrue(e.getMessage().contains("problema nella creazione"), "Errore atteso non rilevato: " + e.getMessage());
    }

    @Test
    public void testErroreStanzaBloccataConDirezioneNonValida() {
        String labirinto = """
            Stanze: Bloccata1
            Inizio: Bloccata1
            Vincente: Bloccata1
            Magica:
            Buia:
            Bloccata: Bloccata1 invalid_direction chiave
            Mago:
            Strega:
            Cane:
            Attrezzi:
            Uscite:
            """;

        Exception e = assertThrows(IllegalArgumentException.class, () -> {
            new CaricatoreLabirinto(new StringReader(labirinto)).carica();
        });

        // Qui IllegalArgumentException è dovuto a Direzione.valueOf
    }

}
