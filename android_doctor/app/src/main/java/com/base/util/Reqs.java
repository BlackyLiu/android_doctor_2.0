package com.base.util;

import okhttp3.*;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class Reqs {

    private static OkHttpClient okHttpClient = new OkHttpClient();

    /**
     * 异步
     *
     * @param url
     * @return
     */
    public static String get(String url, Callback callback) {
        Request request = new Request.Builder().url(url).get()// 默认就是GET请求，可以不写
                .build();
        //异步的请求方法
        Call call = okHttpClient.newCall(request);
        call.enqueue(callback);
        return "";
    }

    /**
     * 同步请求方法
     *
     * @param url
     * @return
     */
    public static String get(String url) {
        Request request = new Request.Builder().url(url).get()// 默认就是GET请求，可以不写
                .build();
        //同步请求
        Call call = okHttpClient.newCall(request);
        try {
            Response response = call.execute();
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String get(String url, Map<String, String> map) {
        Request.Builder builder = new Request.Builder();
        HttpUrl.Builder hB = HttpUrl.parse(url).newBuilder();
        Set<Map.Entry<String, String>> entrySet = map.entrySet();
        Iterator<Map.Entry<String, String>> iterator = entrySet.iterator();
        FormBody.Builder build = new FormBody.Builder();

        while (iterator.hasNext()) {
            Map.Entry<String, String> next = iterator.next();
            String key = next.getKey();
            String value = next.getValue();
            hB.addQueryParameter(key, value);
        }
        Request request = builder.url(hB.build()).get()// 默认就是GET请求，可以不写
                .build();
        Call call = okHttpClient.newCall(request);
        try {
            Response response = call.execute();
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }



    public static String get(String url, Object o) {
        return  get( url,  o, "id");
    }

    public static String get(String url, Object o,String fieldName) {
        Request.Builder builder = new Request.Builder();
        HttpUrl.Builder hB = HttpUrl.parse(url).newBuilder();
        FormBody.Builder build = new FormBody.Builder();

//        while (iterator.hasNext()) {
//            Map.Entry<String, String> next = iterator.next();
//            String key = next.getKey();
//            String value = next.getValue();
//            hB.addQueryParameter(key, value);
//        }
        try {
            if(o instanceof  Number){
                hB.addQueryParameter(fieldName,o.toString());
            }else {
                Field[] fields = o.getClass().getDeclaredFields();
                for (Field field : fields) {
                    field.setAccessible(true);
                    Object obj = field.get(o);
                    if(obj != null){
                        hB.addQueryParameter(field.getName(),obj.toString());
                    }
                }
            }
            Request request = builder.url(hB.build()).get()// 默认就是GET请求，可以不写
                    .build();
            Call call = okHttpClient.newCall(request);
            Response response = call.execute();
            return response.body().string();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


    /**
     * 同步请求方法
     *
     * @param url
     * @return
     */
    public static String post(String url) {
        Request.Builder builder = new Request.Builder();
        MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
        Request request = builder.url(url).post(RequestBody.create(mediaType, ""))// 默认就是GET请求，可以不写
                .build();
        //同步请求
        Call call = okHttpClient.newCall(request);
        try {
            Response response = call.execute();
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }



    /**
     * 同步请求方法
     *
     * @param url
     * @return
     */
    public static String post(String url, String content) {
        Request.Builder builder = new Request.Builder();
//		MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
        MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
        Request request = builder.url(url).post(RequestBody.create(mediaType, content))// 默认就是GET请求，可以不写
                .build();
        //同步请求
        Call call = okHttpClient.newCall(request);
        try {
            Response response = call.execute();
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String post(String url, Object o){
               return  post(url,o,"id");
    }
    public static String post(String url, Object o,String f) {

        Request.Builder builder = new Request.Builder();
        MultipartBody.Builder body = new MultipartBody.Builder()
                .setType(MultipartBody.FORM);
        Field[] fields = o.getClass().getDeclaredFields();
        try {
            if(o instanceof  Number){
//                hB.addQueryParameter(fieldName,o.toString());
                body.addFormDataPart(f,o.toString());
            }else {
                for (Field field : fields) {
                    field.setAccessible(true);
                    Object obj = field.get(o);
                    if (obj != null) {
                        String name = field.getType().getName();
                        if (name.equals("java.util.Date")) {
                            body.addFormDataPart(field.getName(), DateOlds.formatDate((Date) obj));
                        } else {
                            body.addFormDataPart(field.getName(), obj.toString());
                        }
                    }
                }
            }
//            MultipartBody build = body.build();
            Request request = builder.url(url).post(body.build())// 默认就是GET请求，可以不写
                    .build();
            Call call = okHttpClient.newCall(request);
            try {
                Response response = call.execute();
                return response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "";
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return  "";
    }


    public static String post(String url, Map<String, String> map) {
        Request.Builder builder = new Request.Builder();
        MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
        RequestBody requestBody = null;

        Set<Map.Entry<String, String>> entrySet = map.entrySet();
        Iterator<Map.Entry<String, String>> iterator = entrySet.iterator();
        FormBody.Builder build = new FormBody.Builder();
        while (iterator.hasNext()) {
            Map.Entry<String, String> next = iterator.next();
            String key = next.getKey();
            String value = next.getValue();
            build.add(key, value);
        }
        requestBody = build.build();
        Request request = builder.url(url).post(requestBody)// 默认就是GET请求，可以不写
                .build();
        Call call = okHttpClient.newCall(request);
        try {
            Response response = call.execute();
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";

    }


    public static void post(String url, Map<String, String> header, String content, Callback callback) {
        Request.Builder builder = new Request.Builder();
        Set<Map.Entry<String, String>> entrySet = header.entrySet();
        Iterator<Map.Entry<String, String>> entryIterator = entrySet.iterator();
        while (entryIterator.hasNext()) {
            Map.Entry<String, String> entry = entryIterator.next();
            builder.addHeader(entry.getKey(), entry.getValue());
        }
        MediaType mediaType = MediaType.parse("application/json; charset=utf-8");

        Request request = builder.url(url).post(RequestBody.create(mediaType, content))// 默认就是GET请求，可以不写
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(callback);
    }

    /**
     * @param url
     * @return
     */
    public static String get(String url, String cookie, Callback callback) {
        Request request = new Request.Builder().header("Cookie", cookie).url(url).get()// 默认就是GET请求，可以不写
                .build();
        //异步的请求方法
        Call call = okHttpClient.newCall(request);
        call.enqueue(callback);
        return "";
    }


    /**
     * @param url
     * @return
     */
    public static void get(String url, Map<String, String> header, Callback callback) {
        Request.Builder builder = new Request.Builder();
        Set<Map.Entry<String, String>> entrySet = header.entrySet();
        Iterator<Map.Entry<String, String>> entryIterator = entrySet.iterator();
        while (entryIterator.hasNext()) {
            Map.Entry<String, String> entry = entryIterator.next();
            builder.addHeader(entry.getKey(), entry.getValue());
        }
        Request request = builder.url(url).get()// 默认就是GET请求，可以不写
                .build();
        //异步的请求方法
        Call call = okHttpClient.newCall(request);
        call.enqueue(callback);
    }


    public static void main(String[] args) {


//		String url = "https://www.baidu.com";
//		Reqs.get(url,new Callback() {
//			@Override
//			public void onResponse(Call call, Response response) throws IOException {
//				// TODO Auto-generated method stub
//				System.out.println(response.body().string());
//				System.out.println("结束调用");
//				System.exit(1);
//			}
//
//			@Override
//			public void onFailure(Call call, IOException e) {
//				// TODO Auto-generated method stub
//
//			}
//		});
    }


}
