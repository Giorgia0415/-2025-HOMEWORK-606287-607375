package it.uniroma3.diadia.comandi;

import it.uniroma3.diadia.IO;
import it.uniroma3.diadia.Partita;

public class ComandoFine implements Comando {
	private IO io;
	
	@Override
	public String getNome() {
		return "fine";
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
	 * esegue il comando fine
	 * interrompe la partita
	 */
	@Override
	public void esegui(Partita partita) {
		io.mostraMessaggio("Grazie di aver giocato!");
		partita.setFinita();
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
