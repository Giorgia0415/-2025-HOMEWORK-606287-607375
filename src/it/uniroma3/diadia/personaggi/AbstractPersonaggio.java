package it.uniroma3.diadia.personaggi;

import it.uniroma3.diadia.Partita;
import it.uniroma3.diadia.attrezzi.Attrezzo;

       //una classe astratta non può essere istanziata direttamente
public abstract class AbstractPersonaggio {
	private String nome;
	private String presentazione;
	private boolean haSalutato;
	
	public AbstractPersonaggio(String nome, String presentazione) {
		this.nome=nome;
		this.presentazione=presentazione;
		this.haSalutato=false;
	}
	
	/* METODI CONCRETI */
	//di questi metodi le classi estese ereditano tutta l'implementazione
	
	public String getNome() {
		return this.nome;
	}
	
	public String getPresentazione() {
		return this.presentazione;
	}
	
	public boolean haSalutato() {
		return this.haSalutato;
	}
	
	public String saluta() {
		StringBuilder risposta=new StringBuilder("Ciao, io sono ");
		risposta.append(this.getNome()+". ");
		
		if(!haSalutato) {
			risposta.append(this.presentazione);
		} else {
			risposta.append("Ci siamo già presentati!");
		}
		this.haSalutato=true;
		
		return risposta.toString();
	}
	
	@Override
	public String toString() {
		return this.getNome();
	}
	
	/* METODI ASTRATTI */
	//di questi metodi le classi estese ereditano la segnatura
	//i metodi astratti non possiedono corpo
	
	//il comportamento dipende dal tipo specifico di personaggio
	abstract public String agisci(Partita partita);
	
	abstract public String riceviRegalo(Attrezzo attrezzo, Partita partita);
	
}
