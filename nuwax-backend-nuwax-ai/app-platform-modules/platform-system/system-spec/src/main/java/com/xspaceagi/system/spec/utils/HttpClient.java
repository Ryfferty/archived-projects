package com.xspaceagi.system.spec.utils;

import com.alibaba.fastjson2.JSON;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.fluent.Executor;
import org.apache.http.client.fluent.Request;
import org.apache.http.conn.ssl.AllowAllHostnameVerifier;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.net.ssl.SSLContext;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.*;
import java.util.Map.Entry;

@Component
public class HttpClient {

    static {
        System.setProperty("jdk.tls.allowUnsafeServerCertChange", "true");
        System.setProperty("sun.security.ssl.allowUnsafeRenegotiation", "true");
    }

    private static final Logger LOG = LoggerFactory.getLogger(HttpClient.class);

    private static final int DEFAULT_CONNECT_TIMEOUT = 3000;

    private static final int DEFAULT_SOCKET_TIMEOUT = 180000;

    private static final Charset UTF_8 = Charset.forName("UTF-8");

    private static final int DEFAULT_POOL_SIZE = 200;

    private int connectTimeout = DEFAULT_CONNECT_TIMEOUT;

    private int socketTimeout = DEFAULT_SOCKET_TIMEOUT;

    private int poolSize = DEFAULT_POOL_SIZE;

    private CloseableHttpClient httpClient;

    private Executor executor;

    private ThreadLocal<Integer> timeoutSeconds = new ThreadLocal<>();

    private ThreadLocal<HttpResponse> responseThreadLocal = new ThreadLocal<>();

    @PostConstruct
    public void initHttpClient() throws Exception {
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();

        SSLContext sslContext;

        sslContext = SSLContexts.custom().useTLS().loadTrustMaterial(null, new TrustStrategy() {

            public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                // 信任所有ssl证书
                return true;
            }

        }).build();

        httpClientBuilder.setSslcontext(sslContext);

        httpClientBuilder.setHostnameVerifier(new AllowAllHostnameVerifier());

