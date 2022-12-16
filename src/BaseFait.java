import java.io.*;
import java.util.ArrayList;

public class BaseFait {
	private ArrayList<Fait> faitsInitiaux;
	//ArrayList<Fait> faits;
	
	public BaseFait() {
		this.faitsInitiaux = new ArrayList<Fait>();
		//this.faits = new ArrayList<Fait>();

	}
	
	/*
	public boolean ajouterFait(Fait fait) {
		if(!faits.contains(fait)) {
			return faits.add(fait);
		}else {
			return false;
		}
	}
	
	public boolean supprimerFait(Fait fait) {
		if(faits.contains(fait)) {
			return faits.remove(fait);
		}else {
			return false;
		}
	}
	*/
	
	public boolean ajouterFaitInitiaux(Fait fait) {
		if(!existFaitInitiaux(fait)) {
			return faitsInitiaux.add(fait);
		}else {
			return false;
		}
	}
	
	public boolean supprimerFaitInitiaux(Fait fait) {
		if(faitsInitiaux.contains(fait)) {
			return faitsInitiaux.remove(fait);
		}else {
			return false;
		}
	}
	
	public void genererFaitsInitiaux(String path) throws Exception {
		FileReader input = new FileReader(path);
		BufferedReader bufRead = new BufferedReader(input);
		String myLine = null;

		while ( (myLine = bufRead.readLine()) != null)
		{    
		    String[] listFaits = myLine.split(" & ");
		    for(String strFait: listFaits) {


				Fait f;
				strFait = strFait.replaceAll("[\\n]", "");  
		    	if(strFait.charAt(0)=='~') {
		    		strFait=strFait.substring(1);
		    		f = new Fait(strFait,false);
		    	}
				else if(strFait.contains(">=")){
					String element = (strFait.split(">="))[0];
					int valeur = Integer.parseInt((strFait.split(">="))[1]);
					f = new Fait(element, valeur, ">=");
				}
				else if(strFait.contains("<=")){
					String element = (strFait.split("<="))[0];
					int valeur = Integer.parseInt((strFait.split("<="))[1]);
					f = new Fait(element, valeur, "<=");
				}
				else if(strFait.contains(">")){
					String element = (strFait.split(">"))[0];
					int valeur = Integer.parseInt((strFait.split(">"))[1]);
					f = new Fait(element, valeur, ">");
				}
				else if(strFait.contains("<")){
					String element = (strFait.split("<"))[0];
					int valeur = Integer.parseInt((strFait.split("<"))[1]);
					f = new Fait(element, valeur, "<");
				}
				else if(strFait.contains("==")){
					String element = (strFait.split("=="))[0];
					int valeur = Integer.parseInt((strFait.split("=="))[1]);
					f = new Fait(element, valeur, "==");
				}
				else {
		    		f = new Fait(strFait,true);
		    	}

				/* 
		    	Fait f;
		    	strFait = strFait.replaceAll("[\\n]", "");  
		    	if(strFait.charAt(0)=='~') {
		    		strFait=strFait.substring(1);
		    		f = new Fait(strFait,false);
		    	}
				else if(strFait.contains("==")){
					String element = (strFait.split("=="))[0];
					int valeur = Integer.parseInt((strFait.split("=="))[1]);
					f = new Fait(element, valeur, "==");
				}
				else {
		    		f = new Fait(strFait,true);
		    	}
				*/
			    this.ajouterFaitInitiaux(f);	
			}
		}
	}
	
	
	public void genererFaits(String path) throws Exception {
		FileReader input = new FileReader(path);
		BufferedReader bufRead = new BufferedReader(input);
		String myLine = null;

		while ( (myLine = bufRead.readLine()) != null)
		{    
		    Fait f = new Fait(myLine,true);
		    this.ajouterFaitInitiaux(f);	
		}
	}
	
