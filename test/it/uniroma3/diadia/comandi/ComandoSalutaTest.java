package it.uniroma3.diadia.comandi;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.uniroma3.diadia.IOConsole;
import it.uniroma3.diadia.Partita;
import it.uniroma3.diadia.ambienti.Labirinto;
import it.uniroma3.diadia.ambienti.Stanza;
import it.uniroma3.diadia.personaggi.Mago;

class ComandoSalutaTest {
	private ComandoSaluta c;
	private Partita p;
	private Labirinto labirinto;

	@BeforeEach
	public void setUp() {
		this.c=new ComandoSaluta("saluta", null, new IOConsole());
		labirinto=Labirinto.newBuilder()
				.addStanzaIniziale(new Stanza("iniz"))
				.getLabirinto();
		this.p=new Partita(labirinto);
	}
	
	//test metodo esegui()
	@Test
	public void testEseguiConPersonaggioNull() {
		c.esegui(p);
		assertEquals("Chi dovrei salutare?...", c.getMessaggio());
	}
	
	@Test
	public void testEseguiPrimaVolta() {
		this.p.getLabirinto().getStanzaCorrente().setPersonaggio(new Mago("mago", "prova", null));
		c.esegui(p);
		assertEquals("Ciao, io sono mago. prova", c.getMessaggio());
	}
	
	@Test
	public void testSalutaNonPrimaVolta() {
		this.p.getLabirinto().getStanzaCorrente().setPersonaggio(new Mago("mago", "prova", null));
		c.esegui(p);
		c.esegui(p);
		assertEquals("Ciao, io sono mago. Ci siamo già presentati!", c.getMessaggio());
	}

}
