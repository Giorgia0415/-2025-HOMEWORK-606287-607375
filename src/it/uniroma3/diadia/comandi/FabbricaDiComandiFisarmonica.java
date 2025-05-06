package it.uniroma3.diadia.comandi;

import java.util.Scanner;
import java.util.StringTokenizer;

public class FabbricaDiComandiFisarmonica implements FabbricaDiComandi {
	/* ogni comando ha un nome e un eventuale parametro */
	String nomeComando;
	String parametro;
	
	/**
	 * in base all'istruzione ricevuta restituisce il comando corrispondente
	 * 
	 * @return comando è il comando scelto dall'utente
	 */
	@Override
	public Comando costruisciComando(String istruzione) {
		
		Comando comando=null;
		nomeComando=null;
		parametro=null;
		
		//controllo necessario in quanto scanner non accetta null come input
		if(istruzione!=null) {
			//la riga di istruzione viene tokenizzata da scanner
			Scanner scannerDiLinee=new Scanner(istruzione);//l'input di scannerDiLinee è l'istruzione ricevuta dal DiaDia separata in token dagli spazi
		
			/* riceve la riga di istruzione da scanner di parole e la analizza per restituire il comando corrispondente */
			if(scannerDiLinee.hasNext()) {
				nomeComando=scannerDiLinee.next();//prende la prima parola dell'istruzione: nome del comando
			}
			if(scannerDiLinee.hasNext()) {
				parametro=scannerDiLinee.next();//prende la seconda parola dell'istruzione: eventuale parametro
			}
		}
		
		/* inizializza comando */
		if(nomeComando==null) {
			comando=new ComandoNonValido();
		} else if(nomeComando.equals("vai")) {
			comando=new ComandoVai();
		} else if(nomeComando.equals("prendi")) {
			comando=new ComandoPrendi();
		} else if(nomeComando.equals("posa")) {
			comando=new ComandoPosa();
		} else if(nomeComando.equals("aiuto")) {
			comando=new ComandoAiuto();
		} else if(nomeComando.equals("fine")) {
			comando=new ComandoFine();
		} else if(nomeComando.equals("guarda")) {
			comando=new ComandoGuarda();
		} else {
			comando=new ComandoNonValido();
		}
		
		/* imposta il parametro del comando selezionato */
		comando.setParametro(parametro);//deve stare dopo gli if e non prima perché prima non è stato ancora inizializzato il tipo di comando
		return comando;
	}

}
