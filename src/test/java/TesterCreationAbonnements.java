
import org.junit.jupiter.api.Test;



import java.util.Iterator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import types.*;


public class TesterCreationAbonnements {
 
    private static FabriqueObjets fabrique;

    private static Produit telephonieTone;
    private static Produit affichageAppelIntrant;
    private static Produit appelEnAttente;
    private static Produit adsl;
    private static Produit appelConference;
    private static ForfaitProduits forfaitBasique;

    private static Produit telephonieFibre;
    private static Produit forfaitInternetFIBE15;
    private static Produit tele50;
    private static ForfaitProduits forfaitFibres;

    private Client martin;

    private Client estelle;

    private Client oualid;

    private static void creerProduits() {
                // 1. création  de produits
                telephonieTone = fabrique.creerProduit("Ligne Tone", "Ligne téléphonique numérique");
                telephonieTone.setCoutAnnuel(30f);

                appelEnAttente = fabrique.creerProduit("Call Waiting", "Appel en attente");
                appelEnAttente.setCoutAnnuel(10f);

                affichageAppelIntrant = fabrique.creerProduit("Caller Display", "Affichage du numéro et du nom de l'appelant");
                affichageAppelIntrant.setCoutAnnuel(5f);

                adsl = fabrique.creerProduit("ADSL illimité", "Forfait ADSL 10 MB illimité");
                adsl.setCoutAnnuel(35f);

                appelConference = fabrique.creerProduit("Appel conférence", "Appel conférence avec trois personnes");
                appelConference.setCoutAnnuel(15f);
                
                telephonieFibre = fabrique.creerProduit("Ligne Fibre", "Ligne téléphonique fibre optique");
                telephonieFibre.setCoutAnnuel(47.5f);

                forfaitInternetFIBE15 = fabrique.creerProduit("Fibre 1.5 GB", "Forfait internet fibre optique, 1.5 GB");
                forfaitInternetFIBE15.setCoutAnnuel(67.5f);

                tele50 = fabrique.creerProduit("Télé 50","Forfait TV, 50 chaines incluses");
                tele50.setCoutAnnuel(45f);
        
                // 2. création de liens
                // 2.a produits exigés
                affichageAppelIntrant.ajouteProduitExige(telephonieTone);
                appelEnAttente.ajouteProduitExige(affichageAppelIntrant);
                adsl.ajouteProduitExige(telephonieTone);

                forfaitInternetFIBE15.ajouteProduitExige(telephonieFibre);
                tele50.ajouteProduitExige(telephonieFibre);

                // 2.b exclusions
                telephonieTone.ajouteProduitExclus(telephonieFibre);
                telephonieFibre.ajouteProduitExclus(telephonieTone);
       
               // 3. création de forfaits
               // 3.a Forfait basique 
               forfaitBasique = fabrique.creerForfaitProduits("Frofait residentiel deluxe", "Ligne digitale + ADSL + affichage + appel en attente");
               forfaitBasique.ajouteProduitInclus(telephonieTone);
               forfaitBasique.ajouteProduitInclus(affichageAppelIntrant);
               forfaitBasique.ajouteProduitInclus(appelEnAttente);
               forfaitBasique.ajouteProduitInclus(adsl);

               // 3.b Forfait fibre
               forfaitFibres = fabrique.creerForfaitProduits("Forfait fibre 15-50", "Forfait fibre comprenant ligne téléphonique, internet 1.5 GB, et télé 50 chaines");
               forfaitFibres.ajouteProduitInclus(telephonieFibre);
               forfaitFibres.ajouteProduitInclus(forfaitInternetFIBE15);
               forfaitFibres.ajouteProduitInclus(tele50);
               forfaitFibres.setCoutAnnuel(135f);
    }

    private void creerClients() {
        estelle = fabrique.creerClient("Tiogo","Estelle");
        estelle.setAdresse(new Adresse("40C", "2200", "Saint-Urbain","Montréal", "H2X 2Y1", "Québec"));
        estelle.setNumeroTelephone(new NumeroTelephone(1,514,9873000,3265));

        martin = fabrique.creerClient("Matte", "Martin");
        martin.setAdresse(new Adresse(null, "2317", "Rue des Pins", "Mascouche", "J7k 2Y3", "Québec"));
        martin.setNumeroTelephone(new NumeroTelephone(1, 450, 8732465));

        oualid = fabrique.creerClient("Zidane", "Oualid");
        oualid.setAdresse(new Adresse(null, "4856", "Ave Saint Laurent", "Montréal","H2T 4G6", "Québec"));
        oualid.setNumeroTelephone(new NumeroTelephone(1,438, 9056782));

    }
 
