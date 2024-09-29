package ca.uqam.info.mgl7460.tp1.implementations;

import ca.uqam.info.mgl7460.tp1.types.Abonnement;
import ca.uqam.info.mgl7460.tp1.types.Adresse;
import ca.uqam.info.mgl7460.tp1.types.Client;
import ca.uqam.info.mgl7460.tp1.types.ExceptionProduitIncompatible;
import ca.uqam.info.mgl7460.tp1.types.FabriqueObjets;
import ca.uqam.info.mgl7460.tp1.types.NumeroTelephone;
import ca.uqam.info.mgl7460.tp1.types.PanierClient;
import ca.uqam.info.mgl7460.tp1.types.Produit;
import java.util.Iterator;


public class ClientImpl implements Client {

    private static int compteur = 0;

    private final String idClient;
    private final String nom;
    private final String prenom;
    private Adresse adresse;
    private NumeroTelephone numeroTelephone;
    private PanierClient panierClient;


    public ClientImpl(String nom, String prenom) {
        
        this.nom = nom;
        this.prenom = prenom;
        ClientImpl.compteur++;
        this.idClient = "CL-" + ClientImpl.compteur;
    }


    /**
     * retourne un identificateur unique pour le client, qui est
     * généré automatiquement par le système
     *
     * @return
     */
    @Override
    public String getIdClient() {
        return idClient;
    }

    /**
     * retourne le nom de famille du client
     *
     * @return
     */
    @Override
    public String getNom() {
        return nom;
    }

    /**
     * retourne le prénom du client
     *
     * @return
     */
    @Override
    public String getPrenom() {
        return prenom;
    }

    /**
     * retourne l'adresse du client. On suppose que c'est l'adresse où
     * le service doit être offert, mais aussi l'adresse de facturatio
     *
     * @return
     */
    @Override
    public Adresse getAdresse() {
        return adresse;
    }

    /**
     * modifie l'adresse du cliet
     *
     * @param adr
     */
    @Override
    public void setAdresse(Adresse adr) {
        this.adresse = adr;
    }

    /**
     * retourne le numéro de téléphone du client
     *
     * @return
     */
    @Override
    public NumeroTelephone getNumeroTelephone() {
        return this.numeroTelephone;
    }

    /**
     * modifie le numéro de téléphone du clienty
     *
     * @param numero
     */
    @Override
    public void setNumeroTelephone(NumeroTelephone numero) {
        this.numeroTelephone = numero;
    }

    /**
     * Cette méthode crée un <code>PanierClient</code>, l'associe
     * au client, et le retourne
     *
     * @return
     */
    @Override
    public PanierClient creerPanier() {
         if(panierClient == null){
            FabriqueObjets fabrique = FabriqueObjets.getSingleton();
            panierClient = fabrique.creerPanierClient(this);
        }
        return panierClient;
    }

    /**
     * Cette méthode retourne le <code>PanierClient</code> associé
     * au client, <code>null</code> s'il n'y en a pas.
     *
     * @return
     */
    @Override
    public PanierClient getPanier() {
        if(panierClient == null){
            creerPanier();
        }
        return panierClient;    
    }

    /**
 * Cette méthode tente d'abonner le client au <code>Produit</code> passé
 * en paramètre:
 * 1) si le nouveau produit est incompatible avec les produits (abonnements)
 * existants, la méthode lance l'exception <code>ExceptionProduitIncompatible</code>.
 * 2) Sinon, on crée un abonnement pour le produit, et on l'ajoute au panier.
 * <p>
 * Si le client n'avait pas un panier en partant, la méthode va en créer un
 * pour le client
 *
 * @param produit
 * @return
 * @throws ExceptionProduitIncompatible
 */
@Override
public Abonnement abonneClient(Produit produit) throws ExceptionProduitIncompatible {
    return getPanier().ajouteProduit(produit);
    
}

    /**
     * cette méthode retourne l'ensemble d'abonnements du client
     *
     * @return
     */
    @Override
    public Iterator<Abonnement> getAbonnements() {
        return getPanier().getAbonnements();
    }
}
