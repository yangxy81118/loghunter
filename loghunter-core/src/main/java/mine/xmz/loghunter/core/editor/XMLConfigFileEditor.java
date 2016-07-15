package mine.xmz.loghunter.core.editor;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import mine.xmz.loghunter.core.LogConfigSchema;
import mine.xmz.loghunter.core.LogHunterRuntimeException;
import mine.xmz.loghunter.core.LogLevel;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

/**
 * xml配置文件修改工具<br/>
 * 考虑到log4j往往配置不会太复杂，暂时考虑用非事件机制读取
 * 
 * @author yangxy8
 *
 */
public class XMLConfigFileEditor implements ConfigFileEditor {

	private File configFile;
	private Document configDocument;

	public XMLConfigFileEditor(File configFile) {
		this.configFile = configFile;
		readDoc();
	}

	private void readDoc() {
		SAXReader reader = new SAXReader();
		try {
			configDocument = reader.read(configFile);
			// TODO 这里先要做一次格式校验
		} catch (DocumentException e) {
			throw new LogHunterRuntimeException("Read Configure File Error!", e);
		}
	}

	@Override
	public void changeClasslevel(String classType, LogLevel level) {
		Element loggerParentNode = getLoggerParentNode();
		List loggerNodes = loggerParentNode.elements();
		
		boolean findClassType = false;
		for (int i = 0; i < loggerNodes.size(); i++) {
			Element childNode = (Element) loggerNodes.get(i);
			Attribute loggerName = childNode
					.attribute(LogConfigSchema.XML_LOGGER_NAME);
			if (loggerName == null) {
				continue;
			}
			if (classType.equals(loggerName.getValue())) {
				findClassType = true;
				Attribute levelAttribute = childNode.attribute(LogConfigSchema.XML_LOGGER_LEVEL);
				levelAttribute.setValue(level.toString());
				break;
			}
		}

		if (!findClassType) {
			createLoggerNode(loggerParentNode, classType, level);
		}
		writeToFile();

	}

	private Element getLoggerParentNode() {

		Element rootNode = configDocument.getRootElement();
		Element loggerParentNode = rootNode
				.element(LogConfigSchema.XML_LOGGERS);

		if (loggerParentNode == null) {
			throw new LogHunterRuntimeException(
					"Config file does not contain node[Loggers]");
		}
		
		return loggerParentNode;
	}

	private void writeToFile() {
		XMLWriter writer = null;
		try {
			FileWriter file = new FileWriter(configFile);
			writer = new XMLWriter(file);
			writer.write(configDocument);
		} catch (IOException e) {
			throw new LogHunterRuntimeException("Fail to write configuration",
					e);
		} finally {
			try {
				writer.close();
			} catch (IOException e) {
			}
		}
	}

	private void createLoggerNode(Element loggerParentNode, String classType,
			LogLevel level) {
		Element loggerElmt = loggerParentNode
				.addElement(LogConfigSchema.XML_LOGGER);
		loggerElmt.addAttribute(LogConfigSchema.XML_LOGGER_NAME, classType);
		loggerElmt.addAttribute(LogConfigSchema.XML_LOGGER_LEVEL,
				level.toString());
		loggerElmt.addAttribute(LogConfigSchema.XML_LOGGER_ADDITIVITY,
				DEFAULT_ADDITIVITY);

		// 下一级的AppenderRef，暂时用Console
		Element appenderRef = loggerElmt
				.addElement(LogConfigSchema.XML_LOGGER_APPENDERREF);
		appenderRef.addAttribute(LogConfigSchema.XML_LOGGER_APPENDERREF_REF,
				"Console");
	}

	private static final String DEFAULT_ADDITIVITY = "false";

	@Override
	public void remove(String classType) {
		Element loggerParentNode = getLoggerParentNode();
		List loggerNodes = loggerParentNode.elements();
		
		boolean findClassType = false;
		for (int i = 0; i < loggerNodes.size(); i++) {
			Element childNode = (Element) loggerNodes.get(i);
			Attribute loggerName = childNode
					.attribute(LogConfigSchema.XML_LOGGER_NAME);
			if (loggerName == null) {
				continue;
			}
			if (classType.equals(loggerName.getValue())) {
				loggerParentNode.remove(childNode);
				break;
			}
		}
		writeToFile();
	}

	@Override
	public void changeRootLevel(LogLevel level) {
		// TODO Auto-generated method stub

	}
}
