package hotrod;

import org.infinispan.client.hotrod.CacheTopologyInfo;
import org.infinispan.client.hotrod.RemoteCache;
import org.infinispan.client.hotrod.RemoteCacheManager;
import org.infinispan.client.hotrod.configuration.ConfigurationBuilder;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.Logger;

public class testHotRod {

	private static final String PROPERTIES_FILE = "jdg.properties";
	private static final String JDG_HOST = "jdg.host";
	private static final String HOTROD_PORT = "jdg.hotrod.port";
	private static RemoteCacheManager rmc = null;
	private static RemoteCache<String, Object> remoteCache = null;
	private final static Logger logger = Logger.getLogger(testHotRod.class.getName());

	public static void main(String[] args) {

		startMyCache(); 		// Starting the cache manager and initialize connection
		putCache(); 			// To put entries in cache teams
		getSizeOfCache(); 		// to get size of cache
		getValueFromCache(); 	// To retrieve value from cache using key
		containsKey(); 			// Boolean method to check if key value is present in cache or not
	}

	public static void startMyCache() {

		if (rmc == null) {
			if (rmc == null) {
				ConfigurationBuilder cb = new ConfigurationBuilder();
				cb.tcpNoDelay(true).connectionPool().addServer().host(jdgProperty(JDG_HOST))
						.port(Integer.parseInt(jdgProperty(HOTROD_PORT)));
				rmc = new RemoteCacheManager(cb.build());
				CacheTopologyInfo cti = rmc.getCache().getCacheTopologyInfo();
				cti.getTopologyId();
				remoteCache = rmc.getCache("bankcode");
				System.out.println("Connection from client to server is established");
				
				//remoteCache.getAll(keys)
			}
		}
	}

	private static void getSizeOfCache() {
		System.out.println("Size of cache is" + " " + remoteCache.size());
	}

	private static void putCache() {
		for (int i = 0; i < 10; i++) {
			remoteCache.put("key" + i, "value2");
		}
	}
	
	private static void getValueFromCache() {
		if (null != remoteCache.get("key9")) {
			String str = (String) remoteCache.get("key9");
			logger.info(str);
			System.out.println("Value for key key9 is" + " " + remoteCache.get("key9"));
		}
	}
	
	private static void containsKey() {
		if (remoteCache.containsKey("arnav")) {
			System.out.println("Value for arnav is" + " " + remoteCache.get("arnav"));
		} else {
			System.out.println("Value for arnav key is not present :(");
		}
	}

	public static String jdgProperty(String name) {
		Properties props = new Properties();
		try {
			props.load(testHotRod.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE));
		} catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}
		return props.getProperty(name);
	}

}
