
public class Fait {
	
	String element;
	boolean etat;
	Integer val;
	String symbole;

	public Fait(String element, boolean etat) {
		this.element = element;
		this.etat = etat;
	}

	public Fait(String element, int val, String symbole) {
		this.element = element;
		this.val = val;
		this.symbole = symbole;
	}

	@Override
	public String toString() {
		String pr= "";
		if(this.val == null){
			if(!etat) {
				pr+="non ";
			}
		}else{
			pr+= symbole+val;
		}
		pr += element;
		return pr;
	}
	
	
}
