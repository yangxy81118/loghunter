package mine.xmz.loghunter.core.collector;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.net.InetAddress;

import mine.xmz.loghunter.core.bean.LoggerApplication;
import mine.xmz.loghunter.core.editor.HunterCoreHandler;
import mine.xmz.loghunter.core.support.IpGetter;

public class ClientRegisterHandler extends ChannelHandlerAdapter {

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		cause.printStackTrace();
		ctx.close();
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {

		LoggerApplication app = new LoggerApplication();
		app.setIp(IpGetter.getLocalIP());
		app.setName("test测试虚拟机");
		app.setPort(18118);

		app.setConfigSource(HunterCoreHandler.getInstance()
				.readLocalConfiguration());

		// TODO 同时还要把log4j2.xml的配置内容发送到admin
		ctx.write(app);

		ctx.flush();
	}

	// 读取服务端的回复
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		ByteBuf buf = (ByteBuf) msg;
		byte[] req = new byte[buf.readableBytes()];
		buf.readBytes(req);
		String body = new String(req, "UTF-8");
		System.out.println("receive from server:" + body);
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		super.channelReadComplete(ctx);
	}

	
	public static void main(String[] args) {
		InetAddress ia = null;
		String localip = null;
		try {
			ia = InetAddress.getLocalHost();
			localip = ia.getHostAddress();
			System.out.println("本机的ip是 ：" + localip);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
