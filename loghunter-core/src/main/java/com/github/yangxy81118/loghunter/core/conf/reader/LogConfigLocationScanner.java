package com.github.yangxy81118.loghunter.core.conf.reader;

import java.io.File;
import java.io.FilenameFilter;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.github.yangxy81118.loghunter.core.bean.LogConfig;
import com.github.yangxy81118.loghunter.core.bean.LoggerConfig;
import com.github.yangxy81118.loghunter.core.conf.LogVersion;
import com.github.yangxy81118.loghunter.core.editor.ConfigFileEditor;
import com.github.yangxy81118.loghunter.core.editor.XMLLog4j2ConfigEditor;
import com.github.yangxy81118.loghunter.core.support.Cats;
import com.github.yangxy81118.loghunter.core.support.ExceptionThrow;

/**
 * log4j 配置文件读取工具
 * 
 * @author yangxy8
 *
 */
public class LogConfigLocationScanner {

	private String DEFAULT_BASE_PATH = this.getClass().getClassLoader()
			.getResource("").getPath();

	private String logConfigBasePath;

//	private Map<LogVersion, LogConfig> logConfigMap = new HashMap<>();
	private LogConfig logConfig = null;

	public LogConfigLocationScanner() {
		logConfigBasePath = DEFAULT_BASE_PATH;
	}

	public LogConfigLocationScanner(String logConfigBasePath) {
		this.logConfigBasePath = logConfigBasePath;
	}
	
	private String logConfigFilePath;

	public void scan() {
		// 检查在指定路径下面是否有Log配置
		File logBaseDir = new File(logConfigBasePath);
		ExceptionThrow.argumentValueIllegal(!logBaseDir.isDirectory(),
				logConfigBasePath + " is not a directory");

		File[] logConfigOptions = logBaseDir.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return name.contains("log4j");
			}
		});

		if (!Cats.arrayNotEmpty(logConfigOptions)) {
			log.info("no log4j config in this application");
			return;
		}

		boolean findConfig = false;
		ConfigFileEditor configReader = null;
		for (File file : logConfigOptions) {
			String fileName = file.getName();
			
			if (fileName.startsWith(LOG4J2_NAME_PREFIX)) {
				if (findConfig)
					break;

				if (fileName.endsWith(".xml")) {
					configReader = new XMLLog4j2ConfigEditor(file);
					logConfigFilePath = file.getAbsolutePath();
					findConfig = true;
				} else {
					log.warn(
							"Now loghunter can just parse log4j2.xml,please move configs from file {} to log4j2.xml",
							fileName);
					findConfig = true;
				}
			}

			if (fileName.startsWith(LOG4J_NAME_PREFIX)) {
				if (findConfig)
					break;
				else if (fileName.endsWith(".properties")) {

					findConfig = true;
				} else {
					log.warn(
							"Now loghunter can just parse log4j.xml or log4j.perperties",
							fileName);
					findConfig = true;
				}
			}
		}
		
		
		logConfig = configReader.readConfig();

	}
	
	public String getLogConfigFilePath() {
		return logConfigFilePath;
	}

	private final String FILE_SEPARATOR = System.getProperty("file.separator");

	private static final Logger log = LogManager
			.getLogger(LogConfigLocationScanner.class.getName());

	private static final String LOG4J2_NAME_PREFIX = "log4j2.";

	private static final String LOG4J_NAME_PREFIX = "log4j.";

}
