
public class Fait {
	
	String element;
	boolean etat;
	
	public Fait(String element, boolean etat) {
		this.element = element;
		this.etat = etat;
	}

	@Override
	public String toString() {
		String pr= "";
		if(!etat) {
			pr+="non ";
		}
		pr += element;
		return pr;
	}
	
	
}
