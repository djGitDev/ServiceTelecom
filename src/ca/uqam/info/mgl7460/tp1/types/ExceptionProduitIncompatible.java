package ca.uqam.info.mgl7460.tp1.types;

public class ExceptionProduitIncompatible extends Exception {
    private final Produit existant;
    private final Produit additionnel;
    public ExceptionProduitIncompatible(Produit nouveau, Produit ancien){
        super("On ne peut ajouter: "+ nouveau.getSommaire()+ " car il entre en conflit avec produit: " + ancien.getSommaire() + " dans le panier.");
        existant = ancien;
        additionnel = nouveau;
    }

    public Produit getProduitExistant() { return existant;}

    public Produit getProduitAdditionnel() {return additionnel;}
}