        RequestConfig requestConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.IGNORE_COOKIES).setSocketTimeout(socketTimeout).setConnectTimeout(connectTimeout).build();
        httpClientBuilder.setRetryHandler((exception, executionCount, context) -> {
            if (executionCount > 3) {
                LOG.warn("Maximum tries reached for client http pool ");
                return false;
            }
            if (exception instanceof org.apache.http.NoHttpResponseException) {
                LOG.warn("No response from server on " + executionCount + " call");
                return true;
            }
            return false;
        });
        httpClient = httpClientBuilder.setMaxConnTotal(poolSize).setMaxConnPerRoute(poolSize).setDefaultRequestConfig(requestConfig).build();
        executor = Executor.newInstance(httpClient);
    }

    @PreDestroy
    public void destroyHttpClient() {
        try {
            httpClient.close();
        } catch (IOException e) {
            LOG.warn("HttpClient关闭失败");
        }
    }

    public String get(String uri) {
        Request request = Request.Get(uri);
        request.connectTimeout(connectTimeout);
        request.socketTimeout(socketTimeout);
        return processRequest(request);
    }

    public String get(String uri, Map<String, String> headers) {
        Request request = Request.Get(uri);
        addHeaders(request, headers);
        request.connectTimeout(connectTimeout);
        request.socketTimeout(socketTimeout);
        return processRequest(request);
    }

    public String get(String url, Map<String, Object> queries, Map<String, Object> headers, Map<String, Object> pathParams) {
        //queries转成queryString
        Map<String, String> queryMap = convertToStringParams(queries);
        Map<String, String> headerMap = convertToStringParams(headers);
        Map<String, String> pathMap = convertToStringParams(pathParams);
        //pathMap替换url中的占位符
        for (Entry<String, String> entry : pathMap.entrySet()) {
            url = url.replace("{" + entry.getKey() + "}", entry.getValue());
        }
        url = addQueryString(url, queryMap);
        Request request = Request.Get(url);
        addHeaders(request, headerMap);
        request.connectTimeout(connectTimeout);
        request.socketTimeout(socketTimeout);
        return processRequest(request);
    }

    private String addQueryString(String uri, Map<String, String> queries) {
        if (queries != null && queries.size() > 0) {
            StringBuilder queryString = new StringBuilder(uri).append("?");
            for (Iterator<Entry<String, String>> iterator = queries.entrySet().iterator(); iterator.hasNext(); ) {
                Entry<String, String> entry = iterator.next();
                queryString.append(entry.getKey()).append("=").append(entry.getValue());
                if (iterator.hasNext()) {
                    queryString.append("&");
                }
            }
            uri = queryString.toString();
        }
        return uri;
    }

    public String post(String url, Map<String, String> params, Map<String, String> headers) {
        List<NameValuePair> formParams = convertToFormParams(params);
        Request request = Request.Post(url).bodyForm(formParams, UTF_8);
        addHeaders(request, headers);
        request.connectTimeout(connectTimeout);
        request.socketTimeout(socketTimeout);
        return processRequest(request);
    }

    public String post(String url, Map<String, Object> pathParams, Map<String, Object> query, Map<String, Object> headers,
                       Map<String, Object> body, ContentType contentType) {
        return postOrPut(url, pathParams, query, headers, body, contentType, true);
    }

    public String put(String url, Map<String, Object> pathParams, Map<String, Object> query, Map<String, Object> headers,
                      Map<String, Object> body, ContentType contentType) {
        return postOrPut(url, pathParams, query, headers, body, contentType, false);
    }

    private String postOrPut(String url, Map<String, Object> pathParams, Map<String, Object> query, Map<String, Object> headers,
                             Map<String, Object> body, ContentType contentType, boolean isPost) {
        Map<String, String> pathMap = convertToStringParams(pathParams);
        //pathMap替换url中的占位符
        for (Entry<String, String> entry : pathMap.entrySet()) {
            url = url.replace("{" + entry.getKey() + "}", entry.getValue());
        }
        url = addQueryString(url, convertToStringParams(query));
        if (contentType == ContentType.APPLICATION_JSON) {
            return post(url, JSON.toJSONString(body), convertToStringParams(headers));
        }
        if (isPost) {
            return post(url, convertToStringParams(body), convertToStringParams(headers));
        }
        return put(url, convertToStringParams(body), convertToStringParams(headers));
    }

    private Map<String, String> convertToStringParams(Map<String, Object> params) {
        Map<String, String> formParams = new HashMap<>();
        convertToStringParams(null, formParams, params);
        return formParams;
    }

    private void convertToStringParams(String preKey, Map<String, String> formParams, Map<String, Object> params) {
        for (Entry<String, Object> entry : params.entrySet()) {
            String key = preKey == null ? entry.getKey() : preKey + "." + entry.getKey();
            Object value = entry.getValue();
            if (value instanceof Map) {
                Map<String, Object> map = (Map<String, Object>) value;
                convertToStringParams(key, formParams, map);
            } else if (value instanceof List) {
                List<Object> list = (List<Object>) value;
                convertToListParams(key, formParams, list);
            } else {
                formParams.put(key, value.toString());
            }
        }
    }

    private void convertToListParams(String preKey, Map<String, String> formParams, List<Object> list) {
        for (int i = 0; i < list.size(); i++) {
            Object objValue = list.get(i);
            if (objValue instanceof Map) {
                Map<String, Object> map = (Map<String, Object>) objValue;
                convertToStringParams(preKey + "[" + i + "]", formParams, map);
            } else if (objValue instanceof List) {
                //formParams.put(key + "[" + i + "]", .toString());
                convertToListParams(preKey + "[" + i + "]", formParams, (List<Object>) objValue);
            } else {
                formParams.put(preKey + "[" + i + "]", String.valueOf(objValue));
            }
        }
    }

    public String post(String url, String body, Map<String, String> headers) {
        Request request = Request.Post(url).bodyString(body, ContentType.APPLICATION_JSON);
        addHeaders(request, headers);
        request.connectTimeout(connectTimeout);
        request.socketTimeout(socketTimeout);
        return processRequest(request);
    }

    public String fileUpload(String url, Map<String, File> files, Map<String, String> params, Map<String, String> headers) {
        // 构建Multipart实体
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();

        // 添加文件
        for (Map.Entry<String, File> entry : files.entrySet()) {
            builder.addBinaryBody(entry.getKey(), entry.getValue(), ContentType.DEFAULT_BINARY, entry.getValue().getName());
        }

        // 添加额外的表单参数
        if (params != null) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                builder.addTextBody(entry.getKey(), entry.getValue());
            }
        }

        Request request = Request.Post(url).body(builder.build());
        addHeaders(request, headers);
        request.connectTimeout(connectTimeout);
        request.socketTimeout(socketTimeout);
        return processRequest(request);
    }


    public String put(String url, Map<String, String> params, Map<String, String> headers) {
        List<NameValuePair> formParams = convertToFormParams(params);
        Request request = Request.Put(url).bodyForm(formParams, UTF_8);
        addHeaders(request, headers);
        request.connectTimeout(connectTimeout);
        request.socketTimeout(socketTimeout);
        return processRequest(request);
    }

    public String put(String url, String body, Map<String, String> headers) {
        Request request = Request.Put(url).bodyString(body, ContentType.APPLICATION_JSON);
        addHeaders(request, headers);
        request.connectTimeout(connectTimeout);
        request.socketTimeout(socketTimeout);
        return processRequest(request);
    }

    public String patch(String url, Map<String, String> params, Map<String, String> headers) {
        List<NameValuePair> formParams = convertToFormParams(params);
        Request request = Request.Patch(url).bodyForm(formParams, UTF_8);
        addHeaders(request, headers);
        request.connectTimeout(connectTimeout);
        request.socketTimeout(socketTimeout);
        return processRequest(request);
    }

    public String patch(String url, String body, Map<String, String> headers) {
        Request request = Request.Patch(url).bodyString(body, ContentType.APPLICATION_JSON);
        addHeaders(request, headers);
        request.connectTimeout(connectTimeout);
        request.socketTimeout(socketTimeout);
        return processRequest(request);
    }

    public String delete(String uri) {
        Request request = Request.Delete(uri);
        request.connectTimeout(connectTimeout);
        request.socketTimeout(socketTimeout);
        return processRequest(request);
    }

    public String delete(String uri, Map<String, String> headers) {
        Request request = Request.Delete(uri);
        addHeaders(request, headers);
        request.connectTimeout(connectTimeout);
        request.socketTimeout(socketTimeout);
        return processRequest(request);
    }

    public String delete(String url, Map<String, Object> pathParams, Map<String, Object> queryParams, Map<String, Object> headers) {
        Map<String, String> pathMap = convertToStringParams(pathParams);
        //pathMap替换url中的占位符
        for (Entry<String, String> entry : pathMap.entrySet()) {
            url = url.replace("{" + entry.getKey() + "}", entry.getValue());
        }
        url = addQueryString(url, convertToStringParams(queryParams));
        Request request = Request.Delete(url);
        addHeaders(request, convertToStringParams(headers));
        request.connectTimeout(connectTimeout);
        request.socketTimeout(socketTimeout);
        return processRequest(request);
    }

    public byte[] download(String url) throws Exception {
        Request request = Request.Get(url);
        request.connectTimeout(connectTimeout);
        request.socketTimeout(socketTimeout);
        return request.execute().returnContent().asBytes();
    }

    public InputStream downloadStream(String url) throws Exception {
        Request request = Request.Get(url);
        return request.execute().returnContent().asStream();
    }

    private void addHeaders(Request request, Map<String, String> headers) {
        if (headers != null) {
            Iterator<Entry<String, String>> ite = headers.entrySet().iterator();
            while (ite.hasNext()) {
                Entry<String, String> entry = ite.next();
                request.addHeader(entry.getKey(), entry.getValue());
            }
        }
    }


    private List<NameValuePair> convertToFormParams(Map<String, String> params) {
        List<NameValuePair> formParams = new ArrayList<>();
        if (params != null) {
            Iterator<Entry<String, String>> ite = params.entrySet().iterator();
            while (ite.hasNext()) {
                Entry<String, String> entry = ite.next();
                BasicNameValuePair formParam = new BasicNameValuePair(entry.getKey(), entry.getValue());
                formParams.add(formParam);
            }
        }
        return formParams;
    }

    private String processRequest(Request request) {
        responseThreadLocal.remove();
        request.connectTimeout(connectTimeout);
        if (timeoutSeconds.get() != null) {
            request.socketTimeout(timeoutSeconds.get());
        }
        HttpResponse response;
        try {
            response = executor.execute(request).returnResponse();
            responseThreadLocal.set(response);
            return EntityUtils.toString(response.getEntity(), UTF_8);
        } catch (Exception ex) {
            responseThreadLocal.remove();
            throw new RuntimeException(ex);
        }
    }

    public HttpResponse getLastResponse() {
        return responseThreadLocal.get();
    }

    public void setTimeoutSeconds(Integer timeoutSeconds) {
        this.timeoutSeconds.set(timeoutSeconds);
    }

    public void removeTimeoutSet() {
        this.timeoutSeconds.remove();
    }
}
