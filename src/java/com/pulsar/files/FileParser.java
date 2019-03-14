package files;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class FileParser {
	
	/**
	 * Loads all files in common folder into a gamefile
	 */
	public static GameFile readCommon() {
		
		GameFile gameFile = new GameFile("");
		
		File common = new File("common"); //gets the file for common

		try {
			enterFolder(common, 0, gameFile);
//			for(File c: common.listFiles()) { //loops through all files in common
//				for(File f: c.listFiles()) { //loops through all files in that folder
//					if(f == null) { //if c is not a folder use c as the file
//						BufferedReader br = new BufferedReader(new FileReader(c));
//						read(br, c.getName().split("\\.")[0], "", 1, g);
//					}
//					else { //otherwise use f
//						BufferedReader br = new BufferedReader(new FileReader(f));
//						read(br, c.getName() + "." + f.getName().split("\\.")[0], "", 2, g);
//					}
//				}
//			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		gameFile.comit();
		
		return gameFile;
		
//		BufferedReader br = null;
//		
//		try {
//			br = new BufferedReader(new FileReader(f));
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		}
//		
//		GameFile file = new GameFile();
//		
//		creatingVariable(null, br, file, vars);
//		
//		return file;
		
	}
	
	/**
	 * loads all files under a folder tree into a gamefile
	 * 
	 * @param folder		the current folder than is being sorted through
	 * @param gameFile		the gameFile to add all of this junk to
	 * @throws IOException 	relevent for reading a line from a bufferedReader
	 */
	private static void enterFolder(File folder, int i, GameFile gameFile) throws IOException {
		for(File f: folder.listFiles()) { //loops through all files in a folder folder
			if(f.isFile()) { //if the file is not a folder just use the file
				BufferedReader br = new BufferedReader(new FileReader(f));
				String path = f.getPath();
				String field = path.substring(path.indexOf("\\")+1, path.lastIndexOf("\\")).replace("\\", ".")+"?";
				read(br, field, "", i, gameFile);
			} else { //otherwise repeat with the current file
				enterFolder(f, i+1, gameFile);
			}
		}
	}
	
	/**
	 * Reads a file and loads it into a gamefile
	 * 
	 * @param br			bufferedReader for the given file
	 * @param field			file path
	 * @param line			current line, set to empty string to get the next line
	 * @param pos			position of the index, relevent for adding numbers next to duplicits
	 * @param gameFile		the gameFile to add all of this junk to
	 * @throws IOException 	relevent for reading a line from a bufferedReader
	 */
	private static void read(BufferedReader br, String field, String line, int pos, GameFile gameFile) throws IOException {
		
		GameFile f = gameFile;
		
		if(field.contains(":")) {
			field = field.replace(":", "");
			f = new GameFile(field);
			gameFile.add(f);
		}
		
		//loops through all line in file
		for(String l = (line.isEmpty())? br.readLine(): line; l != null; l = br.readLine()) {
			
			l = l.replaceAll("\\s+",""); //get rid of white spaces
			
			if(l.contains("{") && l.contains("}")) //if both currly brackets exist on the same line, replace end curlly bracket to prevent exiting the function premacherly
				l = l.replace("}", "\\}");
			if(l.equals("}")) //if the current line contanise only an end currly breaket then exit the function
				return;
			
			readLine(br, field, l, pos, f); //interpets the current line
			
			if(l.contains("}") && !l.contains("\\}")) //if the line containes an end currly breaket in it, exit the function
				return;
			
		}
		
		return;
		
	}
	
	/**
	 * Reads a line and loads it into a gamefile
	 * 
	 * @param br			bufferedReader for the given file
	 * @param field			file path
	 * @param line			current line, set to empty string to get the next line
	 * @param pos			position of the index, relevent for adding numbers next to duplicits
	 * @param gameFile		the gameFile to add all of this junk to
	 * @throws IOException	relevent for reading a line from a bufferedReader
	 */
	private static void readLine(BufferedReader br, String field, String line, int pos, GameFile gameFile) throws IOException {
		
		String[] s = line.split("="); //splits the current line at equal sines
		
		String n = s[0];
		
		if(n.isEmpty()) return;
		if(n.startsWith("#")) return; //if the line is a comment, skip the line
		
		String f = field + "." + n; //add the field name to the end of the field chain
		
		//if the line does not contain an equales sine than just add the line to the gamefile
		if(s.length == 1) {
			gameFile.add(field, n, pos);
			return;
		}
		
		//if the rest of the current line starts with a currly bracket than read that line
		if(s[1].startsWith("{")) {
			String newLine = line.substring(line.indexOf("{")+1); //gets the line after the currly bracket
			if(f.contains("?"))
				f = f.replace("?", ":");
			read(br, f, newLine.replace("\\}", "}"), pos+1, gameFile); //replases the exscaped end currly bracket with a nonecaped one
		} else { //else add the line to the gamefile
			//if the line contains commas, run through whats between the commas as sepret entrys
			if(line.contains(",")) {
				String[] split = line.split(",");
				for(String l: split) {
					readLine(br, field, l, pos, gameFile);
				}
			} else { //else add the line with out currly brackets
				gameFile.add(f, s[1].replace("}", ""), pos);
			}
		}
		
		return;
		
	}
	
/*	
	public static void creatingVariable(String[] l, BufferedReader br, GameFile f, HashMap<String, String> vars) {
		
		while(true) {
			
			boolean opp = false;
		
			String t = null;
			String v = "";
		
			if(l != null) for(int i = 0; i < l.length; i++) {
				
				if(l[i].equals("#")){
					break;
				} else if(l[i].equals("=") && t != null) {
					opp = true;
				} else if(l[i].equals("{")) {
					
					String[] n = null;
					
					if(i < l.length-1) {
						
						n = new String[l.length-1-i];
						
						int j = i+1;
						
						for(int k = 0; !l[j].equals("}"); j++, k++) {
							n[k] = l[j];
						}
						
						n[n.length-1] = "}";
						
						i = j;
						
					}
					
					GameFile a = new GameFile();
					
					creatingVariable(n, br, a, new HashMap<String, String>(vars));
					
					f.add(t, a);
					t = null;
					
					opp = false;
					
					continue;
					
				} else if(opp) {
					
					if(l[i].startsWith("@") && vars.containsKey(l[i])) {
						v += vars.get(l[i]);
					} else {
						v += l[i];
					}
					
					opp = false;
					
				}
				
				if(!opp){
					
					if(t != null) {
						if(v.isEmpty()) {
							f.add(t);
							t = l[i];
						} else {
							
							if(t.startsWith("@")) {
								if(vars.containsKey(t)) {
									vars.replace(t, v);
								} else {
									vars.put(t, v);
								}
							} else {
								f.add(t, v);
							}
							
							t = null;
							
						}
					} else {
						t = l[i];
					}
					
					v = "";
					
				}
				
				if(l[i].equals("}")) {
					return;
				}
				
				
			}
			
			try {
				
				String s = br.readLine();
				
				if(s == null) return;
				
				l = s.trim().replace("\t", " ").replaceAll(" +", " ").split(" ");
			
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		
	}
*/
}
