package it.uniroma3.diadia.personaggi;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.uniroma3.diadia.Partita;
import it.uniroma3.diadia.ambienti.Labirinto;
import it.uniroma3.diadia.ambienti.Stanza;
import it.uniroma3.diadia.attrezzi.Attrezzo;

class MagoTest {
	private Mago mago, magoAttrezzo;
	private Partita p;
	private Labirinto labirinto;

	@BeforeEach
	public void setUp() {
		this.mago=new Mago("mago", "prova", null);
		this.magoAttrezzo=new Mago("magoA", "prova", new Attrezzo("attrezzo", 2));
		labirinto=Labirinto.newBuilder()
				.addStanzaIniziale(new Stanza("iniziale"))
				.getLabirinto();
		this.p=new Partita(labirinto);
	}
	
	//test metodo agisci()
	@Test
	public void testAgisciMagoConAttrezzo() {
		this.p.getLabirinto().getStanzaCorrente().setPersonaggio(magoAttrezzo);
		this.magoAttrezzo.agisci(p);
		assertTrue(p.getLabirinto().getStanzaCorrente().hasAttrezzo("attrezzo"));
		assertNull(magoAttrezzo.getAttrezzo());
	}
	
	@Test
	public void testAgisciMagoSenzaAttrezzo() {
		this.p.getLabirinto().getStanzaCorrente().setPersonaggio(mago);
		assertEquals("Mi spiace, ma non ho più nulla...", mago.agisci(p));
		assertFalse(p.getLabirinto().getStanzaCorrente().hasAttrezzo("attrezzo"));
	}
	
	//test metodo riceviRegalo()
	@Test
	public void testRiceviRegaloNull() {
		this.p.getLabirinto().getStanzaCorrente().setPersonaggio(mago);
		assertEquals("Non mi stai donando niente...", mago.riceviRegalo(null, p));		
	}
	
	@Test
	public void testRiceviRegaloCorrettamente() {
		Attrezzo a=new Attrezzo("attrezzo", 2);
		this.p.getLabirinto().getStanzaCorrente().setPersonaggio(mago);
		assertEquals("Io non so che farmene...Ma se vuoi riprendertelo avrai una sorpresa \"leggera\"", mago.riceviRegalo(a, p));
		assertTrue(p.getLabirinto().getStanzaCorrente().hasAttrezzo(a.getNome()));
		assertEquals(1, a.getPeso());
	}
}