    @BeforeAll
    public static void setUpGlobal() {
        fabrique = FabriqueObjets.getSingleton();
        creerProduits();
    }

    @BeforeEach
    public void setUp() {
        creerClients();
    }

    @Test
    public void testCreationAbonnementProduitSimple() {
        try {
            // 1. initialement, client a un panier vide
            Assertions.assertNull(estelle.getPanier());

            // 2. abonne estelle à la téléphonie fibre
            Abonnement nouvelAbonnement = estelle.abonneClient(telephonieFibre);
            PanierClient panierEstelle = estelle.getPanier();

            // 3. Abonnement non nul et contient les bonnes valeurs
            Assertions.assertNotNull(nouvelAbonnement);
            Assertions.assertEquals(nouvelAbonnement.getClient(), estelle);
            Assertions.assertEquals(nouvelAbonnement.getProduit(), telephonieFibre);

            // 4. Panier non null
            Assertions.assertNotNull(panierEstelle);
            // 5. Panier contient nouvelAbonnement pour telephonieFibre
            Assertions.assertEquals(panierEstelle.getAbonnementPourProduit(telephonieFibre),nouvelAbonnement);

            // 5. coût de l'abonnement
            Assertions.assertEquals(nouvelAbonnement.getCoutAnnuel(), telephonieFibre.getCoutAnnuel());

            // 6. offrons un rabais
            nouvelAbonnement.setTermes(TypeTermes.REDUCTION_POURCENTAGE);
            nouvelAbonnement.setParametreTermes(0.25f);

            // 7. cout de l'abonnement après rabais
            Assertions.assertEquals(nouvelAbonnement.getCoutAnnuel(), 0.75*telephonieFibre.getCoutAnnuel());

    
        } catch (ExceptionProduitIncompatible ex){
            ex.printStackTrace();
        }

    }

    @Test
    public void testCreationAbonnementForfaitProduits() {
        try {
            // 1. initialement, client a un panier vide
            Assertions.assertNull(oualid.getPanier());

            // 2. abonne estelle à la téléphonie fibre
            Abonnement nouvelAbonnementForfait = oualid.abonneClient(forfaitFibres);
            PanierClient panierOualid = oualid.getPanier();

            // 3. Panier Oualid non null
            Assertions.assertNotNull(panierOualid);
            // 4. Panier contient 
            // 4.a nouvelAbonnement pour forfait fibre ...
            Assertions.assertEquals(panierOualid.getAbonnementPourProduit(forfaitFibres),nouvelAbonnementForfait);

            // 4.b ... et chacun des sous produits
            forfaitFibres.getProduitsInclus().forEachRemaining(prod ->
            Assertions.assertNotNull(panierOualid.getAbonnementPourProduit(prod)));
    
        } catch (ExceptionProduitIncompatible ex){
            ex.printStackTrace();
        }

    }

    @Test
    public void testerAjoutProduitIncompatibleDirect() {
         Assertions.assertThrows(ExceptionProduitIncompatible.class, () -> { 
            martin.abonneClient(telephonieTone);
            martin.abonneClient(telephonieFibre);
         });
    }
 
    @Test
    public void testerAjoutProduitIncompatibleInDirect() {
         Assertions.assertThrows(ExceptionProduitIncompatible.class, () -> { 
            martin.abonneClient(telephonieFibre);
            martin.abonneClient(adsl);
         });
    }
    
    @Test
    public void testerAjoutProduitIncompatibleAvecForfait() {
         Assertions.assertThrows(ExceptionProduitIncompatible.class, () -> { 
            martin.abonneClient(telephonieTone);
            martin.abonneClient(forfaitFibres);
         });
    }

