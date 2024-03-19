package com.example.consumer.utils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
@RequiredArgsConstructor
public class HttpUtil {
    // 这两个是重构了方法之后的类
    private final CloseableHttpClient httpClient;

    private final RequestConfig config;

    /**
     * 发送POST请求
     *
     * @param url   请求URL
     * @param param 请求体
     */
    public String doPost(String url, Map<String, Object> param) {

        // 创建Http Post请求
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(config);

        CloseableHttpResponse response = null;
        try {

            // 创建参数列表
            if (param != null) {
                List<NameValuePair> paramList = new ArrayList<>();
                for (Map.Entry<String, Object> entry : param.entrySet()) {
                    paramList.add(new BasicNameValuePair(entry.getKey(), entry.getValue().toString()));
                }
                // 模拟表单
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList);
                httpPost.setEntity(entity);
            }
            // 执行http请求
            response = httpClient.execute(httpPost);
            return EntityUtils.toString(response.getEntity(), "utf-8");
        } catch (Exception e) {
            return null;
        } finally {
            try {
                httpPost.abort();
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 发送post请求
     *
     * @param url     请求URL
     * @param param   请求体
     * @param headers 请求头信息
     */
    public String doPost(String url, Map<String, Object> param, Map<String, String> headers) throws RuntimeException {

        // 创建Http Post请求
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(config);
        if (!CollectionUtils.isEmpty(headers)) {
            headers.forEach((k, v) -> httpPost.addHeader(k, v));
        }

        CloseableHttpResponse response = null;
        try {
            // 创建参数列表
            if (param != null) {
                httpPost.setEntity(processingFormBody(param));
            }
            // 执行http请求 同时如果http请求参数为3XX需要我们进行手动的重定向
            response = httpClient.execute(httpPost);

            //307   302  3XX 如果htppclient不能自动重定向请求都需要手动重定向
            if (response.getStatusLine().getStatusCode() == 307) {
                //第二次发送只是路径变更 ，发送的数据还是自定义。
                Header header = response.getFirstHeader("location"); // 跳转的目标地址是在 HTTP-HEAD上
                String value = header.getValue();
                log.warn("重定向地址：" + value);
                // 这就是跳转后的地址，再向这个地址发出新申请

                HttpPost newHttpPost = new HttpPost(value);
                if (!CollectionUtils.isEmpty(headers)) {
                    headers.forEach((k, v) -> newHttpPost.addHeader(k, v));
                }

                if (param != null) {
                    newHttpPost.setEntity(processingFormBody(param));
                }

                response = httpClient.execute(newHttpPost);
            }
            return EntityUtils.toString(response.getEntity(), "utf-8");
        } catch (Exception e) {
            throw new RuntimeException("https请求发送失败");
        } finally {
            try {
                httpPost.abort();
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public UrlEncodedFormEntity processingFormBody(Map<String, Object> param) throws IOException{
        // 转换成list 对象是 NameValuePair
        List<NameValuePair> paramList = new ArrayList<>();
        for (Map.Entry<String, Object> entry : param.entrySet()) {
            paramList.add(new BasicNameValuePair(entry.getKey(), entry.getValue().toString()));
        }
        // 模拟表单
        return new UrlEncodedFormEntity(paramList);
    }


}
