package implementations;

import types.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ProduitImpl implements Produit {


    private static int compteur = 0;


    protected String nom;
    protected String numero;
    protected String description;
    protected float coutAnnuel;
    protected List<Produit> produitsExiges = new ArrayList<>();
    protected List<Produit> produitsExclus = new ArrayList<>();

    public ProduitImpl(String nom, String description) {
        this.nom = nom;
        this.description = description;
        compteur++;
        numero = "PROD" + compteur;
    }

    @Override
    public String getNumero() {
        return numero;
    }

    @Override
    public String getNom() {
        return nom;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String desc) {
        description = desc;
    }

    @Override
    public float getCoutAnnuel() {
        return coutAnnuel;
    }

    @Override
    public void setCoutAnnuel(float cout) {
        coutAnnuel = cout;
    }

    @Override
    public void ajouteProduitExige(Produit prod) {
        if (!produitEstDejaPresent(prod,produitsExiges))
            produitsExiges.add(prod);
    }

    @Override
    public Produit retireProduitExige(Produit prod) {
        return retireProduit(prod, produitsExiges);
    }

    @Override
    public Iterator<Produit> getProduitsExiges() {
        return produitsExiges.iterator();
    }

    @Override
    public void ajouteProduitExclus(Produit prod) {
        // Vérifie si le produit est déjà exclu en parcourant l'itérateur
        if (!produitEstDejaPresent(prod,produitsExclus)) {
            produitsExclus.add(prod);

            // Vérifie réciproquement que this n'est pas déjà dans les exclus du produit a ajouté
            if (!produitEstDejaExclusDans(prod, this)) {
                prod.ajouteProduitExclus(this);
            }
        }
    }

    // Méthode pour vérifier si un produit est déjà present dans la liste des produits
    private boolean produitEstDejaPresent(Produit prod, List<Produit> produits) {
        for (Produit produit : produits) {
            if (produit.equals(prod)) {
                return true;
            }
        }
        return false;
    }

    // Méthode pour vérifier si l'objet produitAverifier est dans les exclus de prod
    private boolean produitEstDejaExclusDans(Produit prod, Produit produitAverifier) {
        Iterator<Produit> it = prod.getProduitsExclus();
        while (it.hasNext()) {
            if (it.next().equals(produitAverifier)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Produit retireProduitExclus(Produit prod) {
        return retireProduit(prod, produitsExclus);
    }

    @Override
    public Iterator<Produit> getProduitsExclus() {
        return produitsExclus.iterator();
    }

    @Override
    public boolean requiert(Produit p) {
        return estPresent(p, produitsExiges);
    }

    @Override
    public boolean exclue(Produit p) {
        return estAbsent(p, produitsExclus, produitsExiges);
    }

    @Override
    public String getSommaire() {
        return "[ " + numero + "," + nom + " ]";
    }

    @Override
    public boolean equals(Object obj) {
        // Vérifie si c'est le même objet
        if (this == obj) {
            return true;
        }

        // Vérifie si l'objet passé est bien une instance de Produit
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        // Effectue la comparaison des numéros de produits
        Produit produit = (Produit) obj;
        return this.getNumero().equals(produit.getNumero());
    }

    protected Produit retireProduit(Produit prod, List<Produit> produits) {
        Produit ret = null;
        Produit produitASupprimer = null;

        for (Produit courant : produits) {
            if (courant.equals(prod)) {
                ret = courant;
                produitASupprimer = courant;
                break;
            }
        }
        if (produitASupprimer != null) {
            produits.remove(produitASupprimer);
        }
        return ret;
    }

    protected boolean estPresent(Produit p, List<Produit> produits) {
        for (Produit courant : produits) {
            for (Iterator<Produit> it = courant.getProduitsExiges(); it.hasNext(); ) {
                Produit produitExige = it.next();
                if (produitExige.equals(p)) {
                    return true;
                }
            }
            if (courant.equals(p)) {
                return true;
            }
        }

        return false;
    }

    protected boolean estAbsent(Produit produitAverifier, List<Produit> produitsExclus, List<Produit> produitsExiges) {
        for (Produit courant : produitsExclus) {
            for (Iterator<Produit> it = courant.getProduitsExclus(); it.hasNext(); ) {
                Produit produitExclus = it.next();
                if (produitExclus.equals(produitAverifier)) {
                    return true;
                }
            }
            if (courant.equals(produitAverifier)) {
                return true;
            }
        }


        for (Iterator<Produit> it = produitAverifier.getProduitsExclus(); it.hasNext(); ) {
            Produit produitExclusDuProduitAverifier = it.next();
            for (Iterator<Produit> it2 = produitsExiges.iterator(); it2.hasNext(); ) {
                Produit produitExige = it2.next();
                if (produitExclusDuProduitAverifier.equals(produitExige)) {
                    return true;
                }
                for(Iterator<Produit> it3 =produitExige.getProduitsExiges(); it3.hasNext(); ) {
                    Produit produitExigeDuProduitExige = it3.next();
                    if (produitExclusDuProduitAverifier.equals(produitExigeDuProduitExige)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }



}
