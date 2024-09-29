package ca.uqam.info.mgl7460.tp1.tests;

import ca.uqam.info.mgl7460.tp1.types.Adresse;
import ca.uqam.info.mgl7460.tp1.types.Client;
import ca.uqam.info.mgl7460.tp1.types.ExceptionProduitIncompatible;
import ca.uqam.info.mgl7460.tp1.types.ExceptionProduitRequis;
import ca.uqam.info.mgl7460.tp1.types.FabriqueObjets;
import ca.uqam.info.mgl7460.tp1.types.ForfaitProduits;
import ca.uqam.info.mgl7460.tp1.types.NumeroTelephone;
import ca.uqam.info.mgl7460.tp1.types.Produit;

public class ClasseTests {
    public static void main(String[] args) {
        // 1. d'abord, créer le catalogue produits
        creerCatalogueProduits();

        // 2. créer quelques clients
        creerClients();

        // 3. créer et tester des paniers
        creerEtTesterPaniers();
    }

    public static void creerCatalogueProduits() {
        FabriqueObjets fabrique= FabriqueObjets.getSingleton();

        // 1. création de produits simples
        Produit TELEPHONIE_PULSE = fabrique.creerProduit("Ligne Pulse", "Ligne téléphonique analogique"),
        TELEPHONIE_TONE = fabrique.creerProduit("Ligne Tone", "Ligne téléphonique numérique"),
        TELEPHONIE_FIBRE = fabrique.creerProduit("Ligne Fibre", "Ligne téléphonique fibre optique"),
        CALL_WAITING = fabrique.creerProduit("Call Waiting", "Appel en attente"),
        CALLER_DISPLAY = fabrique.creerProduit("Caller Display", "Affichage du numéro et du nom de l'appelant"),
        ADSL = fabrique.creerProduit("Internet ADSL", "Ligne ADSL sur téléphonie digitale"),
        FIBE15 = fabrique.creerProduit("Fibre 1.5 GB", "Forfait internet fibre optique, 1.5 GB"),
        TELE50 = fabrique.creerProduit("Télé 50","Forfait TV, 50 chaines incluses");

        System.out.println("Numéro de produit de Telephonie pulse: " + TELEPHONIE_PULSE.getNumero());

        // 2. création de forfaits
        ForfaitProduits FORFAIT_BASIQUE = fabrique.creerForfaitProduits("Forfait Analogique", "Forfait analogique de base"),
        FORFAIT_MOYEN = fabrique.creerForfaitProduits("Forfait numérique de base", "Forfait comprenant ligne TONE + affichage appelant + ligne ADSL"),
        FORFAIT_VIP = fabrique.creerForfaitProduits("Forfait BS15", "Forfait fibre optique avec télé50, et internet 1.5 GB");
 
        System.out.println("Numéro de produit du forfait VIP: " + FORFAIT_VIP.getNumero());

        // 2.a composition du forfait basique:
        FORFAIT_BASIQUE.ajouteProduitInclus(TELEPHONIE_PULSE);
        FORFAIT_BASIQUE.ajouteProduitInclus(CALL_WAITING);

        // 2.b composition du forfait moyen
        FORFAIT_MOYEN.ajouteProduitInclus(TELEPHONIE_TONE);
        FORFAIT_MOYEN.ajouteProduitInclus(CALLER_DISPLAY);
        FORFAIT_MOYEN.ajouteProduitInclus(ADSL);

        // 2.c composition du forfait VIP
        FORFAIT_VIP.ajouteProduitInclus(TELEPHONIE_FIBRE);
        FORFAIT_VIP.ajouteProduitInclus(TELE50);
        FORFAIT_VIP.ajouteProduitInclus(FIBE15);

        // 3. Les exigences
        // 3.a CALLER_DISPLAY exige TONE
        CALLER_DISPLAY.ajouteProduitExige(TELEPHONIE_TONE);

        // 3.b ADSL exige telephonie TONE
        ADSL.ajouteProduitExige(TELEPHONIE_TONE);

        // 3.c FIBE15 exige telephone fibre
        FIBE15.ajouteProduitExige(TELEPHONIE_FIBRE);

        // 3.d TELE50 exige telephonie fibre
        TELE50.ajouteProduitExige(TELEPHONIE_FIBRE);

        // 4. Les exclusions. On va se contenter des différents
        //    types de lignes téléphoniques
        TELEPHONIE_PULSE.ajouteProduitExclus(TELEPHONIE_TONE);
        TELEPHONIE_PULSE.ajouteProduitExclus(TELEPHONIE_FIBRE);

        TELEPHONIE_TONE.ajouteProduitExclus(TELEPHONIE_PULSE);
        TELEPHONIE_TONE.ajouteProduitExclus(TELEPHONIE_FIBRE);

        TELEPHONIE_FIBRE.ajouteProduitExclus(TELEPHONIE_TONE);
        TELEPHONIE_FIBRE.ajouteProduitExclus(TELEPHONIE_TONE);
    
    }

