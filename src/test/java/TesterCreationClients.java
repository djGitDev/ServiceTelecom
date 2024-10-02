package ca.uqam.info.mgl7460.tp1.tests;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import ca.uqam.info.mgl7460.tp1.types.Adresse;
import ca.uqam.info.mgl7460.tp1.types.Client;
import ca.uqam.info.mgl7460.tp1.types.FabriqueObjets;
import ca.uqam.info.mgl7460.tp1.types.NumeroTelephone;

public class TesterCreationClients {

    private FabriqueObjets fabrique;

    @BeforeEach
    public void setUp() {
        fabrique = FabriqueObjets.getSingleton();
    }

    @Test
    public void testerCreationClient() {
        fabrique= FabriqueObjets.getSingleton();

        // 1. Francois
        String nomFrancois = "Tremblay";
        String prenomFrancois = "François";
        Client francois = fabrique.creerClient(nomFrancois, prenomFrancois);
        francois.setAdresse(new Adresse("210A", "2300", "Saint-Jacques","Montréal", "H2A 3C5", "Québec"));
        francois.setNumeroTelephone(new NumeroTelephone(1,438,5822727));

        // 2. vérification
        // 2.a attributs
        Assertions.assertEquals(francois.getNom(), nomFrancois);
        Assertions.assertEquals(francois.getAdresse().nomRue(), "Saint-Jacques");
        Assertions.assertEquals(francois.getNumeroTelephone().numero(), 5822727);
        
        Client amadou = fabrique.creerClient("Diallo","Amadou");
        amadou.setAdresse(new Adresse("30C", "2200", "Saint-Urbain","Montréal", "H2X 2Y1", "Québec"));
        amadou.setNumeroTelephone(new NumeroTelephone(1,514,9873000,3265));

        Client sonia = fabrique.creerClient("Ben Mohamed","Sonia");
        sonia.setAdresse(new Adresse("20A", "2200", "Saint-Urbain","Montréal", "H2X 2Y1", "Québec"));
        sonia.setNumeroTelephone(new NumeroTelephone(1,514,9873000,3265));
        
    }

}
