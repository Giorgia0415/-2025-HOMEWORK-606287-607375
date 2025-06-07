package it.uniroma3.diadia.ambienti;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.uniroma3.diadia.Partita;
import it.uniroma3.diadia.ambienti.Labirinto.LabirintoBuilder;
import it.uniroma3.diadia.attrezzi.Attrezzo;
import it.uniroma3.diadia.personaggi.*;

public class LabirintoBuilderTest {
	private LabirintoBuilder labirintoBuilder;
	private Stanza StanzaIniziale, StanzaVincente;
	private Stanza biblioteca, stanzaMagica, stanzaBloccata, corridoio, corridoioBloccato, stanzaBuia;
	private Attrezzo spada, spadina, sedia, libroAntico, chiave, lanterna;
	private AbstractPersonaggio Mago, Strega, Cane;
	private Partita partita;

	@BeforeEach
	public void setUp() throws Exception {
		labirintoBuilder = new LabirintoBuilder();
		StanzaIniziale = new Stanza("Atrio");
		StanzaVincente = new Stanza("Uscita");
		
		stanzaMagica=new StanzaMagica("stanzaMagica", 1);
		stanzaBloccata=new StanzaBloccata("stanzaBloccata", Direzione.NORD, "chiave");
		biblioteca=new Stanza("Biblioteca");
		corridoio=new Stanza("corridoio");
		corridoioBloccato=new StanzaBloccata("corridoioBloccato", Direzione.NORD, "chiave");
		stanzaBuia=new StanzaBuia("stanzaBuia", "lanterna");
		
		spada=new Attrezzo("spada", 1);
		spadina=new Attrezzo("spadina", 3);
		sedia=new Attrezzo("sedia", 1);
		libroAntico=new Attrezzo("libro antico", 5);
		chiave=new Attrezzo("chiave", 1);
		lanterna=new Attrezzo("lanterna", 1);
		
		Mago=new Mago("Mago", "Ti dono un oggetto", chiave);
		Strega=new Strega("Strega", "Il mio comportamento dipende da te");
		Cane=new Cane("Cane", "Non ti avvicinare");
		
		partita=new Partita(labirintoBuilder.getLabirinto());
		
	}

	@Test
	public void testMonolocale() {
		Labirinto monolocale = labirintoBuilder
				.addStanzaIniziale(StanzaIniziale)
				.addStanzaVincente(StanzaIniziale)
				.getLabirinto();
	assertEquals(StanzaIniziale.getNome(),monolocale.getStanzaIniziale().getNome());
	assertEquals(StanzaIniziale.getNome(),monolocale.getStanzaVincente().getNome());
	}
	
	@Test
	public void testMonolocaleConAttrezzo() {
		Labirinto monolocale = labirintoBuilder
				.addStanzaIniziale(StanzaIniziale).addAttrezzo(spada)
				.addStanzaVincente(StanzaIniziale).addAttrezzo(spadina)
				.getLabirinto();
		assertEquals(StanzaIniziale.getNome(),monolocale.getStanzaIniziale().getNome());
		assertEquals(StanzaIniziale.getNome(),monolocale.getStanzaVincente().getNome());
		assertEquals("spada",monolocale.getStanzaIniziale().getAttrezzo("spada").getNome());
		assertEquals("spadina",monolocale.getStanzaVincente().getAttrezzo("spadina").getNome());
	}
	
	@Test
	public void testMonolocaleConAttrezzoSingoloDuplicato() {
		Labirinto monolocale = labirintoBuilder
				.addStanzaIniziale(StanzaIniziale)
				.addAttrezzo(spada)
				.addAttrezzo(spada)
				.getLabirinto();
		int size = monolocale.getStanzaIniziale().getAttrezzi().size();
		assertTrue(size==1);
		assertEquals(("["+spada+"]").toString(), monolocale.getStanzaIniziale().getAttrezzi().values().toString());
	}
	
