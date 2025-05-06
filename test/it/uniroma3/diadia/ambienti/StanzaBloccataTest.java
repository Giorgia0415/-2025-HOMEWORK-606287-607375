package it.uniroma3.diadia.ambienti;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.uniroma3.diadia.attrezzi.Attrezzo;

class StanzaBloccataTest {
	private Attrezzo a, p;
	private Stanza sBloccata, s;
	private String dirBloccata, messaggioBloccata;
	
	@BeforeEach
	public void setUp() {
		this.sBloccata=new StanzaBloccata("stanzaBloccata", "nord", "passepartout");
		this.dirBloccata="nord";
		this.a=new Attrezzo("attrezzo", 2);
		this.p=new Attrezzo("passepartout", 2);
		this.s=new Stanza("stanzaBloccata");
		this.messaggioBloccata=s.getDescrizione()+"\nNon puoi accedere alla stanza in direzione "+this.dirBloccata+" perché non è presente l'oggetto "+this.p.getNome();
	}
	
	//test metodo getStanzaAdiacente()
	@Test
	public void testGetStanzaAdiacenteAccessibileNonBloccata() {
		this.sBloccata.impostaStanzaAdiacente("est", s);
		assertEquals(s,sBloccata.getStanzaAdiacente("est"));
	}
	
	@Test
	public void testGetStanzaAdiacenteNonAccessibilePercheNonHaAttrezzo() {
		this.sBloccata.impostaStanzaAdiacente(dirBloccata, s);
		assertEquals(this.sBloccata, sBloccata.getStanzaAdiacente(dirBloccata));
	}
	
	@Test
	public void testGetStanzaAdiacenteNonAccessibilePercheNonHaAttrezzoCorretto() {
		this.sBloccata.impostaStanzaAdiacente(dirBloccata, s);
		this.sBloccata.addAttrezzo(a);
		assertEquals(this.sBloccata, sBloccata.getStanzaAdiacente(dirBloccata));
	}
	
	@Test
	public void testGetStanzaAdiacenteAccessibilePercheHaAttrezzo() {
		this.sBloccata.impostaStanzaAdiacente(dirBloccata, s);
		this.sBloccata.addAttrezzo(p);
		assertEquals(s, sBloccata.getStanzaAdiacente(dirBloccata));
	}
	
	@Test
	public void testGetStanzaAdiacenteNull() {
		assertDoesNotThrow(()->sBloccata.getStanzaAdiacente(null));
	}
	
	@Test
	public void testGetStanzaAdiacenteNullAttrezzoNull() {
		this.sBloccata.addAttrezzo(null);
		assertDoesNotThrow(()->sBloccata.getStanzaAdiacente(null));
	}
	
	//test metodo getDescrizione()
	@Test
	public void testGetDescrizioneNormale() {
		this.sBloccata.addAttrezzo(p);
		s.addAttrezzo(p);
		assertEquals(s.getDescrizione(), sBloccata.getDescrizione());
	}
	
	@Test
	public void testGetDescrizioneBloccata() {
		assertEquals(messaggioBloccata, sBloccata.getDescrizione());
	}

}
