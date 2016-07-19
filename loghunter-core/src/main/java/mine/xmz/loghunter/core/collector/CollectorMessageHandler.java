package mine.xmz.loghunter.core.collector;

import mine.xmz.loghunter.core.collector.handler.CollectServiceHandler;
import mine.xmz.loghunter.core.collector.handler.LogConfigEditHandler;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class CollectorMessageHandler extends ChannelHandlerAdapter {

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		System.out.println("exception:"+cause.getMessage());
		ctx.close();
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		ByteBuf buf = (ByteBuf) msg;
		byte[] req = new byte[buf.readableBytes()];
		buf.readBytes(req);
		String body = new String(req,"UTF-8");
		System.out.println("Recieve Message From Hunter Center:"+body);
		String response = execute(body);
		ctx.write(Unpooled.copiedBuffer(response.getBytes()));
	}

	private String execute(String body) {
		
		//通过body获取对应处理的业务
		CollectServiceHandler hanlder = new LogConfigEditHandler();
		return hanlder.execute(body);
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.flush();
	}


	
}
