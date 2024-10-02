import org.junit.jupiter.api.Test;



import java.util.Iterator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import types.*;


public class TestCreationProduits {

    private FabriqueObjets fabrique;

    @BeforeEach
    public void setUp() {
        fabrique = FabriqueObjets.getSingleton();
    }

    @Test
    public void testerCreationProduitSimple() {
        // 0. valeur des attributs
        String nomProduit = "Ligne Pulse";
        String descriptionProduit = "Ligne téléphonique analogique";
        float coutAnnuel = 35f;
        // 1. créer produit simple
        Produit telephoniePulse = fabrique.creerProduit(nomProduit, descriptionProduit);

        // 2. l'objet a bel et bien été créé
        Assertions.assertNotNull(telephoniePulse);

        // 3. set cout annuel
        telephoniePulse.setCoutAnnuel(coutAnnuel);

        // 4. vérifier que les attributs ont bel et bien été
        // initialisés aux valeurs attendues
        Assertions.assertEquals(telephoniePulse.getNom(), nomProduit);
        Assertions.assertEquals(telephoniePulse.getDescription(), descriptionProduit);
        Assertions.assertEquals(telephoniePulse.getCoutAnnuel(),coutAnnuel);
    }

    @Test
    public void testerCreationForfaitProduit(){

        // 0. créer les produits simples
        Produit telephoneTone = fabrique.creerProduit("Ligne Tone", "Ligne téléphonique numérique");
        telephoneTone.setCoutAnnuel(35f);
        Produit telephonieFibre = fabrique.creerProduit("Ligne Fibre", "Ligne téléphonique fibre optique");
        telephonieFibre.setCoutAnnuel(45f);
        Produit appelEnAttente = fabrique.creerProduit("Call Waiting", "Appel en attente");
        appelEnAttente.setCoutAnnuel(10f);
        Produit affichageAppelIntrant = fabrique.creerProduit("Caller Display", "Affichage du numéro et du nom de l'appelant");
        affichageAppelIntrant.setCoutAnnuel(5f);
        Produit ADSL = fabrique.creerProduit("Internet ADSL", "Ligne ADSL sur téléphonie digitale");
        ADSL.setCoutAnnuel(45f);
        Produit forfaitInternetFIBE15 = fabrique.creerProduit("Fibre 1.5 GB", "Forfait internet fibre optique, 1.5 GB");
        forfaitInternetFIBE15.setCoutAnnuel(75f);
        Produit tele50 = fabrique.creerProduit("Télé 50","Forfait TV, 50 chaines incluses");
        tele50.setCoutAnnuel(40f);

        // 1. Créer les forfaits
        // 1.a  Forfait de base
        ForfaitProduits forfaitBase = fabrique.creerForfaitProduits("Forfait ADSL", "Forfait de base, ligne digitale + affichage + call waiting + ADSL");
        forfaitBase.ajouteProduitInclus(telephoneTone);
        forfaitBase.ajouteProduitInclus(affichageAppelIntrant);
        forfaitBase.ajouteProduitInclus(appelEnAttente);

        // 1.b forfait fibre
        ForfaitProduits forfaitFibre = fabrique.creerForfaitProduits("ForfaitFibe1550","Forfait fibre avec téléphone, 1.5 GB internet illimité, télé 50 chaines");
        forfaitFibre.ajouteProduitInclus(telephonieFibre);
        forfaitFibre.ajouteProduitInclus(forfaitInternetFIBE15);
        forfaitFibre.ajouteProduitInclus(tele50);

        // 2. Les relations d'exigence
        // 2.a CALLER_DISPLAY exige TONE
        affichageAppelIntrant.ajouteProduitExige(telephoneTone);

        // 2.b ADSL exige telephonie TONE
        ADSL.ajouteProduitExige(telephoneTone);

        // 2.c CALL WAITING exige affichageAppelIntrant, et donc
        //     telephoneTone par extension
        appelEnAttente.ajouteProduitExige(affichageAppelIntrant);

        // 2.d FIBE15 exige telephone fibre
        forfaitInternetFIBE15.ajouteProduitExige(telephonieFibre);

        // 2.f TELE50 exige telephonie fibre
        tele50.ajouteProduitExige(telephonieFibre);

        // 3. la vérification des liens
        // 3.a liens d'inclusion
        Iterator<Produit> produitsInclus = forfaitBase.getProduitsInclus();
        boolean  foundTelephonie = false, foundAffichage = false, foundAppelEnAttente = false;
        while (produitsInclus.hasNext()){
            Produit prochain = produitsInclus.next();
            foundTelephonie = foundTelephonie || prochain.equals(telephoneTone);
            foundAffichage = foundAffichage || prochain.equals(affichageAppelIntrant);
            foundAppelEnAttente = foundAppelEnAttente || prochain.equals(appelEnAttente);
        }
        Assertions.assertTrue(foundTelephonie);
        Assertions.assertTrue(foundAffichage);
        Assertions.assertTrue(foundAppelEnAttente);

        // 3.b tester le comportement de getCoutAnnuel pour les produits de
        // de base et les forfaits
        Assertions.assertEquals(telephonieFibre.getCoutAnnuel(),45f);
        Assertions.assertEquals(forfaitFibre.getCoutAnnuel(),160f);

        // 3.c accorder un rabais sur le forfait
        forfaitFibre.setCoutAnnuel(135f);
        Assertions.assertEquals(forfaitFibre.getCoutAnnuel(),135f);
        
    }
}
