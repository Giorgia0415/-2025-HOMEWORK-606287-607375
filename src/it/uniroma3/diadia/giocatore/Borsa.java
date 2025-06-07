package it.uniroma3.diadia.giocatore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import it.uniroma3.diadia.Config;
import it.uniroma3.diadia.attrezzi.Attrezzo;

/**
 * La borsa possiede degli attrezzi
 * e gestisce la loro rimozione o il loro deposito all'interno di essa.
 * 
 * @author docente di POO
 */

public class Borsa {
	
	private Map<String, Attrezzo> attrezzi;
	private int pesoMax;
	
	/* COSTRUTTORI */
	
	public Borsa() {
		this(Config.getPesoMaxBorsa());//questa riga richiama l'altro costruttore Borsa(int pesoMax) nel caso in cui non venga specificato un pesoMax
	}
	
	public Borsa(int pesoMax) {
		this.pesoMax=pesoMax;
		this.attrezzi = new HashMap<>();
	}
	
	public int getPesoMax() {
		return this.pesoMax;
	}
	
	/* restituisce il peso attuale della borsa */
	public int getPeso() {
		int peso=0;
		
		for(Attrezzo a:this.attrezzi.values()) {
			peso+=a.getPeso();
		}

		return peso;
	}
	
	/* ATTREZZI */
	
	public boolean addAttrezzo(Attrezzo attrezzo) {
		if(attrezzo!=null && (this.getPeso()+attrezzo.getPeso())<=this.getPesoMax())
			//put inserisce il nuovo valore con la chiave specificata e se già ne era presente uno restituisce quello vecchio, null altrimenti
			return this.attrezzi.put(attrezzo.getNome(), attrezzo)==null;
		return false;
	}
	
	public Attrezzo getAttrezzo(String nomeAttrezzo) {
		//get restituisce il valore associato a quella specifica chiave
		return this.attrezzi.get(nomeAttrezzo);
	}
	
	/* rimuove un attrezzo dalla borsa e lo restituisce al giocatore */
	public Attrezzo removeAttrezzo(String nomeAttrezzo) {
		//remove rimuove la chiave-valore associata a quella chiave e restituisce il valore rimosso
		return this.attrezzi.remove(nomeAttrezzo);
	}
	
	public boolean hasAttrezzo(String nomeAttrezzo) {
		return this.attrezzi.containsKey(nomeAttrezzo);
	}
	
	public boolean isEmpty() {
		return this.attrezzi.size() == 0;
	}
	
	/* ORDINAMENTO ATTREZZI */
	
	public List<Attrezzo> getContenutoOrdinatoPerPeso() {
		List<Attrezzo> lista=new ArrayList<>();
		
		//classe anonima locale che ordina per peso
		Comparator<Attrezzo> comp=new Comparator<Attrezzo>() {

			@Override
			public int compare(Attrezzo a1, Attrezzo a2) {
				if(a1.getPeso()!=a2.getPeso())
					return a1.getPeso()-a2.getPeso();
				return a1.getNome().compareTo(a2.getNome());//se i pesi sono uguali ordina per nome
			}
		};
		
		lista.addAll(this.attrezzi.values());
		Collections.sort(lista, comp);
		
		return lista;
	}
	
	public SortedSet<Attrezzo> getContenutoOrdinatoPerNome() {
		return new TreeSet<>(this.attrezzi.values());
	}
	
	public Map<Integer, Set<Attrezzo>> getContenutoRaggruppatoPerPeso() {
		Set<Attrezzo> set;
		Map<Integer, Set<Attrezzo>> mappa=new HashMap<>();
		
		for(Attrezzo a: this.attrezzi.values()) {
			set=mappa.get(a.getPeso());
			if(set==null)
				set=new TreeSet<>();
			set.add(a);
			mappa.put(a.getPeso(), set);
		}
		
		return mappa;
	}
	
	public SortedSet<Attrezzo> getSortedSetOrdinatoPerPeso() {
		
		Comparator<Attrezzo> comp=new Comparator<Attrezzo>() {

			@Override
			public int compare(Attrezzo a1, Attrezzo a2) {
				if(a1.getPeso()!=a2.getPeso())
					return a1.getPeso()-a2.getPeso();
				return a1.getNome().compareTo(a2.getNome());
			}
		};
		
		SortedSet<Attrezzo> set=new TreeSet<>(comp);
		set.addAll(this.attrezzi.values());
		
		return set;
	}
	
	/* TO STRING */
	
	public String toString() {
		//una StringBuilder può essere modificata nel corso dell'esecuzione a differenza di una String
		StringBuilder s = new StringBuilder();
		if(!this.isEmpty()) {
			s.append("Contenuto borsa (" + this.getPeso() + " kg/" + this.getPesoMax() + " kg): ");
			s.append(this.attrezzi);
		} else {
			s.append("Borsa vuota");
		}
		
		return s.toString();
	}
}
