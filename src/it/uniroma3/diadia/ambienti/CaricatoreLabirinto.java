package it.uniroma3.diadia.ambienti;

import java.io.*;
import java.util.*;

import it.uniroma3.diadia.attrezzi.Attrezzo;
import it.uniroma3.diadia.personaggi.*;

public class CaricatoreLabirinto {

	/* prefisso di una singola riga di testo contenente tutti i nomi delle stanze */
	private static final String STANZE_MARKER = "Stanze:";             

	/* prefisso di una singola riga contenente il nome della stanza iniziale */
	private static final String STANZA_INIZIALE_MARKER = "Inizio:";    

	/* prefisso della riga contenente il nome stanza vincente */
	private static final String STANZA_VINCENTE_MARKER = "Vincente:";  
	
	/* prefisso della riga contenente il nome stanza buia */
	private static final String STANZE_BUIE_MARKER = "Buia:";  

	/* prefisso della riga contenente il nome stanza bloccata */
	private static final String STANZE_BLOCCATE_MARKER = "Bloccata:";  
	
	/* prefisso della riga contenente il nome stanza bloccata */
	private static final String STANZE_MAGICHE_MARKER = "Magica:";  
	
	/* prefisso della riga contenente le specifiche degli attrezzi da collocare nel formato <nomeMago> <presentazione> <attrezzo> */
	private static final String PERSONAGGI_MARKER_MAGO = "Mago:";
	
	/* prefisso della riga contenente le specifiche degli attrezzi da collocare nel formato <nomeStrega> <presentazione> */
	private static final String PERSONAGGI_MARKER_STREGA = "Strega:";
	
	/* prefisso della riga contenente le specifiche degli attrezzi da collocare nel formato <nomeCane> <presentazione> */
	private static final String PERSONAGGI_MARKER_CANE = "Cane:";
	
	/* prefisso della riga contenente le specifiche degli attrezzi da collocare nel formato <nomeAttrezzo> <peso> <nomeStanza> */
	private static final String ATTREZZI_MARKER = "Attrezzi:";

	/* prefisso della riga contenente le specifiche dei collegamenti tra stanza nel formato <nomeStanzaDa> <direzione> <nomeStanzaA> */
	private static final String USCITE_MARKER = "Uscite:";

	/*
	 *  Esempio di un possibile file di specifica di un labirinto (vedi POO-26-eccezioni-file.pdf)

		Stanze: biblioteca, N10, N11
		Inizio: N10
		Vincente: N11
		Attrezzi: martello 10 biblioteca, pinza 2 N10
		Uscite: biblioteca nord N10, biblioteca sud N11

	 */
	private LineNumberReader reader;

	private Map<String, Stanza> nome2stanza;

	private Stanza stanzaIniziale;
	private Stanza stanzaVincente;


	public CaricatoreLabirinto(String nomeFile) throws FileNotFoundException {
		this.nome2stanza = new HashMap<String,Stanza>();
		this.reader = new LineNumberReader(new FileReader(nomeFile));
	}
	
	public CaricatoreLabirinto(StringReader reader) {
	    this.nome2stanza = new HashMap<>();
	    this.reader = new LineNumberReader(reader);
	}

	public void carica() throws FormatoFileNonValidoException {
		try {
			this.leggiECreaStanze();
			this.leggiInizialeEvincente();
			this.leggiECreaStanzeMagiche();
			this.leggiECreaStanzeBuie();
			this.leggiECreaStanzeBloccate();
			this.leggiECreaMaghi();
			this.leggiECreaStreghe();
			this.leggiECreaCani();
			this.leggiECollocaAttrezzi();
			this.leggiEImpostaUscite();
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}

	}
	
	/* LETTURE DA FILE */
	
	private String leggiRigaCheCominciaPer(String marker) throws FormatoFileNonValidoException {
	    try {
	        String riga;
	        do {
	            riga = this.reader.readLine();
	            if (riga == null)
	                throw new FormatoFileNonValidoException("Fine del file raggiunta mentre si cercava: " + marker);
	            riga = riga.trim(); // elimina spazi bianchi prima e dopo
	        } while (riga.isEmpty()); // salta righe completamente vuote

	        check(riga.startsWith(marker), "era attesa una riga che cominciasse per " + marker);
	        return riga.length() > marker.length() ? riga.substring(marker.length()).trim() : "";
	    } catch (IOException e) {
	        throw new FormatoFileNonValidoException(e.getMessage());
	    }
	}
	
