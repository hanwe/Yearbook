package com.graduation.yearbook.Http.HttpProvider;

import com.graduation.yearbook.application.BaseApplication;
import com.squareup.okhttp.Cache;
import com.squareup.okhttp.ConnectionPool;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.apache.OkApacheClient;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.AllowAllHostnameVerifier;
import java.io.File;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * Created by josephwang on 15/6/25.
 */

public class HttpClientHelper {
    private static HttpClient httpClient;

    private HttpClientHelper() {

    }

    public static synchronized HttpClient getHttpClient() {
        if (null == httpClient) {
            httpClient = createMyHttpClient();
        }
        return httpClient;
    }

    public static HttpClient createMyHttpClient() {
        OkHttpClient client = getOkHttpClient();
        if (client != null) {
            return new OkApacheClient(client);
        }
        return new OkApacheClient();
    }

    public static OkHttpClient getOkHttpClient() {
        try {
            OkHttpClient client = new OkHttpClient();
            ConnectionPool pool = new ConnectionPool(0, 500);
            client.setConnectionPool(pool);
            client.setConnectTimeout(5000, TimeUnit.MILLISECONDS);

            SSLContext sc = SSLContext.getInstance("TLS");
            TrustManager mTrustManager = new X509TrustManager() {
                @Override
                public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }

                @Override
                public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }

                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
            };
            sc.init(null, new TrustManager[]{mTrustManager}, new java.security.SecureRandom());
            javax.net.ssl.SSLSocketFactory newFactory = sc.getSocketFactory();
            client.setSslSocketFactory(newFactory);

            client.setHostnameVerifier(new AllowAllHostnameVerifier());

            client.setConnectTimeout(10, TimeUnit.SECONDS);
            client.setReadTimeout(30, TimeUnit.SECONDS);

            // 需注意Server回傳資料是否會有cache問題
            File httpCacheDir = new File(BaseApplication.getContext().getCacheDir(), "http");
            long httpCacheSize = 30 * 1024 * 1024; // 30 MiB
            try {
                client.setCache(new Cache(httpCacheDir, httpCacheSize));
            } catch (IOException e) {
                e.printStackTrace();
            }

            return client;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();

        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        return null;
    }
}