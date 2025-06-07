package it.uniroma3.diadia.comandi;

import it.uniroma3.diadia.IO;
import it.uniroma3.diadia.Partita;
import it.uniroma3.diadia.ambienti.Direzione;
import it.uniroma3.diadia.ambienti.Stanza;

public class ComandoVai extends AbstractComando {
	
	public ComandoVai() {
		super("vai", null, null);
	}
	
	public ComandoVai(String nome, String parametro, IO io) {
		super(nome, parametro, io);
	}
	
	/**
	 * esegue il comando vai
	 * si sposta nella stanza nella direzione inserita
	 */
	@Override
	public void esegui(Partita partita) {
		
		if(super.getParametro()==null) {
			super.getIO().mostraMessaggio("Dove vuoi andare? Devi specificare una direzione");
			return;
		}
		
		try {
			Stanza prossimaStanza=partita.getLabirinto().getStanzaCorrente().getStanzaAdiacente(Direzione.valueOf(super.getParametro().toUpperCase()));
			if(prossimaStanza==null) {
				super.getIO().mostraMessaggio("Nessuna stanza presente nella direzione specificata");//direzione inesistente significa che in quella direzione non è stata settata nessuna stanza nel labirinto
				return;
			} else {
				partita.getLabirinto().setStanzaCorrente(prossimaStanza);//se la stanza nella direzione inserita esiste vi si sposta
				partita.getGiocatore().setCfu(partita.getGiocatore().getCfu()-1);//ogni volta che cambia stanza perde un cfu
				
				super.getIO().mostraMessaggio("Ti sei spostato in "+partita.getLabirinto().getStanzaCorrente().getNome());
				super.getIO().mostraMessaggio(partita.getLabirinto().getStanzaCorrente().getDescrizione());
			}
		} catch (IllegalArgumentException e) {
			super.getIO().mostraMessaggio("Direzione inesistente");
		}

	}

}
