package it.uniroma3.diadia.giocatore;

import it.uniroma3.diadia.attrezzi.Attrezzo;
import it.uniroma3.diadia.*;

/**
 * La borsa possiede degli attrezzi
 * e gestisce la loro rimozione o il loro deposito all'interno di essa.
 * 
 * @author docente di POO
 */

public class Borsa {
	static final public int DEFAULT_PESO_MAX_BORSA = 10;
	
	private Attrezzo[] attrezzi;
	private int numeroAttrezzi;
	private int pesoMax;
	
	/* COSTRUTTORI */
	
	public Borsa() {
		this(DEFAULT_PESO_MAX_BORSA);//questa riga richiama l'altro costruttore Borsa(int pesoMax) nel caso in cui non venga specificato un pesoMax
	}
	
	public Borsa(int pesoMax) {
		this.pesoMax=pesoMax;
		this.attrezzi = new Attrezzo[10];
		this.numeroAttrezzi=0;
	}
	
	public int getPesoMax() {
		return this.pesoMax;
	}
	
	/* restituise il peso attuale della borsa */
	public int getPeso() {
		int peso=0;
		for(Attrezzo a : this .attrezzi) {
			if(a!=null) {
				peso+=a.getPeso();
			}
		}
		return peso;
	}
	
	/* ATTREZZI */
	
	public boolean addAttrezzo(Attrezzo attrezzo) {
		if(attrezzo==null)
			return false;
		
		// se il nuovo attrezzo sommato al peso della borsa è troppo alto non viene aggiunto 
		if(this.getPeso() + attrezzo.getPeso() > this.getPesoMax()) {
			return false;
		}
		// se c'è gia il numero massimo di attrezzi non viene aggiunto quello nuovo
		if(this.numeroAttrezzi==10) {
			return false;
		}
		
		this.attrezzi[this.numeroAttrezzi] = attrezzo;
		this.numeroAttrezzi++;
		return true;
	}
	
	public Attrezzo getAttrezzo(String nomeAttrezzo) {
		for(Attrezzo a : this.attrezzi) {
			if(a!=null) {
				if(a.getNome().equals(nomeAttrezzo)) {
					return a;
				}
			}
		}
		return null;
	}
	
	/* rimuove un attrezzo dalla borsa e lo restituisce al giocatore */
	public Attrezzo removeAttrezzo(String nomeAttrezzo, IOConsole c) {
		Attrezzo a=null;
		if(this.hasAttrezzo(nomeAttrezzo)) {
			a=this.getAttrezzo(nomeAttrezzo);
			c.mostraMessaggio("Attrezzo preso dalla borsa");
			
			//procede a rimuovere l'attrezzo dalla borsa
			for(int i=0; i<this.attrezzi.length; i++) {
				if(attrezzi[i]!=null)
					if(attrezzi[i].getNome().equals(nomeAttrezzo)) {
						attrezzi[i]=null;//quando si rimuovono gli attrezzi e po se ne riaggiungono
									     //la posizione delle celle degli array cambia e si possono alternare celle con attrezzo a celle null
										 //per questo poi è bene scorrere tutto l'array per fare le operazioni correttamente
						this.numeroAttrezzi--;
					}
			}
		} else {
			c.mostraMessaggio("Attrezzo non presente in borsa");
		}
		
		return a;
	}
	
	public boolean hasAttrezzo(String nomeAttrezzo) {
		return this.getAttrezzo(nomeAttrezzo)!=null;
	}
	
	public boolean isEmpty() {
		return this.numeroAttrezzi == 0;
	}
	
	public String toString() {
		StringBuilder s = new StringBuilder();
		if(!this.isEmpty()) {
			s.append("Contenuto borsa (" + this.getPeso() + " kg/" + this.getPesoMax() + " kg): ");
			for(Attrezzo a : attrezzi) {
				if(a!=null)
					s.append(a.toString() + " ");
			}
		} else {
			s.append("Borsa vuota");
		}
		
		return s.toString();
	}
}
