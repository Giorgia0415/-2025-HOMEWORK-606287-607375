package it.uniroma3.diadia.giocatore;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.uniroma3.diadia.IOConsole;
import it.uniroma3.diadia.Partita;
import it.uniroma3.diadia.ambienti.Direzione;
import it.uniroma3.diadia.ambienti.Labirinto;
import it.uniroma3.diadia.ambienti.Stanza;
import it.uniroma3.diadia.comandi.AbstractComando;
import it.uniroma3.diadia.comandi.ComandoVai;

class GiocatoreTest {
	private Partita p;
	private AbstractComando c;
	private Labirinto labirinto;
	
	@BeforeEach
	public void setUp() {
		Stanza s1=new Stanza("s1");
		Stanza s2=new Stanza("s2");
		labirinto=Labirinto.newBuilder()
				.addStanzaIniziale(s1)
				.addStanza(s2)
				.addAdiacenza(s1, s2, Direzione.EST)
				.addAdiacenza(s2, s1, Direzione.EST)
				.getLabirinto();
		this.p=new Partita(labirinto);
		this.c=new ComandoVai("vai", "est", new IOConsole());
	}
	
	//test metodo getCfu()
	@Test
	public void testGetCfuAllInizio() {
		assertEquals(20, p.getGiocatore().getCfu());
	}
	
	@Test
	public void testGetCfuDurantePartita() {
		c.esegui(p);
		c.esegui(p);
		assertEquals(18, p.getGiocatore().getCfu());
	}
	
	@Test
	public void testGetCfuFinePartita() {
		for(int i=0; i<20; i++) {
			c.esegui(p);
		}
		assertEquals(0, p.getGiocatore().getCfu());
	}
	
	//test metodo getBorsa()
	@Test
	public void testGetBorsa() {
		assertNotNull(p.getGiocatore().getBorsa());
	}

}
