package mine.xmz.loghunter.core.pipe.netty.handler;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import mine.xmz.loghunter.core.bean.ActionConstraints;
import mine.xmz.loghunter.core.bean.LogConfigAction;
import mine.xmz.loghunter.core.pipe.netty.TransferSychronizeLock;

public class NettyPushHandler extends ChannelHandlerAdapter {

	private LogConfigAction actionParam;
	
	public NettyPushHandler(LogConfigAction actionParam) {
		this.actionParam = actionParam;
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		cause.printStackTrace();
		ctx.close();
	}

	//本次Push的行为处理
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		
		//从ThreadLocal中获取本次通信参数s
		ctx.write(actionParam);
		ctx.flush();
	}

	//Push完后对方的的回复处理
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		
		LogConfigAction action = (LogConfigAction)msg;
		if(action.getResponseCode().equals(ActionConstraints.RESPONSE_OK)){
			String lockName = TransferSychronizeLock.LOCK_CONFIG_EDIT+action.getLoggerApplication().getKey();
			TransferSychronizeLock.release(lockName);
			synchronized (TransferSychronizeLock.class) {
				TransferSychronizeLock.class.notifyAll();
			}
		}
		
//		
//		ByteBuf buf = (ByteBuf) msg;
//		byte[] req = new byte[buf.readableBytes()];
//		buf.readBytes(req);
//		String body = new String(req,"UTF-8");
//		System.out.println("receive from server:"+body);
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		super.channelReadComplete(ctx);
	}

	
}
