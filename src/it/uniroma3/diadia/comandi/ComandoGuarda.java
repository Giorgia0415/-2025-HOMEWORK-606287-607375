package it.uniroma3.diadia.comandi;

import it.uniroma3.diadia.IO;
import it.uniroma3.diadia.Partita;

public class ComandoGuarda implements Comando {
	private IO io;
	
	@Override
	public String getNome() {
		return "guarda";
	}

	@Override
	public String getParametro() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void setParametro(String parametro) {
		// TODO Auto-generated method stub

	}
	
	/**
	 * esegue il comando guarda
	 * stampa un resoconto della situazione
	 */
	@Override
	public void esegui(Partita partita) {
		io.mostraMessaggio("Ti trovi in: ");
		io.mostraMessaggio(partita.getLabirinto().getStanzaCorrente().getDescrizione());
		io.mostraMessaggio(partita.getGiocatore().toString());
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
