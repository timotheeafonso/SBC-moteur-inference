import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

public class MoteurInference {
	
	public static void chainageAvant(BaseRegle br,BaseFait bf,boolean paquet) {
		ArrayList<Regle> explication = new ArrayList<Regle>();
		if(paquet){
			Collections.sort(br.getRegles());
		}

		int nbInf = 0;
		boolean inf = true;
		
		System.out.println("========================= Chainage avant ===============================\n");
		while(inf) {
			inf=false;
			for(Regle r : br.getRegles()) {
				boolean regleSupprimer=false;
				for(Regle re : explication) {
					if(re.equals(r)) {
						regleSupprimer=true;
					}
				}
				if(!regleSupprimer) {
					boolean dec = true;
					
					for (Fait antecedent : r.getPremisses()) {
						if(!bf.existFaitInitiaux(antecedent)) {
							dec=false;
						}
					}			
					if(dec) {
						for(Fait consequence : r.getConsequences()) {
							System.out.println("Regle apliquable: "+r.toString());
							boolean conflit=false;
							// Gestion conflit
							for(Regle rexplique : explication){
								for(Fait f : rexplique.getConsequences()){
									if(f.getElement().equals(consequence.getElement())){
										System.out.println("Conflit pour "+f.getElement());
										conflit=true;
										if(r.getPremisses().size()>rexplique.getPremisses().size()){
											bf.supprimerFaitInitiaux(f);
											bf.ajouterFaitInitiaux(consequence);
											System.out.println(consequence.toString()+" possède plus de prémisse, on applique donc: "+r.toString());
										}else if(r.getPremisses().size()==rexplique.getPremisses().size()){
											bf.supprimerFaitInitiaux(f);
											bf.ajouterFaitInitiaux(consequence);
											System.out.println(consequence.toString()+" possède autant de prémisse que "+ f.toString()+", On applique la regle avec les faits déduit les plus récents: "+r.toString());
										}else{
											System.out.println(f.toString()+" possède plus de prémisse, on applique donc: "+rexplique.toString());
										}
									}
								}
							}	
							for(Fait f : bf.getFaitsInitiaux()){
								if(f.getElement().equals(consequence.getElement()) && f.getVal()!=consequence.getVal() && conflit==false){
									conflit=true;
									System.out.println("Conflit entre le fait dans la base de fait: "+f.toString()+" et le fait dans la regle accepté: "+consequence.toString()+" on garde la première valeur de "+f.getElement());
								}
							}
							if(!conflit){
								bf.ajouterFaitInitiaux(consequence);
							}

							inf=true;
							nbInf++;
							explication.add(r);
						}
					}
				}
			}
		}
		System.out.println("==============================================================");

		String str="\n========== Base de Faits Final ===============================";
		
		for (Fait f : bf.getFaitsInitiaux()) {
			str+="\n"+f.toString();
		}
		str+="\n==============================================================";
		System.out.println(str);
	}

	public static boolean chainageArriere(BaseRegle br, Fait but, BaseFait bf , boolean paquet,Fait butInit){
		System.out.println("On démontre le but : "+but.toString());
		
		boolean dem = false;
			if(paquet){
				Collections.sort(br.getRegles());
			}
			
			//1er cas
			if(bf.existFaitInitiaux(but)){
				dem = true;
			}

			//2e cas
			int i=0;
			while(dem==false && i<=br.getRegles().size()){
				i++;
				for(Regle r : br.getRegles()){
					if(r.existFait(r.getConsequences(), but)){
						System.out.println("Regle utilisé: "+r.toString());
						dem = verif(br, r.getPremisses(), bf,butInit);
					}
				}
			}

			//3e cas
			if(!dem){
				for (Regle r : br.getRegles()){
					if(!r.existFait(r.getConsequences(),but)){
								dem=true;
					}
				}
			}
			boolean conflit=false;

			for(Fait f : bf.getFaitsInitiaux()){
				if(f.getElement().equals(but.getElement()) && (f.getVal()!=but.getVal() || f.isEtat() != but.isEtat())){
					System.out.println("Conflit entre le fait dans la base de fait: "+f.toString()+" et le nouveau but à accepté: "+but.toString()+" on garde la première valeur de "+f.getElement());
					conflit=true;
				}
			}
			if(!conflit){
				if(dem){
					bf.ajouterFaitInitiaux(but);
				}
			}
		
		if(bf.existFaitInitiaux(butInit)){
			System.out.println("\n==============================================================");
			System.out.println(bf.toString());
		}

		return dem;
	}

