package mine.xmz.loghunter.core.support;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Collection;

import org.dom4j.Document;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

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
			int end = 0;
			while ((end = reader.read(buf)) > 0) {
				writer.write(buf, 0, end);
			}
		} finally {
			closeQuitely(reader);
			closeQuitely(writer);
		}

	}

	public static String formatXML(String inputXML) throws Exception {
		SAXReader reader = new SAXReader();
		Document document = reader.read(new StringReader(inputXML));
		String requestXML = null;
		XMLWriter writer = null;
		if (document != null) {
			try {
				StringWriter stringWriter = new StringWriter();
				OutputFormat format = new OutputFormat(" ", true);
				writer = new XMLWriter(stringWriter, format);
				writer.write(document);
				writer.flush();
				requestXML = stringWriter.getBuffer().toString();
			} finally {
				if (writer != null) {
					try {
						writer.close();
					} catch (IOException e) {
					}
				}
			}
		}
		return requestXML;
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

	public static String decodeHTML(String logConfigSource) {
		logConfigSource = logConfigSource.replace("&gt;", ">");
		logConfigSource = logConfigSource.replace("&lt;", "<");
		return logConfigSource;
	}
}
