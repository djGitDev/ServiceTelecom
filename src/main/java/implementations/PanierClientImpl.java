
package implementations;

import types.*;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

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
            coutTotal += abonnement.getCoutAnnuel();
        }

        // Applique le rabais si applicable
        if (termes == TypeTermes.REDUCTION_POURCENTAGE) {
            coutTotal = coutTotal * (1 - parametreTermes); // Applique le pourcentage de réduction
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

        // Vérifie si le produit est déjà abonné
        for (Abonnement abonnement : abonnements) {
            if (abonnement.getProduit().getNom().equals(prod.getNom())) {
                return abonnement;
            }
        }

        // Vérifie les produits exclus
        for (Abonnement abonnement : abonnements) {
            for (Iterator<Produit> it = abonnement.getProduit().getProduitsExclus(); it.hasNext(); ) {
                Produit produitExclus = it.next();
                if (produitExclus.getNom().equals(prod.getNom())) {
                    throw new ExceptionProduitIncompatible(prod, produitExclus);
                }
            }
        }

        // Crée un nouvel abonnement et l'ajoute à la liste des abonnements
        Abonnement nouvelAbonnement = new AbonnementImpl(client, prod);
        abonnements.add(nouvelAbonnement);

        // Vérifie les produits exigés et les ajoute si nécessaire
        for (Abonnement abonnement : abonnements) {
            for (Iterator<Produit> it = abonnement.getProduit().getProduitsExiges(); it.hasNext(); ) {
                Produit produitExiges = it.next();
                if (produitExiges.getNom().equals(prod.getNom())) {
                    try {
                        ajouteProduit(produitExiges);
                    } catch (ExceptionProduitIncompatible e) {
                        throw new ExceptionProduitIncompatible(prod, produitExiges);
                    }
                }
            }
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

        Abonnement abonnementARetirer = null;
        for (Abonnement abonnement : abonnements) {
            if (abonnement.getProduit().getNom().equals(prod.getNom())) {
                for (Abonnement abonnement2 : abonnements) {
                    for (Iterator<Produit> it = abonnement2.getProduit().getProduitsExiges(); it.hasNext(); ) {
                        Produit produitExiges = it.next();
                        if (produitExiges.getNom().equals(prod.getNom())) {
                            throw new ExceptionProduitRequis(prod, produitExiges);
                        }
                    }
                }
                abonnementARetirer = abonnement;
                abonnements.remove(abonnementARetirer);
            }
        }

        return abonnementARetirer;
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
            if (abonnement.getProduit().getNom().equals(prod.getNom())) {
                abonnementPourProduit = abonnement;
            }
        }
        return abonnementPourProduit;
    }

}
