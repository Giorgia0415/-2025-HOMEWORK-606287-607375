package it.uniroma3.diadia.ambienti;

import it.uniroma3.diadia.attrezzi.Attrezzo;

public class StanzaMagicaProtected extends StanzaProtected {
	private static final int SOGLIA_MAGICA_DEFAULT=3;
	private int sogliaMagica;
	private int contatoreAttrezziPosati;
	
	/* COSTRUTTORI */
	
	public StanzaMagicaProtected(String nome) {
		this(nome, SOGLIA_MAGICA_DEFAULT);
	}
	
	public StanzaMagicaProtected(String nome, int sogliaMagica) {
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
		
		if(attrezzo!=null) {
			//accede alle variabili della classe base che sono protected
			if(this.numeroAttrezzi<this.attrezzi.length) {
				this.attrezzi[this.numeroAttrezzi]=attrezzo;
				this.numeroAttrezzi++;
				return true;
			}
		}
		
		return false;
		//return super.addAttrezzo(attrezzo);//per aggiungere l'attrezzo richiama il metodo della classe base, in quanto pur possedento le sue stesse variabili non vi può accede in qaunto private
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
