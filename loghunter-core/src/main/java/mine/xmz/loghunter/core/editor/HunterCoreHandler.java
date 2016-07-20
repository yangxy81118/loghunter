package mine.xmz.loghunter.core.editor;

import java.io.File;
import java.io.IOException;

import mine.xmz.loghunter.core.Cats;
import mine.xmz.loghunter.core.LogHunterRuntimeException;
import mine.xmz.loghunter.core.bean.LogConfig;
import mine.xmz.loghunter.core.bean.LogLevel;
import mine.xmz.loghunter.core.conf.LogConfiguration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;

/**
 * 配置文件核心编辑逻辑处理类
 * 
 * @author yangxy8
 *
 */
public class HunterCoreHandler {

	
	/**
	 * 读取本地log配置文件,暂时采用直接读取整个文件的做法
	 * @return
	 */
	public String readLocalConfiguration(){
		
		File configFile = LogConfiguration.getInstance().getConfigfile();
//		ConfigFileEditor editor = getLogConfigEditeExcutor(configFile);
//		LogConfig configBean = editor.readConfig();
		String configSource = null;
		try {
			configSource = Cats.readFile(configFile);
		} catch (IOException e) {
			throw new LogHunterRuntimeException("Read configFile error!", e);
		}
		return configSource;
	}
	
	/**
	 * 
	 * @param classType
	 * @param level
	 */
	public void changeLevel(String classType, LogLevel level) {
		File configFile = LogConfiguration.getInstance().getConfigfile();
		ConfigFileEditor editor = getLogConfigEditeExcutor(configFile);
		editor.changeClasslevel(classType, level);
		reloadLoggerContext();
	}
	
	
	private void reloadLoggerContext() {
		LoggerContext ctx = (LoggerContext)LogManager.getContext(false);
		ctx.reconfigure();
	}


	/**
	 * 
	 * @param classType
	 * @param level
	 */
	public void remove(String classType) {
		File configFile = LogConfiguration.getInstance().getConfigfile();
		ConfigFileEditor editor = getLogConfigEditeExcutor(configFile);
		editor.remove(classType);
	}

	private ConfigFileEditor getLogConfigEditeExcutor(File configFile) {
		String fileName = configFile.getName();
		ConfigFileEditor executor = null;
		if (fileName.endsWith(".xml")) {
			executor = new XMLConfigFileEditor(configFile);
		}else{
			throw new LogHunterRuntimeException("non-xml file does not been supported");
		}
		return executor;
	}
	
	private static final HunterCoreHandler editor = new HunterCoreHandler();

	public static final HunterCoreHandler getInstance() {
		return editor;
	}
}
