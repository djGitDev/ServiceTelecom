package ca.uqam.info.mgl7460.tp1.types;

import ca.uqam.info.mgl7460.tp1.implementations.FabriqueObjetsImpl;

import java.util.Iterator;

//import ca.uqam.info.mgl7460.tp1.implementations.FabriqueObjetsImpl;

/**
 * cette interface représente la <>fabrique abstraite</i> pour cette
 * application. On va trouver une méthode par type du domaine,
 * sauf pour <code>Adresse</code> et <code>NumeroTelephone</code>, qui
 * sont des <code>record</code>, et <code>TypeTermes</code> qui
 * est une énumération.
 * 
 * Pour chaque méthode de création, on se limite aux "attributs identifiants"
 * (e.g, nom et prénom pour le client) que l'on doit nécessairement spécifier au
 * moment de la construction de l'objet (ceux qui représenteraient une "primary key").
 * D'ailleurs, on reconnait ces attributs par le fait qu'ils aient un getter et pas
 * un setter: on ne peut les modifier après la création des objets.
 * 
 * Les autres attributs des objets concernés peuvent être initialisés avec les 
 * setters fournis dans les interfaces correspondantes
 * 
 */
public interface FabriqueObjets {
    
    /**
     * retourne un client avec nom et prenom passés en paramètre. Un client
     * ID est créé automatiquement et affecté à l'attribut correspondant
     * @param nom
     * @param prenom
     * @return
     */
    public Client creerClient(String nom, String prenom);

    /**
     * retourne un produit avec nom et description passés en paramètre. Un ID
     * produit est créé automatiquement et affecté à l'attribut correspondant
     * @param nom
     * @param description
     * @return
     */
    public Produit creerProduit(String nom, String description);

    /**
     * retourne un <code>ForfaitProduits</code> avec nom et description passés 
     * en paramètre. Un ID produit est créé automatiquement et affecté à 
     * l'attribut correspondant. Les éléments du (produits compris dans le)
     * forfait peuvent être ajoutés ou retirés par la suite par les méthodes 
     * d'instance de <code>ForfaitProduits</code>
     * 
     * @param nom
     * @param description
     * @return
     */
    public ForfaitProduits creerForfaitProduits(String nom, String description);

    /**
     * Crée un <code>PanierClient</code> pour le <code>Client</code>
     * passé en paramètre, et lie les deux
     * @param cli
     * @return
     */
    public PanierClient creerPanierClient(Client cli);

    /**
     * Crée un <code>Abonnement</code> pour le client 
     * <code>cli</code> au produit <code>prod</code>,
     * et lie le client à l'abonnement
     * @param cli
     * @param prod
     * @return
     */
    public Abonnement creerAbonnement(Client cli, Produit prod);

    /**
     * 
     * @param id
     * @return
     */
    public Client getClientAvecId(String id);

    /**
     * Retourne la liste de clients
     * @return
     */
    public Iterator<Client> getListeClients();

    /**
     * Retourne la liste de produits
     * @return
     */
    public Iterator<Produit> getListeProduits();

    /**
     * Retourne produit ayant comme numéro 
     * @param numero
     * @return
     */
    public Produit getProduitAvecNumero(String numero);

    public static FabriqueObjets getSingleton() {
        return FabriqueObjetsImpl.getSingleton();
    }
}
