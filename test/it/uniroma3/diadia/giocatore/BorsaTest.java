package it.uniroma3.diadia.giocatore;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.uniroma3.diadia.IOConsole;
import it.uniroma3.diadia.Partita;
import it.uniroma3.diadia.ambienti.Labirinto;
import it.uniroma3.diadia.attrezzi.*;
import it.uniroma3.diadia.comandi.*;

class BorsaTest {
	private Borsa b, borsa;
	private Attrezzo a, piombo, ps, piuma, libro;
	private Partita partita;
	
	@BeforeEach
	public void setUp() {
		b = new Borsa();
		a = new Attrezzo("attrezzo", 3);
		borsa=new Borsa(25);
		piombo=new Attrezzo("piombo", 10);
		ps=new Attrezzo("ps", 5);
		piuma=new Attrezzo("piuma", 1);
		libro=new Attrezzo("libro", 5);
		borsa.addAttrezzo(piombo);
		borsa.addAttrezzo(ps);
		borsa.addAttrezzo(piuma);
		borsa.addAttrezzo(libro);
		partita=new Partita(Labirinto.newBuilder().getLabirinto());
		partita.getGiocatore().setBorsa(borsa);
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
		assertNull(b.removeAttrezzo(null));
	}
	
	@Test
	public void testRemoveAttrezzoBorsaVuota() {
		assertNull(b.removeAttrezzo("attrezzo"));
	}
	
	@Test
	public void testRemoveAttrezzoPresente() {
		b.addAttrezzo(a);
		assertNotNull(b.removeAttrezzo("attrezzo"));
	}
	
	@Test
	public void testRemoveAttrezzoNonPresente() {
		b.addAttrezzo(a);
		assertNull(b.removeAttrezzo("attrezzo2"));
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
	
	//test metodo getContenutoOrdinatoPerPeso()
	@Test
	public void testGetContenutoOrdinatoPerPesoBorsaVuota() {
		assertTrue(b.getContenutoOrdinatoPerPeso().isEmpty());
		assertDoesNotThrow(()->b.getContenutoOrdinatoPerPeso());
	}
	
	@Test
	public void testGetContenutoOrdinatoPerPesoPiuOggettiStessoPeso() {
		ComandoGuardaBorsa comando=new ComandoGuardaBorsa("comandoGuardaBorsa", "list", new IOConsole());
		comando.esegui(partita);
		assertEquals("[piuma:1, libro:5, ps:5, piombo:10]", comando.getMessaggio());
	}
	
	//test metodo getContenutoOrdinatoPerNome()
	@Test
	public void testGetContenutoOrdinatoPerNomeBorsaVuota() {
		assertTrue(b.getContenutoOrdinatoPerNome().isEmpty());
		assertDoesNotThrow(()->b.getContenutoOrdinatoPerNome());
	}
	
	@Test
	public void testGetContenutoOrdinatoPerNome() {
		ComandoGuardaBorsa comando=new ComandoGuardaBorsa("comandoGuardaBorsa", "set", new IOConsole());
		comando.esegui(partita);
		assertEquals("{libro, piombo, piuma, ps}", comando.getMessaggio());
	}
	
	//test metodo getContenutoRaggruppatoPerPeso()
	@Test
	public void testGetContenutoRaggruppatoPerPesoBorsaVuota() {
		assertTrue(b.getContenutoRaggruppatoPerPeso().isEmpty());
		assertDoesNotThrow(()->b.getContenutoRaggruppatoPerPeso());
	}
	
	@Test
	public void testGetContenutoRaggruppatoPerPeso() {
		ComandoGuardaBorsa comando=new ComandoGuardaBorsa("comandoGuardaBorsa", "map", new IOConsole());
		comando.esegui(partita);
		assertEquals("(1,{piuma}); (5,{libro, ps}); (10,{piombo})", comando.getMessaggio());
	}
	
	//test metodo getSortedSetOrdinatoPerPeso()
	@Test
	public void testGetSortedSetOrdinatoPerPesoBorsaVuota() {
		assertTrue(b.getSortedSetOrdinatoPerPeso().isEmpty());
		assertDoesNotThrow(()->b.getSortedSetOrdinatoPerPeso());
	}
	
	@Test
	public void testGetSortedSetOrdinatoPerPeso() {
		assertEquals("[piuma:1, libro:5, ps:5, piombo:10]", borsa.getSortedSetOrdinatoPerPeso().toString());
	}

}
