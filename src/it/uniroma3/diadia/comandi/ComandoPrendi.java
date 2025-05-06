package it.uniroma3.diadia.comandi;

import it.uniroma3.diadia.IO;
import it.uniroma3.diadia.Partita;
import it.uniroma3.diadia.attrezzi.Attrezzo;

public class ComandoPrendi implements Comando {
	private IO io;
	String nomeAttrezzo;
	
	@Override
	public String getNome() {
		return "prendi";
	}

	@Override
	public String getParametro() {
		return this.nomeAttrezzo;
	}
	
	/**
	 * imposta il parametro del comando
	 * ovvero il nome dell'attrezzo che si vuole prendere
	 */
	@Override
	public void setParametro(String nomeAttrezzo) {
		this.nomeAttrezzo=nomeAttrezzo;
	}
	
	/**
	 * esegue il comando prendi
	 * prende un attrezzo dalla stanza e lo mette nella borsa
	 */
	@Override
	public void esegui(Partita partita) {
		
		if(nomeAttrezzo==null) {
			io.mostraMessaggio("Che attrezzo vuoi prendere?");
			return;
		}
		
		Attrezzo attrezzoCercato = partita.getLabirinto().getStanzaCorrente().getAttrezzo(nomeAttrezzo);
		if(attrezzoCercato!=null) {
			partita.getGiocatore().getBorsa().addAttrezzo(attrezzoCercato);//aggiunge l'attrezzo trovato in borsa...
			partita.getLabirinto().getStanzaCorrente().removeAttrezzo(nomeAttrezzo);//...e lo rimuove dalla stanza
					
			io.mostraMessaggio(attrezzoCercato.getNome()+" preso da "+partita.getLabirinto().getStanzaCorrente().getNome()+" e messo in borsa");
					
			return;
		}
		
		io.mostraMessaggio("Attrezzo non presente in "+partita.getLabirinto().getStanzaCorrente().getNome());
	}
	
	/**
	 * setta l'istanza per input/output ricevendola da DiaDia
	 * @param io è l'istanza inizializzata dentro il metodo DiaDia.main()
	 */
	@Override
	public void setIo(IO io) {
		this.io=io;
	}

}
