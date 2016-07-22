package mine.xmz.loghunter.core.collector;

import mine.xmz.loghunter.core.bean.LogConfigAction;
import mine.xmz.loghunter.core.editor.HunterCoreHandler;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * 应用端log配置变更处理器
 * 
 * @author yangxy8
 *
 */
public class ClientEditorHandler extends ChannelHandlerAdapter {

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		// TODO Auto-generated method stub
		super.exceptionCaught(ctx, cause);
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		LogConfigAction action = (LogConfigAction) msg;
		editConfiguration(action);
	}

	private void editConfiguration(LogConfigAction action) {
		Integer actionCode = action.getActionCode();
		switch (actionCode) {
		case LogConfigAction.ACTION_LOG_CONFIG_EDIT:
			HunterCoreHandler.getInstance().coverLoggerConfig(
					action.getLoggerApplication().getConfigSource());
			break;
		default:
			break;
		}
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		super.channelReadComplete(ctx);
	}

}
