package it.uniroma3.diadia.personaggi;

import it.uniroma3.diadia.Partita;
import it.uniroma3.diadia.attrezzi.Attrezzo;

public class Cane extends AbstractPersonaggio {
	
	private static final String MESSAGGIO="Grrrr!...Sei stato morso ed hai perso un cfu";
	
	private final String ciboPreferito="osso";
	
	public Cane(String nome, String presentazione) {
		super(nome, presentazione);
	}
	
	/**
	 * ad ogni interazione si perde un cfu
	 */
	@Override
	public String agisci(Partita partita) {
		int cfuAttuali=partita.getGiocatore().getCfu();
		partita.getGiocatore().setCfu(cfuAttuali-1);
		return MESSAGGIO;
	}

	@Override
	public String riceviRegalo(Attrezzo attrezzo, Partita partita) {
		StringBuilder msg=new StringBuilder();
		
		if(attrezzo!=null && attrezzo.getNome().equals(ciboPreferito)) {
			msg.append("Il mio cibo preferito! Bau!");
			partita.getLabirinto().getStanzaCorrente().addAttrezzo(new Attrezzo("collare", 2));
			msg.append("\nUn nuovo attrezzo è stato rilasciato nella stanza, guarda per vedere di cosa si tratta");
		} else {
			msg.append("Questo non è il mio cibo preferito! ");
			msg.append(this.agisci(partita));
		}
		
		return msg.toString();
	}

}
