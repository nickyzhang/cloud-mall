package com.cloud.service.impl;

import com.cloud.common.core.utils.JSONUtils;
import com.cloud.module.ESDocument;
import com.cloud.service.IndexService;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.DocWriteRequest;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.support.WriteRequest;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.VersionType;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class IndexServiceImpl<T> implements IndexService<T> {
    Logger logger = LoggerFactory.getLogger(IndexServiceImpl.class);

    private static final String DOC_TYPE = "doc";

    @Autowired
    RestHighLevelClient client;

//    @Override
//    public void addDocument(ESDocument<T> document) {
//        IndexRequest indexRequest = new IndexRequest(document.getIndex(),DOC_TYPE,document.getDocId());
//        T data = document.getDocument();
//        // 方式一： JSON形式提供数据
//        String content = JSONUtils.objectToJson(data);
//        indexRequest.source(content, XContentType.JSON);
//
//        // 方拾二：XContentBuilder形式
////        try {
////            XContentBuilder builder = XContentFactory.jsonBuilder();
////            builder.startObject();
////            {
////                builder.field("user", "kimchy");
////                builder.field("postDate", new Date());
////                builder.field("message", "trying out Elasticsearch");
////            }
////            builder.endObject();
////        } catch (IOException e) {
////            e.printStackTrace();
////        }
//
//        // 方式三：Map提供，自动转成JSON
////        Map<String, Object> jsonMap = new HashMap<>();
////        jsonMap.put("user", "kimchy");
////        jsonMap.put("postDate", new Date());
////        jsonMap.put("message", "trying out Elasticsearch");
//
//        // 方式四： 通过source提供
////        IndexRequest indexRequest4 = new IndexRequest("posts", "doc", "1")
////                .source("user", "kimchy",
////                        "postDate", new Date(),
////                        "message", "trying out Elasticsearch");
//
//        // 可选参数
//        // 设置路由
//        // indexRequest.routing("routing");
//
//        //设置超时：等待主分片变得可用的时间
//        // TimeValue方式
//        indexRequest.timeout(TimeValue.timeValueSeconds(1));
//        // 字符串方式
//        indexRequest.timeout("1s");
//
//        // 刷新策略
//        indexRequest.setRefreshPolicy(WriteRequest.RefreshPolicy.WAIT_UNTIL);
//        indexRequest.setRefreshPolicy("wait_for");
//
//        // 设置版本和版本类型
//        indexRequest.version(2);
//        indexRequest.versionType(VersionType.EXTERNAL);
//
//        // 操作类型
//        indexRequest.opType(DocWriteRequest.OpType.CREATE);
//        indexRequest.opType("create");
//
//        // 在索引文档之前要执行的pipeline
//        indexRequest.setPipeline("pipeline");
//
//        IndexResponse indexResponse = null;
//        try {
//            indexResponse = restHighLevelClient.index(indexRequest);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        ActionListener<IndexResponse> listener = new ActionListener<IndexResponse>() {
//            @Override
//            public void onResponse(IndexResponse response) {
//                // 执行成功时候调用
//            }
//
//            @Override
//            public void onFailure(Exception e) {
//                //在失败的情况下调用。 引发的异常以参数方式提供
//            }
//        };
//
//        //异步执行索引请求需要将IndexRequest实例和ActionListener实例传递给异步方法：
//        restHighLevelClient.indexAsync(indexRequest, listener);
//
//    }

    @Override
    public String addDocument(ESDocument<T> document) {
        return updateDocument(document);
    }

    @Override
    public String updateDocument(ESDocument<T> document) {
        IndexRequest request = new IndexRequest(document.getIndex(),DOC_TYPE,document.getDocId());
        T data = document.getDocument();
        String content = JSONUtils.objectToJson(data);
        request.source(content, XContentType.JSON);
        ActionListener<IndexResponse> listener = new ActionListener<IndexResponse>() {
            @Override
            public void onResponse(IndexResponse response) {

            }

            @Override
            public void onFailure(Exception e) {
                logger.error(e.getMessage());
            }
        };
        try {
            IndexResponse response = client.index(request);
            return response.getId();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void batchAddOrUpdate(List<ESDocument<T>> documentList) {
        BulkRequest request = new BulkRequest();
        documentList.forEach(doc -> {
            request.add(new IndexRequest(doc.getIndex(),DOC_TYPE,doc.getDocId())
                    .source(JSONUtils.objectToJson(doc.getDocument()),XContentType.JSON));
        });

        ActionListener<BulkResponse> listener = new ActionListener<BulkResponse>() {
            @Override
            public void onResponse(BulkResponse response) {

            }

            @Override
            public void onFailure(Exception e) {
                logger.error(e.getMessage());
            }
        };
        try {
            client.bulkAsync(request,listener);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void batchDelete(List<ESDocument<T>> documentList) {
        Map<String,String> results = new HashMap<>();
        BulkRequest request = new BulkRequest();
        documentList.forEach(doc -> {
            request.add(new DeleteRequest(doc.getIndex(),DOC_TYPE,doc.getDocId()));
        });

        ActionListener<BulkResponse> listener = new ActionListener<BulkResponse>() {
            @Override
            public void onResponse(BulkResponse response) {

            }

            @Override
            public void onFailure(Exception e) {
                logger.error(e.getMessage());
            }
        };
        try {
            client.bulkAsync(request,listener);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String get(String index,String docId) {
        GetRequest request = new GetRequest(index,DOC_TYPE,docId);
        // 如果不想获取source
        // request.fetchSourceContext(new FetchSourceContext(false));

        // 配置需要返回的source字段
        // String[] includes = new String[]{"message", "*Date"};
        // String[] excludes = Strings.EMPTY_ARRAY;
        // FetchSourceContext fetchSourceContext = new FetchSourceContext(true, includes, excludes);
        // request.fetchSourceContext(fetchSourceContext);
        GetResponse response = null;
        try {
            response = client.get(request);
            if (!response.isExists()) {
                return response.getSourceAsString();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
