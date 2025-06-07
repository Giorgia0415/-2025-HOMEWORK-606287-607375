package it.uniroma3.diadia;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.uniroma3.diadia.ambienti.*;
import it.uniroma3.diadia.giocatore.*;

//classe test
public class PartitaTest {
	private Partita p;
	private Labirinto l;
	private Giocatore g;
	
	//test-case
	@BeforeEach
	public void setUp() {
		Stanza stanzaIn=new Stanza("iniziale");
		Stanza stanzaVin=new Stanza("vincente");
		this.l=Labirinto.newBuilder()
				.addStanzaIniziale(stanzaIn)
				.addStanzaVincente(stanzaVin)
				.addAdiacenza(stanzaIn, stanzaVin, Direzione.NORD)
				.getLabirinto();
		this.p = new Partita(l);
		this.g = p.getGiocatore();
	}
	
	//test metodo getLabirinto()
	@Test
	public void testGetLabirinto() {
		assertNotNull(p.getLabirinto());
	}
	
	//test metodo getGiocatore()
	@Test
	public void testGetGiocatore() {
		assertNotNull(p.getGiocatore());
	}
	
	//test metodo vinta()
	@Test
	public void testVintaSubitoDopoCreazione() {
		assertFalse(p.vinta());
	}
	
	@Test
	public void testVintaDopoSetStanzaCorrenteNonVincente() {
		Stanza stanzaNonVincente = new Stanza("");
		l.setStanzaCorrente(stanzaNonVincente);
		assertFalse(p.vinta());
	}
	
	@Test
	public void testVintaDopoSetStanzaCorrenteVincente() {
		l.setStanzaCorrente(l.getStanzaVincente());
		assertTrue(p.vinta());
	}
	
	//test metodo persa()
	@Test
	public void testPersaQuandoNonPersa() {
		g.setCfu(1);
		assertFalse(p.persa());
	}
	
	@Test
	public void testPersaQuandoCfuZero() {
		g.setCfu(0);
		assertTrue(p.persa());
	}
	
	@Test
	public void testPersaQuandoCfuNegativi() {
		g.setCfu(-2);
		assertTrue(p.persa());
	}
	
	//test metodo isFinita()
	@Test
	public void testIsFinitaAllInizio() {
		assertFalse(p.isFinita());
	}
	
	@Test
	public void testIsFinitaQuandoGiocatoreHaCfuZero() {
		g.setCfu(0);
		assertTrue(p.isFinita());
	}
	
	@Test
	public void testIsFinitaQuandoPartitaVinta() {
		l.setStanzaCorrente(l.getStanzaVincente());
		assertTrue(p.isFinita());
	}
	
	@Test
	public void testIsFinitaQuandoNessunaCondizioneDiVittoriaVerificata() {
		Stanza stanzaNonVincente = new Stanza("");
		l.setStanzaCorrente(stanzaNonVincente);
		g.setCfu(20);
		assertFalse(p.isFinita());
	}

}
