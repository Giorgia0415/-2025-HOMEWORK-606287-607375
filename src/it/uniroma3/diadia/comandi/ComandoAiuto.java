package it.uniroma3.diadia.comandi;

import it.uniroma3.diadia.IO;
import it.uniroma3.diadia.Partita;

public class ComandoAiuto extends AbstractComando {

	private static final String[] elencoComandi = {"vai", "aiuto", "fine", "prendi", "posa", "guarda", "guardaBorsa", "interagisci", "saluta", "regala"};
	private static final String[] descrizioneComandi = {"Raggiunge la stanza nella direzione specificata",
										   				"Mostra l'elenco dei comandi",
										   				"Termina la partita",
										   				"Prende un attrezzo dalla stanza e lo mette nella borsa",
										   				"Prende un attrezzo dalla borsa e lo lascia nella stanza",
										   				"Mostra lo stato attuale della partita",
										   				"Mostra il contenuto della borsa ordinato per: peso(list), nome(set), raggruppato per peso(map)",
										   				"Interagisce con il personaggio presente nella stanza",
										   				"Saluta il personaggio presente nella stanza",
										   				"Rimuove un attrezzo dalla borsa e lo regala al personaggio nella stanza"};
	
	public ComandoAiuto() {
		super("aiuto", null, null);
	}
	
	public ComandoAiuto(String nome, String parametro, IO io) {
		super(nome, parametro, io);
	}
	
	/**
	 * esegue il comando aiuto
	 * mostra tutti i possibili comandi
	 */
	@Override
	public void esegui(Partita partita) {
		for(int i=0; i<elencoComandi.length; i++) {
			super.getIO().mostraMessaggio(elencoComandi[i]+": "+descrizioneComandi[i]);
		}
	}

}
