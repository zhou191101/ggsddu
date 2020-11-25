package com.ggsddu.http;

import org.apache.http.HttpHeaders;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

public class HttpUtils {

    public static String doGet(String url, String auth) {
        CloseableHttpClient client = null;
        CloseableHttpResponse response = null;
        String result = null;
        try {

            PoolingHttpClientConnectionManager connectionManager = connectionManagerBuilder();
            client = HttpClients.custom().setConnectionManager(connectionManager).build();

            HttpGet httpGet = new HttpGet(url);
            httpGet.setHeader(HttpHeaders.AUTHORIZATION, "Basic " + auth);

            response = client.execute(httpGet);

            result = EntityUtils.toString(response.getEntity(), "UTF-8");

        } catch (NoSuchAlgorithmException | KeyManagementException | IOException e) {
            e.printStackTrace();
        } finally {
            closeAll(client, response);
        }

        return result;
    }


    public static String doPost(String url, String auth, String body) {
        CloseableHttpClient client = null;
        CloseableHttpResponse response = null;
        String result = null;
        try {

            PoolingHttpClientConnectionManager connectionManager = connectionManagerBuilder();
            client = HttpClients.custom().setConnectionManager(connectionManager).build();

            HttpPost httpPost = new HttpPost(url);
            httpPost.setHeader(HttpHeaders.AUTHORIZATION, "Basic " + auth);

            httpPost.setHeader(HttpHeaders.CONTENT_TYPE, "applicatioon/json");
            StringEntity stringEntity = new StringEntity(body, "UTF-8");
            stringEntity.setContentEncoding("UTF-8");
            httpPost.setEntity(stringEntity);
            response = client.execute(httpPost);

            result = EntityUtils.toString(response.getEntity(), "UTF-8");

        } catch (NoSuchAlgorithmException | KeyManagementException | IOException e) {
            e.printStackTrace();
        } finally {
            closeAll(client, response);
        }

        return result;
    }

    public static String doPut(String url, String auth, String body) {
        CloseableHttpClient client = null;
        CloseableHttpResponse response = null;
        String result = null;
        try {

            PoolingHttpClientConnectionManager connectionManager = connectionManagerBuilder();
            client = HttpClients.custom().setConnectionManager(connectionManager).build();

            HttpPut httpPut = new HttpPut(url);
            httpPut.setHeader(HttpHeaders.AUTHORIZATION, "Basic " + auth);

            httpPut.setHeader(HttpHeaders.CONTENT_TYPE, "applicatioon/json");
            StringEntity stringEntity = new StringEntity(body, "UTF-8");
            stringEntity.setContentEncoding("UTF-8");
            httpPut.setEntity(stringEntity);
            response = client.execute(httpPut);

            result = EntityUtils.toString(response.getEntity(), "UTF-8");

        } catch (NoSuchAlgorithmException | KeyManagementException | IOException e) {
            e.printStackTrace();
        } finally {
            closeAll(client, response);
        }

        return result;
    }


    private static PoolingHttpClientConnectionManager connectionManagerBuilder() throws NoSuchAlgorithmException, KeyManagementException {
        SSLContext sslContext = SSLContext.getInstance("TSL");
        // 绕过安全验证
        X509TrustManager trustManager = new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

            }

            @Override
            public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }
        };
        sslContext.init(null, new TrustManager[]{trustManager}, null);
        Registry<ConnectionSocketFactory> connectionSocketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.INSTANCE)
                // NoopHostnameVerifier.INSTANCE 为不做ip校验
                .register("https", new SSLConnectionSocketFactory(sslContext, NoopHostnameVerifier.INSTANCE))
                .build();

        return new PoolingHttpClientConnectionManager(connectionSocketFactoryRegistry);
    }


    private static void closeAll(CloseableHttpClient client, CloseableHttpResponse response) {
        if (client != null) {
            try {
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (response != null) {
            try {
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
