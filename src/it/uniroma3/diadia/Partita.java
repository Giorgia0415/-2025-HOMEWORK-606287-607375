package it.uniroma3.diadia;

import it.uniroma3.diadia.ambienti.Labirinto;
import it.uniroma3.diadia.ambienti.Stanza;
import it.uniroma3.diadia.giocatore.Giocatore;

/**
 * Questa classe modella una partita del gioco.
 * Gestisce lo stato della partita.
 * Possiede il giocatore e il labirinto avviando la creazione di quest'ultimo.
 *
 * @author  docente di POO
 * @see Stanza
 * @version base
 */

public class Partita {

	private boolean finita;
	private Labirinto labirinto;
	private Giocatore giocatore;
	
	public Partita(Labirinto labirinto){
		this.finita = false;
		this.giocatore = new Giocatore();
		this.labirinto = labirinto;
	}
	
	public Labirinto getLabirinto() {
		return this.labirinto;
	}
	
	public void setLabirinto(Labirinto labirinto) {
		this.labirinto=labirinto;
	}
	
	public Giocatore getGiocatore() {
		return this.giocatore;
	}
	
	public void setGiocatore(Giocatore giocatore) {
		this.giocatore=giocatore;
	}
	
	/**
	 * Restituisce vero se e solo se la partita e' stata vinta
	 * @return vero se partita vinta
	 */
	public boolean vinta() {
		return this.labirinto.getStanzaCorrente() == this.labirinto.getStanzaVincente();
	}
	
	/**
	 * restituisce vero se il giocatore ha esaurito i cfu
	 * @return vero se la partita è persa
	 */
	public boolean persa() {
		return this.giocatore.getCfu()<=0;
	}

	/**
	 * Restituisce vero se e solo se la partita e' finita
	 * @return vero se partita finita
	 */
	public boolean isFinita() {
		return this.finita || this.persa() || this.vinta();
	}

	/**
	 * Imposta la partita come finita
	 */
	public void setFinita() {
		this.finita = true;
	}
	
	/**
	 * Stampa lo stato della partita
	 */
	public String toString() {
		return "Cfu = " + this.giocatore.getCfu() ;
	}
	
}