package com.cloud.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Component
public class RestService {

    @Autowired
    RestTemplate restTemplate;

    public <T> T exchange(String url, HttpMethod method, T obj) {
        ParameterizedTypeReference<T> responseType = new ParameterizedTypeReference<T>() {};
        ResponseEntity<T> responseEntity = this.restTemplate.exchange(url, method,null,responseType);
        return responseEntity.getBody();
    }

    public <T> T exchange(String url, HttpMethod method, Class<T> responseType,
                          MultiValueMap<String, Object> params,
                          MultiValueMap<String,String> headers) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.addAll(headers);
        if (params == null) {
            params = new LinkedMultiValueMap<>();
        }
        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<MultiValueMap<String, Object>>(params, httpHeaders);
        ResponseEntity<T> responseEntity = this.restTemplate.exchange(url,method,request,responseType);
        return responseEntity.getBody();
    }

    public <T> T exchange(String url, HttpMethod method, ParameterizedTypeReference<T> responseType) {
        ResponseEntity<T> responseEntity = this.restTemplate.exchange(url, method,null,responseType);
        return responseEntity.getBody();
    }

    public <T> T exchange(String url, HttpMethod method, ParameterizedTypeReference<T> responseType,
                          MultiValueMap<String, Object> params) {
        ResponseEntity<T> responseEntity = this.restTemplate.exchange(url, method,null,responseType,params);
        return responseEntity.getBody();
    }

    public <T> T exchange(String url, HttpEntity entity, HttpMethod method, T obj) {
        ParameterizedTypeReference<T> responseType = new ParameterizedTypeReference<T>() {};
        ResponseEntity<T> responseEntity = this.restTemplate.exchange(url,method,entity,responseType);
        return responseEntity.getBody();
    }

    public <T> T getForObject(String url, Class<T> clazz) {
        return this.restTemplate.getForObject(url, clazz);
    }

    public <T> T getForObject(String url, Class<T> clazz, MultiValueMap<String, Object> params) {
        return this.restTemplate.getForObject(url, clazz,params);
    }

    public <T> T postForObject(String url, Object request, Class<T> clazz, MultiValueMap<String, Object> params) {
        return this.restTemplate.postForObject(url,request,clazz,params);
    }

    public <T> T postForObject(String url, Class<T> clazz, MultiValueMap<String, Object> params) {
        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<MultiValueMap<String, Object>>(params, new HttpHeaders());
        return this.restTemplate.postForObject(url,request,clazz, params);
    }

    public <T> T postForObject(String url, Class<T> clazz) {
        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<MultiValueMap<String, Object>>(params, new HttpHeaders());
        return this.restTemplate.postForObject(url,request,clazz, params);
    }

    public <T> T postForEntity(String url, Class<T> clazz, MultiValueMap<String, Object> params,
                               MultiValueMap<String,String> headers)  {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.addAll(headers);
        if (params == null) {
            params = new LinkedMultiValueMap<>();
        }
        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<MultiValueMap<String, Object>>(params, httpHeaders);
        ResponseEntity<T> responseEntity = this.restTemplate.postForEntity(url,request,clazz);
        return responseEntity.getBody();
    }
}
