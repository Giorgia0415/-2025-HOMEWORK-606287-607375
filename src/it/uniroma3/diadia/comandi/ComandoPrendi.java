package it.uniroma3.diadia.comandi;

import it.uniroma3.diadia.IO;
import it.uniroma3.diadia.Partita;
import it.uniroma3.diadia.attrezzi.Attrezzo;

public class ComandoPrendi extends AbstractComando {
	
	public ComandoPrendi() {
		super("prendi", null, null);
	}
	
	public ComandoPrendi(String nome, String parametro, IO io) {
		super(nome, parametro, io);
	}

	/**
	 * esegue il comando prendi
	 * prende un attrezzo dalla stanza e lo mette nella borsa
	 */
	@Override
	public void esegui(Partita partita) {
		
		if(super.getParametro()==null) {
			super.getIO().mostraMessaggio("Che attrezzo vuoi prendere?");
			return;
		}
		
		Attrezzo attrezzoCercato = partita.getLabirinto().getStanzaCorrente().getAttrezzo(super.getParametro());
		if(attrezzoCercato!=null) {
			partita.getGiocatore().getBorsa().addAttrezzo(attrezzoCercato);//aggiunge l'attrezzo trovato in borsa...
			partita.getLabirinto().getStanzaCorrente().removeAttrezzo(super.getParametro());//...e lo rimuove dalla stanza
					
			super.getIO().mostraMessaggio(attrezzoCercato.getNome()+" preso da "+partita.getLabirinto().getStanzaCorrente().getNome()+" e messo in borsa");
					
			return;
		}
		
		super.getIO().mostraMessaggio("Attrezzo non presente in "+partita.getLabirinto().getStanzaCorrente().getNome());
	}

}
