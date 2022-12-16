public class TestPaquet {


    public static void main(String[] args) throws Exception {
        
        BaseFait bf = new BaseFait();
        try {
            bf.genererFaitsInitiaux("faitsInit2.txt");
        } catch (Exception e) {
            e.printStackTrace();
        }
        BaseRegle br = new BaseRegle();
        try {
            br.generer("regles4.txt");
        } catch (Exception e) {
            e.printStackTrace();
        }

        MoteurInference.execChainageAvant(bf, br, true);

    }

    
}
