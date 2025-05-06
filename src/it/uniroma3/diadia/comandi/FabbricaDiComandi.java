package it.uniroma3.diadia.comandi;

public interface FabbricaDiComandi {
	
	/**
	 * in base all'istruzione ricevuta restituisce il comando corrispondente
	 * 
	 * @param istruzione è l'istruzione ricevua in input dall'utente
	 * @return il comando richiesto
	 */
	public Comando costruisciComando(String istruzione);
}
