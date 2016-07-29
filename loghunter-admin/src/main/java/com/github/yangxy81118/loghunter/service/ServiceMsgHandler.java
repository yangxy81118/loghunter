package com.github.yangxy81118.loghunter.service;

import com.github.yangxy81118.loghunter.core.exception.ExceptionConstraints;
import com.github.yangxy81118.loghunter.core.exception.LogHunterRuntimeException;
import com.github.yangxy81118.loghunter.distribute.bean.ActionConstraints;
import com.github.yangxy81118.loghunter.distribute.bean.LogConfigAction;
import com.github.yangxy81118.loghunter.service.delegator.RegisterService;
import com.github.yangxy81118.loghunter.service.delegator.ServerEndPointService;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

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
					.setResponseCode(ExceptionConstraints.SYSTEM_ERROR);
			resultAction.setResponseMsg(e.getMessage());
		}

		return resultAction;
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.flush();
	}

}
