package com.cloud.common.httpclient.service;

import com.cloud.common.core.constants.SymbolConstants;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.collections4.MapUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class HttpClientService {

    private static ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private HttpClient httpClient;

    public Map<String,Object> doGet(String url) throws IOException {
        Map<String,Object> results = new HashMap<>();
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        try {
            HttpResponse response = this.httpClient.execute(httpGet);
            if (response.getStatusLine().getStatusCode() == 200) {
                String entity = EntityUtils.toString(response.getEntity());
                results = objectMapper.readValue(entity, Map.class);
            }
        } finally {
            client.close();
        }
        return results;
    }

    public Map<String,Object> doPost(String url) throws IOException {
        Map<String,Object> results = new HashMap<>();
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        try {
            HttpResponse response = this.httpClient.execute(httpPost);
            if (response.getStatusLine().getStatusCode() == 200) {
                String entity = EntityUtils.toString(response.getEntity());
                results = objectMapper.readValue(entity, Map.class);
            }
        } finally {
            client.close();
        }
        return results;
    }


    public Map<String,Object> doGet(String url, Map<String,String> params) throws IOException {
        Map<String,Object> results = new HashMap<>();
        CloseableHttpClient client = HttpClients.createDefault();
        StringBuilder sb = new StringBuilder(url);
        if (MapUtils.isNotEmpty(params)) {
            sb.append(SymbolConstants.QUESQION_SYMBOL);
            params.forEach( (name,value) -> {
                sb.append(name).append(SymbolConstants.AND_SYMBOL).append(value);
            });
        }
        HttpGet httpGet = new HttpGet(sb.toString());
        try {
            HttpResponse response = this.httpClient.execute(httpGet);
            if (response.getStatusLine().getStatusCode() == 200) {
                String entity = EntityUtils.toString(response.getEntity());
                results = objectMapper.readValue(entity, Map.class);
            }
        } finally {
            client.close();
        }
        return results;
    }

    public Map<String,Object> doPost(String url, Map<String,String> params) throws IOException {
        Map<String,Object> results = new HashMap<>();
        CloseableHttpClient client = HttpClients.createDefault();
        StringBuilder sb = new StringBuilder(url);
        if (MapUtils.isNotEmpty(params)) {
            sb.append(SymbolConstants.QUESQION_SYMBOL);
            params.forEach( (name,value) -> {
                sb.append(name).append(SymbolConstants.AND_SYMBOL).append(value);
            });
        }
        HttpPost httpPost = new HttpPost(sb.toString());
        try {
            HttpResponse response = this.httpClient.execute(httpPost);
            if (response.getStatusLine().getStatusCode() == 200) {
                String entity = EntityUtils.toString(response.getEntity());
                results = objectMapper.readValue(entity, Map.class);
            }
        } finally {
            client.close();
        }
        return results;
    }


}
