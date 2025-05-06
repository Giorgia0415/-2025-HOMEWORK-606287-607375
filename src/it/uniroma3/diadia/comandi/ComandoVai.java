package it.uniroma3.diadia.comandi;

import it.uniroma3.diadia.IO;
import it.uniroma3.diadia.Partita;
import it.uniroma3.diadia.ambienti.Stanza;

public class ComandoVai implements Comando {
	private IO io;
	private String direzione;
	
	@Override
	public String getNome() {
		return "vai";
	}

	@Override
	public String getParametro() {
		return this.direzione;
	}
	
	/**
	 * imposta il parametro del comando
	 * ovvero la direzione in cui deve andare
	 */
	@Override
	public void setParametro(String parametro) {
		this.direzione=parametro;
	}
	
	/**
	 * esegue il comando vai
	 * si sposta nella stanza nella direzione inserita
	 */
	@Override
	public void esegui(Partita partita) {
		
		if(direzione==null) {
			io.mostraMessaggio("Dove vuoi andare? Devi specificare una direzione");
			return;
		}
		
		Stanza prossimaStanza=partita.getLabirinto().getStanzaCorrente().getStanzaAdiacente(this.direzione);
		if(prossimaStanza==null) {
			io.mostraMessaggio("Direzione inesistente");//direzione inesistente significa che in quella direzione non è stata settata nessuna stanza nel labirinto
			return;
		} else {
			partita.getLabirinto().setStanzaCorrente(prossimaStanza);//se la stanza nella direzione inserita esiste vi si sposta
			partita.getGiocatore().setCfu(partita.getGiocatore().getCfu()-1);//ogni volta che cambia stanza perde un cfu
			
			io.mostraMessaggio("Ti sei spostato in "+partita.getLabirinto().getStanzaCorrente().getNome());
		}

	}
	
	/**
	 * setta l'istanza per input/output ricevendola da DiaDia
	 * @param io è l'istanza inizializzata dentro il metodo DiaDia.main()
	 */
	@Override
	public void setIo(IO io) {
		this.io=io;
	}

}
