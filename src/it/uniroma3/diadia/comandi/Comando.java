package it.uniroma3.diadia.comandi;

import it.uniroma3.diadia.IO;
import it.uniroma3.diadia.Partita;

/**
 * Questa interfaccia modella i metodi di un comando.
 * ci sono diversi tipi di comandi che implementano questa interfaccia
 * ognuno con diverse istanze
 *
 * @author  docente di POO
 * @version base
 */

public interface Comando {
	
	public String getNome();
	
	public String getParametro();
	
	/**
	 * set parametro del comando
	 * @param parametro
	 */
	public void setParametro(String parametro);
	
	/**
	 * esecuzione del comando in base al tipo dinamico del comando assegnato in FabbricaDiComandiFisarmonica
	 * @param partita è la partita attuale in cui eseguire il comando
	 */
	public void esegui(Partita partita);
	
	/**
	 * setta l'istanza per input/output ricevendola da DiaDia
	 * @param io è l'istanza inizializzata dentro il metodo DiaDia.main()
	 */
	public void setIO(IO io);

}
