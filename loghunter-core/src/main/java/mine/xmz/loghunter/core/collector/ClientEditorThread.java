package mine.xmz.loghunter.core.collector;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * 应用自身开启监听server，用来接受loghunter-admin的命令
 * 
 * @author yangxy8
 *
 */
public class ClientEditorThread extends Thread {

	private Integer localClientPort;

	public ClientEditorThread(int port) {
		this.localClientPort = port;
	}

	@Override
	public void run() {
		// 配置服务端的NIO线程组
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			System.out.println("等待LogHunter管理控制台发出控制命令");
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup, workerGroup)
					.channel(NioServerSocketChannel.class)
					.option(ChannelOption.SO_BACKLOG, 100)
					.handler(new LoggingHandler(LogLevel.INFO))
					.childHandler(new ChannelInitializer<SocketChannel>() {
						@Override
						public void initChannel(SocketChannel ch) {
							ch.pipeline()
									.addLast(
											new ObjectDecoder(
													1024 * 1024,
													ClassResolvers
															.weakCachingConcurrentResolver(this
																	.getClass()
																	.getClassLoader())));
							ch.pipeline().addLast(new ObjectEncoder());
							ch.pipeline().addLast(new ClientEditorHandler());
						}
					});

			// 绑定端口，同步等待成功
			ChannelFuture f = b.bind(localClientPort).sync();

			// 等待服务端监听端口关闭
			f.channel().closeFuture().sync();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}

}