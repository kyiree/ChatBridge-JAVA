package com.cn.chat.bridge.common.http;

import lombok.extern.slf4j.Slf4j;
import org.apache.hc.client5.http.config.ConnectionConfig;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder;
import org.apache.hc.client5.http.ssl.NoopHostnameVerifier;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactory;
import org.apache.hc.core5.http.io.SocketConfig;
import org.apache.hc.core5.ssl.SSLContexts;
import org.apache.hc.core5.ssl.TrustStrategy;
import org.apache.hc.core5.util.Timeout;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;

import javax.net.ssl.SSLContext;

@Slf4j
public class HttpRequestFactoryBuilder {

    private static final String[] GMSSL_PROTOCOLS = new String[]{"GMSSLv1.1"};

    private static final String[] GMSSL_CIPHER_SUITES = new String[]{"ECC_SM4_CBC_SM3", "ECC_SM4_GCM_SM3"};

    public static HttpComponentsClientHttpRequestFactory build(int connectTimeout, int readTimeout) {
        try {
            TrustStrategy acceptingTrustStrategy = ((x509Certificates, s) -> true);
            PoolingHttpClientConnectionManagerBuilder managerBuilder = PoolingHttpClientConnectionManagerBuilder.create();
            SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(null, acceptingTrustStrategy).build();
            SSLConnectionSocketFactory connectionSocketFactory = new SSLConnectionSocketFactory(sslContext, new NoopHostnameVerifier());
            managerBuilder.setSSLSocketFactory(connectionSocketFactory);
            managerBuilder.setDefaultSocketConfig(SocketConfig.custom().setSoTimeout(Timeout.ofMicroseconds(readTimeout * 1000L)).build());
            managerBuilder.setDefaultConnectionConfig(ConnectionConfig.custom().setConnectTimeout(Timeout.ofMilliseconds(connectTimeout * 1000L)).setSocketTimeout(Timeout.ofMilliseconds(connectTimeout * 1000L)).build());
            HttpClientBuilder httpClientBuilder = HttpClients.custom();
            httpClientBuilder.setConnectionManager(managerBuilder.build());
            CloseableHttpClient httpClient = httpClientBuilder.build();
            HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
            factory.setConnectTimeout(connectTimeout * 1000);
            factory.setHttpClient(httpClient);
            return factory;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        return null;
    }
}
