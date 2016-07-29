package com.github.yangxy81118.loghunter.distribute.pipe.netty;

import com.github.yangxy81118.loghunter.distribute.bean.LogConfigAction;
import com.github.yangxy81118.loghunter.distribute.pipe.MessagePipe;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

/**
 * 
 * @author yangxy8
 *
 */
public class NettyPipe implements MessagePipe {

	private static NettyPipe PIPE = null;

	private Bootstrap bootstrap;

	private EventLoopGroup group;

	public static NettyPipe getPIPE() {

		if (PIPE == null) {
			synchronized (NettyPipe.class) {
				if (PIPE == null) {
					PIPE = new NettyPipe();
				}
			}
		}
		return PIPE;
	}

	private NettyPipe() {

		// 对Netty进行初始化
		// TODO 之后需要采用参数话配置Netty池大小
		initNettyPool();

	}

	private void initNettyPool() {

		group = new NioEventLoopGroup();
		bootstrap = new Bootstrap();
		bootstrap.group(group).channel(NioSocketChannel.class)
				.option(ChannelOption.TCP_NODELAY, true);

	}

	@Override
	public void transfer(String ip, int port, final LogConfigAction actionParam) {

		try {
			bootstrap.handler(new ChannelInitializer<SocketChannel>() {

				@Override
				protected void initChannel(SocketChannel ch) throws Exception {

					ch.pipeline().addLast(
							new ObjectDecoder(1024 * 1024, ClassResolvers
									.cacheDisabled(this.getClass()
											.getClassLoader())));
					ch.pipeline().addLast(new ObjectEncoder());
					ch.pipeline().addLast(new NettyPipeHandler(actionParam));
				}

			});
			
			bootstrap.connect(ip, port).sync();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("transfer结束");

		// f.channel().closeFuture().sync();
	}

}
