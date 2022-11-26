import java.util.ArrayList;

public class Regle implements Comparable{

	private int id;
	private ArrayList<Fait> premisses;
	private ArrayList<Fait> consequences;
	private int paquet;

	public Regle(ArrayList<Fait> premisses, ArrayList<Fait> consequences) {
		this.premisses = premisses;
		this.consequences = consequences;
		paquet=0;
	}

	public void setPaquet(int p){
		paquet=p;
	}
	

	public ArrayList<Fait> getPremisses() {
		return premisses;
	}

	public ArrayList<Fait> getConsequences() {
		return consequences;
	}

	public int getPaquet() {
		return paquet;
	}

	@Override
	public String toString() {
		int i=0;
		String pr=Integer.toString(paquet)+" ";
		for(Fait premisse : premisses) {
			if(i>0) {
				pr+= " et ";
			}
			pr+=premisse.toString();
			i++;
		} 
		i=0;
		pr += " -> ";
		for(Fait consequence : consequences) {
			if(i>0) {
				pr+= " et ";
			}
			pr+=consequence.toString();
		}
		return pr;
	}

	public boolean existFait(ArrayList<Fait> Listfaits,Fait fait) {
		boolean exist=false;
		
			for(Fait f : Listfaits) {
				if(fait.getVal()==null){
					if(f.isEtat()==fait.isEtat() && f.getElement().equals(fait.getElement())) {
						exist=true;
					}
				}else{					
					if(f.getElement().equals(fait.getElement())) {
						switch(fait.getSymbole()){
							case ">":
								exist=f.getVal()>fait.getVal();
								break;
							case "<":
								exist=f.getVal()<fait.getVal();
								break;
							case "==":							
								exist=f.getVal()==fait.getVal();
								break;
							case "<=":
								exist=f.getVal()<=fait.getVal();
								break;
							case ">=":
								exist=f.getVal()>=fait.getVal();
								break;
						}
					}
				}
			}
		return exist;
	}

	@Override
	public int compareTo(Object o) {
		if(o instanceof Regle){
			int p = ((Regle)o).paquet;
			return p-this.paquet; 
		}
		return 0;
	}
	
	
}
