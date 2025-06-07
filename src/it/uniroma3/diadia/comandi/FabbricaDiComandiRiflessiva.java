package it.uniroma3.diadia.comandi;

import java.util.Scanner;

import it.uniroma3.diadia.IO;

public class FabbricaDiComandiRiflessiva implements FabbricaDiComandi {
	
	/**
	 * genere il comando a tempo dinamico in base a ciò che viene inserito da tastiera
	 */
	@Override
	public AbstractComando costruisciComando(String istruzione, IO io) {
		
		if(istruzione==null || istruzione.trim().isEmpty()) {
			return new ComandoNonValido("nonValido", null, io);
		}
		
		Scanner scannerDiParole=new Scanner(istruzione);
		String nomeComando=null;
		String parametro=null;
		AbstractComando comando=null;
		
		if(scannerDiParole.hasNext())
			nomeComando=scannerDiParole.next();//prima parola: nome del comando
		if(scannerDiParole.hasNext())
			parametro=scannerDiParole.next();//seconda parola: eventuale parametro
		
		try {
		StringBuilder nomeClasse=new StringBuilder("it.uniroma3.diadia.comandi.Comando");
												//prende nomeComando dall'istruzione passata da tastiera
		nomeClasse.append(Character.toUpperCase(nomeComando.charAt(0)));//deve modificare nomeComando in quanto è case-sensitive
		nomeClasse.append(nomeComando.substring(1));
		
		comando= (AbstractComando) Class.forName(nomeClasse.toString()).newInstance();//solo dopo aver ricevuto l'input da tastiera viene creata un'istanza del comando corrispondente...
																			 		  //...riconoscendolo per nome(es "vai")
		comando.setIO(io);
		comando.setParametro(parametro);
		} catch (Exception e) {
			comando=new ComandoNonValido("nonValido", null, io);
		}
		
		return comando;
	}

}
