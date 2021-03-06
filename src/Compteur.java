
import java.util.Map;
import java.util.TreeMap;


public class Compteur{

	private static Map<String, Integer> map;
	
	private Compteur(){
		map= new TreeMap<String, Integer>();
	}
	
	private static Compteur compteur = null;
	
	public static synchronized Compteur getInstance(){
		if(compteur==null){
			compteur = new Compteur();
		}
		return compteur;
	}
	
	public static void compte(String method){
		getInstance();		
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

	public static int getCompte(String str){
		getInstance();
		if (map.containsKey(str))
			return map.get(str);
		return 0;
	}
}
