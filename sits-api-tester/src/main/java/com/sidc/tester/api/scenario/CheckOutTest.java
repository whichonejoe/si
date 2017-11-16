package com.sidc.tester.api.scenario;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import com.derex.cm.stb.api.request.APIRequest;
import com.google.gson.Gson;
import com.sidc.blackcore.api.agent.request.CheckOutRequest;

/**
 * 
 * @author Allen Huang	
 *
 */
public class CheckOutTest {

	@Test
	public void test() {
		
//		String[] roomno = new String[] { "601", "607" };
//		
//		for (String string : roomno) {
			HttpClient httpclient = HttpClients.createDefault();
			HttpPost httppost = new HttpPost("http://10.60.1.251:7056/blackcore/checkout");
			
			// Request parameters and other properties.
			APIRequest enity = new APIRequest(new CheckOutRequest("611"));
			Gson gson = new Gson();
			String json = gson.toJson(enity);
			
			try {
				StringEntity entity = new StringEntity(json, "UTF-8");
				httppost.setEntity(entity);
		        HttpResponse response = httpclient.execute(httppost);
		        String result = EntityUtils.toString(response.getEntity());
		        System.out.println(result);
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
//	}

}
