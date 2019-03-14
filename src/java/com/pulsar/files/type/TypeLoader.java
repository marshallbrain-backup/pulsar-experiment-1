package files.type;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;import java.util.Map.Entry;

import files.GameFile;

import java.util.Random;

public class TypeLoader {
	
	private HashMap<String, Integer> total = new HashMap<String, Integer>(); //the max total of the array in odds for the same name, save cpu time
	private HashMap<String, ArrayList<Integer>> odds = new HashMap<String, ArrayList<Integer>>(); //list of chanceses for each type in the arraylist form type list
	private HashMap<String, ArrayList<Type>> typeList = new HashMap<String, ArrayList<Type>>(); //list of arraylists of types with the key of the given name for them, used quick retreval later
	
	private Random r;
	
	/**
	 * initalizes typeloader
	 */
	public TypeLoader() {
		r = new Random();
	}
	
	/**
	 * makes classes besed on the class give that extends type with all the propertys it needs
	 * 
	 * @param name name of the set, save the end array in a hashmap with name as the key for retreval later
	 * @param fields a list of fields to be given to the classes
	 * @param trim determans where to cut the string for cleaning perpuses
	 * @param type the class that extends the type class that every thing in fields will be givin to. relevent for checking perpeses
	 * @return an array of classes that extends type that contain all the information form fields
	 */
	public ArrayList<Type> addTypes(String name, HashMap<String, String> fields, int trim, Class<?> type){
		
		ArrayList<Type> types = new ArrayList<Type>();
		HashMap<String, GameFile> gf = new HashMap<String, GameFile>();
		
		odds.remove(name);
		total.remove(name);
		
		//makes a gamefile based on the trim-1 position of the key
		for(Entry<String, String> e: fields.entrySet()) { //loops through everything in fields
			
			String f = e.getKey();
			
//			int index = 0;
//			for(int i = 0; i < trim; i++) {
//				index = f.indexOf(".", index)+1;
//			}
			
			int index = f.indexOf(f.split("\\.")[trim]); //index of peried based on the trim pramader
			
			String newF = f.substring(index);
			
			GameFile g = gf.getOrDefault(f.split("\\.")[trim-1], new GameFile(f.split("\\.")[trim-1])); //gets the gamefile with the name of the object or create it with said name
			g.add(newF, e.getValue()); //adds the trimed feild name and corasponding value to the gamefile
			gf.putIfAbsent(f.split("\\.")[trim-1], g); //put it back in the hashmap if it is not in there already
			
		}
		
		Class<?>[] parameters = new Class[] {GameFile.class}; //sets the pramaters
		Constructor<?> constructor;
		try {
			constructor = type.getConstructor(parameters); //creates the constructor and gives it the parameters
			
			for(GameFile f: gf.values()) {
				
				Type typeClass = (Type) constructor.newInstance(new Object[] {f}); //creats a new class based on the type given and passes it a gamefile
				
				ArrayList<Integer> o = odds.getOrDefault(name, new ArrayList<Integer>());
				int newTotal = total.getOrDefault(name, 0);
				
				types.add(typeClass);
				
				String so = typeClass.getField("spawn_odds");
				
				if(so == null)
					so = "1";
				
				double odd = Double.parseDouble(so); //gets the spawn odds for the type
				
				//converts the odds into a precent
				o.add((int) odd*100);
				newTotal += (int) odd*100;
				
				odds.putIfAbsent(name, o);
				total.put(name, newTotal);
			}
			
		//usless junk
		} catch (NoSuchMethodException e2) {
			e2.printStackTrace();
		} catch (SecurityException e2) {
			e2.printStackTrace();
		} catch (InstantiationException e1) {
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			e1.printStackTrace();
		} catch (IllegalArgumentException e1) {
			e1.printStackTrace();
		} catch (InvocationTargetException e1) {
			e1.printStackTrace();
		}
		
		typeList.put(name, types);
		
		return types;
		
	}
	
	/**
	 * gets the type array for a given name
	 * 
	 * @param name the name that the array is stored under
	 * @return the array
	 */
	public ArrayList<Type> getTypes(String name){
		return typeList.get(name);
	}
	
	/**
	 * gets all the types that match the contents of the array
	 * 
	 * @param name the name that the array is stored under
	 * @param type array of the name of the spesific types wanted
	 * @return the types whose name maches those in types
	 */
	public ArrayList<Type> getTypes(String name, String[] type) {
		
		ArrayList<Type> out = new ArrayList<Type>();
		ArrayList<Type> list = typeList.get(name);
		
		// fined all matches and put them in out
		for(String f: type) {
			for(Type t: list) {
				if(t.getName().equals(f))
					out.add(t);
			}
		}
		return out;
	}
	
	/**
	 * gets a ramdom type from the array stored with the spesified name based on spawn chanses
	 * 
	 * @param name the name that the array is stored under
	 * @return the ramdom type
	 */
	public Type getRandomType(String name) {
			
		ArrayList<Type> type = typeList.get(name);
		
		ArrayList<Integer> o = odds.get(name);
		int n = r.nextInt(total.get(name)); //generates a random number whose max is the sum of everything in the odds array
		int t = 0;
		
		//loops through all the odds untill the random number gets below zero
		for(int i = 0; i < o.size(); i++) {
			n -= o.get(i);
			if(n < 0) {
				t = i;
				break;
			}
		}
		
		//get the type corasponding to the randome index
		return type.get(t);
				
	}

}
