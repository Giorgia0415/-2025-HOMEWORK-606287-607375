package it.uniroma3.diadia.comandi;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.uniroma3.diadia.IOConsole;
import it.uniroma3.diadia.Partita;
import it.uniroma3.diadia.ambienti.Labirinto;
import it.uniroma3.diadia.ambienti.Stanza;
import it.uniroma3.diadia.attrezzi.Attrezzo;
import it.uniroma3.diadia.personaggi.Mago;

class ComandoRegalaTest {
	private ComandoRegala comandoRegala;
	private Partita partita;
	private Labirinto labirinto;

	@BeforeEach
	public void setUp() {
		this.comandoRegala=new ComandoRegala("regala", "attrezzo", new IOConsole());
		this.labirinto=Labirinto.newBuilder()
				.addStanzaIniziale(new Stanza("iniziale"))
				.getLabirinto();
		this.partita=new Partita(this.labirinto);
	}
	
	//test metodo esegui()
	@Test
	public void testEseguiPersonaggioNull() {
		this.comandoRegala.esegui(this.partita);
		assertEquals("A chi dovrei regalare l'attrezzo?...", this.comandoRegala.getMessaggio());
	}
	
	@Test
	public void testEseguiCorrettamente() {
		Attrezzo a=new Attrezzo("attrezzo", 2);
		this.partita.getLabirinto().getStanzaCorrente().setPersonaggio(new Mago("mago", "prova", null));
		this.partita.getGiocatore().getBorsa().addAttrezzo(a);
		this.comandoRegala.esegui(this.partita);
		assertFalse(this.partita.getGiocatore().getBorsa().hasAttrezzo("attrezzo"));
		assertEquals("attrezzo rimosso dalla borsa e donato a mago", this.comandoRegala.getMessaggio());
	}

}
