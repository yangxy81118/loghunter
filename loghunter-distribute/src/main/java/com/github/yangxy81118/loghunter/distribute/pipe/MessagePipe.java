package com.github.yangxy81118.loghunter.distribute.pipe;

import com.github.yangxy81118.loghunter.distribute.bean.LogConfigAction;

/**
 * 数据通信专用规范接口，用来扩展多种通信方式
 * <p>目前已有netty
 * @author yangxy8
 *
 */
public interface MessagePipe {

	/**
	 * 传输数据
	 * 
	 * @param ip 目标机器IP
	 * @param port 目标机器端口
	 * @param actionParam 事件相关参数
	 */
	public void transfer(String ip,int port,LogConfigAction actionParam);
	
}
