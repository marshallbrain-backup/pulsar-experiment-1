package settings;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

public class Settings {
	
	private HashMap<String, String> settings = new HashMap<String, String>();
	
	public Settings() {

		FileReader in;
		
		try {
			
			File file = new File("settings.txt");
			
			if (!file.exists()) {
				file.createNewFile();
			}
			
			in = new FileReader(file);
			BufferedReader br = new BufferedReader(in);
			//gets the setting's file
			
			String l;
			//loops through all lines in the file
			while ((l = br.readLine()) != null) {
				settings.put(l.split(":")[0], l.split(":")[1]); //split the current line by ":"
			}
			
			in.close();
			
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
	}
	
	/**
	 * Gets the spesified setting value
	 *
	 * @param  setting	the setting to get
	 * @param  defalt	the defult value for the setting
	 * @return			the value for the setting 
	 */
	public int get(String setting, int defalt) {
		
		String s = settings.get(setting);
		
		if(s == null) {
			add(setting, Integer.toString(defalt)); //if the setting is not present, add it to the file
			return defalt;
		}
		
		return Integer.parseInt(s);
		
	}
	
	/**
	 * Gets the spesified setting value
	 *
	 * @param  setting	the setting to get
	 * @param  defalt	the defult value for the setting
	 * @return			the value for the setting 
	 */
	public boolean get(String setting, boolean defalt) {
		
		String s = settings.get(setting);
		
		if(s == null) {
			add(setting, Boolean.toString(defalt)); //if the setting is not present, add it to the file
			return defalt;
		}
		
		return Boolean.parseBoolean(s);
		
	}
	
	/**
	 * Adds the setting and value to the settings file
	 *
	 * @param  setting	the name of the setting
	 * @param  value	the value
	 */
	private void add(String setting, String value) {

		BufferedWriter bw = null;
		
		try {
			
			File file = new File("settings.txt");
			
			if (!file.exists()) {
				file.createNewFile();
			}
			//reads in settings file
			
			FileWriter fw = new FileWriter(file, true); //set to append mode
			bw = new BufferedWriter(fw);
			PrintWriter out = new PrintWriter(bw);
			
			out.println(setting + ":" + value); //adds the setting
			
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			 try {
				bw.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		
		settings.put(setting, value); //adds new setting to the hashmap
		
	}

}
