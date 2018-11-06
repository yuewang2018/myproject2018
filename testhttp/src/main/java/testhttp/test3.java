package testhttp;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class test3 {

	@Test
	public void test() throws IOException {
        CredentialsProvider credsProvider = new BasicCredentialsProvider();
        credsProvider.setCredentials(
                new AuthScope("idcs-37d5e8c3a7394d079fdf3ef4aedb991e.identity.c9qa132.oc9qadev.com", 8080),
                new UsernamePasswordCredentials("yue.yw.wang", "Welcome12345@"));
        CloseableHttpClient httpclient = HttpClients.custom()
                .setDefaultCredentialsProvider(credsProvider)
                .build();
        try {
        	// https://idcs-37d5e8c3a7394d079fdf3ef4aedb991e.identity.c9qa132.oc9qadev.com/ui/v1/signin
        	// https://idcs-37d5e8c3a7394d079fdf3ef4aedb991e.identity.c9qa132.oc9qadev.com/ui/v1/adminconsole
            HttpGet httpget = new HttpGet("https://idcs-37d5e8c3a7394d079fdf3ef4aedb991e.identity.c9qa132.oc9qadev.com/ui/v1/adminconsole");

            System.out.println("Executing request " + httpget.getRequestLine());
            CloseableHttpResponse response = httpclient.execute(httpget);
            try {
                System.out.println("----------------------------------------");
                System.out.println(response.getStatusLine());
                System.out.println(EntityUtils.toString(response.getEntity()));
            } finally {
                response.close();
            }
        } finally {
            httpclient.close();
        }
	}
}
