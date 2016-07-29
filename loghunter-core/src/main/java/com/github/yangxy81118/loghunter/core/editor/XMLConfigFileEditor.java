package com.github.yangxy81118.loghunter.core.editor;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import com.github.yangxy81118.loghunter.core.LogConfigSchema;
import com.github.yangxy81118.loghunter.core.bean.LogConfig;
import com.github.yangxy81118.loghunter.core.bean.LogLevel;
import com.github.yangxy81118.loghunter.core.exception.ExceptionConstraints;
import com.github.yangxy81118.loghunter.core.exception.LogHunterRuntimeException;

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
	private Element rootNode = null;
	private List<Element> loggersNodes = null;
	private List<Element> appendersNodes = null;

	public XMLConfigFileEditor(File configFile) {
		this.configFile = configFile;
		readDoc();
	}

	private void readDoc() {
		SAXReader reader = new SAXReader();
		try {
			configDocument = reader.read(configFile);
			rootNode = configDocument.getRootElement();

			// 将来再做
			// Element loggersNode =
			// rootNode.element(LogConfigSchema.XML_LOGGERS);
			// loggersNodes = loggersNode.elements();
			// if(!Cats.collectionNotEmpty(loggersNodes)){
			// throw new
			// LogHunterRuntimeException("Logger element is required!");
			// }
			//
			// Element loggersNode =
			// rootNode.element(LogConfigSchema.XML_LOGGERS);
			// loggersNodes = loggersNode.elements();
			// if(!Cats.collectionNotEmpty(loggersNodes)){
			// throw new
			// LogHunterRuntimeException("Appender element is required!");
			// }

		} catch (DocumentException e) {
			throw new LogHunterRuntimeException("Read Configure File Error!",
					e, ExceptionConstraints.SYSTEM_ERROR);
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
				Attribute levelAttribute = childNode
						.attribute(LogConfigSchema.XML_LOGGER_LEVEL);
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
					"Config file does not contain node[Loggers]",
					ExceptionConstraints.SYSTEM_ERROR);
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
					e, ExceptionConstraints.SYSTEM_ERROR);
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

	@Override
	public LogConfig readConfig() {

		LogConfig config = new LogConfig();
		Element root = configDocument.getRootElement();

		// loggers

		return null;
	}
}
