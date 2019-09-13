package com.redhat.jdg.producer;

import com.redhat.jdg.pojo.Product;
import com.redhat.jdg.producer.cache.RemoteCacheManagerFactory;
import org.infinispan.client.hotrod.RemoteCache;
import org.infinispan.client.hotrod.RemoteCacheManager;
import org.infinispan.client.hotrod.Search;
import org.infinispan.query.dsl.Query;
import org.infinispan.query.dsl.QueryFactory;
import org.infinispan.query.remote.client.ProtobufMetadataManagerConstants;

import java.util.List;

public class QueryCache {

    RemoteCacheManagerFactory remoteCacheManagerFactory;

    public QueryCache(RemoteCacheManagerFactory rcmf) {
        remoteCacheManagerFactory = rcmf;
    }

    public void getQueryData(){
        try {

            RemoteCacheManagerFactory rmcf = new RemoteCacheManagerFactory();
            RemoteCacheManager remoteCacheManager = rmcf.getRemoteCacheFactory("com.redhat.jdg.pojo.Product");
            RemoteCache<String, Product> remoteCache = remoteCacheManager.getCache("bankcode");

            RemoteCache<String, String> metaCache = remoteCacheManager.getCache(ProtobufMetadataManagerConstants.PROTOBUF_METADATA_CACHE_NAME);
            metaCache.put("Product.proto", rmcf.getGeneratedSchema());

            QueryFactory qf = Search.getQueryFactory(remoteCache);
            Query query = qf.from(Product.class).having("description").like("%Moisture-wicking%").toBuilder().build();//qf.from(Product.class).having("description").like("dobby").toBuilder().build();

            List<Product> lst = query.list();
            System.out.println("query size " + lst.size());
            for (int i = 0; i < lst.size(); i++) {
                System.out.println(((Product)lst.get(i)).toString());
            }
            remoteCacheManager.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
