package ca.uqam.info.mgl7460.tp1.types;

import java.util.Iterator;

/**
 * @author: Hafedh Mili
 * Cette interface représente un forfait composé de plusieurs produits.
 * C'est une implantation légère du patron composite.
 */
public interface ForfaitProduits extends Produit{

    public void ajouteProduitInclus(Produit prod);

    public Produit retireProduitInclus(Produit prod);

    public Iterator<Produit> getProduitsInclus();

}