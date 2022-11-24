import java.util.ArrayList;
import java.util.Collections;

public class MoteurInference {
	
	public static void chainageAvant(BaseRegle br,BaseFait bf) {
		ArrayList<Regle> explication = new ArrayList<Regle>();

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
					
					for (Fait antecedent : r.premisses) {
						if(!bf.existFaitInitiaux(antecedent)) {
							dec=false;
						}
					}			
					if(dec) {
						for(Fait consequence : r.consequences) {
							bf.ajouterFaitInitiaux(consequence);
							//br.supprimer(r);
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
	}

	public static void chainageArriere(BaseRegle br,BaseFait but) {
		
	}


	public static void main(String[] args) throws Exception {
		BaseRegle br = new BaseRegle();
		br.generer("regles.txt");
		Collections.sort(br.getRegles());
		System.out.println(br.toString());
		BaseFait bf = new BaseFait();
		//bf.genererFaits("faits.txt");
		bf.genererFaitsInitiaux("faitsInit.txt");
		System.out.println(bf.toString());
		chainageAvant(br,bf);
		
	}

}
