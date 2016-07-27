package mine.xmz.loghunter.core.bean;

import java.io.Serializable;

/**
 * 接受监控与控制的应用单元
 * 
 * @author yangxy8
 *
 */
public class LoggerApplication implements Serializable {

	private static final long serialVersionUID = -3893231282997820537L;

	private Integer id;

	private String key;

	private String ip;

	private Integer port;

	private String name;

	private LogConfig logConfig;

	/**
	 * 配置文件源文件内容
	 */
	private String configSource;

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public LogConfig getLogConfig() {
		return logConfig;
	}

	public void setLogConfig(LogConfig logConfig) {
		this.logConfig = logConfig;
	}

	public String getConfigSource() {
		return configSource;
	}

	public void setConfigSource(String configSource) {
		this.configSource = configSource;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	@Override
	public String toString() {
		return "LoggerApplication [id=" + id + ", ip=" + ip + ", port=" + port
				+ ", name=" + name + ", logConfig=" + logConfig
				+ ", configSource=" + configSource + "]";
	}

}
