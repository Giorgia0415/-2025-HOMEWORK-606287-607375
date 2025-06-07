package it.uniroma3.diadia.comandi;

import it.uniroma3.diadia.IO;
import it.uniroma3.diadia.Partita;
import it.uniroma3.diadia.attrezzi.Attrezzo;
import it.uniroma3.diadia.personaggi.AbstractPersonaggio;

public class ComandoRegala extends AbstractComando{
	
	private static final String MESSAGGIO_CON_CHI="A chi dovrei regalare l'attrezzo?...";
	
	private String messaggio;
	
	public ComandoRegala() {
		super("regala", null, null);
	}

	public ComandoRegala(String nome, String parametro, IO io) {
		super(nome, parametro, io);
	}
	
	public String getMessaggio() {
		return this.messaggio;
	}

	@Override
	public void esegui(Partita partita) {
		
		if(super.getParametro()==null) {
			super.getIO().mostraMessaggio("Quale attrezzo vuoi regalare?");
			return;
		}
		
		AbstractPersonaggio personaggio=partita.getLabirinto().getStanzaCorrente().getPersonaggio();
		if(personaggio!=null) {
			Attrezzo attrezzoDaDonare=partita.getGiocatore().getBorsa().removeAttrezzo(super.getParametro());
			this.messaggio=personaggio.riceviRegalo(attrezzoDaDonare, partita);
		} else {
			this.messaggio=MESSAGGIO_CON_CHI;
		}
		super.getIO().mostraMessaggio(this.messaggio);
		
	}

}
