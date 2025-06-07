package it.uniroma3.diadia.comandi;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.uniroma3.diadia.IO;
import it.uniroma3.diadia.IOConsole;
import it.uniroma3.diadia.Partita;

class FakeComando extends AbstractComando {

	public FakeComando(String nome, String parametro, IO io) {
		super(nome, parametro, io);
	}

	@Override
	public void esegui(Partita partita) {
		
	}
	
}
public class AbstractComandoTest {
	AbstractComando fake;
	
	@BeforeEach
	public void setUp() {
		fake=new FakeComando("fake", null, new IOConsole());
	}
	
	//test metodo getNome()
	@Test
	public void testGetNome() {
		assertEquals("fake", fake.getNome());
	}
	
	//test metodo getParametro()
	@Test
	public void testGetParametro() {
		assertNull(fake.getParametro());
	}
	

}
