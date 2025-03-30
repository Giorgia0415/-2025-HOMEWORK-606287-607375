package it.uniroma3.diadia.ambienti;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LabirintoTest {
	private Labirinto l;
	
	@BeforeEach
	public void setUp() {
		l = new Labirinto();
		l.creaLabirinto();
	}
	
	//test metodo getStanzaCorrente()
	@Test
	public void testGetStanzaCorrenteIniziale() {
		Stanza s = new Stanza("Atrio");
		l.setStanzaCorrente(s);
		assertEquals("Atrio", l.getStanzaCorrente().getNome());
	}
	
	@Test
	public void testGetStanzaCorrenteQualunqueCorretta() {
		Stanza s = new Stanza("stanzaQualunque");
		l.setStanzaCorrente(s);
		assertEquals("stanzaQualunque", l.getStanzaCorrente().getNome());
	}
	
	@Test
	public void testGetStanzaCorrenteQualunqueScorretta() {
		assertNotEquals("stanzaQualunque", l.getStanzaCorrente().getNome());
	}
	
	//test metodo getStanzaVincente()
	@Test
	public void testGetStanzaVincente() {
		assertEquals("Biblioteca", l.getStanzaVincente().getNome());
	}

}
