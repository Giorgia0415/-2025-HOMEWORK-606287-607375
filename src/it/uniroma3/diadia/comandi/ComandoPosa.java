package it.uniroma3.diadia.comandi;

import it.uniroma3.diadia.IO;
import it.uniroma3.diadia.Partita;
import it.uniroma3.diadia.attrezzi.Attrezzo;

public class ComandoPosa extends AbstractComando {
	
	public ComandoPosa() {
		super("posa", null, null);
	}
	
	public ComandoPosa(String nome, String parametro, IO io) {
		super(nome, parametro, io);
	}
	
	/**
	 * esegue il comando posa
	 * prende un attrezzo dalla borsa e lo posa nella stanza
	 */
	@Override
	public void esegui(Partita partita) {
		
		if(super.getParametro()==null) {
			super.getIO().mostraMessaggio("Che attrezzo vuoi posare?");
			return;
		}
		
		Attrezzo attrezzoCercato=partita.getGiocatore().getBorsa().getAttrezzo(super.getParametro());
		if(attrezzoCercato!=null) {
			partita.getGiocatore().getBorsa().removeAttrezzo(super.getParametro());//rimuove l'attrezzo trovato dalla borsa...
			partita.getLabirinto().getStanzaCorrente().addAttrezzo(attrezzoCercato);//...e lo mette nella stanza
			
			super.getIO().mostraMessaggio(attrezzoCercato.getNome()+" preso dalla borsa e posato in "+partita.getLabirinto().getStanzaCorrente().getNome());
			
			return;
		}
		
		super.getIO().mostraMessaggio("Attrezzo non presente in borsa");

	}

}
