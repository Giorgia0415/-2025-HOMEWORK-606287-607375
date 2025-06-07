package it.uniroma3.diadia.comandi;

import it.uniroma3.diadia.IO;
import it.uniroma3.diadia.Partita;

public abstract class AbstractComando implements Comando {
	private String nome;
	private String parametro;
	private IO io;
	
	public AbstractComando(String nome, String parametro, IO io) {
		this.nome=nome;
		this.parametro=parametro;
		this.io=io;
	}
	
	/* METODI CONCRETI */
	
	@Override
	public String getNome() {
		return this.nome;
	}
	
	@Override
	public void setParametro(String parametro) {
		this.parametro=parametro;
	}
	
	@Override
	public String getParametro() {
		return this.parametro;
	}
	
	@Override
	public void setIO(IO io) {
		this.io=io;
	}
	
	public IO getIO() {
		return this.io;
	}
	
	/* METODI ASTRATTI */
	
	public abstract void esegui(Partita partita);
}
