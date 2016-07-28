package mine.xmz.loghunter.distribute.client;

import mine.xmz.loghunter.core.editor.HunterCoreHandler;
import mine.xmz.loghunter.core.support.IpGetter;
import mine.xmz.loghunter.distribute.bean.ActionConstraints;
import mine.xmz.loghunter.distribute.bean.LogConfigAction;
import mine.xmz.loghunter.distribute.bean.LoggerApplication;
import mine.xmz.loghunter.distribute.pipe.netty.NettyPipe;

public class ClientRegisterThread extends Thread {

	private Integer registerCenterPort;
	private String regiserCenterIp;
	private String registerClientName;
	private Integer registerClientPort;

	public ClientRegisterThread(Integer port, String ip, String registerClientName,Integer registerClientPort) {
		this.registerCenterPort = port;
		this.regiserCenterIp = ip;
		this.registerClientName = registerClientName;
		this.registerClientPort = registerClientPort;
	}

	@Override
	public void run() {
		System.out.println("开始准备注册到LogHunter管理控制台");

		// 采用NettyPipe的做法
		NettyPipe nettyPipe = NettyPipe.getPIPE();
		nettyPipe.transfer(regiserCenterIp, registerCenterPort,
				buildRegisterAction());

	}

	private LogConfigAction buildRegisterAction() {

		LoggerApplication app = new LoggerApplication();
		app.setIp(IpGetter.getLocalIP());
		app.setName(registerClientName);
		app.setPort(registerClientPort);

		app.setConfigSource(HunterCoreHandler.getInstance()
				.readLocalConfiguration());

		LogConfigAction action = new LogConfigAction();
		action.setActionCode(ActionConstraints.ACTION_REGISTER);
		action.setLoggerApplication(app);

		return action;
	}

}