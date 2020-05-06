package com.cloud.handler;

public interface DataHandler<T> {

    public abstract void handle(T data);
}
