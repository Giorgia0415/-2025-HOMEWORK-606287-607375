package it.uniroma3.diadia.comandi;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.uniroma3.diadia.IOConsole;
import it.uniroma3.diadia.Partita;

class ComandoVaiTest {
	private Partita p;
	private Comando c;

	@BeforeEach
	public void setUp() throws Exception {
		this.c=new ComandoVai();
		c.setIo(new IOConsole());
		this.p=new Partita();
	}
	
	//test metodo esegui()
	@Test
	public void testEseguiDirezioneNullNonDaEccezioni() {
		assertDoesNotThrow(()->c.esegui(null));
	}
	
	@Test
	public void testEseguiDirezioneNullCfuNonDiminuiti() {
		c.esegui(null);
		assertEquals("Atrio", p.getLabirinto().getStanzaCorrente().getNome());
		assertEquals(20, p.getGiocatore().getCfu());
	}
	
	@Test
	public void testEseguiDirezioneNonEsistenteCfuNonDiminuiti() {
		c.setParametro("nord-est");
		c.esegui(p);
		assertEquals("Atrio", p.getLabirinto().getStanzaCorrente().getNome());
		assertEquals(20, p.getGiocatore().getCfu());
	}
	
	@Test
	public void testEseguiDirezioneEsistenteCfuDiminuiti() {
		c.setParametro("nord");
		c.esegui(p);
		assertEquals("Biblioteca", p.getLabirinto().getStanzaCorrente().getNome());
		assertEquals(19, p.getGiocatore().getCfu());
	}

}
