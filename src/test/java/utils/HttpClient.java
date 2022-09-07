package utils;

import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;

import java.io.IOException;

public class HttpClient {

    public static HttpResponse get(String url) throws IOException {
        HttpGet getRequest = new HttpGet(url);
        getRequest.addHeader(new BasicHeader(HttpHeaders.ACCEPT, "application/json"));
        System.out.println(getRequest);

        HttpResponse response = HttpClientBuilder.create().build().execute(getRequest);

        return response;
    }
}
