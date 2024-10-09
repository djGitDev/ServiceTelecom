package implementations;

import types.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PanierClientImpl implements PanierClient {

    private float coutTotal;
    private float parametreTermes;
    private TypeTermes termes;

    private Client client;
    private List<Abonnement> abonnements;

    //constructeur
    public PanierClientImpl(Client client) {
        this.client = client;
        this.abonnements = new ArrayList<>();
    }

    @Override
    public Client getClient() {
        return client;
    }

    @Override
    public TypeTermes getTermes() {
        return termes;
    }

    @Override
    public void setTermes(TypeTermes tt) {
        termes = tt;
    }

    @Override
    public float getParametreTermes() {
        return parametreTermes;
    }

    @Override
    public void setParametreTermes(float param) {
        parametreTermes = param;
    }

    // Calculer le cout total
    @Override
    public float getCoutTotal() {

        float coutTotal = 0.0f;
        for (Abonnement abonnement : abonnements) {
            if(abonnement.getProduit() instanceof ForfaitProduits forfait){
                for (Iterator<Produit> it = forfait.getProduitsInclus(); it.hasNext(); ) {
                    Produit produit = it.next();
                    coutTotal -= produit.getCoutAnnuel();
                }
            }
            coutTotal += abonnement.getCoutAnnuel();
        }


        return coutTotal;
    }

    @Override
    public Iterator<Abonnement> getAbonnements() {
        return abonnements.iterator();
    }

    @Override
    public Iterator<Produit> getProduitsAbonnes() {

        List<Produit> produits = new ArrayList<>();
        for (Abonnement abonnement : abonnements) {
            produits.add(abonnement.getProduit());
        }
        return produits.iterator();
    }


    /**
     * Ajoute un produit au panier du client.
     *
     * @param prod Le produit à ajouter.
     * @return Le nouvel abonnement créé ou l'abonnement existant si le produit est déjà présent.
     * @throws ExceptionProduitIncompatible Si le produit est incompatible avec un produit déjà abonné.
     */
    @Override
    public Abonnement ajouteProduit(Produit prod) throws ExceptionProduitIncompatible {


        for (Abonnement abonnement : abonnements) {
            if (abonnement.getProduit().equals(prod)) {
                return abonnement;
            }
        }


        // Vérifie les produits exclus
        for (Abonnement abonnement : abonnements) {
            Produit produitAbonne = abonnement.getProduit();
            for (Iterator<Produit> it = produitAbonne.getProduitsExclus(); it.hasNext(); ) {
                Produit produitExclus = it.next();
                if (produitExclus.equals(prod)) {
                    throw new ExceptionProduitIncompatible(prod, produitExclus);
                }
            }
        }


        // Crée un nouvel abonnement et l'ajoute à la liste des abonnements
        Abonnement nouvelAbonnement = new AbonnementImpl(client, prod);
        abonnements.add(nouvelAbonnement);




        if (prod instanceof ForfaitProduits forfait) {
            for (Iterator<Produit> it = forfait.getProduitsInclus(); it.hasNext(); ) {
                Produit produit = it.next();
                ajouteProduit(produit);
            }
        }


        // Vérifie les produits exigés et les ajoute si nécessaire
        for (Iterator<Produit> it = prod.getProduitsExiges(); it.hasNext(); ) {
            Produit produitExiges = it.next();
            ajouteProduit(produitExiges);
        }



        return nouvelAbonnement;
    }

    /**
     * Retire un abonnement pour un produit donné.
     *
     * @param prod Le produit pour lequel l'abonnement doit être retiré.
     * @return L'abonnement retiré ou null si aucun abonnement n'a été trouvé.
     * @throws ExceptionProduitRequis Si le produit est requis par un autre abonnement.
     */
    @Override
    public Abonnement retirerAbonnement(Produit prod) throws ExceptionProduitRequis {

        for (Abonnement abonnement : abonnements) {
            for (Iterator<Produit> it = abonnement.getProduit().getProduitsExiges(); it.hasNext(); ) {
                Produit produitExiges = it.next();
                if (produitExiges.equals(prod)) {
                    throw new ExceptionProduitRequis(prod, produitExiges);
                }
            }
        }

        for (Abonnement abonnement : abonnements) {
            if (abonnement.getProduit().equals(prod)) {
                abonnements.remove(abonnement);
                return abonnement;
            }
        }

        return null;

    }

    /**
     * Récupère l'abonnement pour un produit donné.
     *
     * @param prod Le produit pour lequel l'abonnement doit être récupéré.
     * @return L'abonnement correspondant au produit ou null si aucun abonnement n'a été trouvé.
     */
    @Override
    public Abonnement getAbonnementPourProduit(Produit prod) {
        Abonnement abonnementPourProduit = null;
        for (Abonnement abonnement : abonnements) {
            if (abonnement.getProduit().equals(prod)) {
                abonnementPourProduit = abonnement;
            }
        }
        return abonnementPourProduit;
    }

}
