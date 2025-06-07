package it.uniroma3.diadia.ambienti;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.uniroma3.diadia.attrezzi.Attrezzo;

/**
 * Classe Stanza - una stanza in un gioco di ruolo.
 * Una stanza e' un luogo fisico nel gioco.
 * E' collegata ad altre stanze attraverso delle uscite.
 * Ogni uscita e' associata ad una direzione.
 * Può contenere degli oggetti.
 * 
 * @author docente di POO 
 * @see Attrezzo
 * @version base
*/

public class StanzaProtected {
	
	static final private int NUMERO_MASSIMO_ATTREZZI = 10;
	
	private String nome;
	
    protected Map<String, Attrezzo> attrezzi;
    
    private Map<Direzione, Stanza> stanzeAdiacenti;
    
	private List<Direzione> direzioni;
    
    /**
     * Costruttore
     * Crea una stanza. Non ci sono stanze adiacenti, non ci sono attrezzi.
     * @param nome il nome della stanza
     */
    public StanzaProtected(String nome) {
        this.nome = nome;
        this.direzioni = new ArrayList<>();
        this.stanzeAdiacenti = new HashMap<>();
        this.attrezzi = new HashMap<>();
    }
    
    /**
     * Restituisce il nome della stanza.
     * @return il nome della stanza
     */
    public String getNome() {
        return this.nome;
    }
    
    /* STANZE ADIACENTI */
    
    /**
     * Imposta una stanza adiacente.
     *
     * @param direzione direzione in cui sara' posta la stanza adiacente.
     * @param stanza stanza adiacente nella direzione indicata dal primo parametro.
     */
    public void impostaStanzaAdiacente(Direzione direzione, Stanza stanza) {
        this.stanzeAdiacenti.put(direzione, stanza);
    }

    /**
     * Restituisce la stanza adiacente nella direzione specificata
     * @param direzione
     */
	public Stanza getStanzaAdiacente(Direzione direzione) {
        return this.stanzeAdiacenti.get(direzione);
	}

    /* ATTREZZI */

    /**
     * Restituisce la collezione di attrezzi presenti nella stanza.
     * @return la collezione di attrezzi nella stanza.
     */
    public Map<String, Attrezzo> getAttrezzi() {
        return this.attrezzi; //restituendo l'intero array si potrà scegliere dopo a quale attrezzo accedere
    }
    
    /**
     * Restituisce l'attrezzo nomeAttrezzo se presente nella stanza.
	 * @param nomeAttrezzo
	 * @return l'attrezzo presente nella stanza.
     * 		   null se l'attrezzo non e' presente.
	 */
	public Attrezzo getAttrezzo(String nomeAttrezzo) {
		return this.attrezzi.get(nomeAttrezzo);
	}

    /**
     * Mette un attrezzo nella stanza.
     * @param attrezzo l'attrezzo da mettere nella stanza.
     * @return true se riesce ad aggiungere l'attrezzo, false atrimenti.
     */
    public boolean addAttrezzo(Attrezzo attrezzo) {
    	if(attrezzo!=null && this.attrezzi.size()<NUMERO_MASSIMO_ATTREZZI) {
    		this.attrezzi.put(attrezzo.getNome(), attrezzo);
    		return this.attrezzi.containsValue(attrezzo);
	    }
        return false;
        
    }
    
    /**
	* Controlla se un attrezzo esiste nella stanza (uguaglianza sul nome).
	* @return true se l'attrezzo esiste nella stanza, false altrimenti.
	*/
	public boolean hasAttrezzo(String nomeAttrezzo) {
		return this.attrezzi.containsKey(nomeAttrezzo);
	}
	
	/**
	 * Rimuove un attrezzo dalla stanza (ricerca in base al nome).
	 * @param nomeAttrezzo
	 * @return true se l'attrezzo e' stato rimosso, false altrimenti
	 */
	public boolean removeAttrezzo(String nomeAttrezzo) {
		if(nomeAttrezzo!=null && this.attrezzi.containsKey(nomeAttrezzo)) {
			this.attrezzi.remove(nomeAttrezzo);
			return !this.attrezzi.containsKey(nomeAttrezzo);
		} else {
			return false;
		}
	}
	
	/* DIREZIONI */
	
	public List<Direzione> getDirezioni() {
		return this.direzioni;
    }
	
	/* TOSTRING() */

   /**
	* Restituisce una rappresentazione stringa di questa stanza,
	* stampadone la descrizione, le uscite e gli eventuali attrezzi contenuti
	* @return la rappresentazione stringa
	*/
    public String toString() {
    	StringBuilder risultato = new StringBuilder();
    	risultato.append(this.nome);
    	
    	risultato.append("\nUscite: ");
    	risultato.append(this.direzioni);
    	
    	risultato.append("\nAttrezzi nella stanza: ");
    	risultato.append(this.attrezzi);
    	
    	return risultato.toString();
    }
    
    /**
     * Restituisce la descrizione della stanza.
     * @return la descrizione della stanza
     */
    public String getDescrizione() {
        return this.toString();
    }

}
