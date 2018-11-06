package testhttp;

import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;


import org.junit.Test;

public class test2 {

	@Test
	public void test() {
		try {
		HttpGet request = new HttpGet("https://idcs-cloudinfra.identity.aucom-east-1.c9qa132.oc9qadev.com/oauth2/v1/token");
		String auth = "WYMyRoot_APPID" + ":" + "cf8104c0-83ce-4bdc-b01e-6e0e90c9424e"; //MyRoot_APPID:cf8104c0-83ce-4bdc-b01e-6e0e90c9424e
		byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(StandardCharsets.ISO_8859_1));
		String authHeader = "Basic " + new String(encodedAuth);
		request.setHeader(HttpHeaders.AUTHORIZATION, authHeader);

		

		CloseableHttpClient client = HttpClientBuilder.create().build();
		HttpResponse response = client.execute(request);
 
		int statusCode = response.getStatusLine().getStatusCode();
		
		System.out.println(statusCode);
		}catch (Exception ex)
			{
				System.out.println(ex);
			}
		}
}
