package it.uniroma3.diadia.personaggi;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.uniroma3.diadia.IOConsole;
import it.uniroma3.diadia.Partita;
import it.uniroma3.diadia.ambienti.Labirinto;
import it.uniroma3.diadia.ambienti.Stanza;
import it.uniroma3.diadia.attrezzi.Attrezzo;
import it.uniroma3.diadia.comandi.ComandoSaluta;

class StregaTest {
	private Strega strega;
	private Partita p;
	private ComandoSaluta c;
	private Labirinto labirinto;

	@BeforeEach
	public void setUp() {
		this.strega=new Strega("strega", "prova");
		labirinto=Labirinto.newBuilder()
				.addStanzaIniziale(new Stanza("iniziale"))
				.addPersonaggio(strega)
				.getLabirinto();
		this.p=new Partita(labirinto);
		this.c=new ComandoSaluta("saluta", null, new IOConsole());
	}
	
	//test metodo agisci()
	@Test
	public void testAgisciSalutato() {
		Stanza s=strega.stanzaFinale(true, p);
		c.esegui(p);
		strega.agisci(p);
		assertEquals(s, p.getLabirinto().getStanzaCorrente());
	}
	
	@Test
	public void testAgisciNonSalutato() {
		Stanza s=strega.stanzaFinale(false, p);
		strega.agisci(p);
		assertEquals(s, p.getLabirinto().getStanzaCorrente());
	}
	
	//test metodo riceviRegalo()
	@Test
	public void testRiceviRegaloNull() {
		assertEquals("Mi prendi in giro?!", strega.riceviRegalo(null, p));
	}
	
	@Test
	public void testRiceviRegaloCorrettamente() {
		assertEquals("Non riavrai più indietro il tuo attrezzo...AHAHAHAHAHAHAH", strega.riceviRegalo(new Attrezzo("attrezzo", 2), p));
	}

}
