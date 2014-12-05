import test.oui.Test1;
import test.oui.Test2;


public class TestCouverture {
	
    public static void main(String[] args){
		
		Test1 t1 = new Test1();
		t1 = new Test1();
		
		t1.getJ();
		t1.getJ();
		t1.getJ();
		t1.getJ();
		t1.getJ();
		
		t1.getV();
		t1.getV();
		t1.getV();
		
		t1.setK(true);
		t1.setK(true);
		t1.setK(true);
		
		/*Test2 t2 = new Test2();
		
		t2.setH('l');*/
		
		Compteur.afficher();
	}
}
