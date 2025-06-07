package it.uniroma3.diadia;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.uniroma3.diadia.ambienti.Direzione;
import it.uniroma3.diadia.ambienti.Labirinto;
import it.uniroma3.diadia.ambienti.*;
import it.uniroma3.diadia.attrezzi.Attrezzo;
import it.uniroma3.diadia.personaggi.*;

public class DiaDiaTest {
	private IOSimulator io;
	private DiaDia gioco;
	private Labirinto labirinto;
	private List<String> comandi;
	private Stanza iniziale, vincente, corridoio, bagno, labCampus;
	private Stanza magica, bloccata, buia;
	private AbstractPersonaggio Mago, Strega, Cane;
	
	@BeforeEach
	public void setUp() {
		iniziale=new Stanza("iniziale");
		vincente=new Stanza("vincente");
		corridoio=new Stanza("corridoio");
		bagno=new Stanza("bagno");
		labCampus=new Stanza("labCampus");
		
		magica=new Stanza("magica");
		bloccata=new Stanza("bloccata");
		buia=new Stanza("buia");
		
	}

	@Test
	public void testGiocoVintoSubito() {
		this.comandi=Arrays.asList("vai nord");
		this.io=new IOSimulator(comandi);
		this.labirinto= new Labirinto.LabirintoBuilder()
				.addStanzaIniziale(iniziale)
				.addStanzaVincente(vincente)
				.addAdiacenza(iniziale, vincente, Direzione.NORD)
				.getLabirinto();
		this.gioco=new DiaDia(labirinto, io);
		this.gioco.gioca();
		
		Map<String, List<String>> messaggi=io.getMessaggi();
		assertTrue(messaggi.get("vai nord").contains("Hai vinto!"));
	}
	
	@Test
	public void testFineGiocoImmediata() {
		this.comandi=Arrays.asList("fine");
		this.io=new IOSimulator(comandi);
		this.labirinto= new Labirinto.LabirintoBuilder().getLabirinto();
		this.gioco=new DiaDia(labirinto, io);
		this.gioco.gioca();
		
		Map<String, List<String>> messaggi=io.getMessaggi();
		assertTrue(messaggi.get("fine").contains("Grazie di aver giocato!"));
	}
	
	@Test
	public void testGiocoComandoNonValido() {
		this.comandi=Arrays.asList("nonValido", "fine");
		this.io=new IOSimulator(comandi);
		this.labirinto= new Labirinto.LabirintoBuilder().getLabirinto();
		this.gioco=new DiaDia(labirinto, io);
		this.gioco.gioca();
		
		Map<String, List<String>> messaggi=io.getMessaggi();
		assertTrue(messaggi.get("nonValido").contains("Comando non valido"));
	}
	
	@Test
	public void testPartitaSpostamentoInLabirintoPrimaDiVittoria() {
		this.comandi=Arrays.asList("vai nord", "vai est", "vai ovest", "vai ovest", "vai nord");
		this.io=new IOSimulator(comandi);
		this.labirinto= new Labirinto.LabirintoBuilder()
				.addStanzaIniziale(iniziale)
				.addStanzaVincente(vincente)
				.addStanza(corridoio)
				.addStanza(bagno)
				.addAdiacenza(iniziale, corridoio, Direzione.NORD)
				.addAdiacenza(corridoio, bagno, Direzione.EST)
				.addAdiacenza(corridoio, vincente, Direzione.NORD)
				.getLabirinto();
		this.gioco=new DiaDia(labirinto, io);
		this.gioco.gioca();
		
		Map<String, List<String>> messaggi=io.getMessaggi();
		assertTrue(messaggi.get("vai ovest").contains("Nessuna stanza presente nella direzione specificata"));
		assertTrue(messaggi.get("vai nord").contains("Hai vinto!"));
	}
	
