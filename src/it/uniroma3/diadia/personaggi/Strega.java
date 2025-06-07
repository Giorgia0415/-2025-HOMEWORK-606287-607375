package it.uniroma3.diadia.personaggi;

import it.uniroma3.diadia.Partita;
import it.uniroma3.diadia.ambienti.Stanza;
import it.uniroma3.diadia.attrezzi.Attrezzo;

public class Strega extends AbstractPersonaggio {
	
	private static final String MESSAGGIO_PREMIO="Che piacere conoscerti! Per ricompensarti della tua educazione ecco un premio per agevolarti la prosecuzione";
	
	private static final String MESSAGGIO_PUNIZIONE="Nemmeno un saluto! Che maleducazione! Questo ti costerà la mia punizione!";

	public Strega(String nome, String presentazione) {
		super(nome, presentazione);
	}
	
	/**
	 * seleziona la stanza in cui bisogna spostare il giocatore
	 * 
	 * @param haSalutato verifica che è soddisfatto il requisito per ottenere le grazie della strega
	 * @return stanzaFinale la stanza in cui viene spostato il giocatore
	 */
	public Stanza stanzaFinale(boolean haSalutato, Partita partita) {
		Stanza stanzaFinale=null;
		int numAttrezzi;
		
		if(haSalutato) {
			numAttrezzi=-1;
			for(Stanza s:partita.getLabirinto().getStanzaCorrente().getStanzeAdiacenti().values()) {
				if(s!=null)
					if(s.getAttrezzi().size()>numAttrezzi) {
						numAttrezzi=s.getAttrezzi().size();
						stanzaFinale=s;
					}
			}
		} else {
			numAttrezzi=11;
			for(Stanza s:partita.getLabirinto().getStanzaCorrente().getStanzeAdiacenti().values()) {
				if(s!=null)
					if(s.getAttrezzi().size()<numAttrezzi) {
						numAttrezzi=s.getAttrezzi().size();
						stanzaFinale=s;
					}
			}
		}
		
		return stanzaFinale;
	}

	@Override
	public String agisci(Partita partita) {
		String msg;
		AbstractPersonaggio strega=partita.getLabirinto().getStanzaCorrente().getPersonaggio();
		if(strega.haSalutato()) {
			partita.getLabirinto().setStanzaCorrente(stanzaFinale(strega.haSalutato(), partita));
			msg=MESSAGGIO_PREMIO;
		} else {
			partita.getLabirinto().setStanzaCorrente(stanzaFinale(strega.haSalutato(), partita));
			msg=MESSAGGIO_PUNIZIONE;
		}
		
		return msg;
	}

	@Override
	public String riceviRegalo(Attrezzo attrezzo, Partita partita) {
		StringBuilder msg=new StringBuilder();
		if(attrezzo!=null) {
			msg.append("Non riavrai più indietro il tuo attrezzo...AHAHAHAHAHAHAH");
		} else {
			msg.append("Mi prendi in giro?!");
		}
		return msg.toString();
	}

}
