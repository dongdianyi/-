package com.example.common.model;

import android.text.TextUtils;

import com.example.common.data.BaseUrl;
import com.example.common.view.IView;
import com.liqi.nohttputils.RxNoHttpUtils;
import com.liqi.nohttputils.interfa.OnDialogGetListener;
import com.liqi.nohttputils.interfa.OnIsRequestListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;


/**
 * 网络请求类
 */
public class NoHttpRx implements IModelBiz {
    private IView iView;

    public NoHttpRx(IView iView) {
        this.iView = iView;
    }


    //get请求
    @Override
    public void getHttp(String header, final String flag, String url, Map mapParameter, OnDialogGetListener onDialogGetListener) {
        RxNoHttpUtils.rxNohttpRequest()
                .get()
                .url(BaseUrl.BASE_URL + url)
//                .addParameter("pageNum",1)
//                .addParameter("companyId","1")
//                .addParameter(mapParameter)
                .addParameter(mapParameter)
//                .addHeader("header",header)
                .setOnDialogGetListener(onDialogGetListener)//请求加载框
                .setQueue(false)
                //单个请求设置读取时间(单位秒，默认以全局读取超时时间。)
                // .setReadTimeout(40)
                //单个请求设置链接超时时间(单位秒，默认以全局链接超时时间。)
                // .setConnectTimeout(30)
                //单个请求设置请求失败重试计数。默认值是0,也就是说,失败后不会再次发起请求。
                //.setRetryCount(3)
                //单个请求设置缓存key
                //.setCacheKey("get请求Key")
                //单个请求设置缓存模式
                // .setCacheMode(CacheMode.REQUEST_NETWORK_FAILED_READ_CACHE)
                // 设置请求bodyEntity为StringEntity，并传请求类型。
//                .requestStringEntity("application/json")
                //为StringEntity添加body中String值
//                .addStringEntityParameter(mapParameter)
                //从bodyEntity切换到请求配置对象
//                 .transitionToRequest()
                //设置请求bodyEntity为JsonObjectEntity.json格式：{"xx":"xxx","yy":"yyy"}
                // .requestJsonObjectEntity()
                //给JsonObjectEntity添加参数和值
                //.addEntityParameter("key","Valu")
                //从bodyEntity切换到请求配置对象
                // .transitionToRequest()
                //设置请求bodyEntity为JsonListEntity.json格式：[{"xx":"xxx"},{"yy":"yyy"}]
                //.requestJsonListEntity()
                //给JsonList创造对象，并传键值参数
                //.addObjectEntityParameter("key","Valu")
                //在创造对象的上添加键值参数
                //.addEntityParameter("key","Valu")
                //把创造对象刷进进JsonList里面
                //.objectBrushIntoList()
                //从bodyEntity切换到请求配置对象
                //.transitionToRequest()
                //设置请求bodyEntity为InputStreamEntity
                //.requestInputStreamEntity(Content-Type)
                //给InputStreamEntity添加输入流
                //.addEntityInputStreamParameter(InputStream)
                //从bodyEntity切换到请求配置对象
                //.transitionToRequest()
                .setSign(flag)
                .builder(String.class, new OnIsRequestListener<String>() {
                    @Override
                    public void onNext(String s) {
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            if (TextUtils.isEmpty(s) || s.equals("") || s.trim().equals("")) {
                                iView.fail(false, flag, new Throwable("亲！取得数据为空"));
                            } else if ("200".equals(jsonObject.getString("code"))) {
                                iView.toData(flag, s);
                            } else {
                                iView.fail(false, flag, new Throwable(jsonObject.getString("msg")));
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }

                    @Override
                    public void onError(Throwable throwable) {
                        iView.fail(false, flag, throwable);
                    }
                })
                .requestRxNoHttp();
    }

    @Override
    public void getHttp(String header, final String flag, String url, final OnDialogGetListener onDialogGetListener) {
        RxNoHttpUtils.rxNohttpRequest()
                .get()
                .url(BaseUrl.BASE_URL + url)
                .setOnDialogGetListener(onDialogGetListener)//请求加载框
                .setQueue(false)
                .setSign(flag)
                .builder(String.class, new OnIsRequestListener<String>() {
                    @Override
                    public void onNext(String s) {
                        if (null != onDialogGetListener && onDialogGetListener.getDialog() != null) {
                            onDialogGetListener.getDialog().dismiss();
                        }
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            if (TextUtils.isEmpty(s) || s.equals("") || s.trim().equals("")) {
                                iView.fail(false, flag, new Throwable("亲！取得数据为空"));
                            } else if ("200".equals(jsonObject.getString("code"))) {
                                iView.toData(flag, s);
                            } else {
                                iView.fail(false, flag, new Throwable(jsonObject.getString("msg")));
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }

                    @Override
                    public void onError(Throwable throwable) {
                        if (null != onDialogGetListener && onDialogGetListener.getDialog() != null) {
                            onDialogGetListener.getDialog().dismiss();
                        }
                        iView.fail(false, flag, throwable);
                    }
                })
                .requestRxNoHttp();
    }

    @Override
    public void getHttp(String url) {
        RxNoHttpUtils.rxNohttpRequest()
                .get()
                .url(url)
                .setQueue(false)
                .builder(String.class, new OnIsRequestListener<String>() {
                    @Override
                    public void onNext(String s) {
                        iView.toData("ip", s);
                    }
                    @Override
                    public void onError(Throwable throwable) {
                        iView.fail(true, "ip", throwable);
                    }
                })
                .requestRxNoHttp();
    }

    //post请求
    @Override
    public void postHttp(final String flag, String url, Map mapParameter, OnDialogGetListener onDialogGetListener) {
    }

    //post请求
    @Override
    public void postHttpJson(Map header, final String flag, String url, String parameter, final OnDialogGetListener onDialogGetListener) {
        RxNoHttpUtils.rxNohttpRequest()
                .post()
//                .url(BaseUrl.BASE_URL + url)
                .url(url)
                .addHeader(header)
                .setOnDialogGetListener(onDialogGetListener)//请求加载框
                .setAnUnknownErrorHint("POST未知错误提示")
                //设置请求bodyEntity为StringEntity，并传请求类型。
                .requestStringEntity("application/json")
                //为StringEntity添加body中String值
                .addStringEntityParameter(parameter)
                //从bodyEntity切换到请求配置对象
                .transitionToRequest()
                .setSign(flag)
                .builder(String.class, new OnIsRequestListener<String>() {
                    @Override
                    public void onNext(String s) {
                        if (null != onDialogGetListener && onDialogGetListener.getDialog() != null) {
                            onDialogGetListener.getDialog().dismiss();
                        }
//                        RxNoHttpUtils.cancel(flag);
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            if (TextUtils.isEmpty(s) || s.equals("") || s.trim().equals("")) {
                                iView.fail(false, flag, new Throwable("亲！取得数据为空"));
                            } else if ("200".equals(jsonObject.getString("code"))) {
                                iView.toData(flag, s);
                            } else {
                                iView.fail(false, flag, new Throwable(jsonObject.getString("msg")));
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        if (null != onDialogGetListener && onDialogGetListener.getDialog() != null) {
                            onDialogGetListener.getDialog().dismiss();
                        }
                        iView.fail(true, flag, throwable);

                    }
                })

                .requestRxNoHttp();
    }
}
