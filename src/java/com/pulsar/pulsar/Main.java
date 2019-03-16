package pulsar;
import java.awt.Dimension;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystemNotFoundException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Iterator;
import java.util.stream.Stream;

import javax.swing.JFrame;

import settings.Settings;

public class Main {
	
	public static int SCALE;
	public static int WIDTH, HEIGHT;
	
	//main start method for the game
	public static void main(String[] args) {
		
		//test commit
		
		Settings settings = new Settings(); //creates setting class
		
		try {
			cloneResorses();
		} catch (URISyntaxException | IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		JFrame frame; //makes jframe for the game
		
		frame = new JFrame("Stellaris");
		
		//gets settings
		HEIGHT = settings.get("WindowedHeight", 1080);
		int winX = settings.get("WindowedPosX", 0);
		int winY = settings.get("WindowedPosY", 0);
		WIDTH = settings.get("WindowedWidth", 1920);
		SCALE = settings.get("Scale", 1);
		
		Pulsar game = new Pulsar(frame, settings); //makes game
		
		//sets up game and frame
		game.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		game.setLocation(winX, winY);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.add(game);
		frame.setVisible(true);
		frame.pack();
		
		game.start(); //starts game
		
	}
	
	private static void cloneResorses() throws URISyntaxException, IOException, ClassNotFoundException {
		
		URI commonOld = Main.class.getResource("/resorces/common/").toURI();
		URI assetsOld = Main.class.getResource("/resorces/gfx/").toURI();
		URI uiOld = Main.class.getResource("/resorces/ui/").toURI();
		
		File commonNew = new File("common");
		File assetsNew = new File("gfx");
		File uiNew = new File("interface");
		
		cloneFolder(commonOld, commonNew);
		cloneFolder(assetsOld, assetsNew);
		cloneFolder(uiOld, uiNew);
		
	}
	
	private static void cloneFolder(URI o, File n) throws IOException {
		
		Path old;
		
		if(!n.exists())
			n.mkdirs();
		
//		System.out.println(o);
//		System.out.println(o.toString().substring(o.toString().lastIndexOf("/")));
		
		if (o.getScheme().equals("jar")) {
			
			FileSystem fileSystem;
			
			try {
				fileSystem = FileSystems.getFileSystem(o);
			} catch (FileSystemNotFoundException e) {
				fileSystem = FileSystems.newFileSystem(o, Collections.<String, Object>emptyMap());
			}
			
			old = fileSystem.getPath(o.toString().substring(o.toString().lastIndexOf("!")+1));
			
		} else {
		    old = Paths.get(o);
		}
		
		@SuppressWarnings("resource")
		Stream<Path> walk = Files.walk(old, 1);
		walk = walk.skip(1);
		for (Iterator<Path> it = walk.iterator(); it.hasNext();) {
			
			Path p = it.next();
			
			if(!p.toString().contains(".")) {
				
				File n2 = new File(n.getPath() + "\\" + p.getFileName());
				n2.mkdirs();
				
//				try {
//					Thread.sleep(1000);
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
				
				cloneFolder(p.toUri(), n2);
				
			} else {
				
				File n2 = new File(n.getPath() + "\\" + p.getFileName());
				
				if(!n2.exists())
					try {
//						System.out.println(n2.toPath());
						Files.copy(p, n2.toPath());
					} catch (IOException e) {
						e.printStackTrace();
					}
				
			}
			
		}
		
		walk.close();
		walk.close();
        
//		for(File f: o.listFiles()) {
//			if(f.isDirectory()) {
//				File p = new File(n.getPath() + "\\" + f.getName());
//				if(!p.exists())
//					p.mkdir();
//				cloneFolder(f, p);
//			} else {
//				File p = new File(n.getPath() + "\\" + f.getName());
//				if(!p.exists())
//					try {
//						Files.copy(f.toPath(), p.toPath());
//					} catch (IOException e) {
//						e.printStackTrace();
//					}
//			}
//		}
		
	}

}
