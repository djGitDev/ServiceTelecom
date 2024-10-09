package implementations;


import types.*;

import java.util.Date;
import java.util.Iterator;

public class AbonnementImpl implements Abonnement {

    private final Produit produit;
    private final Client client;
    private Date dateDebut;
    private Date dateFin;
    private TypeTermes termes;
    private float parametreTermes;
    private float coutAnnuel;

    public AbonnementImpl(Client cli, Produit prod){
        this.client = cli;
        this.produit = prod;
    }

    @Override
    public Produit getProduit() {
        return produit;
    }

    @Override
    public Client getClient() {
        return client;
    }

    @Override
    public Date getDateDebut() {
        return dateDebut;
    }

    @Override
    public void setDateDebut(Date dd) {
        dateDebut = dd;
    }

    @Override
    public Date getDateFin() {
        return dateFin;
    }

    @Override
    public void setDateFin(Date fin) {
        dateFin = fin;
    }

    @Override
    public float getParametreTermes() {
        return parametreTermes;
    }

    @Override
    public void setParametreTermes(float param) {
        parametreTermes = param;
    }

    @Override
    public float getCoutAnnuel() {
        Produit produitAbonne = getProduit();
        float coutAnnuelSansReduction = produitAbonne.getCoutAnnuel();
        if (termes == TypeTermes.REDUCTION_POURCENTAGE) {
            return coutAnnuelSansReduction * (1 - parametreTermes);
        }else if (termes == TypeTermes.REDUCTION_VALEUR) {
            return coutAnnuelSansReduction - parametreTermes;
        }
        return coutAnnuelSansReduction;
    }

    @Override
    public TypeTermes getTermes() {
        return termes;
    }

    @Override
    public void setTermes(TypeTermes termes) {
        this.termes = termes;
    }
}
