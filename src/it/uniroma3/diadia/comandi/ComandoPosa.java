package it.uniroma3.diadia.comandi;

import it.uniroma3.diadia.IO;
import it.uniroma3.diadia.Partita;
import it.uniroma3.diadia.attrezzi.Attrezzo;

public class ComandoPosa implements Comando {
	private IO io;
	String nomeAttrezzo;
	
	@Override
	public String getNome() {
		return "posa";
	}

	@Override
	public String getParametro() {
		return this.nomeAttrezzo;
	}
	
	/**
	 * imposta il parametro del comando
	 * ovvero il nome dell'attrezzo che si vuole posare
	 */
	@Override
	public void setParametro(String parametro) {
		this.nomeAttrezzo=parametro;
	}
	
	/**
	 * esegue il comando posa
	 * prende un attrezzo dalla borsa e lo posa nella stanza
	 */
	@Override
	public void esegui(Partita partita) {
		
		if(nomeAttrezzo==null) {
			io.mostraMessaggio("Che attrezzo vuoi posare?");
			return;
		}
		
		Attrezzo attrezzoCercato=partita.getGiocatore().getBorsa().getAttrezzo(nomeAttrezzo);
		if(attrezzoCercato!=null) {
			partita.getGiocatore().getBorsa().removeAttrezzo(nomeAttrezzo);//rimuove l'attrezzo trovato dalla borsa...
			partita.getLabirinto().getStanzaCorrente().addAttrezzo(attrezzoCercato);//...e lo mette nella stanza
			
			io.mostraMessaggio(attrezzoCercato.getNome()+" preso dalla borsa e posato in "+partita.getLabirinto().getStanzaCorrente().getNome());
			
			return;
		}
		
		io.mostraMessaggio("Attrezzo non presente in borsa");

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