	@Test
	public void testBilocale() {
		Labirinto bilocale = labirintoBuilder
				.addStanzaIniziale(StanzaIniziale)
				.addStanzaVincente(StanzaVincente)
				.addAdiacenza(StanzaIniziale, StanzaVincente, Direzione.NORD)
				.getLabirinto();
		assertEquals(bilocale.getStanzaVincente(),bilocale.getStanzaIniziale().getStanzaAdiacente(Direzione.NORD));
		assertEquals(Arrays.asList(Direzione.NORD), Arrays.asList(bilocale.getStanzaIniziale().getDirezioni().toArray()));
		assertEquals(Arrays.asList(Direzione.SUD), Arrays.asList(bilocale.getStanzaVincente().getDirezioni().toArray()));
	}
	
	@Test
	public void testTrilocale(){
		Labirinto trilocale = labirintoBuilder
				.addStanzaIniziale(StanzaIniziale).addAttrezzo(sedia)
				.addStanza(biblioteca)
				.addAdiacenza(StanzaIniziale, biblioteca, Direzione.SUD)
				.addAttrezzo(libroAntico)
				.addStanzaVincente(StanzaVincente)
				.addAdiacenza(biblioteca, StanzaVincente, Direzione.EST)
				.getLabirinto();	
		assertEquals(StanzaIniziale.getNome(), trilocale.getStanzaIniziale().getNome());
		assertEquals(StanzaVincente.getNome(), trilocale.getStanzaVincente().getNome());
		assertEquals("Biblioteca",trilocale.getStanzaIniziale().getStanzaAdiacente(Direzione.SUD).getNome());
	}
	
	@Test
	public void testTrilocaleConStanzaDuplicata() {
		Stanza stanzaGenerica=new Stanza("stanza generica");
		labirintoBuilder
				.addStanzaIniziale(StanzaIniziale)
				.addStanza(stanzaGenerica)
				.addStanza(stanzaGenerica)
				.addAdiacenza(StanzaIniziale, stanzaGenerica, Direzione.NORD)
				.getLabirinto();
		assertTrue(labirintoBuilder.getListaStanze().size()==2);
	}
	
	@Test
	public void testQuattroAdiacenti() {
		Stanza stanza1=new Stanza("stanza1");
		Stanza stanza2=new Stanza("stanza2");
		Stanza stanza3=new Stanza("stanza3");
		Stanza stanza4=new Stanza("stanza4");
		Labirinto maze = labirintoBuilder
				.addStanzaIniziale(StanzaIniziale)
				.addStanza(stanza1)
				.addStanza(stanza2)
				.addStanza(stanza3)
				.addStanza(stanza4)
				.addAdiacenza(StanzaIniziale, stanza1, Direzione.NORD)
				.addAdiacenza(StanzaIniziale, stanza2, Direzione.OVEST)
				.addAdiacenza(StanzaIniziale, stanza3, Direzione.SUD)
				.addAdiacenza(StanzaIniziale, stanza4, Direzione.EST)
				.getLabirinto();
		assertTrue(maze.getStanzaIniziale().getStanzeAdiacenti().size()<=4);
		Map<Direzione, Stanza> mappa = new HashMap<>();
		mappa.put(Direzione.NORD, new Stanza("stanza 1"));
		mappa.put(Direzione.OVEST, new Stanza("stanza 2"));
		mappa.put(Direzione.SUD, new Stanza("stanza 3"));
		mappa.put(Direzione.EST, new Stanza("stanza 4"));
		mappa.get(Direzione.NORD).impostaStanzaAdiacente(Direzione.SUD, StanzaIniziale);
		mappa.get(Direzione.SUD).impostaStanzaAdiacente(Direzione.NORD, StanzaIniziale);
		mappa.get(Direzione.EST).impostaStanzaAdiacente(Direzione.OVEST, StanzaIniziale);
		mappa.get(Direzione.OVEST).impostaStanzaAdiacente(Direzione.EST, StanzaIniziale);
		assertEquals(mappa, maze.getStanzaIniziale().getStanzeAdiacenti());
	}
	
	@Test
	public void testImpostaStanzaInizialeCambiandola() {
		Stanza nuovaIniziale=new Stanza("nuova iniziale");
		Labirinto maze = labirintoBuilder
				.addStanzaIniziale(this.StanzaIniziale)
				.addStanza(nuovaIniziale)
				.addStanzaIniziale(nuovaIniziale)
				.getLabirinto();
		assertEquals(nuovaIniziale.getNome(), maze.getStanzaIniziale().getNome());
	}
	
