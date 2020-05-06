package com.cloud.module;

import lombok.Data;

@Data
public class ESDocument<T> {
    private String docId;
    private String index;
    private T document;

    public ESDocument() {
    }

    public ESDocument(String docId, String index, T document) {
        this.docId = docId;
        this.index = index;
        this.document = document;
    }
}
