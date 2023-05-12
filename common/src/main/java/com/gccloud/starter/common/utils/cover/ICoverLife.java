package com.gccloud.starter.common.utils.cover;

public interface ICoverLife<S, T> {
    void after(Object source, T target);
}
