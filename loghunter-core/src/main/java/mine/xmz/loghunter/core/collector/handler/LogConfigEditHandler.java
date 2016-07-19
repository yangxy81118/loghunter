package mine.xmz.loghunter.core.collector.handler;

import mine.xmz.loghunter.core.LogLevel;
import mine.xmz.loghunter.core.editor.HunterCoreEditor;


public class LogConfigEditHandler implements CollectServiceHandler {

	@Override
	public String execute(String body) {
		
		//解析body
		
		//处理
		HunterCoreEditor.getInstance().changeLevel("com.yubo.feeder.controller.SvgController", LogLevel.debug);
		
		return null;
	}

}
