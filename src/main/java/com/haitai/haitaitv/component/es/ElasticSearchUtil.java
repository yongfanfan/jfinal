package com.haitai.haitaitv.component.es;

import com.haitai.haitaitv.component.constant.OtherConsts;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.action.bulk.BackoffPolicy;
import org.elasticsearch.action.bulk.BulkProcessor;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.common.unit.ByteSizeUnit;
import org.elasticsearch.common.unit.ByteSizeValue;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

public class ElasticSearchUtil {

    private static final Logger LOGGER = LogManager.getLogger(ElasticSearchUtil.class);
    private static TransportClient client;

    public static void init() {
        if (OtherConsts.ES_DISABLE) {
            LOGGER.info("************由于没有配置ES_IP_ADDRESSES，故没有启动es客户端************");
            return;
        }
        Settings settings = Settings.builder()
                .put("cluster.name", OtherConsts.ES_CLUSTER_NAME)
                .put("client.transport.sniff", true).build();
        client = new PreBuiltTransportClient(settings);

        String[] addresses = OtherConsts.ES_IP_ADDRESSES.split(",");
        for (String address : addresses) {
            String[] hostAndPort = address.split(":");
            TransportAddress transportAddress;
            try {
                transportAddress = new InetSocketTransportAddress(InetAddress.getByName(hostAndPort[0]),
                        Integer.valueOf(hostAndPort[1]));
            } catch (UnknownHostException e) {
                LOGGER.error("es初始化时发生异常，未知主机 {}，跳过该主机", address);
                continue;
            }
            client.addTransportAddresses(transportAddress);
        }
        LOGGER.info("************************es客户端启动成功******************");
    }

    public static void stop() {
        if (client != null) {
            client.close();
        }
    }

    public static Client getClient() {
        return client;
    }

    public static MyListener batchIndex(List<EsIndexBuilder> targets) {
        MyListener listener = new MyListener();
        BulkProcessor processor = BulkProcessor.builder(client, listener)
                .setBulkActions(10000)
                .setBulkSize(new ByteSizeValue(5, ByteSizeUnit.MB))
                .setFlushInterval(TimeValue.timeValueSeconds(5))
                .setConcurrentRequests(1)
                .setBackoffPolicy(
                        BackoffPolicy.exponentialBackoff(TimeValue.timeValueMillis(100), 3))
                .build();
        for (EsIndexBuilder target : targets) {
            IndexRequest request = client.prepareIndex(target.getIndex(), target.getType(), target.getId())
                    .setSource(target.getSource(), XContentType.JSON).request();
            processor.add(request);
        }
        processor.close();
        return listener;
    }

}
