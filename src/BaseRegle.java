import java.io.*;
import java.util.ArrayList;

public class BaseRegle {
	ArrayList<Regle> regles;

	public BaseRegle() {
		this.regles = new ArrayList<Regle>();
	}
	
	public boolean ajouter(Regle regle) {
		if(!regles.contains(regle)) {
			return regles.add(regle);
		}else {
			return false;
		}
	}
	
	/*
	public boolean supprimer(Regle regle) {
		if(regles.contains(regle)) {
			return regles.remove(regle);
		}else {
			return false;
		}
	}
	*/
	
	public void generer(String path) throws Exception {
		FileReader input = new FileReader(path);
		BufferedReader bufRead = new BufferedReader(input);
		String myLine = null;

		while ( (myLine = bufRead.readLine()) != null)
		{    
		    String[] array1 = myLine.split(" = ");
		    String[] premisses = array1[0].split(" & ");
		    String[] consequences = array1[1].split(" & ");
		    
		    ArrayList<Fait> listPremisses=new ArrayList<Fait>();
		    for(String pr: premisses) {
		    	Fait f;
		    	if(pr.charAt(0)=='~') {
		    		pr=pr.substring(1);
		    		f = new Fait(pr,false);
		    	}else {
		    		f = new Fait(pr,true);
		    	}
		    	listPremisses.add(f);	
		    }
		    
		    ArrayList<Fait> listConsequences=new ArrayList<Fait>();
		    for(String cs: consequences) {
		    	Fait f;
		    	if(cs.charAt(0)=='~') {
		    		cs=cs.substring(1);
		    		f = new Fait(cs,false);
		    	}else {
		    		f = new Fait(cs,true);
		    	}
		    	listConsequences.add(f);	
		    }
		    
		    Regle r = new Regle(listPremisses,listConsequences);
		    this.ajouter(r);
		}

	}

	@Override
	public String toString() {
		String str = "\n======== Base de règles ======================================";
		for(Regle regle : regles) {
			str+= "\n";
			str += regle.toString();
		}
		str+="\n==============================================================";
		return str;
	}
	
	
}