	/**
	 * separa la lunga stringa ricevuta in input in base alle virgole
	 * così da prendere le singole stanze
	 * 
	 * @param string la stringa da separare
	 * @return una lista contenente tutte le stanze
	 */
	private List<String> separaStringheAlleVirgole(String string) {
		List<String> result = new LinkedList<>();
		Scanner scanner = new Scanner(string);
		scanner.useDelimiter(",");
		while (scanner.hasNext()) {
			result.add(scanner.next().trim());
		}
		return result;
	}
	
	/* STANZE */

	private void leggiECreaStanze() throws FormatoFileNonValidoException  {
		//prende la riga contenente le stanze con i loro dettagli nel file
		String nomiStanze = this.leggiRigaCheCominciaPer(STANZE_MARKER);
		for(String nomeStanza : separaStringheAlleVirgole(nomiStanze)) {
			Stanza stanza = new Stanza(nomeStanza);
			this.nome2stanza.put(nomeStanza, stanza);
		}
	}
	
	private void leggiInizialeEvincente() throws FormatoFileNonValidoException {
		//legge le due stanze dal file
		String nomeStanzaIniziale = null;
		nomeStanzaIniziale = this.leggiRigaCheCominciaPer(STANZA_INIZIALE_MARKER);
		check(this.isStanzaValida(nomeStanzaIniziale), nomeStanzaIniziale +" non definita");
		String nomeStanzaVincente = this.leggiRigaCheCominciaPer(STANZA_VINCENTE_MARKER);
		check(this.isStanzaValida(nomeStanzaVincente), nomeStanzaVincente + " non definita");
		//e le prende dalla stanza se esistono
		//poiché sono state gia inserite tutte tramite leggiECreaStanze()
		this.stanzaIniziale = this.nome2stanza.get(nomeStanzaIniziale);
		this.stanzaVincente = this.nome2stanza.get(nomeStanzaVincente);
	}
	
	private void leggiECreaStanzeMagiche() throws FormatoFileNonValidoException  {
		String nomiStanze = this.leggiRigaCheCominciaPer(STANZE_MAGICHE_MARKER);
		for(String nomeStanza : separaStringheAlleVirgole(nomiStanze)) {
			Stanza stanza = new StanzaMagica(nomeStanza);
			this.nome2stanza.put(nomeStanza, stanza);
		}
	}

	private void leggiECreaStanzeBuie() throws FormatoFileNonValidoException {
		String specificheStanze = this.leggiRigaCheCominciaPer(STANZE_BUIE_MARKER);
		for(String specifica : separaStringheAlleVirgole(specificheStanze)) {
		    try (Scanner scannerDiLinea = new Scanner(specifica)) {
		        check(scannerDiLinea.hasNext(), msgTerminazionePrecoce("la stanza " + specifica + " non esiste\n"));
		        String nomeStanza = scannerDiLinea.next();
		        check(scannerDiLinea.hasNext(), msgTerminazionePrecoce("problema nella creazione dell'attrezzo per vedere la stanza " + specifica + "\n"));
		        String attrezzoPerVedere = scannerDiLinea.next();

		        Stanza stanza = new StanzaBuia(nomeStanza, attrezzoPerVedere);
		        this.nome2stanza.put(nomeStanza, stanza);
		    }
		}

	}
	
	private void leggiECreaStanzeBloccate() throws FormatoFileNonValidoException {
		String specificheStanze = this.leggiRigaCheCominciaPer(STANZE_BLOCCATE_MARKER);
		for(String specifica : separaStringheAlleVirgole(specificheStanze)) {
		    try (Scanner scannerDiLinea = new Scanner(specifica)) {
		        check(scannerDiLinea.hasNext(), msgTerminazionePrecoce("la stanza " + specifica + " non esiste\n"));
		        String nomeStanza = scannerDiLinea.next();
		        check(scannerDiLinea.hasNext(), msgTerminazionePrecoce("la direzione della stanza " + specifica + " non esiste\n"));
		        Direzione direzione = Direzione.valueOf(scannerDiLinea.next().toUpperCase());
		        check(scannerDiLinea.hasNext(), msgTerminazionePrecoce("problema nella creazione dell'attrezzo per sbloccare la stanza " + specifica + "\n"));
		        String attrezzoSbloccante = scannerDiLinea.next();

		        Stanza stanza = new StanzaBloccata(nomeStanza, direzione, attrezzoSbloccante);
		        this.nome2stanza.put(nomeStanza, stanza);
		    }
		} 
	}
	
	/* ATTREZZI */

