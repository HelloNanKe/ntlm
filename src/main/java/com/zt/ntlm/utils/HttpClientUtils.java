package com.zt.ntlm.utils;

import com.zt.ntlm.common.HttpKey;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: zt
 * @Date: 2018/12/22 18:44
 */
public class HttpClientUtils {

    /**
     * 发送get请求
     *
     * @param url 链接地址
     * @return
     */
    public static Map<String, Object> doGet(String url, Map<String, String> headerMap) {
        Map<String, Object> resMap = new HashMap<>();

        HttpClient httpClient = null;
        HttpGet httpGet = null;
        String result = null;
        try {
            httpClient = new DefaultHttpClient();

            httpGet = new HttpGet(url);

            if (!CollectionUtils.isEmpty(headerMap)) {
                for (Map.Entry entry : headerMap.entrySet()) {
                    httpGet.addHeader(entry.getKey().toString(), entry.getValue().toString());
                }
            }

            HttpResponse response = httpClient.execute(httpGet);

            resMap.put(HttpKey.HEADERS, response.getAllHeaders());
            resMap.put(HttpKey.STATUS_CODE, response.getStatusLine().getStatusCode());

            if (!ObjectUtils.isEmpty(response)) {
                HttpEntity resEntity = response.getEntity();
                if (!ObjectUtils.isEmpty(resEntity)) {

                    if (resEntity.getContentType() != null) {
                        if (!StringUtils.isEmpty(resEntity.getContentType().getValue())) {
                            resMap.put(HttpKey.PAGETYPE, resEntity.getContentType().getValue());
                        }
                    }
                    result = EntityUtils.toString(resEntity, "UTF-8");
                    resMap.put(HttpKey.RES, result);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (!ObjectUtils.isEmpty(httpGet)) {
                httpGet.releaseConnection();
            }
        }
        return resMap;
    }


}
