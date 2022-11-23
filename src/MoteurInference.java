import java.util.ArrayList;

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

	/*public static boolean chainageArriere(BaseRegle br, Fait but, BaseFait bf, BaseFait demandables){
		
		boolean dem = false;

		//1er cas
		if(bf.getFaitsInitiaux().contains(but)){
			dem = true;
		}
		
		//2e cas
		if( //Si b déductible à partir de BR U BF){
			for(Regle r : br.getRegles()){
				while(dem == false){
					//dem = verif(br, Antecedent(r), bf, demandables)
				}
			}
		}
		
		//3e cas
		if(dem==false && demandables.getFaitsInitiaux().contains(but)){
			//Poser la question : b? 
			//dem = reponse(b)
		}

		if(dem){
			bf.ajouterFaitInitiaux(but);
		}

		return dem;
	}

	public boolean verif(BaseRegle br, BaseFait buts, BaseFait bf, BaseFait demandables){
		boolean ver = true;

		for(Fait b : buts.getFaitsInitiaux()){
			while(ver){
				ver = chainageArriere(br, b, bf, demandables);
			}
		}

		return ver;
	}*/

	public static void main(String[] args) throws Exception {
		BaseRegle br = new BaseRegle();
		br.generer("regles.txt");
		System.out.println(br.toString());
		BaseFait bf = new BaseFait();
		//bf.genererFaits("faits.txt");
		bf.genererFaitsInitiaux("faitsInit.txt");
		System.out.println(bf.toString());
		chainageAvant(br,bf);


		/*BaseFait demandables = new BaseFait();
		demandables.genererFaits("faits.txt");
		Fait but = new Fait("lilas", true);

		chainageArriere(br, but, new BaseFait(), demandables);*/
		
	}

}
