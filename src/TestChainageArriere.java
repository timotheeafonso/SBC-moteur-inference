public class TestChainageArriere {

    public static void main(String[] args) throws Exception {
		
		BaseRegle br = new BaseRegle();
		try {
			br.generer("regles.txt");
		} catch (Exception e) {
			e.printStackTrace();
		}

        Fait but = new Fait("orage", true);

		MoteurInference.execChainageArriere(new BaseFait(),br,but,true);

	}

}
