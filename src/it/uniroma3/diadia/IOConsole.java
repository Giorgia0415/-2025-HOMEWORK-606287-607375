package it.uniroma3.diadia;

import java.util.Scanner;

public class IOConsole implements IO {
	
	private Scanner scannerDiLinee;
	
	public IOConsole() {
        this.scannerDiLinee = new Scanner(System.in);
    }
	
	/* riceve come parametro il messaggio da mostrare a schermo e lo porta in output */
	@Override
	public void mostraMessaggio(String msg) {
		System.out.println(msg);//stampa a schermo msg
	}
	
	/* acquisisce la riga successiva dalla tastiera */
	@Override
	public String leggiRiga() {
		return this.scannerDiLinee.nextLine();
	}
	
	/**
     * Metodo per chiudere lo scanner (da chiamare al termine del programma)
     */
    public void chiudi() {
        if (scannerDiLinee != null) {
            scannerDiLinee.close();
        }
    }

}
