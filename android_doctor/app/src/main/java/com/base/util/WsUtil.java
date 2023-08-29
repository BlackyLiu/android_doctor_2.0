package com.base.util;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class WsUtil {

    final static String SERVICE_NS = "http://service.base.com/";

    final static String ip = "127.0.0.1";
//    final static String ip = "localhost";
//    final static String ip = " 169.254.6.81";

    final static String BASE_URL = String.format("%s%s%s", "http://",ip,":8080/");

    public static String sendWSReuqest(String moduleName,String methodName,String params){
        HttpTransportSE ht = new HttpTransportSE(String.format("%s/%s",BASE_URL,moduleName));
        //使用soap1.1协议创建Envelop对象
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        //实例化SoapObject对象
        SoapObject request = new SoapObject(SERVICE_NS, methodName);
        envelope.setOutputSoapObject(request);
        if(!Strings.isEmpty(params)){
            request.addProperty("arg0",params);
        }
        try{
            ht.call(null, envelope);
            if(envelope.getResponse() != null){
                SoapObject result = (SoapObject) envelope.bodyIn;
                return    result.getProperty(0).toString();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }

}
