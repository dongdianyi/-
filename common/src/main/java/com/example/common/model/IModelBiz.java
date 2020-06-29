package com.example.common.model;

import com.liqi.nohttputils.interfa.OnDialogGetListener;

import java.util.Map;

public interface IModelBiz {
     void getHttp(String header,String flag, String url, Map mapParameter, OnDialogGetListener onDialogGetListener);
     void getHttp(String header,String flag, String url, OnDialogGetListener onDialogGetListener);
     void postHttp(String flag, String url, Map mapParameter, OnDialogGetListener onDialogGetListener);
     void postHttpJson(String header,String flag, String url, String parameter, OnDialogGetListener onDialogGetListener);
}