	@Test
	public void testAggiuntaAttrezziAStanze_Iniziale() {
		Attrezzo attrezzo1=new Attrezzo("attrezzo", 1);
		Labirinto maze = this.labirintoBuilder
				.addStanzaIniziale(this.StanzaIniziale)
				.addAttrezzo(attrezzo1)
				.getLabirinto();
		Attrezzo attrezzo2 = new Attrezzo("attrezzo", 1);
		assertEquals(attrezzo2, maze.getStanzaIniziale().getAttrezzo(attrezzo1.getNome()));
	}
	
	@Test
	public void testAggiuntaAttrezziAStanze_AppenaAggiunte() {
		Attrezzo attrezzo=new Attrezzo("attrezzo", 1);
		Stanza stanza1=new Stanza("stanza1");
		labirintoBuilder
				.addStanzaIniziale(StanzaIniziale)
				.addStanza(stanza1)
				.addAttrezzo(attrezzo)
				.getLabirinto();
		assertTrue(this.labirintoBuilder.getListaStanze().get(labirintoBuilder.getListaStanze().indexOf(stanza1)).getAttrezzi().containsValue(new Attrezzo (attrezzo.getNome(), attrezzo.getPeso())));
		assertEquals(new Attrezzo(attrezzo.getNome(), attrezzo.getPeso()), this.labirintoBuilder.getListaStanze().get(labirintoBuilder.getListaStanze().indexOf(stanza1)).getAttrezzo(attrezzo.getNome()));
	}
	
	@Test
	public void testAggiuntaAttrezzoAStanze_AppenaAggiunteMultiple() {
		Attrezzo attrezzo=new Attrezzo("attrezzo", 1);
		Stanza stanza1=new Stanza("stanza1");
		labirintoBuilder
				.addStanzaIniziale(StanzaIniziale)
				.addStanza(stanza1)
				.addAttrezzo(attrezzo)
				.getLabirinto();
		List<Attrezzo> attrezzi = labirintoBuilder.getListaStanze().get(labirintoBuilder.getListaStanze().indexOf(stanza1)).getListaAttrezzi();
		assertEquals(attrezzo, attrezzi.get(attrezzi.indexOf(attrezzo)));
	}
	
	@Test
	public void testAggiuntaAttrezzoAStanze_MoltepliciAttrezziStessaStanza() {
		Attrezzo attrezzo1=new Attrezzo("attrezzo1", 1);
		Attrezzo attrezzo2=new Attrezzo("attrezzo2", 2);
		Stanza stanza1=new Stanza("stanza1");
		labirintoBuilder
				.addStanza(stanza1)
				.addAttrezzo(attrezzo1)
				.addAttrezzo(attrezzo2)
				.getLabirinto();
		Map<String, Stanza> mappaStanze = labirintoBuilder.getStanze();
		assertEquals(attrezzo2, mappaStanze.get(stanza1.getNome()).getAttrezzo(attrezzo2.getNome()));
		assertEquals(attrezzo1, mappaStanze.get(stanza1.getNome()).getAttrezzo(attrezzo1.getNome()));
	}
	
	
	@Test  //verifico che gli attrezzi vengano aggiunti all'ultima stanza aggiunta
	public void testAggiuntaAttrezzoAStanze_AttrezzoAggiuntoAllaSecondaStanza() {
		Attrezzo attrezzo1=new Attrezzo("attrezzo1", 1);
		Attrezzo attrezzo2=new Attrezzo("attrezzo2", 2);
		Stanza stanza1=new Stanza("stanza1");
		Stanza stanza2=new Stanza("stanza2");
		labirintoBuilder
				.addStanza(stanza1)
				.addStanza(stanza2)
				.addAttrezzo(attrezzo1)
				.addAttrezzo(attrezzo2);
		Map<String, Stanza> mappaStanze = labirintoBuilder.getStanze();
		assertEquals(attrezzo1, mappaStanze.get(stanza2.getNome()).getAttrezzo(attrezzo1.getNome()));
		assertEquals(attrezzo2, mappaStanze.get(stanza2.getNome()).getAttrezzo(attrezzo2.getNome()));
	}
	
