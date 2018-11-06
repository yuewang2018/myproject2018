package testhttp;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;


import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.HttpHost;
import org.apache.http.message.BasicNameValuePair;


import org.junit.Test;

public class test1 {

	@Test
	public void test() {
		System.out.print("lauching");
		//fail("Not yet implemented");
		
		//String url = "http://www.google.com/search?q=httpClient";
		String url = "http://localhost:8080/server1/index.jsp";

		HttpClient client = HttpClientBuilder.create().build();
		HttpGet request = new HttpGet(url);

		// add request header
		request.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.99 Safari/537.36");
		
		try {
			HttpResponse response = client.execute(request);
	
			System.out.println("Response Code : " 
		                + response.getStatusLine().getStatusCode());
	
			BufferedReader rd = new BufferedReader(
				new InputStreamReader(response.getEntity().getContent()));
	
			StringBuffer result = new StringBuffer();
			String line = "";
			while ((line = rd.readLine()) != null) {
				result.append(line);
			}
			
			System.out.println(result);
		
		}catch (Exception ex)
		{
			System.out.println(ex);
		}
    
	}
	
	@Test
	public void testabc()
	{
		System.out.println("testabc");
	}

}
