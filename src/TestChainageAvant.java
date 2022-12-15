public class TestChainageAvant {


    public static void main(String[] args) throws Exception {
		
		BaseFait bf = new BaseFait();
		try {
			bf.genererFaitsInitiaux("faitsInit.txt");
		} catch (Exception e) {
			e.printStackTrace();
		}
		BaseRegle br = new BaseRegle();
		try {
			br.generer("regles.txt");
		} catch (Exception e) {
			e.printStackTrace();
		}

        

	}

}
