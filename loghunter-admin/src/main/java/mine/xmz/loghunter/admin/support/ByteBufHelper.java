package mine.xmz.loghunter.admin.support;

import java.io.UnsupportedEncodingException;

import io.netty.buffer.ByteBuf;

public class ByteBufHelper {

	public static String getString(Object msg) throws UnsupportedEncodingException {

		ByteBuf buf = (ByteBuf) msg;
		byte[] req = new byte[buf.readableBytes()];
		buf.readBytes(req);
		String body = new String(req,"UTF-8");
		return body;
	}

}
