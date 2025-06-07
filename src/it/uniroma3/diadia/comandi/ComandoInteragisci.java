package it.uniroma3.diadia.comandi;

import it.uniroma3.diadia.IO;
import it.uniroma3.diadia.Partita;
import it.uniroma3.diadia.personaggi.AbstractPersonaggio;

public class ComandoInteragisci extends AbstractComando {

	private static final String MESSAGGIO_CON_CHI="Con chi dovrei interagire?...";
	
	private String messaggio;
	
	public ComandoInteragisci() {
		super("interagisci", null, null);
	}
	
	public ComandoInteragisci(String nome, String parametro, IO io) {
		super(nome, parametro, io);
	}
	
	public String getMessaggio() {
		return this.messaggio;
	}
	
	/**
	 * esegue il comando interagisci invocato nella partita corrente
	 * nei confronti del personaggio richiesto
	 */
	@Override
	public void esegui(Partita partita) {
		AbstractPersonaggio personaggio=partita.getLabirinto().getStanzaCorrente().getPersonaggio();
		if(personaggio!=null) {
			this.messaggio=personaggio.agisci(partita);
		} else {
			this.messaggio=MESSAGGIO_CON_CHI;
		}
		super.getIO().mostraMessaggio(this.messaggio);
	}

}
