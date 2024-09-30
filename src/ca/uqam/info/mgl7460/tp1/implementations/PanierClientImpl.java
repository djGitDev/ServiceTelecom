package ca.uqam.info.mgl7460.tp1.implementations;
import ca.uqam.info.mgl7460.tp1.types.Produit;
import ca.uqam.info.mgl7460.tp1.types.TypeTermes;
import ca.uqam.info.mgl7460.tp1.types.PanierClient;
import ca.uqam.info.mgl7460.tp1.types.Client;
import ca.uqam.info.mgl7460.tp1.types.Adresse;
import ca.uqam.info.mgl7460.tp1.types.NumeroTelephone;
import ca.uqam.info.mgl7460.tp1.types.Abonnement;
import ca.uqam.info.mgl7460.tp1.types.ExceptionProduitIncompatible;
import ca.uqam.info.mgl7460.tp1.types.ExceptionProduitRequis;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

public class PanierClientImpl implements PanierClient  {

    private float coutTotal;
    private float parametreTermes;
    private TypeTermes termes;

    private Client client;
    private List <Abonnement> abonnements;

    //constructeur
    public PanierClientImpl (float coutTotal, float parametreTermes, TypeTermes termes, Client client){
        this.coutTotal = coutTotal;
        this.parametreTermes = parametreTermes;
        this.termes = termes;
        this.client = client;
        this.abonnements = new ArrayList<>();

    }

    @Override
    public Client getClient() {
        return client;
    }

    @Override
    public TypeTermes getTermes() {
        return termes;
    }

    @Override
    public void setTermes(TypeTermes tt) {
        termes = tt;
    }

    @Override
    public float getParametreTermes() {
      return parametreTermes;
    }

    @Override
    public void setParametreTermes(float param) {
        parametreTermes = param;
    }

    // Calculer le cout total
    @Override
    public float getCoutTotal() {
        float coutTotal = 0.0f;
        for (Abonnement abonnement : abonnements) {
            coutTotal += abonnement.getCoutAnnuel();
        }

        // Applique le rabais si applicable
        if (termes == TypeTermes.REDUCTION_POURCENTAGE) {
            coutTotal = coutTotal * (1 - parametreTermes); // Applique le pourcentage de réduction
        }
        
        return coutTotal;
    }

    @Override
    public Iterator<Abonnement> getAbonnements() {
        return abonnements.iterator();
    }

    @Override
    public Iterator<Produit> getProduitsAbonnes() {

        List<Produit> produits = new ArrayList<>();
        for (Abonnement abonnement : abonnements) {
            produits.add(abonnement.getProduit());
        }
        return produits.iterator();
    }

    @Override
    public Abonnement ajouteProduit(Produit prod) throws ExceptionProduitIncompatible {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'ajouteProduit'");
    }

    @Override
    public Abonnement retirerAbonnement(Produit prod) throws ExceptionProduitRequis {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'retirerAbonnement'");
    }

    @Override
    public Abonnement getAbonnementPourProduit(Produit prod) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAbonnementPourProduit'");
    }
    
}
