package mine.xmz.loghunter.core.collector;

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

public class ClientRegisterThread extends Thread {

	private Integer registerCenterPort;
	private String regiserCenterIp;

	public ClientRegisterThread(int port, String ip) {
		this.registerCenterPort = port;
		this.regiserCenterIp = ip;
	}

	@Override
	public void run() {
		System.out.println("开始准备注册到LogHunter管理控制台");
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
							ch.pipeline().addLast(new ClientRegisterHandler());
						}

					});

			ChannelFuture f = b.connect(regiserCenterIp, registerCenterPort);
			f.sync();

			f.channel().closeFuture().sync();
			System.out.println("完成准备注册到LogHunter管理控制台");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			group.shutdownGracefully();
		}
	}

}