	private void leggiECollocaAttrezzi() throws FormatoFileNonValidoException {
		//legge la riga di attrezzi
		String specificheAttrezzi = this.leggiRigaCheCominciaPer(ATTREZZI_MARKER);

		for(String specificaAttrezzo : separaStringheAlleVirgole(specificheAttrezzi)) {
			String nomeAttrezzo = null;
			String pesoAttrezzo = null;
			String nomeStanza = null; 
			try (Scanner scannerLinea = new Scanner(specificaAttrezzo)) {
				check(scannerLinea.hasNext(),msgTerminazionePrecoce("il nome di un attrezzo."));
				nomeAttrezzo = scannerLinea.next();
				check(scannerLinea.hasNext(),msgTerminazionePrecoce("il peso dell'attrezzo "+nomeAttrezzo+"."));
				pesoAttrezzo = scannerLinea.next();
				check(scannerLinea.hasNext(),msgTerminazionePrecoce("il nome della stanza in cui collocare l'attrezzo "+nomeAttrezzo+"."));
				nomeStanza = scannerLinea.next();
			}
			//se tutto va bene colloca l'attrezzo nella stanza
			posaAttrezzo(nomeAttrezzo, pesoAttrezzo, nomeStanza);
		}
	}

	private void posaAttrezzo(String nomeAttrezzo, String pesoAttrezzo, String nomeStanza) throws FormatoFileNonValidoException {
		int peso;
		try {
			//prende la stringa del peso e la rende un intero
			peso = Integer.parseInt(pesoAttrezzo);
			//crea il nuovo attrezzo
			Attrezzo attrezzo = new Attrezzo(nomeAttrezzo, peso);
			check(isStanzaValida(nomeStanza),"Attrezzo "+ nomeAttrezzo+" non collocabile: stanza " +nomeStanza+" inesistente");
			//aggiunge l'attrezzo alla stanza
			this.nome2stanza.get(nomeStanza).addAttrezzo(attrezzo);
		}
		catch (NumberFormatException e) {
			check(false, "Peso attrezzo "+nomeAttrezzo+" non valido");
		}
	}
	
	/* PERSONAGGI */
	
	private void leggiECreaMaghi() throws FormatoFileNonValidoException {
	    String specificheStanze = this.leggiRigaCheCominciaPer(PERSONAGGI_MARKER_MAGO);
	    for (String specifica : separaStringheAlleVirgole(specificheStanze)) {
	        try (Scanner scannerDiLinea = new Scanner(specifica)) {
	            check(scannerDiLinea.hasNext(), msgTerminazionePrecoce("la stanza " + specifica + " per aggiungere il mago non esiste\n"));
	            String nomeStanza = scannerDiLinea.next();

	            check(scannerDiLinea.hasNext(), msgTerminazionePrecoce("problemi nella creazione del mago ...\n"));
	            String mago = scannerDiLinea.next();

	            // Legge presentazione tra virgolette
	            check(scannerDiLinea.hasNext(), msgTerminazionePrecoce("specifica la presentazione del mago tra virgolette\n"));
	            									  //cerca una sottostringa racchiusa tra ' '
	            String presentazione = scannerDiLinea.findInLine("'[^\"]+'");
	            check(presentazione != null, msgTerminazionePrecoce("presentazione mancante o non tra virgolette"));

	            // Rimuove le virgolette
	            presentazione = presentazione.substring(1, presentazione.length() - 1);

	            check(scannerDiLinea.hasNext(), msgTerminazionePrecoce("problema nella creazione dell'attrezzo per il mago della stanza " + specifica + "\n"));
	            String attrezzo = scannerDiLinea.next();

	            AbstractPersonaggio personaggio = new Mago(mago, presentazione, new Attrezzo(attrezzo, 4));
	            this.nome2stanza.get(nomeStanza).setPersonaggio(personaggio);
	        }
	    }
	}
	
