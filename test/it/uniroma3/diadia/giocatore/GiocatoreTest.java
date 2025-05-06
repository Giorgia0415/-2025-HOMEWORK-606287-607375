package it.uniroma3.diadia.giocatore;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.uniroma3.diadia.Partita;
import it.uniroma3.diadia.comandi.Comando;
import it.uniroma3.diadia.comandi.ComandoVai;

class GiocatoreTest {
	private Partita p;
	private Comando c;
	
	@BeforeEach
	public void setUp() {
		this.p=new Partita();
		this.c=new ComandoVai();
		this.c.setParametro("est");
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
