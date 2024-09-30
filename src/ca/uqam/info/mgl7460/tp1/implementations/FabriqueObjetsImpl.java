package ca.uqam.info.mgl7460.tp1.implementations;

import ca.uqam.info.mgl7460.tp1.types.*;

import java.util.HashMap;
import java.util.Iterator;

public class FabriqueObjetsImpl implements FabriqueObjets {

    private static FabriqueObjetsImpl instance = null;

    private HashMap<String, Client> clients = new HashMap<>();
    private HashMap<String, Produit> produits = new HashMap<>();


    @Override
    public Client creerClient(String nom, String prenom) {
        Client client =  new ClientImpl(nom,prenom);
        clients.put(client.getIdClient(),client);
        return client;
    }

    @Override
    public Produit creerProduit(String nom, String description) {
        Produit produit = new ProduitImpl(nom, description);
        produits.put(produit.getNumero(),produit);
        return produit;
    }

    @Override
    public ForfaitProduits creerForfaitProduits(String nom, String description) {
        ForfaitProduits produit =  new ForfaitProduitsImpl(nom, description);
        produits.put(produit.getNumero(),produit);
        return produit;
    }

    @Override
    public PanierClient creerPanierClient(Client cli) {
        return new PanierClientImpl(cli);
    }

    @Override
    public Abonnement creerAbonnement(Client cli, Produit prod) {
        return new AbonnementImpl(cli,prod);
    }

    @Override
    public Client getClientAvecId(String id) {
        return  clients.get(id);
    }

    @Override
    public Iterator<Client> getListeClients() {
        return clients.values().iterator();
    }

    @Override
    public Iterator<Produit> getListeProduits() {
        return produits.values().iterator();
    }

    @Override
    public Produit getProduitAvecNumero(String numero) {
        return produits.get(numero);
    }

    public static  FabriqueObjetsImpl getSingleton() {
        if (instance == null) {
            instance = new FabriqueObjetsImpl();
        }
        return instance;
    }



}
