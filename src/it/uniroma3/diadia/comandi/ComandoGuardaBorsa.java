package it.uniroma3.diadia.comandi;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;

import it.uniroma3.diadia.IO;
import it.uniroma3.diadia.Partita;
import it.uniroma3.diadia.attrezzi.Attrezzo;

public class ComandoGuardaBorsa extends AbstractComando {
	private String messaggio;

	public ComandoGuardaBorsa() {
		super("guardaBorsa", null, null);
	}
	
	public ComandoGuardaBorsa(String nome, String parametro, IO io) {
		super(nome, parametro, io);
		this.messaggio=null;
	}
	
	public String getMessaggio() {
		return this.messaggio;
	}

	@Override
	public void esegui(Partita partita) {
		if(super.getParametro()==null) {
			super.getIO().mostraMessaggio("Come vuoi visualizzare il contenuto della borsa?");
			return;
		}
		
		StringBuilder risultato=new StringBuilder();
		
		String parametro=super.getParametro();
		
		if(parametro.equals("set")) {
			SortedSet<Attrezzo> set=partita.getGiocatore().getBorsa().getContenutoOrdinatoPerNome();
			Iterator<Attrezzo> it=set.iterator();
			
			risultato.append("{");
			while(it.hasNext()) {
				risultato.append(it.next().getNome());
				if(it.hasNext())
					risultato.append(", ");
			}
			risultato.append("}");
			
			this.messaggio=risultato.toString();
			super.getIO().mostraMessaggio(risultato.toString());
			
		} else if(parametro.equals("list")) {
			List<Attrezzo> list=partita.getGiocatore().getBorsa().getContenutoOrdinatoPerPeso();
			Iterator<Attrezzo> it=list.iterator();
			
			risultato.append("[");
			while(it.hasNext()) {
				risultato.append(it.next().toString());
				if(it.hasNext())
					risultato.append(", ");
			}
			risultato.append("]");
			
			this.messaggio=risultato.toString();
			super.getIO().mostraMessaggio(risultato.toString());
			
		} else if(parametro.equals("map")) {
			Map<Integer, Set<Attrezzo>> map=partita.getGiocatore().getBorsa().getContenutoRaggruppatoPerPeso();
			
			Iterator<Map.Entry<Integer, Set<Attrezzo>>> iterator=map.entrySet().iterator();
			for(Map.Entry<Integer, Set<Attrezzo>> entry: map.entrySet()) {
				iterator.next();
				Set<Attrezzo> attrezzi=entry.getValue();
				
				risultato.append("(");
				risultato.append(entry.getKey());
				risultato.append(",{");
				
				Iterator<Attrezzo> it=attrezzi.iterator();
				while(it.hasNext()) {
					risultato.append(it.next().getNome());
					if(it.hasNext())
						risultato.append(", ");
				}
				risultato.append("})");
				
				if(iterator.hasNext()) {
					risultato.append("; ");
				}
					
			}
			
			this.messaggio=risultato.toString();
			super.getIO().mostraMessaggio(risultato.toString());
			
		} else {
			super.getIO().mostraMessaggio("Formato non valido");
		}
		
	}

}
