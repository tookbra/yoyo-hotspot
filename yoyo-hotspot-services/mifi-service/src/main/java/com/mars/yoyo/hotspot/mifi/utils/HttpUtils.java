package com.mars.yoyo.hotspot.mifi.utils;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.*;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.http.HttpMethod;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author tookbra
 * @date 2018/9/10
 * @description
 */
@Slf4j
public class HttpUtils {

    private static final String SYMBOL = "?";

    public static String get(String url, Map<String, String> params) {
        HttpGet get = new HttpGet(urlJoin(url, params));
        return execute(get,null, null);
    }

    /**
     * post请求 application/json
     * @param url 请求地址
     * @param params 请求参数
     * @return 响应结果
     */
    public static String post(String url, Map<String, Object> params){
        HttpPost post = new HttpPost(url);
        post.setHeader("accept", "*/*");
        post.setHeader("connection", "Keep-Alive");
        post.setHeader("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
        post.setHeader("Content-Type", "application/x-www-form-urlencoded");

        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        if(params != null){
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                nvps.add(new BasicNameValuePair(entry.getKey(), (String)entry.getValue()));
            }
        }
        try {
            post.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return execute(post,null, null);
    }

    /**
     * 拼接get请求参数
     * @param url 请求地址
     * @param params 请求参数
     * @return 最终的地址
     */
    private static String urlJoin(String url,Map<String, String> params){
        String requestParams = formatParams(params);
        if (url.contains(SYMBOL)){
            if (url.endsWith(SYMBOL)){
                url = url + requestParams;
            }else{
                url = url + "&" + params;
            }
        } else {
            url += "?" + requestParams;
        }
        return url;
    }

    /**
     * 格式化请求参数
     * @param params get请求参数
     * @return 结果,如果还有特殊字符会进行转义或编码
     */
    private static String formatParams(Map<String, String> params){
        if (params != null){
            List<NameValuePair> valuePairs = Lists.newArrayList();
            for (Map.Entry<String,String> entry : params.entrySet()){
                valuePairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
            try {
                return EntityUtils.toString(new UrlEncodedFormEntity(valuePairs, Consts.UTF_8));
            }catch (Exception e){
                log.error("url参数编码错误", e);
            }
        }
        return null;
    }

    /**
     * 执行Http请求
     * @param request 请求参数
     * @param client httpClient对象
     * @param config 请求配置
     * @return 结果响应
     */
    private static String execute(HttpUriRequest request, CloseableHttpClient client, RequestConfig config){
        if (config == null){
            config = defaultConfig();
        }
        if(client == null){
            client = HttpClients.custom().setDefaultRequestConfig(config).build();
        }
        try (CloseableHttpResponse response = client.execute(request)){
            int code = response.getStatusLine().getStatusCode();
            if (code != HttpStatus.SC_OK){
                log.error("http请求响应状态码异常,code:{}",code);
                return null;
            }
            HttpEntity responseEntity = response.getEntity();
            String entity = EntityUtils.toString(responseEntity, Consts.UTF_8);
            log.info("http响应结果:{}",entity);
            return entity;
        } catch (IOException e) {
            log.error("http请求异常",e);
        }finally {
            try {
                client.close();
            } catch (IOException e) {
                log.error("关闭client异常",e);
            }
        }
        return null;
    }

    /**
     * 默认请求配置 post get通用
     * @return 配置信息
     */
    private static RequestConfig defaultConfig(){
        return RequestConfig.custom().setSocketTimeout(6000).setConnectionRequestTimeout(6000).setConnectTimeout(6000).build();
    }
}
