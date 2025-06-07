package it.uniroma3.diadia.comandi;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.uniroma3.diadia.IOConsole;
import it.uniroma3.diadia.Partita;
import it.uniroma3.diadia.ambienti.Labirinto;
import it.uniroma3.diadia.ambienti.Stanza;
import it.uniroma3.diadia.attrezzi.Attrezzo;

class ComandoPosaTest {
	private Partita p;
	private AbstractComando c;
	private Attrezzo a;
	private Labirinto labirinto;

	@BeforeEach
	public void setUp() {
		labirinto=Labirinto.newBuilder()
				.addStanzaIniziale(new Stanza("iniz"))
				.getLabirinto();
		this.p=new Partita(labirinto);
		this.c=new ComandoPosa("posa", null, new IOConsole());
		this.a=new Attrezzo("attrezzo", 2);
	}
	
	//test metodo esegui()
	@Test
	public void testEseguiNullNonDaEccezioni() {
		assertDoesNotThrow(()->c.esegui(null));
	}
	
	@Test
	public void testEseguiAttrezzoNull() {
		c.setParametro(null);
		c.esegui(p);
		assertFalse(p.getLabirinto().getStanzaCorrente().hasAttrezzo("attrezzo"));
	}
	
	@Test
	public void testEseguiAttrezzoNonPosatoPercheNonPosseduto() {
		c.setParametro("attrezzo");
		c.esegui(p);
		assertFalse(p.getLabirinto().getStanzaCorrente().hasAttrezzo("attrezzo"));
	}
	
	@Test
	public void testEseguiAttrezzoPosato() {
		p.getGiocatore().getBorsa().addAttrezzo(a);
		c.setParametro("attrezzo");
		c.esegui(p);
		assertTrue(p.getLabirinto().getStanzaCorrente().hasAttrezzo("attrezzo"));
		assertFalse(p.getGiocatore().getBorsa().hasAttrezzo("attrezzo"));
	}

}
