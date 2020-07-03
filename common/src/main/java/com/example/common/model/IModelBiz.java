package com.example.common.model;

import com.liqi.nohttputils.interfa.OnDialogGetListener;

import java.util.Map;

public interface IModelBiz {
     void getHttp(String header,String flag, String url, Map mapParameter, OnDialogGetListener onDialogGetListener);
     void getHttp(String header,String flag, String url, OnDialogGetListener onDialogGetListener);
     void getHttp(String url);
     void postHttp(String flag, String url, Map mapParameter, OnDialogGetListener onDialogGetListener);
     void postHttpJson(Map header,String flag, String url, String parameter, OnDialogGetListener onDialogGetListener);
}