	@Test
	public void testPartitaConStanzeSpeciali() {
	    Attrezzo lanterna = new Attrezzo("lanterna", 1);
	    Attrezzo chiave = new Attrezzo("chiave", 1);
	    Attrezzo libro = new Attrezzo("libro", 1);
	    Attrezzo zaino = new Attrezzo("zaino", 2);
	    Attrezzo sasso = new Attrezzo("sasso", 1);

	    buia = new StanzaBuia("buia", "lanterna");
	    bloccata = new StanzaBloccata("bloccata", Direzione.EST, "chiave");
	    magica = new StanzaMagica("magica", 2);

	    this.comandi = Arrays.asList(
	        "vai nord",        // iniziale -> buia (senza lanterna, messaggio buio)
	        "posa lanterna",   // posa lanterna nella stanza buia
	        "vai sud",         // torni in iniziale
	        "vai nord",        // ora lanterna c’è -> descrizione completa
	        "vai est",         // buia -> bloccata (ma bloccata, chiave non presente)
	        "posa chiave",     // poso chiave in buia
	        "vai est",         // ora posso accedere a bloccata
	        "vai est",         // bloccata -> magica
	        "posa libro",      // poso libro
	        "posa zaino",      // poso zaino (effetto magico non attivo ancora)
	        "posa sasso",      // poso sasso (effetto magico attivo, attrezzo trasformato)
	        "fine"
	    );

	    this.io = new IOSimulator(comandi);
	    this.labirinto = new Labirinto.LabirintoBuilder()
	            .addStanzaIniziale(iniziale)
	            .addStanza(buia)
	            .addStanza(bloccata)
	            .addStanza(magica)
	            .addAdiacenza(iniziale, buia, Direzione.NORD)
	            .addAdiacenza(buia, bloccata, Direzione.EST)
	            .addAdiacenza(bloccata, magica, Direzione.EST)
	            .addAttrezzo(lanterna)
	            .addAttrezzo(chiave)
	            .addAttrezzo(libro)
	            .addAttrezzo(zaino)
	            .addAttrezzo(sasso)
	            .getLabirinto();

	    this.gioco = new DiaDia(labirinto, io);
	    this.gioco.gioca();

	    Map<String, List<String>> messaggi = io.getMessaggi();
	    
	    assertTrue(messaggi.get("vai nord").stream().anyMatch(msg -> msg.contains("buio")));
	    assertTrue(messaggi.get("vai est").stream().anyMatch(msg -> msg.contains("Non puoi accedere")));
	}

	
	@Test
	public void testPartitaConPersonaggi() {
	    Mago = new Mago("Merlino", "Sono il mago della stanza", new Attrezzo("bacchetta", 1));
	    Strega = new Strega("Morgana", "Una strega misteriosa");
	    Cane = new Cane("Fido", "Un cane ringhioso");

	    corridoio.setPersonaggio(Mago);
	    bagno.setPersonaggio(Strega);
	    labCampus.setPersonaggio(Cane);

	    this.comandi = Arrays.asList(
	        "vai nord",     // iniziale -> corridoio (mago)
	        "saluta",       // saluto mago
	        "interagisci",  // mago lascia oggetto
	        "vai est",      // corridoio -> bagno (strega)
	        "interagisci",  // non saluto: strega punisce
	        "vai ovest",    // torno a corridoio
	        "vai ovest",    // corridoio -> labCampus (cane)
	        "interagisci",  // cane morde
	        "fine"
	    );

	    this.io = new IOSimulator(comandi);
	    this.labirinto = new Labirinto.LabirintoBuilder()
	            .addStanzaIniziale(iniziale)
	            .addStanza(corridoio)
	            .addStanza(bagno)
	            .addStanza(labCampus)
	            .addAdiacenza(iniziale, corridoio, Direzione.NORD)
	            .addAdiacenza(corridoio, bagno, Direzione.EST)
	            .addAdiacenza(corridoio, labCampus, Direzione.OVEST)
	            .addPersonaggio(Mago)
	            .addPersonaggio(Strega)
	            .addPersonaggio(Cane)
	            .getLabirinto();

	    this.gioco = new DiaDia(labirinto, io);
	    this.gioco.gioca();

	    Map<String, List<String>> messaggi = io.getMessaggi();
	    assertTrue(messaggi.get("interagisci").stream().anyMatch(m -> m.contains("magica azione")));
	    assertTrue(messaggi.get("interagisci").stream().anyMatch(m -> m.contains("punizione")));
	    assertTrue(messaggi.get("interagisci").stream().anyMatch(m -> m.contains("Grrrr")));
	}

	
	@Test
	public void testPartitaConPersonaggiEStanzeSpeciali() {
	    magica = new StanzaMagica("magica", 1);
	    bloccata = new StanzaBloccata("bloccata", Direzione.EST, "osso");
	    buia = new StanzaBuia("buia", "lanterna");

	    Mago = new Mago("Gandalf", "Tu non puoi passare!", new Attrezzo("anello", 1));
	    Strega = new Strega("Circe", "Bella e pericolosa");
	    Cane = new Cane("Cerbero", "Custode dell’Ade");

	    magica.setPersonaggio(Mago);
	    bloccata.setPersonaggio(Strega);
	    buia.setPersonaggio(Cane);

	    this.comandi = Arrays.asList(
	        "vai nord",       // iniziale -> magica
	        "saluta",
	        "interagisci",    // mago lascia anello
	        "vai est",        // magica -> bloccata (bloccata perché manca osso)
	        "posa osso",      // poso osso in magica
	        "vai est",        // ora accesso consentito
	        "saluta",
	        "interagisci",    // strega premia (ci ha salutato)
	        "vai nord",       // spostati dalla strega
	        "vai sud",        // ritorno
	        "vai est",        // -> buia
	        "interagisci",    // cane morde
	        "regala osso",    // cane riceve cibo preferito
	        "fine"
	    );

	    this.io = new IOSimulator(comandi);
	    this.labirinto = new Labirinto.LabirintoBuilder()
	            .addStanzaIniziale(iniziale)
	            .addStanza(magica)
	            .addStanza(bloccata)
	            .addStanza(buia)
	            .addAdiacenza(iniziale, magica, Direzione.NORD)
	            .addAdiacenza(magica, bloccata, Direzione.EST)
	            .addAdiacenza(bloccata, buia, Direzione.EST)
	            .addPersonaggio(Mago)
	            .addPersonaggio(Strega)
	            .addPersonaggio(Cane)
	            .addAttrezzo(new Attrezzo("osso", 1)) // per il cane
	            .addAttrezzo(new Attrezzo("lanterna", 1)) // per la stanza buia
	            .getLabirinto();

	    this.gioco = new DiaDia(labirinto, io);
	    this.gioco.gioca();

	    Map<String, List<String>> messaggi = io.getMessaggi();
	    assertTrue(messaggi.get("interagisci").stream().anyMatch(m -> m.contains("magica azione") || m.contains("punizione") || m.contains("Grrrr")));
	    assertTrue(messaggi.get("regala osso").stream().anyMatch(m -> m.contains("cibo preferito")));
	}

}
