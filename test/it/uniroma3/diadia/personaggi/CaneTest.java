package it.uniroma3.diadia.personaggi;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.uniroma3.diadia.Partita;
import it.uniroma3.diadia.ambienti.Labirinto;
import it.uniroma3.diadia.ambienti.Stanza;
import it.uniroma3.diadia.attrezzi.Attrezzo;

class CaneTest {
	private Cane cane;
	private Partita p;
	private Labirinto labirinto;

	@BeforeEach
	public void setUp() {
		this.cane=new Cane("cane", "prova");
		labirinto=Labirinto.newBuilder()
				.addStanzaIniziale(new Stanza("iniziale"))
				.addPersonaggio(cane)
				.getLabirinto();
		p=new Partita(labirinto);
	}
	
	//test metodo agisci()
	@Test
	public void testAgisciPrimaVolta() {
		cane.agisci(p);
		assertEquals(19, p.getGiocatore().getCfu());
	}
	
	@Test
	public void testAgisciPiuVolte() {
		cane.agisci(p);
		cane.agisci(p);
		cane.agisci(p);
		assertEquals(17, p.getGiocatore().getCfu());
	}
	
	@Test
	public void testAgisciPortandoCfuAZero() {
		p.getGiocatore().setCfu(1);
		cane.agisci(p);
		assertEquals(0, p.getGiocatore().getCfu());
		assertTrue(p.isFinita());
	}
	
	//test metodo riceviRegalo()
	@Test
	public void testRiceviRegaloNull() {
		assertEquals("Questo non è il mio cibo preferito! Grrrr!...Sei stato morso ed hai perso un cfu", cane.riceviRegalo(null, p));
		assertEquals(19, p.getGiocatore().getCfu());
	}
	
	@Test
	public void testRiceviRegaloCorrettamente() {
		String messaggio="Il mio cibo preferito! Bau!\nUn nuovo attrezzo è stato rilasciato nella stanza, guarda per vedere di cosa si tratta";
		assertEquals(messaggio, cane.riceviRegalo(new Attrezzo("osso", 1), p));
		assertEquals(20, p.getGiocatore().getCfu());
		assertTrue(p.getLabirinto().getStanzaCorrente().hasAttrezzo("collare"));
	}

}