	private void leggiECreaStreghe() throws FormatoFileNonValidoException {
	    String specificheStanze = this.leggiRigaCheCominciaPer(PERSONAGGI_MARKER_STREGA);
	    for (String specifica : separaStringheAlleVirgole(specificheStanze)) {
	        try (Scanner scannerDiLinea = new Scanner(specifica)) {
	            check(scannerDiLinea.hasNext(), msgTerminazionePrecoce("la stanza " + specifica + " per aggiungere la strega non esiste\n"));
	            String nomeStanza = scannerDiLinea.next();

	            check(scannerDiLinea.hasNext(), msgTerminazionePrecoce("problemi nella creazione della strega ...\n"));
	            String strega = scannerDiLinea.next();

	            check(scannerDiLinea.hasNext(), msgTerminazionePrecoce("specifica la presentazione della strega tra virgolette\n"));
	            String presentazione = scannerDiLinea.findInLine("'[^\"]+'");
	            check(presentazione != null, msgTerminazionePrecoce("presentazione mancante o non tra virgolette"));

	            presentazione = presentazione.substring(1, presentazione.length() - 1);

	            AbstractPersonaggio personaggio = new Strega(strega, presentazione);
	            this.nome2stanza.get(nomeStanza).setPersonaggio(personaggio);
	        }
	    }
	}

	
	private void leggiECreaCani() throws FormatoFileNonValidoException {
	    String specificheStanze = this.leggiRigaCheCominciaPer(PERSONAGGI_MARKER_CANE);
	    for (String specifica : separaStringheAlleVirgole(specificheStanze)) {
	        try (Scanner scannerDiLinea = new Scanner(specifica)) {
	            check(scannerDiLinea.hasNext(), msgTerminazionePrecoce("la stanza " + specifica + " per aggiungere il cane non esiste\n"));
	            String nomeStanza = scannerDiLinea.next();

	            check(scannerDiLinea.hasNext(), msgTerminazionePrecoce("problemi nella creazione del cane ...\n"));
	            String cane = scannerDiLinea.next();

	            check(scannerDiLinea.hasNext(), msgTerminazionePrecoce("specifica la presentazione del cane tra virgolette\n"));
	            String presentazione = scannerDiLinea.findInLine("'[^\"]+'");
	            check(presentazione != null, msgTerminazionePrecoce("presentazione mancante o non tra virgolette"));

	            presentazione = presentazione.substring(1, presentazione.length() - 1);

	            AbstractPersonaggio personaggio = new Cane(cane, presentazione);
	            this.nome2stanza.get(nomeStanza).setPersonaggio(personaggio);
	        }
	    }
	}
	
	/* USCITE */
	
	private void impostaUscita(String stanzaDa, Direzione dir, String nomeA) throws FormatoFileNonValidoException {
		check(isStanzaValida(stanzaDa),"Stanza di partenza sconosciuta "+dir.name());
		check(isStanzaValida(nomeA),"Stanza di destinazione sconosciuta "+ dir.name());
		Stanza partenzaDa = this.nome2stanza.get(stanzaDa);
		Stanza arrivoA = this.nome2stanza.get(nomeA);
		partenzaDa.impostaStanzaAdiacente(dir, arrivoA);
	}
	
	private void leggiEImpostaUscite() throws FormatoFileNonValidoException {
		String specificheUscite = this.leggiRigaCheCominciaPer(USCITE_MARKER);
		try (Scanner scannerDiLinea = new Scanner(specificheUscite)) {			

			while (scannerDiLinea.hasNext()) {
				check(scannerDiLinea.hasNext(),msgTerminazionePrecoce("le uscite di una stanza."));
				String stanzaPartenza = scannerDiLinea.next();
				check(scannerDiLinea.hasNext(),msgTerminazionePrecoce("la direzione di una uscita della stanza "+stanzaPartenza));
				String dir = scannerDiLinea.next();
				check(scannerDiLinea.hasNext(),msgTerminazionePrecoce("la destinazione di una uscita della stanza "+stanzaPartenza+" nella direzione "+dir));
				String stanzaDestinazione = scannerDiLinea.next();
				
				impostaUscita(stanzaPartenza, Direzione.valueOf(dir.toUpperCase()), stanzaDestinazione);
			}
		} 
	}
	
	/* CONTROLLI */
	
	private boolean isStanzaValida(String nomeStanza) {
		return this.nome2stanza.containsKey(nomeStanza);
	}
	
	private String msgTerminazionePrecoce(String msg) {
		return "Terminazione precoce del file prima di leggere "+msg;
	}

	final private void check(boolean condizioneCheDeveEsseraVera, String messaggioErrore) throws FormatoFileNonValidoException {
		if (!condizioneCheDeveEsseraVera)
			throw new FormatoFileNonValidoException("Formato file non valido [" + this.reader.getLineNumber() + "] "+messaggioErrore);		
	}
	
	/* GETTERS */

	public Stanza getStanzaIniziale() {
		return this.stanzaIniziale;
	}

	public Stanza getStanzaVincente() {
		return this.stanzaVincente;
	}

	public Map<String, Stanza> getStanze() {
		return this.nome2stanza;
	}
}
