import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.ParseException;

public class Officiel implements Comparable{
	String nom;
	String prenom;
	String libelle;
	String championnat;
	int match_no;
	String local_eq;
	String visiteur_eq;
	String categorie;
	String horaire;
	Date match_date;
	String lieu;
		
	public Officiel(String st) {
		try {
			String[] parts=st.split(",");
			this.nom=parts[0];
			this.prenom=parts[1];
			this.libelle=parts[2];
			this.championnat=parts[3];
			this.match_no=Integer.parseInt(parts[4]);
			this.local_eq=parts[5];
			this.visiteur_eq=parts[6];
			this.categorie=parts[7];
			this.horaire=parts[8];
			this.match_date=new SimpleDateFormat("dd/MM/yyyy").parse(parts[9]);
			this.lieu=parts[10];
		} catch (ParseException e) {
            e.printStackTrace();
        }
	}
	
	public String get_NomPrenom() {
		return this.nom+","+this.prenom;
	}
	public String get_NomPrenomStDt() {
		return this.nom+","+this.prenom+","+this.libelle+","+this.match_date;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nom == null) ? 0 : nom.hashCode());
		result = prime * result + ((prenom == null) ? 0 : prenom.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Officiel other = (Officiel) obj;
		if (nom == null) {
			if (other.nom != null)
				return false;
		} else if (!nom.equalsIgnoreCase(other.nom))
			return false;
		if (prenom == null) {
			if (other.prenom != null)
				return false;
		} else if (!prenom.equalsIgnoreCase(other.prenom))
			return false;
		return true;
	}
	
	public Date getMatch_date() {
		return match_date;
	}

	public void setMatch_date(Date match_date) {
		this.match_date = match_date;
	}

	@Override
	public String toString() {
		return this.get_NomPrenom() + " - " + this.getMatch_date();
	}

	@Override
	public int compareTo(Object o) {
		//return  this.get_NomPrenom().compareTo(((Officiel) o).get_NomPrenom()) ;
		return this.get_NomPrenom().compareTo(((Officiel) o).get_NomPrenom()) + this.getMatch_date().compareTo(((Officiel) o).match_date);
	}
	

}
