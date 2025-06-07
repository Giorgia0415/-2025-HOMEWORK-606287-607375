package it.uniroma3.diadia;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IOSimulator implements IO {
	private List<String> comandiDaLeggere;
	private int indiceLetture;
	private Map<String, List<String>> messaggiPerComando;
	private String comandoCorrente;
	
	/**
	 * il costruttore riceve le righe lette dall'esterno e le inizializza
	 * 
	 * @param arrayDiLetture memorizza le righe da leggere dall'input
	 * @param arrayDiMessaggi memorizza le righe da stampare in output
	 */
	public IOSimulator(List<String> listaDiLetture) {
		this.comandiDaLeggere=new ArrayList<>(listaDiLetture);
		this.indiceLetture=0;
		this.messaggiPerComando=new HashMap<>();
	}
	
	/**
	 * salva i messaggi da stampare a video
	 */
	@Override
	public void mostraMessaggio(String msg) {
		if(this.comandoCorrente!=null)
			//aggiunge alla lista dei messaggi stamapati dopo un comando quello nuovo
			this.messaggiPerComando.get(comandoCorrente).add(msg);
	}
	
	/**
	 * restituisce l'ultima istruzione inserita da input
	 */
	@Override
	public String leggiRiga() {
		if(this.indiceLetture<this.comandiDaLeggere.size()) {
			//legge l'ultimo comando inserito e non ancora letto
			this.comandoCorrente=this.comandiDaLeggere.get(indiceLetture++);
			//crea un nuova lista di messaggi per un nuovo comando
			this.messaggiPerComando.putIfAbsent(comandoCorrente, new ArrayList<>());
			return this.comandoCorrente;
		} else
			return null;
	}
	
	public Map<String, List<String>> getMessaggi() {
		return this.messaggiPerComando;
	}

}
