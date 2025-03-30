package it.uniroma3.diadia;

import java.util.Scanner;

import it.uniroma3.diadia.ambienti.Stanza;
import it.uniroma3.diadia.attrezzi.Attrezzo;

/**
 * Classe principale di diadia, un semplice gioco di ruolo ambientato al dia.
 * Per giocare crea un'istanza di questa classe e invoca il metodo gioca
 *
 * Questa e' la classe principale crea e istanzia tutte le altre
 *
 * @author  docente di POO 
 *         (da un'idea di Michael Kolling and David J. Barnes) 
 *          
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
	
	static final private String[] elencoComandi = {"vai", "aiuto", "fine", "prendi", "posa"};

	private Partita partita;
	private Scanner scannerDiLinee;//dichirando scannerDiLinee come variabile globale la si può chiudere a fine gioco senza problemi

	public DiaDia() {
		this.partita = new Partita();
	}

	public void gioca() {
		String istruzione; 

		System.out.println(MESSAGGIO_BENVENUTO);
		System.out.println("\nTi trovi in:\n" + this.partita.getLabirinto().getStanzaCorrente() + "\n" + this.partita.getGiocatore());
		
		scannerDiLinee = new Scanner(System.in);//da l'avvio all'input dalla tastiera e lo mette dentro scannerDiLinee
				
		do	{
			istruzione = scannerDiLinee.nextLine();//prende (continuamente) la riga successiva(la riga termina cliccando invio) che legge da tastiera...
		} while (!processaIstruzione(istruzione)); //...e la manda al metodo processaIstruzione() tramite questo while
												   //da processaIstruzione() vengono invocati tutti gli eventuali altri metododi...
												   //...e poi restituito il true o false che fa capire se bisogna continuare a leggere l'input(le righe) o meno
		
		scannerDiLinee.close();
	}   


	/**
	 * Processa una istruzione 
	 *
	 * @return true se l'istruzione e' eseguita e il gioco finisce, false altrimenti e continua a leggere istruzioni
	 */
	private boolean processaIstruzione(String istruzione) { //riceve l'istruzione da gioca()...
		Comando comandoDaEseguire = new Comando(istruzione);//...e la mette dentro comandoDaEseguire
		
		if(comandoDaEseguire.getNome() == null) {
			return false;
		}
		
		if (comandoDaEseguire.getNome().equals("fine")) {
			this.fine(); 
			return true;
		} else if (comandoDaEseguire.getNome().equals("aiuto")) {
			this.aiuto();
		} else if (comandoDaEseguire.getNome().equals("vai")) {
			this.vai(comandoDaEseguire.getParametro());
		} else if(comandoDaEseguire.getNome().equals("prendi") ) {
			this.prendi(comandoDaEseguire.getParametro());
		} else if(comandoDaEseguire.getNome().equals("posa")) {
			this.posa(comandoDaEseguire.getParametro());
		} else {
			System.out.println("Comando sconosciuto");
		}
		
		if (this.partita.vinta()) {
			System.out.println("Hai vinto!");
			return true;
		} else if(this.partita.isFinita()) {
			System.out.println("Partita finita! Game over!");
			return true;
		} else
			return false;
	}   

	// implementazioni dei comandi dell'utente:
	
	/**
	 * Comando "Fine".
	 */
	private void fine() {
		System.out.println("Grazie di aver giocato!");// si desidera smettere
	}
	
	/**
	 * Stampa informazioni di aiuto.
	 */
	private void aiuto() {
		for(int i=0; i< elencoComandi.length; i++) 
			System.out.print(elencoComandi[i]+" ");
		System.out.println();
	}

	/**
	 * Cerca di andare in una direzione. Se c'e' una stanza ci entra 
	 * e ne stampa il nome, altrimenti stampa un messaggio di errore
	 */
	private void vai(String direzione) {
		
		if(direzione==null) {
			System.out.println("Dove vuoi andare ?");
			//bisogna prendere un nuovo input per la direzione
			do
				direzione = scannerDiLinee.nextLine();
			while(direzione==null || direzione=="");
		}
		
		//prende la stanza nella direzione desiderata
		Stanza prossimaStanza = null;
		prossimaStanza = this.partita.getLabirinto().getStanzaCorrente().getStanzaAdiacente(direzione);
		
		if (prossimaStanza == null)
			System.out.println("Direzione inesistente");
		else {
			this.partita.getLabirinto().setStanzaCorrente(prossimaStanza); // si è spostato nella stanza desiderata
			int cfu = this.partita.getGiocatore().getCfu()-1;//ogni volta che si sposta esaurisce un cfu
			this.partita.getGiocatore().setCfu(cfu);
		}
		
		//stampa una descrizione della situazione
		System.out.println(partita.getLabirinto().getStanzaCorrente().getDescrizione() + "\n" + this.partita.getGiocatore());
	}

	
	
	/**
	 * Prende un attrezzo da una stanza e lo mette nella borsa
	 * 
	 * @param nomeAttrezzo il nome dell'attrezzo che si vuole prendere
	 * @return true se l'attrezzo è stato preso, false altrimenti
	 */
	private boolean prendi(String nomeAttrezzo) {
		
		if(nomeAttrezzo == null) {
			System.out.println("Che attrezzo vuoi prendere? ");
			
			do 
				nomeAttrezzo=scannerDiLinee.nextLine();
			while(nomeAttrezzo==null || nomeAttrezzo=="");
		}
		
		//cerca l'attrezzo che si vuole prendere
		for(Attrezzo a : this.partita.getLabirinto().getStanzaCorrente().getAttrezzi()) {
			if(a!=null)
				if(a.getNome().equals(nomeAttrezzo)) {
					this.partita.getGiocatore().getBorsa().addAttrezzo(a);//mette l'attrezo nella borsa...
					this.partita.getLabirinto().getStanzaCorrente().removeAttrezzo(nomeAttrezzo);//...e lo rimuove dalla stanza
					
					System.out.println("Attrezzo " + a.getNome() + " preso e messo nella borsa");
					//stampa un riassunto della situazione
					System.out.println(this.partita.getLabirinto().getStanzaCorrente() + "\n" + this.partita.getGiocatore());
					return true;
				}
		}
		
		System.out.println("Attrezzo non presente nella stanza");
		//stampa un riassunto della situazione
		System.out.println(this.partita.getLabirinto().getStanzaCorrente() + "\n" + this.partita.getGiocatore());
		return false;
	}
	
	/**
	 * Prende un attrezzo dalla borsa e lo lascia nella stanza
	 * 
	 * @param nomeAttrezzo il nome dell'attrezzo che si vuole posare
	 * @return true se l'attrezzo è stato posato, false altrimenti
	 */
	private boolean posa(String nomeAttrezzo) {
		
		if(nomeAttrezzo == null) {
			System.out.println("Che attrezzo vuoi posare? ");
			
			do
				nomeAttrezzo=scannerDiLinee.nextLine();
			while(nomeAttrezzo == null || nomeAttrezzo == "");
		}
		
		//cerca nella borsa l'attrezzo da posare
		Attrezzo a = this.partita.getGiocatore().getBorsa().getAttrezzo(nomeAttrezzo);
		if(a!=null) {
			this.partita.getLabirinto().getStanzaCorrente().addAttrezzo(a);//lascia l'attrezzo nella stanza...
			this.partita.getGiocatore().getBorsa().removeAttrezzo(nomeAttrezzo);//...e lo rimuove dalla borsa
			System.out.println("Attrezzo posato in " + this.partita.getLabirinto().getStanzaCorrente().getNome());
			//stampa un riassunto della situazione
			System.out.println(this.partita.getLabirinto().getStanzaCorrente() + "\n" + this.partita.getGiocatore());
			return true;
		} else {
			System.out.println("Attrezzo non presente in borsa");
			//stampa un riassunto della situazione
			System.out.println(this.partita.getLabirinto().getStanzaCorrente() + "\n" + this.partita.getGiocatore());
			return false;
		}
	}

	public static void main(String[] argc) {
		DiaDia gioco = new DiaDia();
		gioco.gioca();
	}
}