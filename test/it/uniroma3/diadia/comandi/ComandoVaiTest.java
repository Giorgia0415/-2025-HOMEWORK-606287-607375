package it.uniroma3.diadia.comandi;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.uniroma3.diadia.IOConsole;
import it.uniroma3.diadia.Partita;
import it.uniroma3.diadia.ambienti.Direzione;
import it.uniroma3.diadia.ambienti.Labirinto;
import it.uniroma3.diadia.ambienti.Stanza;

class ComandoVaiTest {
	private Partita p;
	private AbstractComando c;
	private Labirinto labirinto;

	@BeforeEach
	public void setUp() throws Exception {
		this.c=new ComandoVai("vai", null, new IOConsole());
		Stanza stanzaIn=new Stanza("iniziale");
		Stanza stanzaVin=new Stanza("vincente");
		labirinto=Labirinto.newBuilder()
				.addStanzaIniziale(stanzaIn)
				.addStanzaVincente(stanzaVin)
				.addAdiacenza(stanzaIn, stanzaVin, Direzione.NORD)
				.getLabirinto();
		this.p=new Partita(labirinto);
	}
	
	//test metodo esegui()
	@Test
	public void testEseguiDirezioneNullNonDaEccezioni() {
		assertDoesNotThrow(()->c.esegui(null));
	}
	
	@Test
	public void testEseguiDirezioneNullCfuNonDiminuiti() {
		c.esegui(null);
		assertEquals("iniziale", p.getLabirinto().getStanzaCorrente().getNome());
		assertEquals(20, p.getGiocatore().getCfu());
	}
	
	@Test
	public void testEseguiDirezioneSenzaStanzaCfuNonDiminuiti() {
		c.setParametro("est");
		c.esegui(p);
		c.setParametro("nord");
		c.esegui(p);
		assertEquals("vincente", p.getLabirinto().getStanzaCorrente().getNome());
		assertEquals(19, p.getGiocatore().getCfu());
	}
	
	@Test
	public void testEseguiDirezioneNonEsistenteCfuNonDiminuiti() {
		c.setParametro("nord-est");
		c.esegui(p);
		assertEquals("iniziale", p.getLabirinto().getStanzaCorrente().getNome());
		assertEquals(20, p.getGiocatore().getCfu());
	}
	
	@Test
	public void testEseguiDirezioneEsistenteCfuDiminuiti() {
		c.setParametro("nord");
		c.esegui(p);
		assertEquals("vincente", p.getLabirinto().getStanzaCorrente().getNome());
		assertEquals(19, p.getGiocatore().getCfu());
	}

}
