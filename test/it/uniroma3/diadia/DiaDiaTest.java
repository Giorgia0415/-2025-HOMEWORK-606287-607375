package it.uniroma3.diadia;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class DiaDiaTest {
	private IOSimulator io;
	private DiaDia gioco;
	private String[] comandi;

	@Test
	void testGiocoVintoSubito() {
		this.comandi=new String[] {"vai nord"};
		this.io=new IOSimulator(comandi);
		this.gioco=new DiaDia(io);
		this.gioco.gioca();
		boolean vinto=false;
		for(String msg:io.getArrayDiMessaggi()) {
			if(msg.contains("Hai vinto!")) {
				vinto=true;
				break;//break è necessario poichè quando la partita finisce il gioco smette di leggere righe
			}
		}
		assertTrue(vinto);
	}

}
