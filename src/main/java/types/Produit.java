package ca.uqam.info.mgl7460.tp1.types;
import java.util.Iterator;

public interface Produit {

    public String getNumero();

    public String getNom();

    public String getDescription();

    public void setDescription(String desc);

    public float getCoutAnnuel();

    public void setCoutAnnuel(float cout);

    public void ajouteProduitExige(Produit prod);

    public Produit retireProduitExige(Produit prod);

    public Iterator<Produit> getProduitsExiges();

    public void ajouteProduitExclus(Produit prod);

    public Produit retireProduitExclus(Produit prod);

    public Iterator<Produit> getProduitsExclus();

    public boolean requiert(Produit p);

    public boolean exclue(Produit p);

    /**
     * retourne une description sommaire du produit, consistant
     * en le numéro de produit puis le nom entre []
     * @return
     */
    public String getSommaire();

}
