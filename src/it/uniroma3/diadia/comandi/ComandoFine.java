package it.uniroma3.diadia.comandi;

import it.uniroma3.diadia.IO;
import it.uniroma3.diadia.Partita;

public class ComandoFine extends AbstractComando {
	
	public ComandoFine() {
		super("fine", null, null);
	}
	
	public ComandoFine(String nome, String parametro, IO io) {
		super(nome, parametro, io);
	}
	
	/**
	 * esegue il comando fine
	 * interrompe la partita
	 */
	@Override
	public void esegui(Partita partita) {
		super.getIO().mostraMessaggio("Grazie di aver giocato!");
		partita.setFinita();
	}

}