	@Test
	public void testAggiuntaAttrezzoAStanze_PrimoAttrezzoInUnaStanzaSecondoAttrezzoSecondaStanza() {
		Attrezzo attrezzo1=new Attrezzo("attrezzo1", 1);
		Attrezzo attrezzo2=new Attrezzo("attrezzo2", 2);
		Stanza stanza1=new Stanza("stanza1");
		Stanza stanza2=new Stanza("stanza2");
		labirintoBuilder
				.addStanza(stanza1)
				.addAttrezzo(attrezzo1)
				.addStanza(stanza2)
				.addAttrezzo(attrezzo2);
		Map<String, Stanza> mappaStanze = labirintoBuilder.getStanze();
		assertEquals(attrezzo1, mappaStanze.get(stanza1.getNome()).getAttrezzo(attrezzo1.getNome()));
		assertEquals(attrezzo2, mappaStanze.get(stanza2.getNome()).getAttrezzo(attrezzo2.getNome()));
	}
	
	@Test
	public void testLabirintoConStanzaMagica() {
		labirintoBuilder
				.addStanza(stanzaMagica);
		stanzaMagica = labirintoBuilder.getListaStanze().get(labirintoBuilder.getListaStanze().indexOf(stanzaMagica));
		assertTrue(stanzaMagica.isMagica());
	}
	
	@Test
	public void testLabirintoConStanzaMagica_AggiuntaElementoOltreSogliaMagica() {
		Attrezzo attrezzo1=new Attrezzo("attrezzo1", 1);
		Attrezzo attrezzo2=new Attrezzo("attrezzo2", 2);
		StringBuilder nome2Invertito=new StringBuilder();
		nome2Invertito.append(attrezzo2.getNome()).reverse();
		labirintoBuilder
				.addStanza(stanzaMagica)
				.addAttrezzo(attrezzo1)
				.addAttrezzo(attrezzo2);
		Map<String, Stanza> mappaStanze = labirintoBuilder.getStanze();
		assertEquals(new Attrezzo("2ozzertta", 4), mappaStanze.get(stanzaMagica.getNome()).getAttrezzo(nome2Invertito.toString()));
		assertEquals(attrezzo1, mappaStanze.get(stanzaMagica.getNome()).getAttrezzo(attrezzo1.getNome()));
	}
	
	
	@Test
	public void testLabirintoConStanzaBloccata_ConPassepartout() {
		labirintoBuilder
				.addStanzaIniziale(StanzaIniziale)
				.addStanza(stanzaBloccata).addAttrezzo(chiave)
				.addAdiacenza(StanzaIniziale, stanzaBloccata, Direzione.NORD)
				.addStanzaVincente(StanzaVincente)
				.addAdiacenza(stanzaBloccata, StanzaVincente, Direzione.NORD)
				.getLabirinto();
		Stanza stanzaVincente = new Stanza(this.StanzaVincente.getNome());
		stanzaVincente.impostaStanzaAdiacente(Direzione.SUD, stanzaBloccata);
		//Asserisce che in presenza di passepartout, invocato il metodo getStanzaAdiacente(), la stanza bloccata ritorna la corretta adiacenza
		assertEquals(stanzaVincente,labirintoBuilder.getListaStanze().get(labirintoBuilder.getListaStanze().indexOf(stanzaBloccata)).getStanzaAdiacente(Direzione.NORD));	
	}
	
	@Test
	public void testLabirintoConStanzaBloccata_SenzaPassepartout() {
		labirintoBuilder
			.addStanzaIniziale(StanzaIniziale)
			.addStanza(stanzaBloccata)
			.addAdiacenza(StanzaIniziale, stanzaBloccata, Direzione.NORD)
			.addStanzaVincente(StanzaVincente)
			.addAdiacenza(stanzaBloccata, StanzaVincente, Direzione.NORD)
			.getLabirinto();
		//Asserisce che in caso di mancanza di passepartout, invocato il metodo getStanzaAdiacente(), la stanza bloccata ritorna se stessa
		assertEquals(stanzaBloccata,labirintoBuilder.getListaStanze().get(labirintoBuilder.getListaStanze().indexOf(stanzaBloccata)).getStanzaAdiacente(Direzione.NORD));
	}
	
