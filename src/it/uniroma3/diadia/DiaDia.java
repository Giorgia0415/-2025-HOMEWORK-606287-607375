package it.uniroma3.diadia;

import it.uniroma3.diadia.comandi.Comando;
import it.uniroma3.diadia.comandi.FabbricaDiComandi;
import it.uniroma3.diadia.comandi.FabbricaDiComandiFisarmonica;

/**
 * Classe principale di diadia, un semplice gioco di ruolo ambientato al dia.
 * Per giocare crea un'istanza di questa classe e invoca il metodo gioca
 *
 * Questa e' la classe principale crea e istanzia tutte le altre
 *
 * @author  docente di POO 
 *         (da un'idea di Michael Kolling and David J. Barnes) 
 *          
 * @see IOConsole         
 * @version base
 */

public class DiaDia {

	static final private String MESSAGGIO_BENVENUTO = ""+
			"Ti trovi nell'Universita', ma oggi e' diversa dal solito...\n" +
			"Meglio andare al piu' presto in biblioteca a studiare. Ma dov'e'?\n"+
			"I locali sono popolati da strani personaggi, " +
			"alcuni amici, altri... chissa!\n"+
			"Ci sono attrezzi che potrebbero servirti nell'impresa:\n"+
			"puoi raccoglierli, usarli, posarli quando ti sembrano inutili\n" +
			"o regalarli se pensi che possano ingraziarti qualcuno.\n\n"+
			"Per conoscere le istruzioni usa il comando 'aiuto'.";

	private Partita partita;
	private IO io;
	
	/* COSTRUTTORE */
	
	public DiaDia(IO io) {
		this.partita = new Partita();//quando inizia DiaDia viene generata dal costruttore una nuova partita
		this.io=io;
	}
	
	/* METODI DI DIADIA */
	
	public void gioca() {
		String istruzione; 
		
		io.mostraMessaggio(MESSAGGIO_BENVENUTO);
		io.mostraMessaggio("\nTi trovi in:\n" + this.partita.getLabirinto().getStanzaCorrente() + "\n" + this.partita.getGiocatore());
		
		do
			istruzione=io.leggiRiga();
		while(!processaIstruzione(istruzione));//finché la partita non è finita continua a leggere istruzioni da tastiera
	}   


	/**
	 * Processa una istruzione 
	 *
	 * @return lo stato della partita dopo aver processato un'istruzione
	 */
	private boolean processaIstruzione(String istruzione) { 
		Comando comandoDaEseguire;//comandoDaEseguire è polimorfo, assume un comportamento diverso in base all'istruzione
		FabbricaDiComandi factory = new FabbricaDiComandiFisarmonica();//il tipo dinamico è FabbricaDiComandiFisarmonica così andrà a prendere l'implementazione dentro questa classe
		
		comandoDaEseguire=factory.costruisciComando(istruzione);//qui viene restituito il tipo dinamico di comandoDaEseguire
		comandoDaEseguire.setIo(io);
		comandoDaEseguire.esegui(this.partita);//in base al comando ricevuto prima da factory si sceglie da quale classe andare a prende l'implementazione da usare
		
		/* situazioni che si possono verificare al di fuori dei comandi */
		if(this.partita.vinta()) {
			io.mostraMessaggio("Hai vinto!");
		} else if(this.partita.persa()) {
			io.mostraMessaggio("Game over! Hai esaurito i CFU...");
		}
		
		return this.partita.isFinita();//restituisce lo stato della partita
	}
	
	/* MAIN */

	public static void main(String[] argc) {
		IO io = new IOConsole();
		DiaDia gioco = new DiaDia(io);
		gioco.gioca();
	}
}