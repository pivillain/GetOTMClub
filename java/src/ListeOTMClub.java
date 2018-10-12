import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class ListeOTMClub {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws IOException {
		   ArrayList<Officiel> listeOfficiels = new ArrayList<>();
		   int count = 0;
		   
		   String file = "C:\\temp\\GetOTMClub\\data\\2014_2018.csv";
		   
		   
		   BufferedReader br = new BufferedReader(new FileReader(file));
		   String ligne = "";
		   ligne = br.readLine();
		   while ((ligne = br.readLine()) != null) {
		            Officiel rec = new Officiel(ligne);
		            listeOfficiels.add(rec);
		            count++;
		   }
		   br.close();
		   
		   System.out.println(count+" elements enregistrés");
		   Collections.sort( listeOfficiels );
		   for(Officiel str: listeOfficiels){
				System.out.println(str.toString());
		   }
	}
}