	public static boolean verif(BaseRegle br, ArrayList<Fait> buts, BaseFait bf,Fait butInit){
		boolean ver = true;
		System.out.print("Il faut vérifier : ");
		int i=0;
		for(Fait b : buts){
			System.out.print(b.toString()+" ");
			if(i<buts.size()-1){
				System.out.print("et ");
			}
			i++;
		}
		System.out.println("\n");

		for(Fait b : buts){
			ver = chainageArriere(br, b, bf,false,butInit);
		}
		return ver;
	}

	public static boolean checkCoherence(BaseFait bf,BaseRegle br){
		boolean coherencebr=true;
		boolean coherencebf=true;

		ArrayList<Fait> faitsDansRegles= new ArrayList<Fait>();
		ArrayList<String> faitsCheck= new ArrayList<String>();
		for(Regle r  : br.getRegles()){
			for(Fait f : r.getConsequences()){
				faitsDansRegles.add(f);
			}
			for(Fait f : r.getPremisses()){
				faitsDansRegles.add(f);
			}
		}
		for(Fait f: faitsDansRegles){
			for(Fait f2 : faitsDansRegles){
				if(f.getElement().equals(f2.getElement()) && ((f.getVal()==null && f2.getVal()!=null)||(f.getVal()!=null && f2.getVal()==null))){
					faitsCheck.add(f.getElement());
					coherencebr=false;
				}
			}
		}

		for(Regle r : br.getRegles()){
			for(Fait f : r.getPremisses()){
				for(Fait f2 : r.getConsequences()){
					if(f.getElement().equals(f2.getElement())){
						if(!r.existFait(r.getConsequences(), f)){
							System.out.println("erreur: incohérence dans la règle "+r.toString()+" pour le fait "+f.getElement());
						}
					}
				}
				
			}
		}

		HashSet<String> uniqueFaitsCheck = new HashSet<String>(faitsCheck);
		for(String s : uniqueFaitsCheck){
			System.out.println("erreur: incohérence dans la base de règle\n-deux type de valeur différent pour le fait "+s+" (booleen/variable)");
		}
		faitsCheck.clear();
		uniqueFaitsCheck.clear();

		for(Fait f : bf.getFaitsInitiaux()){
			for(Fait f2 : bf.getFaitsInitiaux()){
				if(f.getElement().equals(f2.getElement()) && ((f.getVal()==null && f2.getVal()!=null)||(f.getVal()!=null && f2.getVal()==null) ||(f.getVal()!=f2.getVal()))){
					faitsCheck.add(f.getElement());
					coherencebf=false;
				}
			}
		}
		uniqueFaitsCheck = new HashSet<String>(faitsCheck);
		for(String s : uniqueFaitsCheck){
			System.out.println("erreur: incohérence dans la base de fait\nfait "+s+" insérer plusieurs fois");
		}
		faitsCheck.clear();


		
		return coherencebf && coherencebr;
	}

	public static void execChainageAvant(BaseFait bf,BaseRegle br,boolean paquet){
		System.out.println(br.toString());
		System.out.println(bf.toString());
		checkCoherence(bf, br);
		chainageAvant(br,bf,paquet);
	}

	public static void execChainageArriere(BaseFait bf,BaseRegle br,Fait but,boolean paquet){
		System.out.println(br.toString());
		String str="\n========================= But ===============================\n";
		str+=but.toString();
		str+="\n==============================================================";
		System.out.println(str);
		checkCoherence(bf, br);
		System.out.println("\n========================= Chainage arriere ===============================\n");
		chainageArriere(br, but, bf,paquet,but);	
	}

	public static void main(String[] args) throws Exception {
		
		BaseFait bf = new BaseFait();
		try {
			bf.genererFaitsInitiaux("faitsInit2.txt");
		} catch (Exception e) {
			e.printStackTrace();
		}
		BaseRegle br = new BaseRegle();
		try {
			br.generer("regles2.txt");
		} catch (Exception e) {
			e.printStackTrace();
		}

		execChainageAvant(bf,br,true);

		Fait but = new Fait("orage", true);

		//execChainageArriere(new BaseFait(),br,but,true);
	}

}
