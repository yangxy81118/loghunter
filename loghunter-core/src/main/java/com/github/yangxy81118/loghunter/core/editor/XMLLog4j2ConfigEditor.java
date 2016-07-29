package com.github.yangxy81118.loghunter.core.editor;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
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
import com.github.yangxy81118.loghunter.core.bean.LoggerConfig;
import com.github.yangxy81118.loghunter.core.exception.ExceptionConstraints;
import com.github.yangxy81118.loghunter.core.exception.LogHunterRuntimeException;
import com.github.yangxy81118.loghunter.core.support.Cats;

/**
 * xml配置文件修改工具<br/>
 * 考虑到log4j往往配置不会太复杂，暂时考虑用非事件机制读取
 * 
 * @author yangxy8
 *
 */
public class XMLLog4j2ConfigEditor implements ConfigFileEditor {

	private File configFile;
	private Document configDocument;
	private Element rootNode = null;
	private List<Element> loggerNodes;
	private List<Element> appendersNodes;
	private Element loggersParentNode;

	public XMLLog4j2ConfigEditor(File configFile) {
		this.configFile = configFile;
		readConfig();
	}

	@Override
	public void changeClasslevel(String classType, LogLevel level) {
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
			createLoggerNode(classType, level);
		}
		writeToFile();

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

	//TODO 优化appender
	private void createLoggerNode(String classType,
			LogLevel level) {
		Element loggerElmt = loggersParentNode
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
		boolean findClassType = false;
		for (int i = 0; i < loggerNodes.size(); i++) {
			Element childNode = (Element) loggerNodes.get(i);
			Attribute loggerName = childNode
					.attribute(LogConfigSchema.XML_LOGGER_NAME);
			if (loggerName == null) {
				continue;
			}
			if (classType.equals(loggerName.getValue())) {
				loggersParentNode.remove(childNode);
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

		SAXReader reader = new SAXReader();
		try {
			configDocument = reader.read(configFile);
			rootNode = configDocument.getRootElement();
		} catch (DocumentException e) {
			throw new LogHunterRuntimeException("Read Configure File Error!",
					e, ExceptionConstraints.SYSTEM_ERROR);
		}
		
		LogConfig config = new LogConfig();
		Element root = configDocument.getRootElement();

		// loggers
		loggersParentNode = rootNode.element(LogConfigSchema.XML_LOGGERS);
		loggerNodes = loggersParentNode.elements();
		if (!Cats.collectionNotEmpty(loggerNodes)) {
			throw new LogHunterRuntimeException("Logger element is required!",
					ExceptionConstraints.LOG_CONFIG_ILLEGAL);
		}

		List<LoggerConfig> loggersList = new ArrayList<>();
		for (Element element : loggerNodes) {
			if (element.getName().equals(LogConfigSchema.XML_LOGGER)) {
				LoggerConfig logger = new LoggerConfig();
				logger.setClassType(element
						.attributeValue(LogConfigSchema.XML_LOGGER_NAME));
				logger.setLevel(LogLevel.valueOf(element
						.attributeValue(LogConfigSchema.XML_LOGGER_LEVEL)));
				loggersList.add(logger);
			} else if (element.getName()
					.equals(LogConfigSchema.XML_LOGGER_ROOT)) {
				config.setRootLevel(LogLevel.valueOf(element
						.attributeValue(LogConfigSchema.XML_LOGGER_LEVEL)));
			}
		}

		config.setLoggers(loggersList);

		return config;
	}
}
