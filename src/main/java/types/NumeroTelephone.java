package types;

public record NumeroTelephone(int codePays, int codeRegional, int numero, int ... poste) {
    public boolean equals(Object obj){
        if (!(obj instanceof NumeroTelephone)) return false;
        NumeroTelephone arg = (NumeroTelephone)obj;
        return (this.codePays == arg.codePays) && 
        (this.codeRegional == arg.codeRegional) &&
        (this.numero == arg.numero) &&
        (this.poste == arg.poste);
    }

    public String toString() {
        String returnString = "+" + codePays + "(" + codeRegional + ")" + numero;
        if (poste.length > 0) returnString += ", poste " + poste;
        return returnString;
    }

    public int hashCode() {
        return toString().hashCode();
    }
    
}