	public boolean existFaitInitiaux(Fait fait) {
		boolean exist=false;
			for(Fait f : faitsInitiaux) {
				if(f.getElement().equals(fait.getElement())){
				if(fait.getVal()==null && f.getVal()==null){
					if(fait.isEtat()==f.isEtat() && f.getElement().equals(fait.getElement())) {
						exist=true;
					}
				}else if(fait.getVal()!=null && f.getVal()!=null){		
					switch(fait.getSymbole()){
						case ">":
							if(f.getSymbole()==">"){
								if(f.getVal()>=fait.getVal()){
									exist=true;
								}else{
									System.out.println("Sous condition que "+f.getElement()+" > "+fait.getVal());
									exist=true;
								}
							}
							if(f.getSymbole()=="<"){
								if(f.getVal()<=fait.getVal()){
									exist=false;
								}else{
									System.out.println("Sous condition que "+f.getElement()+" > "+fait.getVal());
									exist=true;
								}
							}	
							if(f.getSymbole()=="=="){
								exist=f.getVal()>fait.getVal();
							}
							if(f.getSymbole()=="!="){
								if(f.getVal()<=fait.getVal()){
									exist = true;
								}else{
									System.out.println("Sous condition que "+f.getElement()+" != "+fait.getVal());
									exist=true;
								}
							}
							if(f.getSymbole()==">="){
								if(f.getVal()>fait.getVal()){
									exist=true;
								}else{
									System.out.println("Sous condition que "+f.getElement()+" > "+fait.getVal());
									exist=true;
								}
							}						
							if(f.getSymbole()=="<="){
								if(f.getVal()<fait.getVal()){
									exist=false;
								}else{
									System.out.println("Sous condition que "+f.getElement()+" >= "+fait.getVal());
									exist=true;
								}
							}	
							break;
						case "<":
							if(f.getSymbole()==">"){
								if(f.getVal()>=fait.getVal()){
									exist=false;
								}else{
									System.out.println("Sous condition que "+f.getElement()+" < "+fait.getVal());
									exist=true;
								}
							}
							if(f.getSymbole()=="<"){
								if(f.getVal()<fait.getVal()){
									exist=true;
								}else{
									System.out.println("Sous condition que "+f.getElement()+" < "+fait.getVal());
									exist=true;
								}
							}	
							if(f.getSymbole()=="=="){
								exist=f.getVal()<fait.getVal();
							}
							if(f.getSymbole()=="!="){
								if(f.getVal()>=fait.getVal()){
									exist = true;
								}else{
									System.out.println("Sous condition que "+f.getElement()+" != "+fait.getVal());
									exist=true;
								}
							}
							if(f.getSymbole()==">="){
								if(f.getVal()>=fait.getVal()){
									exist=false;
								}else{
									System.out.println("Sous condition que "+f.getElement()+" < "+fait.getVal());
									exist=true;
								}
							}						
							if(f.getSymbole()=="<="){
								if(f.getVal()<fait.getVal()){
									exist=true;
								}else{
									System.out.println("Sous condition que "+f.getElement()+" < "+fait.getVal());
									exist=true;
								}
							}	
							break;
						case "==":			
							if(f.getSymbole()==">"){
								exist = f.getVal()<fait.getVal();
							}
							if(f.getSymbole()=="<"){
								exist=f.getVal()>fait.getVal();
							}	
							if(f.getSymbole()=="=="){
								exist=f.getVal()==fait.getVal();
							}
							if(f.getSymbole()=="!="){
								exist=f.getVal()!=fait.getVal();
							}		
							if(f.getSymbole()==">="){
								exist=f.getVal()<=fait.getVal();
							}						
							if(f.getSymbole()=="<="){
								exist=f.getVal()>=fait.getVal();
							}	
						break;
						case "<=":
							if(f.getSymbole()==">"){
								if(f.getVal()>fait.getVal()){
									exist=false;
								}else{
									System.out.println("Sous condition que "+f.getElement()+" <= "+fait.getVal());
									exist=true;
								}
							}
							if(f.getSymbole()=="<"){
								if(f.getVal()<=fait.getVal()){
									exist=true;
								}else{
									System.out.println("Sous condition que "+f.getElement()+" <= "+fait.getVal());
									exist=true;
								}
							}	
							if(f.getSymbole()=="=="){
								exist=f.getVal()<=fait.getVal();
							}
							if(f.getSymbole()=="!="){
								if(f.getVal()>fait.getVal()){
									exist = true;
								}else{
									System.out.println("Sous condition que "+f.getElement()+" != "+fait.getVal());
									exist=true;
								}
							}
							if(f.getSymbole()==">="){
								if(f.getVal()>fait.getVal()){
									exist=false;
								}else{
									System.out.println("Sous condition que "+f.getElement()+" <= "+fait.getVal());
									exist=true;
								}
							}						
							if(f.getSymbole()=="<="){
								if(f.getVal()<=fait.getVal()){
									exist=true;
								}else{
									System.out.println("Sous condition que "+f.getElement()+" <= "+fait.getVal());
									exist=true;
								}
							}	
							break;
						case ">=":
							exist=f.getVal()>=fait.getVal();
							if(f.getSymbole()==">"){
								if(f.getVal()>=fait.getVal()){
									exist=true;
								}else{
									System.out.println("Sous condition que "+f.getElement()+" >= "+fait.getVal());
									exist=true;
								}
							}
							if(f.getSymbole()=="<"){
								if(f.getVal()<fait.getVal()){
									exist=false;
								}else{
									System.out.println("Sous condition que "+f.getElement()+" >= "+fait.getVal());
									exist=true;
								}
							}	
							if(f.getSymbole()=="=="){
								exist=f.getVal()>=fait.getVal();
							}
							if(f.getSymbole()=="!="){
								if(f.getVal()<fait.getVal()){
									exist = true;
								}else{
									System.out.println("Sous condition que "+f.getElement()+" != "+fait.getVal());
									exist=true;
								}
							}	
							if(f.getSymbole()==">="){
								if(f.getVal()>=fait.getVal()){
									exist=true;
								}else{
									System.out.println("Sous condition que "+f.getElement()+" >= "+fait.getVal());
									exist=true;
								}
							}						
							if(f.getSymbole()=="<="){
								if(f.getVal()<fait.getVal()){
									exist=false;
								}else{
									System.out.println("Sous condition que "+f.getElement()+" >= "+fait.getVal());
									exist=true;
								}
							}	
							break;
						case "!=":		
							if(f.getSymbole()==">"){
								if(exist=f.getVal()<=fait.getVal()){
									exist = true;
								}else{
									System.out.println("Sous condition que "+f.getElement()+" != "+fait.getVal());
									exist=true;
								}
							}
							if(f.getSymbole()=="<"){
								if(exist=f.getVal()>=fait.getVal()){
									exist = true;
								}else{
									System.out.println("Sous condition que "+f.getElement()+" != "+fait.getVal());
									exist=true;
								}
							}	
							if(f.getSymbole()=="=="){
								exist=f.getVal()!=fait.getVal();
							}
							if(f.getSymbole()=="!="){
								if(exist=f.getVal()==fait.getVal()){
									exist = true;
								}else{
									System.out.println("Sous condition que "+f.getElement()+" != "+fait.getVal());
									exist=true;
								}
							}		
							if(f.getSymbole()==">="){
								if(exist=f.getVal()<fait.getVal()){
									exist = true;
								}else{
									System.out.println("Sous condition que "+f.getElement()+" != "+fait.getVal());
									exist=true;
								}
							}						
							if(f.getSymbole()=="<="){
								if(exist=f.getVal()>fait.getVal()){
									exist = true;
								}else{
									System.out.println("Sous condition que "+f.getElement()+" != "+fait.getVal());
									exist=true;
								}
							}	
							break;
					}
				}
			}
		}
	return exist;
	}
	
	@Override
	public String toString() {
		String str="\n======== Base de Faits Initiaux =======================================\n";
		int i=0;
		for(Fait f : faitsInitiaux) {
			if(i>0) {
				str+= " et ";
			}
			str+=f.toString();
			i++;
		}
		str+="\n==============================================================\n";
		
		/*
		str+="\n======== Base de Faits Demandables =======================================";
		for(Fait f : faits) {
			str+="\n";
			str+=f.toString();
		}
		str+="\n==============================================================";
		*/
		return str;
	}

	public ArrayList<Fait> getFaitsInitiaux() {
		return faitsInitiaux;
	}
	
	
	
}
