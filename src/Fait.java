
public class Fait {
	
	String element;
	boolean etat;
	Integer val;

	public Fait(String element, boolean etat) {
		this.element = element;
		this.etat = etat;
	}

	public Fait(String element, int val) {
		this.element = element;
		this.val = val;
	}

	@Override
	public String toString() {
		String pr= "";
		if(this.val == null){
			if(!etat) {
				pr+="non ";
			}
		}else{
			pr+="="+val;
		}
		pr += element;
		return pr;
	}
	
	
}
