package test;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.util.ArrayList;
import java.util.List;

import mine.xmz.loghunter.core.bean.LogConfig;
import mine.xmz.loghunter.core.bean.LogLevel;
import mine.xmz.loghunter.core.bean.LoggerApplication;
import mine.xmz.loghunter.core.bean.LoggerConfig;

public class ClientHandler extends ChannelHandlerAdapter {

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		ctx.close();
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		byte[] req = "{\"action\":1000,\"ip\":\"10.201.7.245\",\"port\":18118,\"name\":\"测试机器\"}".getBytes();
		
		LoggerApplication app = new LoggerApplication();
		app.setIp("10.201.7.245");
		app.setName("test测试虚拟机");
		app.setPort(18118);

		LogConfig config = new LogConfig();
//		config.setRootLevel(LogLevel.info);

		List<LoggerConfig> loggers = new ArrayList<>();
		LoggerConfig logger = new LoggerConfig();
		logger.setClassType("com.yxyx.hahahah");
//		logger.setLevel(LogLevel.debug);
		loggers.add(logger);
		config.setLoggers(loggers);
		
		app.setLogConfig(config);
		
		//TODO 同时还要把log4j2.xml的配置内容发送到admin
		ctx.write(app);
		
		ctx.flush();
	}

	//读取服务端的回复
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		ByteBuf buf = (ByteBuf) msg;
		byte[] req = new byte[buf.readableBytes()];
		buf.readBytes(req);
		String body = new String(req,"UTF-8");
		System.out.println("receive from server:"+body);
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		super.channelReadComplete(ctx);
	}

	
}
