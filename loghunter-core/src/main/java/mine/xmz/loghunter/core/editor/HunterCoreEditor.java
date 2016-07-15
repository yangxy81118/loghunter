package mine.xmz.loghunter.core.editor;

import java.io.File;

import mine.xmz.loghunter.core.LogHunterRuntimeException;
import mine.xmz.loghunter.core.LogLevel;
import mine.xmz.loghunter.core.conf.LogConfiguration;

/**
 * 配置文件核心编辑逻辑处理类
 * 
 * @author yangxy8
 *
 */
public class HunterCoreEditor {

	/**
	 * 
	 * @param classType
	 * @param level
	 */
	public void changeLevel(String classType, LogLevel level) {
		File configFile = LogConfiguration.getInstance().getConfigfile();
		ConfigFileEditor editor = getLogConfigEditeExcutor(configFile);
		editor.changeClasslevel(classType, level);
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
	
	private static final HunterCoreEditor editor = new HunterCoreEditor();

	public static final HunterCoreEditor getInstance() {
		return editor;
	}
}
