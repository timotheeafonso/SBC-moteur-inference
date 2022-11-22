
public class Fait {
	
	private String element;
	private boolean etat;
	private Integer val;
	private String symbole;

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

	public String getElement() {
		return element;
	}

	public boolean isEtat() {
		return etat;
	}

	public Integer getVal() {
		return val;
	}

	public String getSymbole() {
		return symbole;
	}

	
	
	
}
