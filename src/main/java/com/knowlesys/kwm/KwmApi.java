package com.knowlesys.kwm;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * Copyright © 2017-present, Knowlesys, Inc.
 * All rights reserved.
 * <p>
 * Created by hfx on 2018-05-09.
 */
public class KwmApi {

    public static void main(String[] args) {
        String url = "http://knowlesys.qnoddns.org.cn:8181/kwm/module/system_tool/other/user_token_generator.php";
        try {
            URIBuilder uriBuilder = new URIBuilder(url);
            uriBuilder.setParameter("isApiLoginRequest", "1");
            uriBuilder.setParameter("apiUserAccount", "bjzpyt_admin");
            uriBuilder.setParameter("apiPassword", PwdEncoder.encoder("admin1@"));

            url = uriBuilder.toString();
            System.out.println(url);

            String result = httpGet(url);
            System.out.println(result);

        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private static String httpGet(String url) {
        String result = null;
        HttpClientBuilder builder = HttpClients.custom();
        CloseableHttpClient httpClient = builder.build();

        CloseableHttpResponse response = null;

        HttpGet get = new HttpGet(url);
        try {
            response = httpClient.execute(get);
            System.out.println(response.getStatusLine().getStatusCode());
            HttpEntity httpEntity = response.getEntity();
            if (httpEntity != null) {
                result = EntityUtils.toString(response.getEntity());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }
}
