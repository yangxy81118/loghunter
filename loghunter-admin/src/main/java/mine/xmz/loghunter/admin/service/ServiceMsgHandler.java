package mine.xmz.loghunter.admin.service;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import mine.xmz.loghunter.admin.service.LoggerAppContainer;
import mine.xmz.loghunter.admin.service.delegator.RegisterService;
import mine.xmz.loghunter.admin.service.delegator.ServerEndPointService;
import mine.xmz.loghunter.admin.support.ByteBufHelper;
import mine.xmz.loghunter.core.bean.ActionConstraints;
import mine.xmz.loghunter.core.bean.LogConfigAction;
import mine.xmz.loghunter.core.bean.LoggerApplication;
import mine.xmz.loghunter.core.exception.IllegalClientAppNameException;
import mine.xmz.loghunter.core.exception.LogHunterRuntimeException;

import com.alibaba.fastjson.JSONObject;

/**
 * 
 * @author yangxy8
 *
 */
public class ServiceMsgHandler extends ChannelHandlerAdapter {

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		// TODO Auto-generated method stub
		super.exceptionCaught(ctx, cause);
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {

		LogConfigAction app = (LogConfigAction) msg;
		System.out.println("收到请求:" + app);

		LogConfigAction response = execute(app);
		ctx.writeAndFlush(response);
	}

	private LogConfigAction execute(LogConfigAction clientAction) {

		Integer code = clientAction.getActionCode();
		ServerEndPointService epServcie = null;

		switch (code) {
		case ActionConstraints.ACTION_REGISTER:
			epServcie = new RegisterService();
			break;
		default:
			break;
		}

		LogConfigAction resultAction = clientAction;
		try {
			resultAction = epServcie.execute(clientAction);
		} catch (LogHunterRuntimeException e) {
			resultAction.setResponseCode(e.getErrorCode());
			resultAction.setResponseMsg(e.getMessage());
		} catch (Exception e) {
			resultAction
					.setResponseCode(ActionConstraints.RESPONSE_SYSTEM_ERROR);
			resultAction.setResponseMsg(e.getMessage());
		}

		return resultAction;
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.flush();
	}

}
