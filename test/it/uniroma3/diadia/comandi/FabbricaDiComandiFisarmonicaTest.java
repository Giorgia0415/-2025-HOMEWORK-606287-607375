package it.uniroma3.diadia.comandi;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FabbricaDiComandiFisarmonicaTest {
	Comando cVai, cPosa, cPrendi, cGuarda, cAiuto, cFine, cNonValido;
	FabbricaDiComandi f;

	@BeforeEach
	void setUp() {
		this.cVai=new ComandoVai();
		this.cPosa=new ComandoPosa();
		this.cPrendi=new ComandoPrendi();
		this.cGuarda=new ComandoGuarda();
		this.cAiuto=new ComandoAiuto();
		this.cFine=new ComandoFine();
		this.cNonValido=new ComandoNonValido();
		this.f=new FabbricaDiComandiFisarmonica();
	}
	
	//test metodo costruisciComando()
	@Test
	void testCostruisciComandoNonValido() {
		assertEquals(cNonValido.getNome(), f.costruisciComando("nonValido").getNome());
	}
	
	@Test
	void testCostruisciComandoNull() {
		assertEquals(cNonValido.getNome(), f.costruisciComando(null).getNome());
	}
	
	@Test
	void testCostruisciComandoVai() {
		assertEquals(cVai.getNome(), f.costruisciComando("vai").getNome());
	}
	
	@Test
	void testCostruisciComandoPosa() {
		assertEquals(cPosa.getNome(), f.costruisciComando("posa").getNome());
	}
	
	@Test
	void testCostruisciComandoPrendi() {
		assertEquals(cPrendi.getNome(), f.costruisciComando("prendi").getNome());
	}
	
	@Test
	void testCostruisciComandoGuarda() {
		assertEquals(cGuarda.getNome(), f.costruisciComando("guarda").getNome());
	}
	
	@Test
	void testCostruisciComandoAiuto() {
		assertEquals(cAiuto.getNome(), f.costruisciComando("aiuto").getNome());
	}
	
	@Test
	void testCostruisciComandoFine() {
		assertEquals(cFine.getNome(), f.costruisciComando("fine").getNome());
	}

}
