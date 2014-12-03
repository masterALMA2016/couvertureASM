import java.io.IOException;

import test.oui.Test1;


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
		
		Compteur.afficher();
	}
}
