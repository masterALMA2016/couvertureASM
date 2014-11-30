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
		//hhhhhhh
		Compteur.getInstance();
		Compteur.compte("jj");
		//hhhhhhhh
		System.out.println("kkk");
		TestClass tc = new TestClass();
		tc.getStr();
		tc.getStr();
		tc.setNb(2);
		tc.setStr("kkk");
		
	}
		
}
