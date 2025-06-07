package it.uniroma3.diadia.ambienti;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.uniroma3.diadia.attrezzi.*;

public class StanzaTest {
	private Stanza s;
	private Attrezzo a;
	
	@BeforeEach
	public void setUp() {
		s = new Stanza("stanzaSetUp");
		a = new Attrezzo("attrezzo", 1);
	}
	
	//test metodo getNome()
	@Test
	public void testGetNomeCorretto() {
		assertEquals("stanzaSetUp", s.getNome());
	}
	
	@Test
	public void testGetNomeScorretto() {
		assertNotEquals("stanza", s.getNome());
	}
	
	//test metodo getStanzaAdiacente()
	@Test
	public void testGetStanzaAdiacenteInDirezioneNull() {
		assertNull(s.getStanzaAdiacente(null));
	}
	
	@Test
	public void testGetStanzaAdiacente_StanzaInesistente() {
		s.impostaStanzaAdiacente(Direzione.NORD, null);
		assertNull(s.getStanzaAdiacente(Direzione.NORD));
	}
	
	@Test
	public void testGetStanzaAdiacente_StanzaEsistente() {
		Stanza stanza = new Stanza("stanza");
		s.impostaStanzaAdiacente(Direzione.NORD, stanza);
		assertNotNull(s.getStanzaAdiacente(Direzione.NORD));
	}
	
	//test metodo getAttrezzi()
	@Test
	public void testGetAttrezzi() {
		assertNotNull(s.getAttrezzi());
	}
	
	//test metodo addAttrezzo()
	@Test
	public void testAddAttrezzoQuandoStanzaNonPiena() {
		assertTrue(s.addAttrezzo(a));
	}
	
	@Test
	public void testAddAttrezzoQuandoStanzaPiena() {
		for(int i=0; i<10; i++) {
			s.addAttrezzo(a);
		}
		assertFalse(s.addAttrezzo(a));
	}
	
	@Test
	public void testAddAttrezzoGiaPresente() {
		s.addAttrezzo(a);
		assertFalse(s.addAttrezzo(a));
	}
	
	@Test
	public void testAddAttrezzoNomeUgualeOggettiDiverso() {
		s.addAttrezzo(a);
		Attrezzo a2=new Attrezzo("attrezzo", 1);
		assertFalse(s.addAttrezzo(a2));
	}
	
	@Test
	public void testAddAttrezzoNull() {
		assertFalse(s.addAttrezzo(null));
	}
	
	//test metodo hasAttrezzo()
	@Test
	public void testHasAttrezzoNull() {
		assertFalse(s.hasAttrezzo(null));
	}
	
	@Test
	public void testHasAttrezzoPresente() {
		s.addAttrezzo(a);
		assertTrue(s.hasAttrezzo("attrezzo"));
	}
	
	@Test
	public void testHasAttrezzoNonPresente() {
		assertFalse(s.hasAttrezzo("attrezzo"));
	}
	
	//test metodo getAttrezzo()
	@Test
	public void testGetAttrezzoNull() {
		s.addAttrezzo(null);
		assertNull(s.getAttrezzo("attrezzo"));
	}
	
	@Test
	public void testGetAttrezzoPresente() {
		s.addAttrezzo(a);
		assertNotNull(s.getAttrezzo("attrezzo"));
	}
	
	@Test
	public void testGetAttrezzoNonPresente() {
		assertNull(s.getAttrezzo("attrezzo"));
	}
	
	//test metodo removeAttrezzo()
	@Test
	public void testRemoveAttrezzoNull() {
		assertFalse(s.removeAttrezzo(null));
	}
	
	@Test
	public void testRemoveAttrezzoNonPresente() {
		assertFalse(s.removeAttrezzo("attrezzo"));
	}
	
	@Test
	public void testRemoveAttrezzoPresente() {
		s.addAttrezzo(a);
		assertTrue(s.removeAttrezzo("attrezzo"));
	}
}