    public static void creerClients() {
        FabriqueObjets fabrique= FabriqueObjets.getSingleton();

        Client francois = fabrique.creerClient("Tremblay", "François");
        francois.setAdresse(new Adresse("210A", "2300", "Saint-Jacques","Montréal", "H2A 3C5", "Québec"));
        francois.setNumeroTelephone(new NumeroTelephone(1,438,5822727));
        System.out.println("ID client de Francois: " + francois.getIdClient());
        
        Client amadou = fabrique.creerClient("Diallo","Amadou");
        amadou.setAdresse(new Adresse("30C", "2200", "Saint-Urbain","Montréal", "H2X 2Y1", "Québec"));
        amadou.setNumeroTelephone(new NumeroTelephone(1,514,9873000,3265));
        System.out.println("ID client de Amadou: " + amadou.getIdClient());

        Client sonia = fabrique.creerClient("Ben Mohamed","Sonia");
        sonia.setAdresse(new Adresse("20A", "2200", "Saint-Urbain","Montréal", "H2X 2Y1", "Québec"));
        sonia.setNumeroTelephone(new NumeroTelephone(1,514,9873000,3265));
        System.out.println("ID client de Amadou: " + sonia.getIdClient());

        
    }

    public static void creerEtTesterPaniers() {
        FabriqueObjets fabrique = FabriqueObjets.getSingleton();

        String id_Francois = "CL-1",
                id_Amadou = "CL-2",
                id_Sonia = "CL-3",
                numero_pulse = "PROD1",
                numero_tone = "PROD2",
                numero_fibre = "PROD3",
                numero_call_waiting = "PROD4",
                numero_caller_display= "PROD5",
                numero_ADSL = "PROD6",
                numero_Fibe = "PROD7",
                numero_tele50 = "PROD8",
                numero_forfait_base = "PROD9",
                numero_forfait_moyen = "PROD10",
                numero_forfait_VIP = "PROD11";

        // 1. créer un panier pour francois
        Client francois = fabrique.getClientAvecId(id_Francois);

        try {
            // 1.a ajoute ligne téléphonique de base
            francois.abonneClient(fabrique.getProduitAvecNumero(numero_pulse));

            // 1.b ajoute call waiting
            francois.abonneClient(fabrique.getProduitAvecNumero(numero_tone));

        } catch (ExceptionProduitIncompatible epi ) {
            epi.printStackTrace();

        }

        // 2. créer un panier pour Amadou
        Client amadou = fabrique.getClientAvecId(id_Amadou);

        try {
            // 1.a ajoute ligne téléphonique de base
            amadou.abonneClient(fabrique.getProduitAvecNumero(numero_pulse));

            // 1.b ajoute tele50
            amadou.abonneClient(fabrique.getProduitAvecNumero(numero_tele50));

        } catch (ExceptionProduitIncompatible epi ) {
            epi.printStackTrace();
        }

        // 3. créer un panier pour sonia
        Client sonia = fabrique.getClientAvecId(id_Sonia);

        try {
            // 1.a ajoute ligne téléphonique de base
            sonia.abonneClient(fabrique.getProduitAvecNumero(numero_forfait_VIP));

            // 1.b retire telephonie fibre
            sonia.getPanier().retirerAbonnement(fabrique.getProduitAvecNumero(numero_fibre));

        } catch (ExceptionProduitIncompatible epi ) {
            epi.printStackTrace();
        } catch (ExceptionProduitRequis epr) {
            epr.printStackTrace();
        }
    }
}
