package it.uniroma3.diadia.comandi;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.uniroma3.diadia.IOConsole;
import it.uniroma3.diadia.Partita;
import it.uniroma3.diadia.attrezzi.Attrezzo;

class ComandoPosaTest {
	private Partita p;
	private Comando c;
	private Attrezzo a;

	@BeforeEach
	public void setUp() {
		this.p=new Partita();
		this.c=new ComandoPosa();
		c.setIo(new IOConsole());
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
