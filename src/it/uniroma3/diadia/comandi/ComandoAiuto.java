package it.uniroma3.diadia.comandi;

import it.uniroma3.diadia.IO;
import it.uniroma3.diadia.Partita;

public class ComandoAiuto implements Comando {
	private IO io;
	private String[] elencoComandi = {"vai", "aiuto", "fine", "prendi", "posa", "guarda"};
	private String[] descrizioneComandi = {"Raggiunge la stanza nella direzione specificata",
										   "Mostra l'elenco dei comandi",
										   "Termina la partita",
										   "Prende un attrezzo dalla stanza e lo mette nella borsa",
										   "Prende un attrezzo dalla borsa e lo lascia nella stanza",
										   "Mostra lo stato attuale della partita"};
	
	@Override
	public String getNome() {
		return "aiuto";
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
	 * esegue il comando aiuto
	 * mostra tutti i possibili comandi
	 */
	@Override
	public void esegui(Partita partita) {
		for(int i=0; i<elencoComandi.length; i++) {
			io.mostraMessaggio(elencoComandi[i]+": "+descrizioneComandi[i]);
		}
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
