import org.junit.jupiter.api.Test;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import types.*;

import java.util.Iterator;


public class TesterRelationsProduits {

    private FabriqueObjets fabrique;

    private Produit telephoniePulse;
    private Produit telephoneTone;
    private Produit telephonieFibre;
    private Produit appelEnAttente;
    private Produit affichageAppelIntrant;
    private Produit ADSL;
    private Produit forfaitInternetFIBE15;
    private Produit tele50;

    @BeforeEach
    public void setUp() {
        fabrique = FabriqueObjets.getSingleton();

        telephoniePulse = fabrique.creerProduit("Ligne Pulse", "Ligne téléphonique analogique");
        telephoneTone = fabrique.creerProduit("Ligne Tone", "Ligne téléphonique numérique");
        telephonieFibre = fabrique.creerProduit("Ligne Fibre", "Ligne téléphonique fibre optique");
        appelEnAttente = fabrique.creerProduit("Call Waiting", "Appel en attente");
        affichageAppelIntrant = fabrique.creerProduit("Caller Display", "Affichage du numéro et du nom de l'appelant");
        ADSL = fabrique.creerProduit("Internet ADSL", "Ligne ADSL sur téléphonie digitale");
        forfaitInternetFIBE15 = fabrique.creerProduit("Fibre 1.5 GB", "Forfait internet fibre optique, 1.5 GB");
        tele50 = fabrique.creerProduit("Télé 50","Forfait TV, 50 chaines incluses");
    }

    @Test
    public void testerRelationExigeProduit(){

        // 1. Les relations d'exigence
        // 1.a CALLER_DISPLAY exige TONE
        affichageAppelIntrant.ajouteProduitExige(telephoneTone);

        // 1.b ADSL exige telephonie TONE
        ADSL.ajouteProduitExige(telephoneTone);

        // 1.c CALL WAITING exige affichageAppelIntrant, et donc
        //     telephoneTone par extension
        appelEnAttente.ajouteProduitExige(affichageAppelIntrant);

        // 1.d FIBE15 exige telephone fibre
        forfaitInternetFIBE15.ajouteProduitExige(telephonieFibre);

        // 1.f TELE50 exige telephonie fibre
        tele50.ajouteProduitExige(telephonieFibre);

        // 2. la vérification des liens
        // 2.a liens directs
        Assertions.assertTrue(ADSL.requiert(telephoneTone));
        Assertions.assertTrue(tele50.requiert(telephonieFibre));
        Assertions.assertTrue(affichageAppelIntrant.requiert(telephoneTone));
        // 2.b liens récursifs
        Assertions.assertTrue(appelEnAttente.requiert(telephoneTone));

        // 2.c
        affichageAppelIntrant.retireProduitExige(telephoneTone);
        Assertions.assertTrue(appelEnAttente.requiert(affichageAppelIntrant));
        Assertions.assertFalse(appelEnAttente.requiert(telephoneTone));
    
    }

    @Test
    public void testerRelationExclutProduit(){

        // 1. Les relations d'exigence
        // 1.a CALLER_DISPLAY exige TONE
        affichageAppelIntrant.ajouteProduitExige(telephoneTone);

        // 1.b CALL WAITING exige affichageAppelIntrant, et donc
        //     telephoneTone par extension
        appelEnAttente.ajouteProduitExige(affichageAppelIntrant);

        // 1.c FIBE15 exige telephone fibre
        forfaitInternetFIBE15.ajouteProduitExige(telephonieFibre);

        // 2. Les exclusions. On va se contenter des différents
        //    types de lignes téléphoniques
        telephoniePulse.ajouteProduitExclus(telephoneTone);
        telephoniePulse.ajouteProduitExclus(telephonieFibre);

        telephoneTone.ajouteProduitExclus(telephoniePulse);
        telephoneTone.ajouteProduitExclus(telephonieFibre);

        telephonieFibre.ajouteProduitExclus(telephoniePulse);
        telephonieFibre.ajouteProduitExclus(telephoneTone);

        // 3. la vérification des liens
        // 3.a liens directs
        Assertions.assertTrue(telephoneTone.exclue(telephonieFibre));
        Assertions.assertTrue(telephonieFibre.exclue(telephoneTone));
        Assertions.assertTrue(telephoneTone.exclue(telephoniePulse));
        Assertions.assertTrue(telephoniePulse.exclue(telephoneTone));
        Assertions.assertTrue(telephonieFibre.exclue(telephoniePulse));
        Assertions.assertTrue(telephoniePulse.exclue(telephonieFibre));
        // 3.b liens indirects
        Assertions.assertTrue(affichageAppelIntrant.exclue(telephoniePulse));
        Assertions.assertTrue(appelEnAttente.exclue(telephoniePulse));
    
    }


}

