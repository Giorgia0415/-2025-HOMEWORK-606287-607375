package it.uniroma3.diadia.comandi;

import it.uniroma3.diadia.IO;

public interface FabbricaDiComandi {
	
	/**
	 * in base all'istruzione ricevuta restituisce il comando corrispondente
	 * 
	 * @param istruzione è l'istruzione ricevuta in input dall'utente
	 * @return il comando richiesto
	 */
	public AbstractComando costruisciComando(String istruzione, IO io);
}
