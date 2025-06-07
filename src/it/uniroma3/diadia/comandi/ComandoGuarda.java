package it.uniroma3.diadia.comandi;

import it.uniroma3.diadia.IO;
import it.uniroma3.diadia.Partita;

public class ComandoGuarda extends AbstractComando {
	
	public ComandoGuarda() {
		super("guarda", null, null);
	}
	
	public ComandoGuarda(String nome, String parametro, IO io) {
		super(nome, parametro, io);
	}
	
	/**
	 * esegue il comando guarda
	 * stampa un resoconto della situazione
	 */
	@Override
	public void esegui(Partita partita) {
		super.getIO().mostraMessaggio("Ti trovi in: ");
		super.getIO().mostraMessaggio(partita.getLabirinto().getStanzaCorrente().getDescrizione());
		super.getIO().mostraMessaggio(partita.getGiocatore().toString());
	}

}
