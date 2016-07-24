package mine.xmz.loghunter.core.collector;

import org.springframework.beans.factory.InitializingBean;

/**
 * 客户端应用注册处理类<br/>
 * 首先注册到服务端<br/>
 * 然后启动一个本地的Netty服务监听，等候服务端的命令处理（比如修改Log4j2配置等等）
 * 
 * @author yangxy8
 *
 */
public class ClientRegister implements InitializingBean {

	private Integer registerCenterPort;

	private String registerServerIp;

	private Integer registerClientPort;

	public Integer getRegisterCenterPort() {
		return registerCenterPort;
	}

	public void setRegisterCenterPort(Integer registerCenterPort) {
		this.registerCenterPort = registerCenterPort;
	}

	public String getRegisterServerIp() {
		return registerServerIp;
	}

	public void setRegisterServerIp(String registerServerIp) {
		this.registerServerIp = registerServerIp;
	}

	public Integer getRegisterClientPort() {
		return registerClientPort;
	}

	public void setRegisterClientPort(Integer registerClientPort) {
		this.registerClientPort = registerClientPort;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		ClientRegisterThread registerThread = new ClientRegisterThread(
				registerCenterPort, registerServerIp);
		registerThread.start();

		ClientEditorThread editorThread = new ClientEditorThread(
				registerClientPort);
		editorThread.start();
	}

}
