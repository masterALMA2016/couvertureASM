import java.util.Map;
import java.util.TreeMap;


public class Compteur {

	private static Compteur cpt = new Compteur();
	
	private Map<String, Integer> map= new TreeMap<String, Integer>(); 
	
	private Compteur(){}
	
	public static Compteur getInstance(){
		return cpt;
	}
	
	public static void compte(String method){
		System.out.println(method);
	}
}
