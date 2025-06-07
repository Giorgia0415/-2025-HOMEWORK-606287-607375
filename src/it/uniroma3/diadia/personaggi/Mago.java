package it.uniroma3.diadia.personaggi;

import it.uniroma3.diadia.Partita;
import it.uniroma3.diadia.attrezzi.Attrezzo;

public class Mago extends AbstractPersonaggio {
	
	private static final String MESSAGGIO_DONO="Sei un vero simpaticone, con una mia magica azione "+
											   "con una mia magica azione, troverai un nuovo oggetto per il tuo borsone!";
	
	private static final String MESSAGGIO_SCUSE="Mi spiace, ma non ho più nulla...";
	
	private Attrezzo attrezzo;

	public Mago(String nome, String presentazione, Attrezzo attrezzo) {
		super(nome, presentazione);
		this.attrezzo=attrezzo;
	}
	
	public Attrezzo getAttrezzo() {
		return this.attrezzo;
	}
	
	/**
	 * se possiede un attrezzo lo dona al giocatore lasciandolo nella stanza
	 * altriementi non gli lascia nulla
	 * 
	 * @return msg il messaggio che deve comunicare al giocatore ciò che è successo
	 */
	@Override
	public String agisci(Partita partita) {
		String msg;
		if(this.attrezzo!=null) {
			partita.getLabirinto().getStanzaCorrente().addAttrezzo(this.attrezzo);
			this.attrezzo=null;
			msg=MESSAGGIO_DONO;
		} else {
			msg=MESSAGGIO_SCUSE;
		}
		
		return msg;
	}

	@Override
	public String riceviRegalo(Attrezzo attrezzo, Partita partita) {
		StringBuilder msg=new StringBuilder();
		if(attrezzo!=null) {
			//java restituisce una copia del riferimento
			//quindi l'unico modo per modificare gli attributi dell'oggetto originale è con un setter
			attrezzo.setPeso(attrezzo.getPeso()/2);
			partita.getLabirinto().getStanzaCorrente().addAttrezzo(attrezzo);
			msg.append("Io non so che farmene...");
			msg.append("Ma se vuoi riprendertelo avrai una sorpresa \"leggera\"");
		} else {
			msg.append("Non mi stai donando niente...");
		}
		
		return msg.toString();
	}

}
