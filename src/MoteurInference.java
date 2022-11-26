import java.util.ArrayList;
import java.util.Collections;

import javax.swing.plaf.basic.BasicEditorPaneUI;

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

	public static boolean chainageArriere(BaseRegle br, Fait but, BaseFait bf, BaseFait demandables , boolean paquet){
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
					dem = verif(br, r.getPremisses(), bf, demandables);
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
		return dem;
	}

	public static boolean verif(BaseRegle br, ArrayList<Fait> buts, BaseFait bf, BaseFait demandables){
		boolean ver = true;
		System.out.print("Verif : ");
		for(Fait b : buts){
			System.out.print(b.toString()+" ");
		}
		System.out.print("\n");

		for(Fait b : buts){
			ver = chainageArriere(br, b, bf, demandables,false);
		}
		return ver;
	}

	public static void main(String[] args) throws Exception {
		BaseRegle br = new BaseRegle();
		br.generer("regles.txt");
		System.out.println(br.toString());
		BaseFait bf = new BaseFait();
		bf.genererFaitsInitiaux("faitsInit.txt");
		System.out.println(bf.toString());
		//chainageAvant(br,bf,true);

		
		 
		BaseFait demandables = new BaseFait();
		demandables.genererFaits("faits.txt");
		Fait but = new Fait("muguet", true);
		String str="\n========================= But ===============================\n";
		str+=but.toString();
		str+="\n==============================================================";
		System.out.println(str);
		boolean trouver=chainageArriere(br, but, new BaseFait(), demandables,true);
		System.out.println(trouver);
		
	}

}
