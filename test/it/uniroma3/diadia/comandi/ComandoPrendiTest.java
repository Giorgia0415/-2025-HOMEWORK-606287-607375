package it.uniroma3.diadia.comandi;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.uniroma3.diadia.IOConsole;
import it.uniroma3.diadia.Partita;
import it.uniroma3.diadia.ambienti.Labirinto;
import it.uniroma3.diadia.ambienti.Stanza;
import it.uniroma3.diadia.attrezzi.Attrezzo;

class ComandoPrendiTest {
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
		this.c=new ComandoPrendi("prendi", null, new IOConsole());
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
		assertFalse(p.getGiocatore().getBorsa().hasAttrezzo("attrezzo"));
	}
	
	@Test
	public void testEseguiAttrezzoNonPresoPercheNonPresente() {
		c.setParametro("attrezzo");
		c.esegui(p);
		assertFalse(p.getGiocatore().getBorsa().hasAttrezzo("attrezzo"));
		assertFalse(p.getLabirinto().getStanzaCorrente().hasAttrezzo("attrezzo"));
	}
	
	@Test
	public void testEseguiAttrezzoPreso() {
		p.getLabirinto().getStanzaCorrente().addAttrezzo(a);
		c.setParametro("attrezzo");
		c.esegui(p);
		assertTrue(p.getGiocatore().getBorsa().hasAttrezzo("attrezzo"));
		assertFalse(p.getLabirinto().getStanzaCorrente().hasAttrezzo("attrezzo"));
	}

}
