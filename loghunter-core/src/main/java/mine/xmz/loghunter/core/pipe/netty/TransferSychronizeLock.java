package mine.xmz.loghunter.core.pipe.netty;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 由于通过Netty走的是异步通信，所以要进行同步加锁
 * 
 * @author yangxy8
 *
 */
public class TransferSychronizeLock {

	public static String LOCK_CONFIG_EDIT = "LOG_CONFIG_EDIT_";
	
	private static ConcurrentMap<String,Boolean> busyLock = new ConcurrentHashMap<>();
	
	public static void lock(String lockName){
		busyLock.put(lockName, true);
	}
	
	public static Boolean getLock(String lockName){
		return busyLock.get(lockName);
	}
	
	public static void release(String lockName){
		busyLock.put(lockName, false);
	}
}
