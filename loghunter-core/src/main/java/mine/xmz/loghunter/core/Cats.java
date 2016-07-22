package mine.xmz.loghunter.core;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.Writer;
import java.util.Collection;

/**
 * 
 * @author yangxy8
 *
 */
public class Cats {

	public static boolean collectionNotEmpty(Collection collection) {
		return collection != null && collection.size() > 0;
	}

	public static String readFile(File configFile) throws IOException {

		BufferedReader reader = null;
		StringBuffer contentBuf = null;
		try {
			reader = new BufferedReader(new FileReader(configFile));
			contentBuf = new StringBuffer();
			String line = null;
			while ((line = reader.readLine()) != null) {
				contentBuf.append(line);
			}
		} finally {
			closeQuitely(reader);
		}

		return contentBuf.toString();
	}

	public static void writeFile(String configSource, File configFile)
			throws IOException {

		BufferedWriter writer = null;
		StringReader reader = null;
		try {
			writer = new BufferedWriter(new FileWriter(configFile));
			reader = new StringReader(configSource);

			char[] buf = new char[1024 * 1024];
			while (reader.read(buf) > 0) {
				writer.write(buf);
			}
		} finally {
			closeQuitely(reader);
			closeQuitely(writer);
		}

	}

	private static void closeQuitely(Reader reader) {
		try {
			reader.close();
		} catch (IOException e) {
		}
	}

	private static void closeQuitely(Writer reader) {
		try {
			reader.close();
		} catch (IOException e) {
		}
	}
}
