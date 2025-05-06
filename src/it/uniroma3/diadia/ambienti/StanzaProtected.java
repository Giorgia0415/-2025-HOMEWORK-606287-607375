package it.uniroma3.diadia.ambienti;

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
	
	static final private int NUMERO_MASSIMO_DIREZIONI = 4;
	static final private int NUMERO_MASSIMO_ATTREZZI = 10;
	
	private String nome;
	
    protected Attrezzo[] attrezzi;
    protected int numeroAttrezzi;
    
    private Stanza[] stanzeAdiacenti;
    private int numeroStanzeAdiacenti;
    
	private String[] direzioni;
    
    /**
     * Costruttore
     * Crea una stanza. Non ci sono stanze adiacenti, non ci sono attrezzi.
     * @param nome il nome della stanza
     */
    public StanzaProtected(String nome) {
        this.nome = nome;
        this.numeroStanzeAdiacenti = 0;
        this.numeroAttrezzi = 0;
        this.direzioni = new String[NUMERO_MASSIMO_DIREZIONI]; //l'array delle direzioni non viene inizializzato con le 4 direzioni
        													   //poichè le direzioni vengono inserite man mano che si inserisce una stanza in ogni direzione
        this.stanzeAdiacenti = new Stanza[NUMERO_MASSIMO_DIREZIONI];
        this.attrezzi = new Attrezzo[NUMERO_MASSIMO_ATTREZZI];
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
    public void impostaStanzaAdiacente(String direzione, Stanza stanza) {
        boolean aggiornato = false;
        //controlla se la direzione inserita come parametro già esiste e aggiona la stanzaAdiacente
    	for(int i=0; i<this.direzioni.length; i++)
        	if (direzione.equals(this.direzioni[i])) { 
        		this.stanzeAdiacenti[i] = stanza;
        		aggiornato = true;
        	}

    	//se non ha aggiornato la stanza vuol dire nella direzione inserita come parametro ancora non era presente nessuna stanzaAdiacente
    	//quindi si procede ad inserire la stanza nella nuova direzione data
    	//!ATTENZIONE che direzione può essere qualsiasi stringa in input poichè non c'è alcun controllo sulle direzioni (es. che possano essere solo "nord", "sud", "est", "ovest")
    	//ma non c'è pericolo durante l'esecuzione poichè l'aggiunta delle stanze la fa solo il programmatore nel codice
    	//e mai l'utente in fase di esecuzione poichè non c'è nessun comando che gli permetta di modificare il labirinto!
    	if (!aggiornato)
    		if (this.numeroStanzeAdiacenti < NUMERO_MASSIMO_DIREZIONI) {
    			this.direzioni[numeroStanzeAdiacenti] = direzione;//aggiunge la direzione nella cella successiva all'ultima direzione inserita
    			this.stanzeAdiacenti[numeroStanzeAdiacenti] = stanza;//inserisce la stanza affianco all'ultima inserita
    		    this.numeroStanzeAdiacenti++;
    		}
    	//non bisogna porre aggiornato=true perchè in questo caso è stata fatta un'aggiunta e non un aggiornamento
    }

    /**
     * Restituisce la stanza adiacente nella direzione specificata
     * @param direzione
     */
	public Stanza getStanzaAdiacente(String direzione) {
        Stanza stanza = null;
		for(int i=0; i<this.numeroStanzeAdiacenti; i++) //basta il numero di stanzeAdiacenti in quanto corrisponde al numero di direzioni della stanza
        	if (this.direzioni[i].equals(direzione))
        		stanza = this.stanzeAdiacenti[i];
        return stanza;
	}

    /* ATTREZZI */

    /**
     * Restituisce la collezione di attrezzi presenti nella stanza.
     * @return la collezione di attrezzi nella stanza.
     */
    public Attrezzo[] getAttrezzi() {
        return this.attrezzi; //restituendo l'intero array si potrà scegliere dopo a quale attrezzo accedere
    }
    
    /**
     * Restituisce l'attrezzo nomeAttrezzo se presente nella stanza.
	 * @param nomeAttrezzo
	 * @return l'attrezzo presente nella stanza.
     * 		   null se l'attrezzo non e' presente.
	 */
	public Attrezzo getAttrezzo(String nomeAttrezzo) {
		Attrezzo attrezzoCercato;
		attrezzoCercato = null;
		for (Attrezzo attrezzo : this.attrezzi) {
			if(attrezzo!=null)
				if (attrezzo.getNome().equals(nomeAttrezzo))
					attrezzoCercato = attrezzo;
		}
		return attrezzoCercato;	
	}

    /**
     * Mette un attrezzo nella stanza.
     * @param attrezzo l'attrezzo da mettere nella stanza.
     * @return true se riesce ad aggiungere l'attrezzo, false atrimenti.
     */
    public boolean addAttrezzo(Attrezzo attrezzo) {
    	if(attrezzo!=null)
	        if (this.numeroAttrezzi < NUMERO_MASSIMO_ATTREZZI) {
	        	this.attrezzi[numeroAttrezzi] = attrezzo;
	        	this.numeroAttrezzi++;
	        	return true;
	        }
        return false;
        
    }
    
    /**
	* Controlla se un attrezzo esiste nella stanza (uguaglianza sul nome).
	* @return true se l'attrezzo esiste nella stanza, false altrimenti.
	*/
	public boolean hasAttrezzo(String nomeAttrezzo) {
		boolean trovato;
		trovato = false;
		for (Attrezzo attrezzo : this.attrezzi) {
			if(attrezzo!=null)
			if (attrezzo.getNome().equals(nomeAttrezzo))
				trovato = true;
		}
		return trovato;
	}
	
	/**
	 * Rimuove un attrezzo dalla stanza (ricerca in base al nome).
	 * @param nomeAttrezzo
	 * @return true se l'attrezzo e' stato rimosso, false altrimenti
	 */
	public boolean removeAttrezzo(String nomeAttrezzo) {
		int i=0;
		for(Attrezzo attrezzo : this.attrezzi) {
			if(attrezzo!=null)
				if(attrezzo.getNome().equals(nomeAttrezzo)) {
					this.attrezzi[i]=null;
					this.numeroAttrezzi--;
					return true;
				}
			i++; //questo incremento va alla fine delle istruzioni nel for altrimenti libera la cella di attrezzi[] abagliata
		}
		
		return false;
	}
	
	/* DIREZIONI */
	
	public String[] getDirezioni() {
		String[] direzioni = new String[this.numeroStanzeAdiacenti];
	    for(int i=0; i<this.numeroStanzeAdiacenti; i++)
	    	direzioni[i] = this.direzioni[i];
	    return direzioni;
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
    	for (String direzione : this.direzioni)
    		if (direzione!=null)
    			risultato.append(" " + direzione);
    	
    	risultato.append("\nAttrezzi nella stanza: ");
    	for (Attrezzo attrezzo : this.attrezzi) {
    		if(attrezzo!=null) {
    			risultato.append(attrezzo.toString()+" ");
    		}
       	}
    	
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
