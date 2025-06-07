package it.uniroma3.diadia.ambienti;

public class StanzaBloccata extends Stanza {
	private Direzione direzioneBloccata;
	private String attrezzoPerSbloccare;

	public StanzaBloccata(String nome, Direzione direzioneBloccata, String attrezzoPerSbloccare) {
		super(nome);
		this.direzioneBloccata=direzioneBloccata;
		this.attrezzoPerSbloccare=attrezzoPerSbloccare;
	}
	
	public Direzione getDirezioneBloccata() {
		return this.direzioneBloccata;
	}
	
	public String getAttrezzoPerSbloccare() {
		return this.attrezzoPerSbloccare;
	}
	
	@Override
	public Stanza getStanzaAdiacente(Direzione direzione) {
		if(direzione==this.direzioneBloccata && !this.hasAttrezzo(attrezzoPerSbloccare)) {
			return this;
		} else {
			return super.getStanzaAdiacente(direzione);
		}
	}
	
	@Override
	public boolean isBloccata() {
		return !this.hasAttrezzo(attrezzoPerSbloccare);
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
