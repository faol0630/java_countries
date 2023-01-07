package com.example.countries.repository;

public interface AsyncTaskReceiver<T> {
    void onSuccess(T result);
    void onFailure(Throwable throwable);
}
