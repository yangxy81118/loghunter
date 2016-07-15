package mine.xmz.loghunter.controller.springmvc;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mine.xmz.loghunter.core.LogLevel;
import mine.xmz.loghunter.core.editor.HunterCoreEditor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * 用户个人信息相关请求入口
 * 
 * @author young.jason
 *
 */
@Controller
@RequestMapping("")
public class LogHunterController {

	@RequestMapping(value = "/loggerhunter/chlvl")
	public void changeLog(@RequestParam String classType,
			@RequestParam LogLevel level, HttpServletResponse response) {
		try {
			HunterCoreEditor.getInstance().changeLevel(classType, level);
			writeResponse(response, ControllerConstraint.CODE_SUCCESS,
					ControllerConstraint.MSG_SUCCESS);
		} catch (Exception e) {
			writeResponse(response, ControllerConstraint.CODE_FAIL,
					ControllerConstraint.MSG_FAIL);
		}

	}


	@RequestMapping(value = "/loggerhunter/remove")
	public void changeLog(@RequestParam String classType,
			HttpServletResponse response) {
		
		try {
			HunterCoreEditor.getInstance().remove(classType);
			writeResponse(response, ControllerConstraint.CODE_SUCCESS,
					ControllerConstraint.MSG_SUCCESS);
		} catch (Exception e) {
			writeResponse(response, ControllerConstraint.CODE_FAIL,
					ControllerConstraint.MSG_FAIL);
		}
	}


	private void writeResponse(HttpServletResponse response, String code,
			String msg) {
		PrintWriter writer;
		try {
			writer = response.getWriter();
			writer.print(buildJSONResponse(code,msg));
		} catch (IOException e) {
		}
	}
	
	private String buildJSONResponse(String code, String msg) {
		StringBuffer jsonBuffer = new StringBuffer();
		jsonBuffer.append("{");
		jsonBuffer.append("\"code\":").append(code).append(",");
		jsonBuffer.append("\"msg\":").append("\"").append(msg).append("\"}");	
		return jsonBuffer.toString();
	}
}
