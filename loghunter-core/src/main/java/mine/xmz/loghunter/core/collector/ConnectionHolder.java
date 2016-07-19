package mine.xmz.loghunter.core.collector;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class ConnectionHolder {

	private Integer port;
	
	/**
	 * 
	 * @param port
	 * @param address
	 * @throws Exception
	 */
	public void start() throws Exception {

		EventLoopGroup bossGroup = null;
		EventLoopGroup workGroup = null;

		try {
			bossGroup = new NioEventLoopGroup();
			workGroup = new NioEventLoopGroup();

			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup, workGroup).channel(NioServerSocketChannel.class)
					.option(ChannelOption.SO_BACKLOG, 1024)
					.childHandler(new ChannelInitializer<SocketChannel>() {
						@Override
						protected void initChannel(SocketChannel channel)
								throws Exception {
							channel.pipeline().addLast(new CollectorMessageHandler());
						}

					});

			ChannelFuture f = b.bind(port);
			f.sync();

			f.channel().closeFuture().sync();

		} finally {
			bossGroup.shutdownGracefully();
			workGroup.shutdownGracefully();
		}
	}
}
