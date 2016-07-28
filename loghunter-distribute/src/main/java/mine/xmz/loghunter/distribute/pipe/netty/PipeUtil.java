package mine.xmz.loghunter.distribute.pipe.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.io.UnsupportedEncodingException;

public class PipeUtil {

	public static ByteBuf parseStrToBuf(String content) {
		return Unpooled.copiedBuffer(content.getBytes());
	}

	public static String parseBufToStr(ByteBuf buf)
			throws UnsupportedEncodingException {
		byte[] req = new byte[buf.readableBytes()];
		buf.readBytes(req);
		String body = new String(req, "UTF-8");
		return body;
	}
}
