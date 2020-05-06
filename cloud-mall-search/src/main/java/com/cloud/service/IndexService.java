package com.cloud.service;

import com.cloud.module.ESDocument;

import java.util.List;

public interface IndexService<T> {
    public String addDocument(ESDocument<T> document);
    public String updateDocument(ESDocument<T> document);
    public void batchAddOrUpdate(List<ESDocument<T>> documentList);
    public void batchDelete(List<ESDocument<T>> documentList);
    public String get(String index,String docId);
}
