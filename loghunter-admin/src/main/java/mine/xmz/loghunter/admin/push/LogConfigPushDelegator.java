package mine.xmz.loghunter.admin.push;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import mine.xmz.loghunter.admin.register.LoggerAppContainer;
import mine.xmz.loghunter.core.bean.LogConfigAction;
import mine.xmz.loghunter.core.bean.LoggerApplication;
import mine.xmz.loghunter.core.collector.ClientRegisterHandler;

/**
 * 对应用端日志配置进行修改的处理器
 * 
 * @author yangxy8
 *
 */
public class LogConfigPushDelegator {

	/**
	 * 推送最新的日志配置内容
	 * 
	 * @param appId
	 * @param logConfigSource
	 */
	public static void pushLogConfigSource(Integer appId, String logConfigSource) {

		LogConfigAction action = new LogConfigAction();
		action.setActionCode(LogConfigAction.ACTION_LOG_CONFIG_EDIT);
		LoggerApplication app = new LoggerApplication();
		app.setConfigSource(logConfigSource);
		action.setLoggerApplication(app);

		pushAction(appId, action);

	}

	private static void pushAction(Integer appId, final LogConfigAction action) {

		LoggerApplication application = LoggerAppContainer
				.getApplication(appId);
		String clientAppIp = application.getIp();
		Integer clientAppPort = application.getPort();

		EventLoopGroup group = new NioEventLoopGroup();
		try {
			Bootstrap b = new Bootstrap();
			b.group(group).channel(NioSocketChannel.class)
					.option(ChannelOption.TCP_NODELAY, true)
					.handler(new ChannelInitializer<SocketChannel>() {
						@Override
						protected void initChannel(SocketChannel ch)
								throws Exception {
							ch.pipeline().addLast(
									new ObjectDecoder(1024 * 1024,
											ClassResolvers.cacheDisabled(this
													.getClass()
													.getClassLoader())));
							ch.pipeline().addLast(new ObjectEncoder());
							ch.pipeline().addLast(new ClientPushHandler(action));
						}

					});

			ChannelFuture f = b.connect(clientAppIp, clientAppPort);
			f.sync();

			f.channel().closeFuture().sync();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			group.shutdownGracefully();
		}
	}

}
