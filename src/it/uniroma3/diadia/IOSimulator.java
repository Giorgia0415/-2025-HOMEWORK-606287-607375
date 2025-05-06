package it.uniroma3.diadia;

public class IOSimulator implements IO {
	private String[] arrayDiLetture;
	private int indiceLetture;
	private String[] arrayDiMessaggi;
	private int indiceMessaggi;
	
	/**
	 * il costruttore riceve le righe lette dall'esterno e le inizializza
	 * 
	 * @param arrayDiLetture memorizza le righe da leggere dall'input
	 * @param arrayDiMessaggi memorizza le righe da stampare in output
	 */
	public IOSimulator(String[] arrayDiLetture) {
		this.arrayDiLetture=arrayDiLetture;
		this.arrayDiMessaggi=new String[1000];
		this.indiceLetture=0;
		this.indiceMessaggi=0;
	}
	
	/**
	 * salva i messaggi da stampare a video
	 */
	@Override
	public void mostraMessaggio(String msg) {
		if(this.indiceMessaggi<this.arrayDiMessaggi.length) {
			this.arrayDiMessaggi[this.indiceMessaggi]=msg;
			this.indiceMessaggi++;
		}
	}
	
	/**
	 * restituisce l'ultima istruzione inserita da input
	 */
	@Override
	public String leggiRiga() {
		String riga=null;
		if(this.indiceLetture<this.arrayDiLetture.length) {
			riga=this.arrayDiLetture[this.indiceLetture];
			this.indiceLetture++;
		}
		return riga;
	}
	
	public String[] getArrayDiMessaggi() {
		return this.arrayDiMessaggi;
	}

}