    @Test
    public void testerRetraitAbonnementNormal() {
        try {
            // 1. initialement, abonne estelle à la téléphonie tone
            Abonnement abonnementTelephonieFibre = estelle.abonneClient(telephonieFibre);
            Abonnement abonnementTele50 = estelle.abonneClient(tele50);

            // 2. Panier contient 
            // 2.a nouvelAbonnement pour telephone fibre ...
            Assertions.assertEquals(estelle.getPanier().getAbonnementPourProduit(telephonieFibre),abonnementTelephonieFibre);

            // 2.b ... et chacun des sous produits
            Assertions.assertEquals(estelle.getPanier().getAbonnementPourProduit(tele50),abonnementTele50);

            // 3. retirer forfait tele50
            estelle.getPanier().retirerAbonnement(tele50);
            Assertions.assertNull(estelle.getPanier().getAbonnementPourProduit(tele50));
    
        } catch (ExceptionProduitIncompatible ex){
            ex.printStackTrace();
        } catch (ExceptionProduitRequis epr) {
            epr.printStackTrace();
        }

    }

    @Test
    public void testerRetraitProduitRequisSimple() {
        try {
            // 1.a ajoute ligne téléphonique de base
            oualid.abonneClient(telephonieTone);
            oualid.abonneClient(adsl);
            Assertions.assertThrows(ExceptionProduitRequis.class,
                () -> {
                    oualid.getPanier().retirerAbonnement(telephonieTone);
                });
    

        } catch (ExceptionProduitIncompatible epi ) {
            epi.printStackTrace();
        }
    }
    @Test
    public void testerRetraitForfaitProduitRequis() {
        try {
            // 1.a ajoute ligne téléphonique de base
            oualid.abonneClient(forfaitFibres);
            Assertions.assertThrows(ExceptionProduitRequis.class,
                () -> {
                    oualid.getPanier().retirerAbonnement(telephonieFibre);
                });
        } catch (ExceptionProduitIncompatible epi ) {
            epi.printStackTrace();
        }
    }

    @Test
    public void testerReductionAbonnement() {
        try {
            // 1.a ajoute ligne téléphonique de base
            Abonnement abonnementTelephonie = oualid.abonneClient(telephonieTone);
            Assertions.assertEquals(abonnementTelephonie.getCoutAnnuel(), telephonieTone.getCoutAnnuel());
            // 1.a ajouter ADSL
            Abonnement abonnementADSL = oualid.abonneClient(adsl);
            Assertions.assertEquals(abonnementADSL.getCoutAnnuel(), adsl.getCoutAnnuel());

            // 2.a attribuer réduction sur ADSL
            abonnementADSL.setTermes(TypeTermes.REDUCTION_POURCENTAGE);
            abonnementADSL.setParametreTermes(0.25f);

            // 2.b vérifier que le cout annuel de l'abonnement est réduit
            Assertions.assertEquals(abonnementADSL.getCoutAnnuel(), 0.75*adsl.getCoutAnnuel());
        } catch (ExceptionProduitIncompatible epi ) {
            epi.printStackTrace();
        }

    }

    @Test
    public void testerCoutPanierSimple() {
        try {
            // 1.a ajoute ligne téléphonique de base
            Abonnement abonnementTelephonie = estelle.abonneClient(telephonieTone);
            // 1.b ajouter ADSL
            Abonnement abonnementADSL = estelle.abonneClient(adsl);

            // 2.a vérifier coût du panier
            Assertions.assertEquals(estelle.getPanier().getCoutTotal(),abonnementTelephonie.getCoutAnnuel()+abonnementADSL.getCoutAnnuel());
        } catch (ExceptionProduitIncompatible epi ) {
            epi.printStackTrace();
        }
    }

    @Test
    public void testerCoutPanierIncluantForfait() {
        try {
            // 1.a ajoute un forfait
            Abonnement abonnementForfait = martin.abonneClient(forfaitBasique);
            // 1.b accorde une réduction sur le forfait
            abonnementForfait.setTermes(TypeTermes.REDUCTION_POURCENTAGE);
            float reduction = 0.2f;
            abonnementForfait.setParametreTermes(reduction);

            // 2. ajoute un produit simple
            martin.abonneClient(appelConference);

            // 3. le coût est la somme du forfait réduit à 20% + appel conference
            Assertions.assertEquals((1- reduction)*forfaitBasique.getCoutAnnuel()+appelConference.getCoutAnnuel(),martin.getPanier().getCoutTotal());

            
        } catch (ExceptionProduitIncompatible e) {
            e.printStackTrace();
        }

    }
    
}
