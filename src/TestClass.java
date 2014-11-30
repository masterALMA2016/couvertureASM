import java.io.IOException;


public class TestClass {

	private int nb =0;
	private String str ="connard";
	public int getNb() {
		return nb;
	}
	public void setNb(int nb) {
		this.nb = nb;
	}
	public String getStr() {
		return str;
	}
	public void setStr(String str) {
		this.str = str;
	}

	public static void main(String[] args){
//		Compteur.getInstance("ij");
		Compteur.compte("jj");
		TestClass cl = new TestClass();
		cl.setStr("jj");
		cl.setNb(5);		cl.setStr("jj");
		cl.setNb(5);		cl.setStr("jj");
		cl.setNb(5);
		cl.getStr();
		Compteur.afficher();
	}
		
}
