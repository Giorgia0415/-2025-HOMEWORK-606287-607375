package it.uniroma3.diadia.comandi;

import it.uniroma3.diadia.IO;
import it.uniroma3.diadia.Partita;
import it.uniroma3.diadia.personaggi.AbstractPersonaggio;

public class ComandoSaluta extends AbstractComando {
	
	private static final String MESSAGGIO_CON_CHI="Chi dovrei salutare?...";
	
	private String messaggio;
	
	public ComandoSaluta() {
		super("saluta", null, null);
	}
	
	public ComandoSaluta(String nome, String parametro, IO io) {
		super(nome, parametro, io);
	}
	
	public String getMessaggio() {
		return this.messaggio;
	}
	
	/**
	 * esegue il comando saluta nei confronti del personaggio richiesto
	 */
	@Override
	public void esegui(Partita partita) {
		AbstractPersonaggio personaggio=partita.getLabirinto().getStanzaCorrente().getPersonaggio();
		if(personaggio!=null) {
			this.messaggio=personaggio.saluta();
		} else {
			this.messaggio=MESSAGGIO_CON_CHI;
		}
		super.getIO().mostraMessaggio(this.messaggio);
	}

}
