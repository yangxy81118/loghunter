package mine.xmz.loghunter.core.collector.handler;

import mine.xmz.loghunter.core.bean.LogLevel;
import mine.xmz.loghunter.core.editor.HunterCoreHandler;


public class LogConfigEditHandler implements CollectServiceHandler {

	@Override
	public String execute(String body) {
		
		//解析body
		
		//处理
		HunterCoreHandler.getInstance().changeLevel("com.yubo.feeder.controller.SvgController", LogLevel.debug);
		
		return null;
	}

}
