package it.uniroma3.diadia.comandi;

import it.uniroma3.diadia.IO;
import it.uniroma3.diadia.Partita;

public class ComandoNonValido implements Comando {
	private IO io;
	
	@Override
	public String getNome() {
		return "NonValido";
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
	 * esegue il comando quando non è stato inserito nessuno dei comandi validi
	 */
	@Override
	public void esegui(Partita partita) {
		io.mostraMessaggio("Comando non valido");
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
