package implementations;


import types.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ForfaitProduitsImpl extends ProduitImpl implements ForfaitProduits {

    private List<Produit> produitsInclus= new ArrayList<>();

    public ForfaitProduitsImpl(String nom, String desc) {
        super(nom,desc);

    }


    @Override
    public void ajouteProduitInclus(Produit prod) {
        produitsInclus.add(prod);
        coutAnnuel += prod.getCoutAnnuel();
    }

    @Override
    public Produit retireProduitInclus(Produit prod) {
        coutAnnuel -= prod.getCoutAnnuel();
        return retireProduit(prod, produitsInclus);
    }

    @Override
    public Iterator<Produit> getProduitsInclus() {
        return produitsInclus.iterator();
    }
}
