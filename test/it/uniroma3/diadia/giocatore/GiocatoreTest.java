package it.uniroma3.diadia.giocatore;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GiocatoreTest {
	private Giocatore g;
	
	@BeforeEach
	public void setUp() {
		g = new Giocatore();
	}
	
	//test metodo getCfu()
	@Test
	public void testGetCfuAllInizio() {
		assertEquals(20, g.getCfu());
	}
	
	@Test
	public void testGetCfuDurantePartita() {
		g.setCfu(13);
		assertEquals(13, g.getCfu());
	}
	
	@Test
	public void testGetCfuFinePartita() {
		g.setCfu(0);
		assertEquals(0, g.getCfu());
	}
	
	//test metodo getBorsa()
	@Test
	public void testGetBorsa() {
		assertNotNull(g.getBorsa());
	}

}
