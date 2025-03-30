package it.uniroma3.diadia.giocatore;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.uniroma3.diadia.attrezzi.*;
import it.uniroma3.diadia.*;

class BorsaTest {
	private Borsa b;
	private Attrezzo a;
	
	@BeforeEach
	public void setUp() {
		b = new Borsa();
		a = new Attrezzo("attrezzo", 3);
	}
	
	//test metodo getPesoMax
	@Test
	void testGetPesoMax() {
		assertEquals(10, b.getPesoMax());
	}
	
	//test metodo getPeso()
	@Test
	public void testGetPesoBorsaVuota() {
		assertEquals(0, b.getPeso());
	}
	
	@Test
	public void testGetPesoBorsaConAttrezzo() {
		b.addAttrezzo(a);
		assertEquals(3, b.getPeso());
	}
	
	//test metodo addAttrezzo()
	@Test
	public void testAddAttrezzoNull() {
		assertFalse(b.addAttrezzo(null));
	}
	
	@Test
	public void testAddAttrezzoBorsaNonPiena() {
		assertTrue(b.addAttrezzo(a));
	}
	
	@Test
	public void testAddAttrezzoBorsaPiena() {
		Attrezzo a2 = new Attrezzo("attrezzo2", 10);
		b.addAttrezzo(a2);
		assertFalse(b.addAttrezzo(a));
	}
	
	@Test
	public void testAddAttrezzoPesoTroppoAltoBorsaNonPiena() {
		Attrezzo a2 = new Attrezzo("attrezzo2", 12);
		assertFalse(b.addAttrezzo(a2));
	}
	
	//test metodo getAttrezzo()
	@Test
	public void testGetAttrezzoNull() {
		assertNull(b.getAttrezzo(null));
	}
	
	@Test
	public void testGetAttrezzoPresente() {
		b.addAttrezzo(a);
		assertNotNull(b.getAttrezzo("attrezzo"));
	}
	
	@Test
	public void testGetAttrezzoNonPresente() {
		assertNull(b.getAttrezzo("attrezzo"));
	}
	
	//test metodo remove attrezzo
	@Test
	public void testRemoveAttrezzoNull() {
		IOConsole c = new IOConsole();
		assertNull(b.removeAttrezzo(null, c));
	}
	
	@Test
	public void testRemoveAttrezzoBorsaVuota() {
		IOConsole c = new IOConsole();
		assertNull(b.removeAttrezzo("attrezzo", c));
	}
	
	@Test
	public void testRemoveAttrezzoPresente() {
		IOConsole c = new IOConsole();
		b.addAttrezzo(a);
		assertNotNull(b.removeAttrezzo("attrezzo", c));
	}
	
	@Test
	public void testRemoveAttrezzoNonPresente() {
		IOConsole c = new IOConsole();
		b.addAttrezzo(a);
		assertNull(b.removeAttrezzo("attrezzo2", c));
	}
	
	//test metodo hasAttrezzo()
	@Test
	public void testHasAttrezzoNull() {
		assertFalse(b.hasAttrezzo(null));
	}
	
	@Test
	public void testHasAttrezzoBorsaVuota() {
		assertFalse(b.hasAttrezzo("attrezzo"));
	}
	
	@Test
	public void testHasAttrezzoPresente() {
		b.addAttrezzo(a);
		assertTrue(b.hasAttrezzo("attrezzo"));
	}
	
	@Test
	public void testHasAttrezzoNonPresente() {
		assertFalse(b.hasAttrezzo("attrezzo"));
	}
	
	//test metodo isEmpty()
	@Test
	public void testIsEmptyAllInizio() {
		assertTrue(b.isEmpty());
	}
	
	@Test
	public void testIsEmptyBorsaNonVuota() {
		b.addAttrezzo(a);
		assertFalse(b.isEmpty());
	}

}
