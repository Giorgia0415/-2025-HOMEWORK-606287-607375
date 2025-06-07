package it.uniroma3.diadia.ambienti;

/**
 * direzione è un enum che rappresenta i quattro punti cardinali
 * 
 * @author io
 */
public enum Direzione {
	/* COSTANTI ENUMERATIVE */
	
	//ogni costante enumerativa rappresenta un'istanza unica dell'enum
	//statica, final, oggetto dell'enum stesso
	NORD(0),//ogni costante ha un valore(gradi) tra parentesi che viene istanziato tramite il costruttore
	SUD(180),
	EST(90),
	OVEST(270);
	
	/* VALORI ASSOCIATI */
	
	private final int gradi;
	
	/* COSTRUTTORE */
	
	//il costruttore di un enum è sempre privato
	private Direzione(int gradi) {//ogni volta che viene istanziata una costante enumerativa(es. NORD) i suo valore gradi viene inizializzato
		this.gradi=gradi;
	}
	
	/* METODI AGGIUNTIVI */
	
	public int getGradi() {
		return this.gradi;
	}
	
	public Direzione opposta(Direzione dir) {
		switch(dir) {
		case NORD: return SUD;
		case SUD: return NORD;
		case EST: return OVEST;
		case OVEST: return EST;
		default: return null;
		}
	}
}
