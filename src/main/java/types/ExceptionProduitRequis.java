package types;

public class ExceptionProduitRequis extends Exception {
    private final Produit produitAEnlever;
    private final Produit produitDependant;
    public ExceptionProduitRequis(Produit aEnlever, Produit dependant){
        super("On ne peut retirer: "+ aEnlever.getSommaire()+ " car il est reqauis par: " + dependant.getSommaire() + " dans le panier.");
        produitAEnlever = aEnlever;
        produitDependant = dependant;
    }

    public Produit getProduitAEnlever() { return produitAEnlever;}

    public Produit getProduitDependant() {return produitDependant;}
    
}
