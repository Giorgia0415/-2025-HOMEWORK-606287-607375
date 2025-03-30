package it.uniroma3.diadia;

import java.util.Scanner;
/**
 * Si tenga presente che la classe Scanner suddivide lo stream dei caratteri in token 
 * cioè in spezzoni di stringhe separate dai caratteri delimitatori. 
 * I caratteri delimitatori di default sono: gli spazi, i caratteri di tabulazione e i caratteri di newline).
 */

/**
 * Questa classe modella un comando.
 * Un comando consiste al piu' di due parole:
 * il nome del comando ed un parametro
 * su cui si applica il comando.
 * (Ad es. alla riga digitata dall'utente "vai nord"
 *  corrisponde un comando di nome "vai" e parametro "nord").
 *
 * @author  docente di POO
 * @version base
 */

public class Comando {

    private String nome;
    private String parametro;
    
    /* costruttore */
    public Comando(String istruzione) {
		Scanner scannerDiParole = new Scanner(istruzione);//crea un oggetto di tipo scanner e gli mette dentro istruzione

		// prima parola: nome del comando
		if (scannerDiParole.hasNext())//restituisce vero se in input è presente un ulteriore token, falso altrimenti
			this.nome = scannerDiParole.next();//mette il primo token dentro la variabile nome

		// seconda parola: eventuale parametro
		if (scannerDiParole.hasNext())
			this.parametro = scannerDiParole.next();//mette il secondo token dentro la variabile parametro
		
		scannerDiParole.close();
    }
  

    public String getNome() {
        return this.nome;
    }

    public String getParametro() {
        return this.parametro;
    }

    public boolean sconosciuto() {
        return (this.nome == null);
    }
}
