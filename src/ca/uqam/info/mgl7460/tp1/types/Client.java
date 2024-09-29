package ca.uqam.info.mgl7460.tp1.types;

import java.util.Iterator;

public interface Client {

    /**
     * retourne un identificateur unique pour le client, qui est
     * généré automatiquement par le système
     * @return
     */
    public String getIdClient();

    /**
     * retourne le nom de famille du client
     * @return
     */
    public String getNom();

    /**
     * retourne le prénom du client
     * @return
     */
    public String getPrenom();

    /**
     * retourne l'adresse du client. On suppose que c'est l'adresse où
     * le service doit être offert, mais aussi l'adresse de facturatio
     * @return
     */
    public Adresse getAdresse();

    /**
     * modifie l'adresse du cliet
     * @param adr
     */
    public void setAdresse(Adresse adr);

    /**
     * retourne le numéro de téléphone du client
     * @return
     */
    public NumeroTelephone getNumeroTelephone();

    /**
     * modifie le numéro de téléphone du clienty
     * @param numero
     */
    public void setNumeroTelephone(NumeroTelephone numero);

    /**
     * Cette méthode crée un <code>PanierClient</code>, l'associe
     * au client, et le retourne
     * @return
     */
    public PanierClient creerPanier();

    /**
     * Cette méthode retourne le <code>PanierClient</code> associé
     * au client, <code>null</code> s'il n'y en a pas.
     * @return
     */
    public PanierClient getPanier();

   /**
     * Cette méthode tente d'abonner le client au <code>Produit</code> passé
     * en paramètre:
     * 1) si le nouveau produit est incompatible avec les produits (abonnements)
     * existants, la méthode lance l'exception <code>ExceptionProduitIncompatible</code>.
     * 2) Sinon, on crée un abonnement pour le produit, et on l'ajoute au panier.
     * 
     * Si le client n'avait pas un panier en partant, la méthode va en créer un 
     * pour le client
     * @param produit
     * @return
     * @throws ExceptionProduitIncompatible
    */
    public Abonnement abonneClient(Produit produit) throws ExceptionProduitIncompatible;

    /**
     * cette méthode retourne l'ensemble d'abonnements du client
     * @return
     */
    public Iterator<Abonnement> getAbonnements();

}
