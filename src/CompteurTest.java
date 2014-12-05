import static org.junit.Assert.*;

import org.junit.Test;


public class CompteurTest {

	@Test
	public void testGetInstance() {
		assert(Compteur.getInstance()!=null);
	}

	@Test
	public void testCompteNewKey() {
		assert(Compteur.getCompte("test")==0);
	}
	
	
	@Test
	public void testNewAjout() {
		Compteur.compte("test");
		assert(Compteur.getCompte("test")==1);
	}
	
	@Test
	public void testMAJKeyVal() {
		Compteur.compte("test");
		Compteur.compte("test");
		assert(Compteur.getCompte("test")==2);
	}

}
