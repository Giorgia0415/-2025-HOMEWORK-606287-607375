package it.uniroma3.diadia.ambienti;

public class StanzaBloccata extends Stanza {
	private String direzioneBloccata;
	private String attrezzoPerSbloccare;

	public StanzaBloccata(String nome, String direzioneBloccata, String attrezzoPerSbloccare) {
		super(nome);
		this.direzioneBloccata=direzioneBloccata;
		this.attrezzoPerSbloccare=attrezzoPerSbloccare;
	}
	
	@Override
	public Stanza getStanzaAdiacente(String direzione) {
		if(direzione==this.direzioneBloccata && !this.hasAttrezzo(attrezzoPerSbloccare)) {
			return this;
		} else {
			return super.getStanzaAdiacente(direzione);
		}
	}
	
	@Override
	public String getDescrizione() {
		if(!this.hasAttrezzo(attrezzoPerSbloccare)) {
			return super.getDescrizione()+"\nNon puoi accedere alla stanza in direzione "+ this.direzioneBloccata+" perché non è presente l'oggetto "+this.attrezzoPerSbloccare;
		} else {
			return super.getDescrizione();
		}
	}

}
