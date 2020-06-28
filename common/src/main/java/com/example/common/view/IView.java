package com.example.common.view;

public interface IView {
    void  toData(String flag, String object);
    void  fail(boolean isNetWork, String flag, Throwable t);
}
