package it.uniroma3.diadia;

import java.util.Scanner;

public class IOConsole {
	
	/* riceve come parametro il messaggio da mostrare a schermo e lo porta in output */
	public void mostraMessaggio(String msg) {
		System.out.println(msg);//stampa a schermo msg
	}
	
	/* acquisisce la riga successiva dalla tastiera */
	public String leggiRiga() {
		Scanner scannerDiLinee = new Scanner(System.in);
		String riga = scannerDiLinee.nextLine();
		
		return riga;
	}

}
