import java.util.ArrayList;

public class Regle implements Comparable{

	ArrayList<Fait> premisses;
	ArrayList<Fait> consequences;
	int paquet;

	public Regle(ArrayList<Fait> premisses, ArrayList<Fait> consequences) {
		this.premisses = premisses;
		this.consequences = consequences;
		paquet=0;
	}

	public void setPaquet(int p){
		paquet=p;
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

	@Override
	public int compareTo(Object o) {
		if(o instanceof Regle){
			int p = ((Regle)o).paquet;
			return this.paquet-p; 
		}
		// TODO Auto-generated method stub
		return 0;
	}
	
	
}
