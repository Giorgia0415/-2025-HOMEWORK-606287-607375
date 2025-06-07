package it.uniroma3.diadia.ambienti;

import it.uniroma3.diadia.attrezzi.Attrezzo;

public class StanzaMagica extends Stanza {
	private static final int SOGLIA_MAGICA_DEFAULT=3;
	private int sogliaMagica;
	private int contatoreAttrezziPosati;
	
	/* COSTRUTTORI */
	
	public StanzaMagica(String nome) {
		this(nome, SOGLIA_MAGICA_DEFAULT);
	}
	
	public StanzaMagica(String nome, int sogliaMagica) {
		super(nome);
		this.sogliaMagica=sogliaMagica;
		this.contatoreAttrezziPosati=0;
	}
	
	/* METODI OVERRIDE */
	
	@Override
	public boolean addAttrezzo(Attrezzo attrezzo) {
		this.contatoreAttrezziPosati++;//nel caso della stanza magica ogni attezzo aggiunto conta per attivare l'effetto magico
		
		if(this.contatoreAttrezziPosati>this.sogliaMagica) {
			attrezzo=this.modificaAttrezzo(attrezzo);
		}
		
		return super.addAttrezzo(attrezzo);//per aggiungere l'attrezzo richiama il metodo della classe base, in quanto pur possedento le sue stesse variabili non vi può accede in quanto private
	}
	
	@Override
	public boolean isMagica() {
		return true;
	}
	
	/* METODI AGGIUNTIVI */
	
	private Attrezzo modificaAttrezzo(Attrezzo attrezzo) {
		StringBuilder nomeInvertito=new StringBuilder(attrezzo.getNome());
		//effetti magici
		int pesoX2=attrezzo.getPeso()*2;
		nomeInvertito=nomeInvertito.reverse();
		
		attrezzo=new Attrezzo(nomeInvertito.toString(), pesoX2);
		return attrezzo;
	}

}
