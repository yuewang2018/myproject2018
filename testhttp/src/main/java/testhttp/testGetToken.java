package testhttp;

import static org.junit.Assert.*;

import org.junit.Test;


import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;

import org.apache.commons.io.FileUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

import org.json.JSONObject;
import org.json.JSONArray;
import org.json.*;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.cert.X509Certificate;

import org.junit.Test;

public class testGetToken {
	public String token="";
	
	public void get_token()
	{
		try {
            TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return null;
                }

                public void checkClientTrusted(X509Certificate[] certs, String authType) {
                }

                public void checkServerTrusted(X509Certificate[] certs, String authType) {
                }

            } };
            
            SSLContext sslcontext = SSLContext.getInstance("SSL");
            sslcontext.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sslcontext.getSocketFactory());
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext);
            CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
            Unirest.setHttpClient(httpclient);
			
			
	        HttpResponse<JsonNode> response = Unirest.post("https://idcs-cloudinfra.identity.aucom-east-1.c9qa132.oc9qadev.com/oauth2/v1/token")
	                .header("content-type", "application/x-www-form-urlencoded; charset=utf-8")
	                .header("authorization","Basic TXlSb290X0FQUElEOmNmODEwNGMwLTgzY2UtNGJkYy1iMDFlLTZlMGU5MGM5NDI0ZQ==")
	                .body("grant_type=client_credentials&scope=urn:opc:idm:__myscopes__")
	                .asJson();
	        System.out.println(response);
	        token = response.getBody().getObject().getString("access_token");
	        System.out.println(token);
		}
		catch (Exception ex)
		{
			System.out.println(ex);
		}
	}
	
	public void get_sys_config()
	{
		try {
			
		     String url = "https://idcs-cloudinfra.identity.aucom-east-1.c9qa132.oc9qadev.com/admin/v1/GlobalConfig/GlobalConfig";
		     HttpResponse<JsonNode> response = Unirest.get(url)
		        .header("content-type", "application/json")
		        .header("authorization","Bearer "+ token)
		        .asJson();
			 String id = response.getBody().getObject().getJSONArray("Resources").getJSONObject(0).get("id").toString();
			 System.out.println(id);
		
		}
		catch (Exception ex)
		{
			 System.out.println(ex);
		}
	}
	
	public void create_application()
	{
		try {
	        File file=new File("c:\\temp\\1.json");
	        String content= FileUtils.readFileToString(file,"UTF-8");
	        JSONObject exampleJson=new JSONObject(content);
			
			
		    String url = "https://idcs-cloudinfra.identity.aucom-east-1.c9qa132.oc9qadev.com/sm/v1/AppServices";
		    HttpResponse<JsonNode> response = Unirest.post(url).header("content-type", "application/json").header("authorization","Bearer "+ token).body(exampleJson).asJson();		        
			
		    String idcsServiceURI = response.getBody().getObject().getString("idcsServiceURI");
  
		    
		    System.out.println("idcsServiceURI is: "+idcsServiceURI);
		    
		}
		catch (Exception ex)
		{
			System.out.println(ex);
		}
	}

	public void process_json()
	{
		try {
			
			// --- Load json obj from file -- done
	        File file=new File("c:\\temp\\1.json");
	        String content= FileUtils.readFileToString(file,"UTF-8");
	        JSONObject exampleJson=new JSONObject(content);
	        
	        // --- print out all content  -- done
	        String[] names=JSONObject.getNames(exampleJson);
	        for (String string:names) {
	        	System.out.println(string+":"+exampleJson.get(string));
	        }

	        // --- Read basic values of first layer -- done
	        String opcServiceInstanceGUID=exampleJson.getString("opcServiceInstanceGUID");
	        System.out.println(opcServiceInstanceGUID);
	        
	        boolean isSingleton=exampleJson.getBoolean("isSingleton");
	        System.out.println(isSingleton);
	        
       
	        // --- Read non-direct values (inside object)  -- version  (done) 
	        String version=exampleJson.getJSONObject("idcsArtifactsList").getString("version");
	        System.out.println(version);
	        
	        // --- Read array as a whole
	        JSONArray allowedGrants=exampleJson.getJSONObject("idcsArtifactsList").getJSONObject("OAuthClient").getJSONArray("allowedGrants");
	        System.out.println(allowedGrants);
	        
            // --- Read array content directly
	        String allowedGrants0=exampleJson.getJSONObject("idcsArtifactsList").getJSONObject("OAuthClient").getJSONArray("allowedGrants").getString(0);
	        System.out.println(allowedGrants0);
	        
	        // --- Read complex array: 
	        // https://blog.csdn.net/xiazdong/article/details/7059573
	        
	        // --- Update basic value of first layer; find the root and put new <key,value> pair and old <key,value> will be updated
	        exampleJson.put("opcServiceInstanceGUID", "testappwy");
	        exampleJson.getJSONObject("idcsArtifactsList").put("version", "18.3.4");
	        
	        // --- Update array value
	        
	        
	        // --- parsing http response
	        //   HttpResponse<JsonNode> response = Unirest.post(url).header("content-type", "application/json").header("authorization","Bearer "+ token).body(exampleJson).asJson();		        
			//   String idcsServiceURI = response.getBody().getObject().getString("idcsServiceURI");  // response.getBody().getObject() is the root for response json
	        
	        // --- Serialize new content  -- done
	        String exampleJsonStr = exampleJson.toString(); 
	        PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(new File("c:\\temp\\out.json"))));
	        writer.write(exampleJsonStr);
	        writer.close();
		    
		}
		catch (Exception ex)
		{
			System.out.println(ex);
		}
	}
	@Test
	public void test() {
		get_token();
		//get_sys_config();	
		create_application();
		//process_json();
		//update_json();
	}
}
