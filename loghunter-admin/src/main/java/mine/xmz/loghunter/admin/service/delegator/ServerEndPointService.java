package mine.xmz.loghunter.admin.service.delegator;

import mine.xmz.loghunter.core.bean.LogConfigAction;

/**
 * 服务端业务处理标准接口
 * <p>专门用来处理各个客户端应用主动push来的功能请求
 * @author yangxy8
 *
 */
public interface ServerEndPointService {

	/**
	 * 
	 * @param clientAction 客户端应用发来的请求
	 * @return 处理结果
	 */
	public LogConfigAction execute(LogConfigAction clientAction);

}
