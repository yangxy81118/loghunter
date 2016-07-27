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

	private Integer registerServerPort;

	private String registerServerIp;

	private Integer registerClientPort;

	private String registerClientName;

	public Integer getRegisterServerPort() {
		return registerServerPort;
	}

	public void setRegisterServerPort(Integer registerServerPort) {
		this.registerServerPort = registerServerPort;
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

	public String getRegisterClientName() {
		return registerClientName;
	}

	public void setRegisterClientName(String registerClientName) {
		this.registerClientName = registerClientName;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		ClientRegisterThread registerThread = new ClientRegisterThread(
				registerServerPort, registerServerIp, registerClientName,
				registerClientPort);
		registerThread.start();

		ClientEditorThread editorThread = new ClientEditorThread(
				registerClientPort);
		editorThread.start();
	}

}
