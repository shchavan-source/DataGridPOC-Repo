package com.redhat.jdg.producer;

import java.rmi.Remote;
import java.util.List;
import java.util.Map;

import org.hibernate.search.query.dsl.QueryBuilder;
import org.infinispan.Cache;
import org.infinispan.client.hotrod.RemoteCache;
import org.infinispan.client.hotrod.RemoteCacheManager;
import org.infinispan.client.hotrod.Search;
import org.infinispan.commons.api.BasicCache;

import org.infinispan.query.CacheQuery;
import org.infinispan.query.SearchManager;
import org.infinispan.query.dsl.Query;
import org.infinispan.query.dsl.QueryFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.redhat.jdg.producer.cache.RemoteCacheManagerFactory;
import com.redhat.jdg.producer.db.DbConnectionFactory;

import com.redhat.jdg.pojo.Product;
import com.redhat.jdg.producer.repository.ProductRepository;

/**
 * Loads {@link Product} data from a <code>PostgreSQL</code> database and stores the data in
 * <code>ProtoBuf</code> format in <code>JBoss Data Grid</code>.
 * 
 * @author <a href="mailto:duncan.doyle@redhat.com">Duncan Doyle</a>
 * @author <a href="mailto:cojan.van.ballegooijen@redhat.com">Cojan van Ballegooijen</a>
 */
public class CacheDataProducer {

	private static final Logger LOGGER = LoggerFactory.getLogger(CacheDataProducer.class);

	private static final String ISPN_CACHE_NAME;

	private static final DbConnectionFactory dbConnectionFactory = new DbConnectionFactory();

	private static RemoteCacheManager rcm = null;

	private static final RemoteCacheManagerFactory rcmFactory = new RemoteCacheManagerFactory();

	private static final ProductRepository productrepo = new ProductRepository();
	
	// Read system properties.
	static {
		ISPN_CACHE_NAME=System.getProperty("cache.name");
	}
	

	public static void main(String[] args) {
		LOGGER.info("Loading data from database and storing in JBoss Data Grid.");

		Map<String, Product> products = productrepo.getProduct();
		LOGGER.info("Found " + products.size() + " rows.");
		LOGGER.info("Pushing data to Infinspan.");
		
//		putInIspn("com.redhat.jdg.pojo.Product",ISPN_CACHE_NAME, products);
		
		LOGGER.info("Data stored in JBoss Data Grid.");

		LOGGER.info("Test Data in Cache");

		getIspn("com.redhat.jdg.pojo.Product", ISPN_CACHE_NAME);

		LOGGER.info("Query Data in Cache");

//		performQuery("com.redhat.jdg.pojo.Product", ISPN_CACHE_NAME);

		QueryCache queryCache = new QueryCache(rcmFactory);
		queryCache.getQueryData();
	}

	/**
	 * Puts the data in the given {@link Map} in <code>JBoss Data Grid</code>
	 * 
	 * 
	 * @param cacheName
	 * 
	 * @param keyValues
	 */
	private static <T> void putInIspn(String clazzName,String cacheName, Map<String, T> keyValues) {

		RemoteCacheManager remoteCacheManager = rcmFactory.getRemoteCacheFactory(clazzName);
		BasicCache<String, T> cache = remoteCacheManager.getCache(cacheName);

		keyValues.forEach((key, value) -> {
			cache.put(key, value);
		});
		remoteCacheManager.stop();
	}

	private static <T> void getIspn(String clazzName, String cacheName){
		RemoteCacheManager rcm = rcmFactory.getRemoteCacheFactory(clazzName);
		BasicCache<String, T> cache = rcm.getCache(cacheName);

		System.out.println(cache.get("266728"));
		rcm.stop();
	}

	private static <T> void performQuery(String clazzName, String cacheName){
		System.out.println("Started to Perform Query");
		RemoteCacheManager rcm = rcmFactory.getRemoteCacheFactory(clazzName);
		RemoteCache<String, T> cache = rcm.getCache(cacheName);

		QueryFactory queryFactory = Search.getQueryFactory(cache);
		Query query = queryFactory.from(Product.class)
						.having("description").eq("dobby").toBuilder().build();

		List<Product> list = query.list();

		for (int i = 0; i < list.size(); i++) {
			System.out.println(((Product)list.get(i)).toString());
		}

		rcm.stop();

	}

}
