import java.util.ArrayList;

public class Regle {
	ArrayList<Fait> premisses;
	ArrayList<Fait> consequences;
	
	public Regle(ArrayList<Fait> premisses, ArrayList<Fait> consequences) {
		this.premisses = premisses;
		this.consequences = consequences;
	}

	@Override
	public String toString() {
		int i=0;
		String pr="";
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
	
	
}
