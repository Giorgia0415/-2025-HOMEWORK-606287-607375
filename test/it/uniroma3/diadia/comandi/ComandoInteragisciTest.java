package it.uniroma3.diadia.comandi;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.uniroma3.diadia.IOConsole;
import it.uniroma3.diadia.Partita;
import it.uniroma3.diadia.ambienti.Labirinto;
import it.uniroma3.diadia.ambienti.Stanza;
import it.uniroma3.diadia.personaggi.Mago;

class ComandoInteragisciTest {
	private ComandoInteragisci c;
	private Partita p;
	private Labirinto labirinto;
	
	@BeforeEach
	public void setUp() {
		labirinto=Labirinto.newBuilder()
				.addStanzaIniziale(new Stanza("iniz"))
				.getLabirinto();
		this.p=new Partita(labirinto);
		this.c=new ComandoInteragisci("interagisci", null, new IOConsole());
	}
	
	//test metodo esegui()
	@Test
	public void testEseguiConPersonaggioNull() {
		c.esegui(p);
		assertEquals("Con chi dovrei interagire?...", c.getMessaggio());
	}
	
	@Test
	public void testEseguiCorrettamente() {
		p.getLabirinto().getStanzaCorrente().setPersonaggio(new Mago("mago", "prova", null));
		c.esegui(p);
		assertEquals("Mi spiace, ma non ho più nulla...", c.getMessaggio());
	}

}
