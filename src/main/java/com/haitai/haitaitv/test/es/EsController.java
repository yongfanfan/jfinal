package com.haitai.haitaitv.test.es;


import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;



























import javax.annotation.Resource;

import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;  
import org.elasticsearch.cluster.node.DiscoveryNode;  
import org.elasticsearch.common.settings.Settings;  
import org.elasticsearch.common.transport.TransportAddress;  
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.sort.SortOrder;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import com.alibaba.fastjson.JSONObject;
  

public class EsController {
    
     TransportClient transportClient;  
    //索引库名  
    String index = "mylogstash";//"databasessss";  
    //类型名称  
    String type = "doc";//"tabless";  
    
    public static void main(String[] args) throws IOException {
        EsController es = new EsController();
        es.before();
        //es.serach(2,11,0,10);
//        es.serach(2,11,0,1);
//        es.serach(2,11,1,1);
    }
      
    public void before() throws IOException  
    {  
        /** 
         * 1:通过 setting对象来指定集群配置信息 
         */  
        Settings setting = Settings.builder()  
            .put("cluster.name", "my-application")//指定集群名称  
            //.put("client.transport.sniff", true)//启动嗅探功能  
            .build();  
          
        /** 
         * 2：创建客户端 
         * 通过setting来创建，若不指定则默认链接的集群名为elasticsearch 
         * 链接使用tcp协议即9300 
         */  
        transportClient =  new PreBuiltTransportClient(Settings.EMPTY);
        TransportAddress transportAddress = null;
        transportAddress = new TransportAddress(InetAddress.getByName("127.0.0.1"), 9300);
        transportClient.addTransportAddresses(transportAddress);  
        System.out.println("链接成功");
        /** 
         * 3：查看集群信息 
         * 注意我的集群结构是： 
         *   131的elasticsearch.yml中指定为主节点不能存储数据， 
         *   128的elasticsearch.yml中指定不为主节点只能存储数据。 
         * 所有控制台只打印了192.168.79.128,只能获取数据节点 
         *  
         */  
//        ImmutableList<DiscoveryNode> connectedNodes = transportClient.connectedNodes();  
//        for(DiscoveryNode node : connectedNodes)  
//        {  
//            System.out.println("node:"+node.getHostAddress());  
//        }  
        SearchResponse response = transportClient.prepareSearch(index)
                .execute()
                 .actionGet();  
        for(SearchHit s : response.getHits().getHits()){
            System.out.println(JSONObject.toJSONString(s.getSourceAsString())); 
        }
        transportClient.close();
    } 
    
    public void testBulk() throws IOException  
    {  
        //1:生成bulk  
        BulkRequestBuilder bulk = transportClient.prepareBulk();  
          
        //2:新增  
//        IndexRequest add = new IndexRequest(index, type, "3");  
//        add.source(XContentFactory.jsonBuilder()  
//                    .startObject()  
//                    .field("name", "Henrry").field("age", 30)  
//                    .endObject());  
        
        for (int i = 0; i < 2; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("name", "lijun"+i);
            map.put("age", 45+i);
            map.put("id", i+"");
            bulk.add(transportClient.prepareIndex(index, type,i+"").setSource(map));
            // 每100条提交一次
            if (i % 100 == 0) {
                System.out.println("***********100********");
                bulk.execute().actionGet();
            }
        }
        bulk.execute().actionGet();
          
        //3:删除  
       // DeleteRequest del = new DeleteRequest(index, type, "1");  
          
        //4:修改  
//        XContentBuilder source = XContentFactory.jsonBuilder().startObject().field("name", "jack_1").field("age", 19).endObject();  
//        UpdateRequest update = new UpdateRequest(index, type, "3");  
//        update.doc(source);  
//          
//        bulk.add(del);  
        //bulk.add(add); 
        //bulk.add(update);  
        //5:执行批处理  
        BulkResponse bulkResponse = bulk.get();  
        if(bulkResponse.hasFailures())  
        {  
            BulkItemResponse[] items = bulkResponse.getItems();  
            for(BulkItemResponse item : items)  
            {  
                System.out.println("nimei:"+item.getFailureMessage());  
            }  
        }  
        else  
        {  
            System.out.println("全部执行成功！");  
        }  
    }  
    
    /** 
     * 通过prepareIndex增加文档，参数为json字符串 
     */  
    public void json()  
    {  
        System.out.println("*****************nihao ******************");
        String source = "{\"name\":\"lijunshuobuxihuanwoninixingma\",\"age\":18}";  
        IndexResponse indexResponse = transportClient  
                .prepareIndex(index, type, "3")
                .setSource(source)
                .execute()
                .actionGet();  
        System.out.println(JSONObject.toJSONString(indexResponse));  
    }  
      
    /** 
     * 通过prepareIndex增加文档，参数为Map<String,Object> 
     */  
    public void map()  
    {  
        Map<String, Object> source = new HashMap<String, Object>(2);  
        source.put("name", "woshuonixaaaaahuan ");  
        source.put("age", 16);  
        IndexResponse indexResponse = transportClient  
                .prepareIndex(index, type, "4")
                .setSource(source)
                .execute().actionGet();  
        System.out.println(JSONObject.toJSONString(indexResponse));  
    }  
    
    public void get() throws IOException {
      IndexResponse response = transportClient.prepareIndex(index,type)
              .execute()
               .actionGet();  
      System.out.println(JSONObject.toJSONString(response));  
  }
    
    public void serach(int i, int age,int num,int size)  {
        try {
            before();
            //json();
            //map();
            //testBulk();
            if(i == 1){
                BoolQueryBuilder b = QueryBuilders.boolQuery();
                b.must(QueryBuilders.matchQuery("name", "wo"));
                b.must(QueryBuilders.matchQuery("age", age));
                SearchResponse response = transportClient.prepareSearch(index).setTypes(type)
                      .setQuery(b)
                      .setSearchType(SearchType.QUERY_THEN_FETCH)
                      .setExplain(true) //explain为true表示根据数据相关度排序，和关键字匹配最高的排在前面  
//                      .setFrom(num)
//                      .setSize(size)//分页  
                      .addSort("age", SortOrder.DESC)//排序  
                      .execute()
                      .actionGet();  
                for(SearchHit s : response.getHits().getHits()){
                    System.out.println(JSONObject.toJSONString(s.getSourceAsString())); 
                }
            }else{
                BoolQueryBuilder b = QueryBuilders.boolQuery();
                //String ss = "*n*x*o*";
                String ss = "*z*";
                System.out.println(ss);
                b.must(QueryBuilders.wildcardQuery("name", "*a*"));
                b.must(QueryBuilders.wildcardQuery("name", "*w*"));
                QueryStringQueryBuilder sb = new QueryStringQueryBuilder("t");
                SearchResponse response = transportClient.prepareSearch(index).setTypes(type)
                        .setQuery(sb.field("name"))
                        //.setPostFilter(QueryBuilders.rangeQuery("age").gte(age))
                        .setSearchType(SearchType.QUERY_THEN_FETCH)
                        .setExplain(true) //explain为true表示根据数据相关度排序，和关键字匹配最高的排在前面
                        .setFrom(num)
                        .setSize(size)//分页  
                        //.addSort("age", SortOrder.DESC)//排序  
                        .execute()
                        .actionGet();  
                for(SearchHit s : response.getHits().getHits()){
                    JSONObject o = JSONObject.parseObject(s.getSourceAsString());
                    System.out.println("id:"+o);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
         
  }
}
