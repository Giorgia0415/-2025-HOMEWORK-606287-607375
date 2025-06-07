package it.uniroma3.diadia.ambienti;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.uniroma3.diadia.attrezzi.Attrezzo;
import it.uniroma3.diadia.personaggi.AbstractPersonaggio;

/**
 * Questa classe genera il labirinto della partita
 * e si occupa della locazione del giocatore all'interno delle stanze
 * 
 * @author io
 */

public class Labirinto {
	private Stanza stanzaIniziale;
	private Stanza stanzaCorrente;
	private Stanza stanzaVincente;
	
	private Labirinto() {}
	
	public Labirinto(String nomeFile) throws FormatoFileNonValidoException, FileNotFoundException {
	    CaricatoreLabirinto caricatore;
	    try {
	        caricatore = new CaricatoreLabirinto(nomeFile);
	        caricatore.carica();
	    } catch (IOException e) {
	        throw new FormatoFileNonValidoException("Errore durante la lettura del file: " + e.getMessage());
	    }

	    LabirintoBuilder builder = Labirinto.newBuilder();
	    for (Stanza stanza : caricatore.getStanze().values())
	        builder.addStanza(stanza);

	    builder.addStanzaIniziale(caricatore.getStanzaIniziale());
	    builder.addStanzaVincente(caricatore.getStanzaVincente());

	    // imposta anche gli attrezzi/personaggi se ne hai bisogno
	    Labirinto l = builder.getLabirinto();

	    this.stanzaIniziale = l.getStanzaIniziale();
	    this.stanzaVincente = l.getStanzaVincente();
	    this.stanzaCorrente = l.getStanzaIniziale();
	}

	
	public static LabirintoBuilder newBuilder() {
		return new LabirintoBuilder();
	}
	
	public void setStanzaVincente(Stanza stanzaVincente) {
		this.stanzaVincente=stanzaVincente;
	}
	
	public Stanza getStanzaVincente() {
		return this.stanzaVincente;
	}
	
	/* modifica la locazione del giocatore */
	public void setStanzaCorrente(Stanza stanzaCorrente) {
		this.stanzaCorrente = stanzaCorrente;
	}

	public Stanza getStanzaCorrente() {
		return this.stanzaCorrente;
	}
	
	public void setStanzaIniziale(Stanza stanzaIniziale) {
		this.stanzaIniziale=stanzaIniziale;
	}
	
	public Stanza getStanzaIniziale() {
		return this.stanzaIniziale;
	}
	
	/* ==============================
	 * NESTED CLASS LABIRINTO BUILDER
	 * ==============================
	 *
	 * classe che permette di costruire un labirinto con il method-chaining
	 * grazie alla caratteristiche di restituire la sua stessa istanza alla fine di ogni metodo
	 */
	public static class LabirintoBuilder {
		private Labirinto labirinto;
		private Stanza ultimaStanzaAggiunta;
		private Map<String, Stanza> stanze;
		
		public LabirintoBuilder() {
			this.labirinto=new Labirinto();
			this.stanze=new HashMap<>();
		}
		
		//restituisce il labirinto appena creato
		public Labirinto getLabirinto() {
			this.labirinto.setStanzaCorrente(this.labirinto.getStanzaIniziale());
			return this.labirinto;
		}
		
		public List<Stanza> getListaStanze() {
			return new ArrayList<>(this.stanze.values());
		}
		
		public Map<String,Stanza> getStanze() {
			return this.stanze;
		}
		
		/* STANZE PRINCIPALI */
		
		public LabirintoBuilder addStanzaIniziale(Stanza stanzaIniziale) {
			this.labirinto.setStanzaIniziale(stanzaIniziale);
			this.stanze.put(stanzaIniziale.getNome(), stanzaIniziale);
			this.ultimaStanzaAggiunta=stanzaIniziale;
			return this;
		}
		
		public LabirintoBuilder addStanzaVincente(Stanza stanzaVincente) {
			this.labirinto.setStanzaVincente(stanzaVincente);
			this.stanze.put(stanzaVincente.getNome(), stanzaVincente);
			this.ultimaStanzaAggiunta=stanzaVincente;
			return this;
		}
		
		public LabirintoBuilder addStanza(Stanza stanza) {
			this.stanze.put(stanza.getNome(), stanza);
			this.ultimaStanzaAggiunta=stanza;
			return this;
		}
		
		/* ADIACENZE TRA STANZE */
		
		public LabirintoBuilder addAdiacenza(Stanza stanzaDa, Stanza stanzaA, Direzione dir) {
			//prende lo stesso riferimento alle stanze che sono nel labirinto...
			Stanza da=this.stanze.get(stanzaDa.getNome());
			Stanza a=this.stanze.get(stanzaA.getNome());
			//...e aggiunge l'adiacenza richiesta
			da.impostaStanzaAdiacente(dir, a);
			return this;
		}
		
		/* ATTREZZI */ 
		
		public LabirintoBuilder addAttrezzo(Attrezzo attrezzo) {
			this.ultimaStanzaAggiunta.addAttrezzo(attrezzo);
			return this;
		}
		
		/* PERSONAGGI */
		
		public LabirintoBuilder addPersonaggio(AbstractPersonaggio personaggio) {
			this.ultimaStanzaAggiunta.setPersonaggio(personaggio);
			return this;
		}
		
	}
    
}
