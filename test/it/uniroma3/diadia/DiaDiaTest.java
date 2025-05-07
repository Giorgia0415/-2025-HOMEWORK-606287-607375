package it.uniroma3.diadia;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class DiaDiaTest {
	private IOSimulator io;
	private DiaDia gioco;
	private String[] comandi;

	@Test
	public void testGiocoVintoSubito() {
		this.comandi=new String[] {"vai nord"};
		this.io=new IOSimulator(comandi);
		this.gioco=new DiaDia(io);
		this.gioco.gioca();
		
		assertTrue(io.getArrayDiMessaggi()[io.getIndiceMessaggiMostrati()-1].equals("Hai vinto!"));
	}
	
	@Test
	public void testFineGiocoImmediata() {
		this.comandi=new String[] {"fine"};
		this.io=new IOSimulator(comandi);
		this.gioco=new DiaDia(io);
		this.gioco.gioca();
		
		assertTrue(io.getArrayDiMessaggi()[io.getIndiceMessaggiMostrati()-1].equals("Grazie di aver giocato!"));
	}
	
	@Test
	public void testGiocoComandoNonValido() {
		this.comandi=new String[] {"nonValido", "fine"};
		this.io=new IOSimulator(comandi);
		this.gioco=new DiaDia(io);
		this.gioco.gioca();
		
		assertTrue(io.getArrayDiMessaggi()[io.getIndiceMessaggiMostrati()-2].equals("Comando non valido"));
	}

}
