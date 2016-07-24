package mine.xmz.loghunter.admin.register;

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

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * 注册中心监控核心类
 * 
 * @author yangxy8
 *
 */
@Component
@PropertySource("classpath:/app-core.properties")
public class RegisterHolder {

	@PostConstruct
	public void init() {
		new RegisterThread(port).start();
	}

	class RegisterThread extends Thread {
		
		private Integer port;
		
		public RegisterThread(int port){
			this.port = port;
		}

		@Override
		public void run() {
			// 配置服务端的NIO线程组
			EventLoopGroup bossGroup = new NioEventLoopGroup();
			EventLoopGroup workerGroup = new NioEventLoopGroup();
			try {
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
					    ch.pipeline().addLast(new RegisterMsgHandler());
					}
				    });

			    b.bind(port).sync().channel().closeFuture().sync();
			} catch(Exception e){
				e.printStackTrace();
			}finally {
			    // 优雅退出，释放线程池资源
			    bossGroup.shutdownGracefully();
			    workerGroup.shutdownGracefully();
			}
		}

	}

	@Value("${admin-port:18118}")
	private Integer port;

}
