package it.uniroma3.diadia.ambienti;

public class StanzaBuia extends Stanza {
	private String attrezzoPerVedere;

	public StanzaBuia(String nome, String attrezzoPerVedere) {
		super(nome);
		this.attrezzoPerVedere=attrezzoPerVedere;
	}
	
	/**
	 * la stanza mostra la descrizione solo se vi è presente l'oggetto richiesto
	 */
	@Override
	public String getDescrizione() {
		if(!this.hasAttrezzo(attrezzoPerVedere)) {
			return "Qui c'è un buio pesto";
		} else {
			return super.getDescrizione();
		}
	}
	

}
