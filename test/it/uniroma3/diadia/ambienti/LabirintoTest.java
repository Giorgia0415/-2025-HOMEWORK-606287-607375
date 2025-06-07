package it.uniroma3.diadia.ambienti;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LabirintoTest {
	private Labirinto l;
	
	@BeforeEach
	public void setUp() {
		Stanza iniziale=new Stanza("iniziale");
		Stanza vincente=new Stanza("vincente");
		l = Labirinto.newBuilder()
				.addStanzaIniziale(iniziale)
				.addStanzaVincente(vincente)
				.getLabirinto();
	}
	
	//test metodo getStanzaIniziale()
	@Test
	public void testGetStanzaInizialeCorretta() {
		assertEquals("iniziale", l.getStanzaIniziale().getNome());
	}
	
	@Test
	public void testGetStanzaInizialeScorretta() {
		assertFalse(l.getStanzaIniziale().getNome().equals("stanzaSbagliata"));
	}
	
	//test metodo getStanzaCorrente()
	@Test
	public void testGetStanzaCorrenteIniziale() {
		Stanza s = new Stanza("iniziale");
		l.setStanzaCorrente(s);
		assertEquals("iniziale", l.getStanzaCorrente().getNome());
	}
	
	@Test
	public void testGetStanzaCorrenteQualunqueCorretta() {
		Stanza s = new Stanza("stanzaQualunque");
		l.setStanzaCorrente(s);
		assertEquals("stanzaQualunque", l.getStanzaCorrente().getNome());
	}
	
	@Test
	public void testGetStanzaCorrenteQualunqueScorretta() {
		//assertNot.. andrebbe evitato perché non è molto preciso
		assertNotEquals("stanzaQualunque", l.getStanzaCorrente().getNome());
	}
	
	//test metodo getStanzaVincente()
	@Test
	public void testGetStanzaVincenteCorretta() {
		assertEquals("vincente", l.getStanzaVincente().getNome());
	}
	
	@Test
	public void testGetStanzaVincenteScorretta() {
		assertFalse(l.getStanzaVincente().getNome().equals("nonVincente"));
	}

}
