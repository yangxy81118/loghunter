package mine.xmz.loghunter.admin.register;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import mine.xmz.loghunter.admin.register.LoggerAppContainer;
import mine.xmz.loghunter.admin.support.ByteBufHelper;
import mine.xmz.loghunter.core.bean.LoggerApplication;

import com.alibaba.fastjson.JSONObject;

/**
 * 
 * @author yangxy8
 *
 */
public class RegisterMsgHandler extends ChannelHandlerAdapter {

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		// TODO Auto-generated method stub
		super.exceptionCaught(ctx, cause);
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		
		LoggerApplication app = (LoggerApplication)msg;
		System.out.println("收到请求:"+app);
		
		String response = execute(app);
		ctx.writeAndFlush(Unpooled.copiedBuffer(response.getBytes()));
	}

	private String execute(LoggerApplication app) {
		LoggerAppContainer.addApplication(app);
		System.out.println("成功加入新注册应用,"+app.getName()+","+app.getIp()+":"+app.getPort());
		
		
		return "200";
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.flush();
	}

}
