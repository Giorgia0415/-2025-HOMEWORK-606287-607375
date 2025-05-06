package it.uniroma3.diadia.ambienti;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.uniroma3.diadia.attrezzi.Attrezzo;

class StanzaMagicaTest {
	Stanza stanzaMagica;
	Attrezzo a1, a2, a3, a4;

	@BeforeEach
	public void setUp() {
		stanzaMagica=new StanzaMagica("labIA");
		a1=new Attrezzo("a1", 1);
		a2=new Attrezzo("a2", 2);
		a3=new Attrezzo("a3", 3);
		a4=new Attrezzo("a4", 4);
	}
	
	//test metodo addAttrezzo
	@Test
	void testAddAttrezzoNull() {
		assertFalse(stanzaMagica.addAttrezzo(null));
	}
	
	@Test
	public void testAddAttrezzoNonNull() {
		stanzaMagica.addAttrezzo(a1);
		assertTrue(stanzaMagica.hasAttrezzo("a1"));
	}
	
	//test comportamento magico
	@Test
	public void testAddAttrezzoNonAttivaMagia() {
		stanzaMagica.addAttrezzo(a1);
		stanzaMagica.addAttrezzo(a2);
		stanzaMagica.addAttrezzo(a3);
		assertTrue(stanzaMagica.hasAttrezzo("a1"));
		assertTrue(stanzaMagica.hasAttrezzo("a2"));
		assertTrue(stanzaMagica.hasAttrezzo("a3"));
	}
	
	@Test
	public void testAddAttrezzoAttivaMagiaConRimozione() {
		stanzaMagica.addAttrezzo(a1);
		stanzaMagica.addAttrezzo(a2);
		stanzaMagica.addAttrezzo(a3);
		stanzaMagica.addAttrezzo(a4);
		stanzaMagica.removeAttrezzo("a1");
		assertFalse(stanzaMagica.hasAttrezzo("a1"));
		assertTrue(stanzaMagica.hasAttrezzo("a2"));
		assertTrue(stanzaMagica.hasAttrezzo("a3"));
		assertFalse(stanzaMagica.hasAttrezzo("a4"));
	}
	
	@Test
	public void testAddAttrezzoAttivaMagia() {
		stanzaMagica.addAttrezzo(a1);
		stanzaMagica.addAttrezzo(a2);
		stanzaMagica.addAttrezzo(a3);
		stanzaMagica.addAttrezzo(a4);
		assertTrue(stanzaMagica.hasAttrezzo("a1"));
		assertTrue(stanzaMagica.hasAttrezzo("a2"));
		assertTrue(stanzaMagica.hasAttrezzo("a3"));
		assertFalse(stanzaMagica.hasAttrezzo("a4"));
		assertTrue(stanzaMagica.hasAttrezzo("4a"));
		assertEquals(8, stanzaMagica.getAttrezzo("4a").getPeso());
	}

}
