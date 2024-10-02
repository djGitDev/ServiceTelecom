package types;
import java.util.Date;

public interface Abonnement {

    public Produit getProduit();

    public Client getClient();

    public Date getDateDebut();

    public void setDateDebut(Date dd);

    public Date getDateFin();

    public void setDateFin(Date fin);

    public float getParametreTermes();

    public void setParametreTermes(float param);

    public float getCoutAnnuel();

    public TypeTermes getTermes();

    public void setTermes(TypeTermes termes);

}
