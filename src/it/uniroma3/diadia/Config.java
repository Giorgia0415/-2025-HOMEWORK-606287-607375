package it.uniroma3.diadia;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {

    private static final String FILE_NAME = "diadia.properties";
    private static Properties properties = new Properties();

    static {
        try (InputStream input = Config.class.getClassLoader().getResourceAsStream(FILE_NAME)) {
            if (input == null)
                throw new RuntimeException("Impossibile trovare il file " + FILE_NAME);
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Errore durante il caricamento di " + FILE_NAME, e);
        }
    }

    public static int getCfuIniziali() {
        return Integer.parseInt(properties.getProperty("giocatore.cfu", "20"));
    }

    public static int getPesoMaxBorsa() {
        return Integer.parseInt(properties.getProperty("borsa.pesoMax", "10"));
    }
}

