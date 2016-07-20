package mine.xmz.loghunter.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;

/**
 * 
 * @author yangxy8
 *
 */
public class Cats {

	public static boolean collectionNotEmpty(Collection collection){
		return collection!=null && collection.size() > 0;
	}

	public static String readFile(File configFile) throws IOException {
		
		BufferedReader reader = new BufferedReader(new FileReader(configFile));
		StringBuffer contentBuf = new StringBuffer();
		String line = null;
		while((line=reader.readLine())!=null){
			contentBuf.append(line);
		}
		
		return contentBuf.toString();
	}
}
