package it.uniroma3.diadia.personaggi;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.uniroma3.diadia.IOConsole;
import it.uniroma3.diadia.Partita;
import it.uniroma3.diadia.ambienti.Labirinto;
import it.uniroma3.diadia.ambienti.Stanza;
import it.uniroma3.diadia.attrezzi.Attrezzo;
import it.uniroma3.diadia.comandi.AbstractComando;
import it.uniroma3.diadia.comandi.ComandoInteragisci;

class FakePersonaggio extends AbstractPersonaggio {

	public FakePersonaggio(String nome, String presentazione) {
		super(nome, presentazione);
	}

	@Override
	public String agisci(Partita partita) {
		return "done";
	}

	@Override
	public String riceviRegalo(Attrezzo attrezzo, Partita partita) {
		return "accepted";
	}
	
}

public class AbstractPersonaggioTest {
	private Partita partita;
	private AbstractPersonaggio p;
	private AbstractComando c;
	private Labirinto labirinto;

	@BeforeEach
	public void setUp() {
		labirinto=Labirinto.newBuilder()
				.addStanzaIniziale(new Stanza("in"))
				.addPersonaggio(p)
				.getLabirinto();
		this.partita=new Partita(labirinto);
		this.p=new FakePersonaggio("Personaggio", "fake");
		this.c=new ComandoInteragisci("interagisci", null, new IOConsole());
	}
	
	//test metodo haSalutato()
	@Test
	public void testHaSalutato() {
		p.saluta();
		assertTrue(p.haSalutato());
	}
	
	@Test
	public void testNonHaSalutatoDopoEsegui() {
		c.esegui(partita);
		assertFalse(p.haSalutato());
	}
	
	@Test
	public void testNonHaSalutatoInizioPartita() {
		assertFalse(p.haSalutato());
	}
	
	//test metodo saluta()
	@Test
	public void testSalutaPrimaVolta() {
		assertEquals("Ciao, io sono Personaggio. fake", p.saluta());
	}
	
	@Test
	public void testSalutaNonPrimaVolta() {
		p.saluta();
		assertEquals("Ciao, io sono Personaggio. Ci siamo già presentati!", p.saluta());
	}

}
