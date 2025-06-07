package it.uniroma3.diadia.giocatore;

import it.uniroma3.diadia.Config;

/**
 * Un giocatore possiede una borsa in cui sono contenuti gli attrezzi
 * e dei cfu che vengono gestiti dal giocatore.
 * 
 * @author io
 */

public class Giocatore {
	
	private int cfu;
	private Borsa borsa;
	
	/* costruttore */
	public Giocatore() {
		this.cfu=Config.getCfuIniziali();
		this.borsa = new Borsa(); //il giocatore inizia la partita con una borsa
	}
	
	public int getCfu() {
		return this.cfu;
	}

	public void setCfu(int cfu) {
		this.cfu = cfu;		
	}
	
	public Borsa getBorsa() {
		return this.borsa;
	}
	
	public void setBorsa(Borsa borsa) {
		this.borsa=borsa;
	}
	
	/**
	 * Stampa lo stato del giocatore
	 */
	public String toString() {
		return "Cfu = " + this.getCfu() + "\n" + this.getBorsa();
		
	}
}
