package it.uniroma3.diadia.ambienti;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.uniroma3.diadia.attrezzi.Attrezzo;

class StanzaBuiaTest {
	private Stanza s;
	private String messaggioAlBuio;
	private Attrezzo a, l;

	@BeforeEach
	public void setUp(){
		this.s=new StanzaBuia("stanzaBuia", "lanterna");
		this.messaggioAlBuio="Qui c'è un buio pesto";
		this.a=new Attrezzo("attrezzo", 2);
		this.l=new Attrezzo("lanterna", 2);
	}
	
	//test metodo getDescrizione()
	@Test
	public void testGetDescrizioneSenzaLanterna() {
		assertEquals(messaggioAlBuio, s.getDescrizione());
	}
	
	@Test
	public void testGetDescrizioneStanzaConAttrezzoQualunque() {
		this.s.addAttrezzo(a);
		assertEquals(messaggioAlBuio, s.getDescrizione());
	}
	
	@Test
	public void testGetDescrizioneStanzaConLanterna() {
		Stanza s1=new Stanza("stanzaBuia");
		s1.addAttrezzo(l);
		this.s.addAttrezzo(l);
		assertEquals(s1.getDescrizione(), this.s.getDescrizione());
	}

}
