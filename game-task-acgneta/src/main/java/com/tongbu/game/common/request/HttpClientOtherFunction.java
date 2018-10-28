package com.tongbu.game.common.request;

import org.apache.http.*;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.*;

/**
 * @author jokin
 * @date 2018/8/20 16:37
 *
 * https://blog.csdn.net/zhuwukai/article/details/78644484
 * 一些其他的写法
 */
public class HttpClientOtherFunction {
    private static final Logger logger = LoggerFactory.getLogger(HttpClientOtherFunction.class);

    private static final String ENCODING = "UTF-8";
    private static final String POST_METHOD = "post";
    private static final String GET_METHOD = "GET";

    private static HttpClient client = null;
    private static PoolingHttpClientConnectionManager connectionManager = null;
    private static HttpClientBuilder httpBuilder = null;
    private static RequestConfig requestConfig = null;

    private static int MAXCONNECTION = 10;

    private static int DEFAULTMAXCONNECTION = 5;

    private static String IP = "cnivi.com.cn";
    private static int PORT = 80;

    static {
        //设置http的状态参数 :新建一个RequestConfig：
        requestConfig = RequestConfig.custom()
                //读取目标服务器数据超时时间：SocketTimeout-->指的是连接上一个url，获取response的返回等待时间
                .setSocketTimeout(5000)
                //连接目标服务器超时时间：ConnectionTimeout-->指的是连接一个url的连接等待时间
                .setConnectTimeout(5000)
                // 从连接池获取连接的超时时间:ConnectionRequestTimeout
                .setConnectionRequestTimeout(5000)
                .build();

        HttpHost target = new HttpHost(IP, PORT);
        connectionManager = new PoolingHttpClientConnectionManager();
        // 客户端总并行链接最大数
        connectionManager.setMaxTotal(MAXCONNECTION);
        //每个主机的最大并行链接数
        connectionManager.setDefaultMaxPerRoute(DEFAULTMAXCONNECTION);
        connectionManager.setMaxPerRoute(new HttpRoute(target), 20);
        httpBuilder = HttpClients.custom();
        httpBuilder.setConnectionManager(connectionManager);
    }

    public static void main(String[] args) throws IOException {
        Map<String, String> map = new HashMap<>();
        map.put("account", "");
        map.put("password", "");

        HttpClient client = HttpClientTool.getHttpClient();
        HttpUriRequest post = getRequestMethod(map, "http://cnivi.com.cn/login", "post");
        HttpResponse response = client.execute(post);

        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            HttpEntity entity = response.getEntity();
            String message = EntityUtils.toString(entity, "utf-8");
            System.out.println(message);
        } else {
            System.out.println("请求失败");
        }
    }

    public static HttpUriRequest getRequestMethod(Map<String, String> map, String url, String method) {
        List<NameValuePair> params = new ArrayList<>();
        Set<Map.Entry<String, String>> entrySet = map.entrySet();
        for (Map.Entry<String, String> e : entrySet) {
            String name = e.getKey();
            String value = e.getValue();
            NameValuePair pair = new BasicNameValuePair(name, value);
            params.add(pair);
        }
        HttpUriRequest reqMethod = null;
        if (POST_METHOD.equals(method)) {
            reqMethod = RequestBuilder.post().setUri(url)
                    .addParameters(params.toArray(new BasicNameValuePair[params.size()]))
                    .setConfig(requestConfig).build();
        } else if (GET_METHOD.equals(method)) {
            reqMethod = RequestBuilder.get().setUri(url)
                    .addParameters(params.toArray(new BasicNameValuePair[params.size()]))
                    .setConfig(requestConfig).build();
        }
        return reqMethod;
    }

    public static void get(String url)
    {
        HttpClient client = HttpClientTool.getHttpClient();
        try {
            HttpGet httpGet = new HttpGet(url);
            //設置httpGet的头部參數信息
            httpGet.setHeader("Accept", "Accept text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
            httpGet.setHeader("Accept-Charset", "GB2312,utf-8;q=0.7,*;q=0.7");
            httpGet.setHeader("Accept-Encoding", "gzip, deflate");
            httpGet.setHeader("Accept-Language", "zh-cn,zh;q=0.5");
            httpGet.setHeader("Connection", "keep-alive");
            httpGet.setHeader("Cookie", "__utma=226521935.73826752.1323672782.1325068020.1328770420.6;");
            httpGet.setHeader("Host", "www.cnblogs.com");
            httpGet.setHeader("refer", "http://www.baidu.com/s?tn=monline_5_dg&bs=httpclient4+MultiThreadedHttpConnectionManager");
            httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; rv:6.0.2) Gecko/20100101 Firefox/6.0.2");
            httpGet.setHeader("Content-type","application/xml; charset=UTF-8");

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
