import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class ListeOTMClub {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws IOException {
		// Init variable   
		ArrayList<Officiel> listeOfficiels = new ArrayList<>();
		int count = 0;
		String foldername="C:\\temp\\GetOTMClub\\data";
		String Outfilename = "C:\\temp\\GetOTMClub\\data\\OTM_Club.csv";
	
		FileWriter out = new FileWriter(Outfilename);
		File FolderList = new File(foldername);
		File[] fList = FolderList.listFiles();
		Arrays.sort(fList);
		//String MsgContent="";
		//String MsgDate="";
		StringBuffer fileOutputContent = new StringBuffer("");
	
		for (File ficmsg : fList) {
			System.out.println("Fichier : "+foldername+"/"+ficmsg.getName());
			FileReader reader = new FileReader(foldername+"/"+ficmsg.getName());
			BufferedReader br = new BufferedReader(reader);
	
			String ligne = "";
			ligne = br.readLine();
			while ((ligne = br.readLine()) != null) {
				if (ligne.contains(",")) {
					//System.out.println(ligne);
					Officiel rec = new Officiel(ligne);
					listeOfficiels.add(rec);
					count++;
				}
			}
			br.close();
		}
			   
		System.out.println(count+" elements enregistrés");
		Collections.sort( listeOfficiels );
		
		String NomPrenom = "";
		String Statut = "club en formation";
		String DateMatch5="";
		int NumMatchs=0;
		fileOutputContent.append("Nom,Prenom,Statut,NumMatchs,DateMatch5\n");
		
		for(Officiel str: listeOfficiels){
			//System.out.println(str.toString());
			if (! NomPrenom.equalsIgnoreCase(str.get_NomPrenom())){
				if (! NomPrenom.isEmpty()) {
					if (! Statut.equalsIgnoreCase("club en formation")) {
						fileOutputContent.append(NomPrenom+","+Statut+","+NumMatchs+","+DateMatch5+"\n");
					}
				}
				NomPrenom = str.get_NomPrenom();
				Statut = "club en formation";
				DateMatch5="";
				NumMatchs=0;
			}
			// || Statut.contains("Officiel")
			if(Statut.contains("club en formation") && (str.get_Statut().toLowerCase().contains("club") || str.get_Statut().toLowerCase().contains("formation"))) { 
				Statut=str.get_Statut();
				NumMatchs++;
				if (NumMatchs == 5) {
					DateMatch5=str.get_DateMatchOutput();
				}
				if (! Statut.toLowerCase().contains("formation")) {
					DateMatch5="";
				}
			}
		}
		if (! Statut.equalsIgnoreCase("club en formation")) {
			fileOutputContent.append(NomPrenom+","+Statut+","+NumMatchs+","+DateMatch5+"\n");
		}

		out.append(fileOutputContent);
		out.close();
	}
}
