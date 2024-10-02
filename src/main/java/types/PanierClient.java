package ca.uqam.info.mgl7460.tp1.types;

import java.util.Iterator;

/**
 * Elle représente le portefeuille de produits du client,
 * comprenant tous ses abonnements
 */
public interface PanierClient {

    /**
     * le client correspondant
     * @return
     */
    public Client getClient();

    /**
     * retourne le type de rabais, le cas échéant, dont bénéficie le 
     * "panier" (c-à-d le client correspondant)
     * @return
     */
    public TypeTermes getTermes();

    /**
     * modifie les termes (rabais) du panier
     * @param tt
     */
    public void setTermes(TypeTermes tt);

    /**
     * retourne le parametre des "termes". Si le type de termes
     * est "pourcentage de réduction", et le paramètre vaut 0.1,
     * cela veut dire que le client bénéficie de 10% de réduction
     * sur la valeur totale du panier, obtenue en additionnanl les
     * coûts des abonnements individuels (qui peuvent eux mêmes
     * bénéficier de réductions par rapport aux prix de liste)
     * @return
     */
    public float getParametreTermes();
    
    /**
     * modifie le paramètre des termes de réduction
     * correspondant. 
     * @param param
     */
    public void setParametreTermes(float param);

    /**
     * retourne le coût total du panier, en tenan compte
     * des termes et paramètres de rebais, le cas échéant.
     * @return
     */
    public float getCoutTotal();
    
    /**
     * un iterateur sur les abonnements
     * @return
     */
    public Iterator<Abonnement> getAbonnements();

    /**
     * un iterateur sur les <i>produits</i> auxquels le client
     * est abonné
     * @return
     */
    public Iterator<Produit> getProduitsAbonnes();

    /**
     * ajoute un produit au panier. 
     * S'il y a déjà un abonnement pour ce produit, on le retourne.
     * 
     * Sinon, si le nouveau produit est compatible
     * avec les produits déjà présents dans le panier, on crée un
     * abonnement et on l'ajoute. Sinon, on lance l' exception
     * <code>ExceptionProduitIncompatible</code> avec le produit existant
     * incompatible et le nouveau produit.
     * 
     * Si le produit exige d'autres produits, qui ne sont pas déjà
     * dans le panier, on les ajoute. Si, ce faisant, on tombe sur produit
     * incompatible, alors ça échoue.
     * 
     * @param prod
     * @return
     * @throws ExceptionProduitIncompatible
     */
    public Abonnement ajouteProduit(Produit prod) throws ExceptionProduitIncompatible;

    /**
     * on retire l'abonnement correspondant au produit passé
     * en paramètre, et on le retourne. Si le panier ne
     * contenait pas d'abonnement pour le produit en question.
     * Si le produit est requis par un autre abonnement, on lance
     * une exception, et on ne le retire pas.
     * ça retourne <code>null</code>
     * @param prod
     * @return
     */
    public Abonnement retirerAbonnement(Produit prod) throws ExceptionProduitRequis;

    /**
     * on retourne l'Abonnement correpondant au produit passé
     * en paramètre,<code>null</code> si le client n'est pas
     * abonné au produit en question.
     * @param prod
     * @return
     */
    public Abonnement getAbonnementPourProduit(Produit prod);
    
}
