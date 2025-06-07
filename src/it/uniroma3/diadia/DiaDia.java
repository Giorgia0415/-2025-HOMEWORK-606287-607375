package it.uniroma3.diadia;

import it.uniroma3.diadia.ambienti.Labirinto;
import it.uniroma3.diadia.comandi.AbstractComando;
import it.uniroma3.diadia.comandi.FabbricaDiComandi;
import it.uniroma3.diadia.comandi.FabbricaDiComandiRiflessiva;

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
	
	public DiaDia(Labirinto labirinto, IO io) {
		this.partita = new Partita(labirinto);//quando inizia DiaDia viene generata dal costruttore una nuova partita
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
	
		// Alla fine della partita chiudo lo scanner
	    if(io instanceof IOConsole) {
	        ((IOConsole)io).chiudi();
	    }
	}   


	/**
	 * Processa una istruzione 
	 *
	 * @return lo stato della partita dopo aver processato un'istruzione
	 */
	private boolean processaIstruzione(String istruzione) { 
		AbstractComando comandoDaEseguire;//comandoDaEseguire è polimorfo, assume un comportamento diverso in base all'istruzione
		FabbricaDiComandi factory = new FabbricaDiComandiRiflessiva();//il tipo dinamico è FabbricaDiComandiRiflessiva così andrà a prendere l'implementazione dentro questa classe
		
		comandoDaEseguire=factory.costruisciComando(istruzione, this.io);//qui viene restituito il tipo dinamico di comandoDaEseguire
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
		Labirinto labirinto=new Labirinto.LabirintoBuilder().getLabirinto();
		DiaDia gioco = new DiaDia(labirinto, io);//essendo main un metodo statico per accedere a membri non statici bisogna prima creare un'istanza della classe
		gioco.gioca();
	}
}