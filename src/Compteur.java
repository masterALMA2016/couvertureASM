import java.util.Map;
import java.util.TreeMap;


public class Compteur{

	private static Map<String, Integer> map;
	
	private Compteur(){
		map= new TreeMap<String, Integer>();
	}
	
	private static Compteur compteur;
	
	public static Compteur getInstance(String s){
		if(compteur==null){
			compteur = new Compteur();
		}
		return compteur;
	}
	
	public static void compte(String method){
		getInstance(method);		
		if(map.get(method)==null){
			map.put(method, 1);
		}
		else{
			int cpt = map.get(method);
			cpt++;
			map.put(method, cpt);
		}
	}
	
	public static void afficher(){
		for(Map.Entry<String, Integer> entry : map.entrySet()){
			System.out.println(entry.getKey() + "  "+entry.getValue());
		}
	}
	
	/*
	private static Compteur cpt = new Compteur();
	
	public Map<String, Integer> map= new TreeMap<String, Integer>(); 
	private Compteur(){}
	
	public static Compteur getInstance(String s){
		return cpt;
	}
	
	public static void compte(String method){
		getInstance(method);
		System.out.println(method);
	}

	public Map<String, Integer> getMap() {
		return map;
	}

	public void setMap(Map<String, Integer> map) {
		this.map = map;
	}*/
}
