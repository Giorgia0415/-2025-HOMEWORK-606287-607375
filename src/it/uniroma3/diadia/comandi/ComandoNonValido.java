package it.uniroma3.diadia.comandi;

import it.uniroma3.diadia.IO;
import it.uniroma3.diadia.Partita;

public class ComandoNonValido extends AbstractComando {
	
	public ComandoNonValido() {
		super("nonValido", null, null);
	}
	
	public ComandoNonValido(String nome, String parametro, IO io) {
		super(nome, parametro, io);
	}
	
	/**
	 * esegue il comando quando non è stato inserito nessuno dei comandi validi
	 */
	@Override
	public void esegui(Partita partita) {
		super.getIO().mostraMessaggio("Comando non valido");
	}

}