	@Test
	public void testLabirintoCompletoConTutteLeStanze() {
		
		Labirinto labirintoCompleto = this.labirintoBuilder
				.addStanzaIniziale(StanzaIniziale)
				.addStanzaVincente(StanzaVincente)
				.addStanza(corridoio)
				.addAttrezzo(chiave)
				.addAttrezzo(lanterna)
				.addStanza(corridoioBloccato)
				.addStanza(stanzaMagica)
				.addStanza(stanzaBuia)
				.addStanza(new Stanza("Aula 1"))
				.addAdiacenza(StanzaIniziale, corridoio, Direzione.NORD)
				.addAdiacenza(corridoio, corridoioBloccato, Direzione.NORD)
				.addAdiacenza(corridoioBloccato, new Stanza("Aula 1"), Direzione.NORD)
				.addAdiacenza(new Stanza("Aula 1"), StanzaVincente,Direzione.NORD)
				.addAdiacenza(corridoio, stanzaMagica, Direzione.EST)
				.addAdiacenza(corridoio, stanzaBuia, Direzione.OVEST)
				.getLabirinto();
		assertEquals(StanzaIniziale.getNome(), labirintoCompleto.getStanzaIniziale().getNome());
		assertEquals(StanzaVincente.getNome(), labirintoCompleto.getStanzaVincente().getNome());
		
		Stanza corridoio = labirintoCompleto.getStanzaIniziale().getStanzaAdiacente(Direzione.NORD);
		assertEquals(corridoio.getNome(), this.corridoio.getNome());
		assertTrue(corridoio.getDirezioni().containsAll(Arrays.asList(Direzione.OVEST, Direzione.NORD, Direzione.SUD, Direzione.EST)));
		
		Map<Direzione,Stanza> mapAdiacenti = new HashMap<>();
		mapAdiacenti.put(Direzione.NORD ,new Stanza(corridoioBloccato.getNome()));
		mapAdiacenti.put(Direzione.SUD,new Stanza(StanzaIniziale.getNome()));
		mapAdiacenti.put(Direzione.EST, new Stanza(stanzaMagica.getNome()));
		mapAdiacenti.put(Direzione.OVEST,new Stanza(stanzaBuia.getNome()));
		mapAdiacenti.get(Direzione.NORD).impostaStanzaAdiacente(Direzione.SUD, corridoio);
		mapAdiacenti.get(Direzione.SUD).impostaStanzaAdiacente(Direzione.NORD, corridoio);
		mapAdiacenti.get(Direzione.EST).impostaStanzaAdiacente(Direzione.OVEST, corridoio);
		mapAdiacenti.get(Direzione.OVEST).impostaStanzaAdiacente(Direzione.EST, corridoio);
		assertEquals(mapAdiacenti,corridoio.getStanzeAdiacenti());
		assertEquals(Arrays.asList(chiave, lanterna),corridoio.getListaAttrezzi());
	}
	
	@Test
	public void testLabirintoConUnPersonaggio() {
		labirintoBuilder
				.addStanzaIniziale(StanzaIniziale)
				.addStanzaVincente(StanzaVincente)
				.addStanza(stanzaBloccata)
				.addPersonaggio(Mago)
				.getLabirinto();
		assertTrue(labirintoBuilder.getStanze().get(stanzaBloccata.getNome()).isBloccata());
		labirintoBuilder.getLabirinto().setStanzaCorrente(stanzaBloccata);
		Mago.agisci(partita);
		assertTrue(labirintoBuilder.getStanze().get(stanzaBloccata.getNome()).hasAttrezzo(chiave.getNome()));
		assertFalse(labirintoBuilder.getStanze().get(stanzaBloccata.getNome()).isBloccata());
	}

}
