package com.github.yangxy81118.loghunter.distribute.pipe.netty;

import com.github.yangxy81118.loghunter.distribute.bean.ActionConstraints;
import com.github.yangxy81118.loghunter.distribute.bean.LogConfigAction;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class NettyPipeHandler extends ChannelHandlerAdapter {

	private LogConfigAction actionParam;
	
	public NettyPipeHandler(LogConfigAction actionParam) {
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
