package ca.uqam.info.mgl7460.tp1.implementations;

import ca.uqam.info.mgl7460.tp1.types.Produit;
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
        produitsExclus.add(prod);
    }

    @Override
    public Produit retireProduitExclus(Produit prod) {
        return retireProduit(prod,produitsExclus);
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
        return estPresent(p,produitsExclus);
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

    protected boolean estPresent(Produit p, List<Produit> produits){
        for (Produit courant : produits) {
            if (courant.equals(p))
                return true;
            break;
        }
        return false;
    }
}
