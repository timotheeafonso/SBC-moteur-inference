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
							bf.ajouterFaitInitiaux(consequence);
							inf=true;
							nbInf++;
							explication.add(r);
						}
					}
				}
			}
		}
		
		String str="\n========== Base de Faits Final ===============================";
		
		for (Fait f : bf.getFaitsInitiaux()) {
			str+="\n"+f.toString();
		}
		str+="\n==============================================================";
		System.out.println(str);

		for(Regle r: explication){
			System.out.println(r.toString());
		}
	}

	public static boolean chainageArriere(BaseRegle br, Fait but, BaseFait bf , boolean paquet,Fait butInit){
		System.out.println("Demo : "+but.toString());
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

		if(dem){
			bf.ajouterFaitInitiaux(but);
		}
		if(bf.existFaitInitiaux(butInit))
			System.out.println("\nRésultat :\n"+dem+bf.toString());
		
		return dem;
	}

	public static boolean verif(BaseRegle br, ArrayList<Fait> buts, BaseFait bf,Fait butInit){
		boolean ver = true;
		System.out.print("Verif : ");
		for(Fait b : buts){
			System.out.print(b.toString()+" ");
		}
		System.out.print("\n");

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
				if(f.getElement().equals(f2.getElement()) && ((f.getVal()==null && f2.getVal()!=null)||(f.getVal()!=null && f2.getVal()==null))){
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

	public static void main(String[] args) throws Exception {
		
		
		BaseRegle br = new BaseRegle();
		br.generer("regles.txt");
		System.out.println(br.toString());
		BaseFait bf = new BaseFait();
		bf.genererFaitsInitiaux("faitsInit.txt");
		System.out.println(bf.toString());
		checkCoherence(bf, br);
		chainageAvant(br,bf,true);
		
		/* 
		BaseFait bf = new BaseFait();
		bf.genererFaitsInitiaux("faitsInit.txt");
		BaseRegle br = new BaseRegle();
		br.generer("regles.txt");
		System.out.println(br.toString());
		Fait but = new Fait("B", true);
		String str="\n========================= But ===============================\n";
		str+=but.toString();
		str+="\n==============================================================";
		System.out.println(str);
		checkCoherence(bf, br);
		//chainageArriere(br, but, new BaseFait(),true,but);		
		*/
		
	}

}
