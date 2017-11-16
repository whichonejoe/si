package com.sidc.tester.api.mobile;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.imageio.ImageIO;

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
import com.sidc.blackcore.api.mobile.activity.bean.ActivityPhotoUploadBean;
import com.sidc.blackcore.api.mobile.laundry.bean.LaundryItemToReturnTypeBean;
import com.sidc.blackcore.api.mobile.laundry.bean.LaundryItemToWashTypeBean;
import com.sidc.blackcore.api.mobile.laundry.bean.LaundryLangBean;
import com.sidc.blackcore.api.mobile.laundry.request.LaundryItemInsertRequest;

public class LaundryItemInsertTest {

	@Test
	public void test() throws Exception {
		List<ActivityPhotoUploadBean> photoList = new ArrayList<ActivityPhotoUploadBean>();
		photoList.add(new ActivityPhotoUploadBean(getByteArray("C:\\Users\\123\\Desktop\\j.jpg"),
				UUID.randomUUID().toString().replace("-", ""), "jpg"));

		List<LaundryLangBean> list = new ArrayList<LaundryLangBean>();
		List<LaundryItemToWashTypeBean> washtypelist = new ArrayList<LaundryItemToWashTypeBean>();
		List<LaundryItemToReturnTypeBean> returntypelist = new ArrayList<LaundryItemToReturnTypeBean>();

		list.add(new LaundryLangBean("zh_TW", "假皮2"));
		list.add(new LaundryLangBean("en_US", "假2"));

		washtypelist.add(new LaundryItemToWashTypeBean(2, 250));
		washtypelist.add(new LaundryItemToWashTypeBean(4, 300));

		returntypelist.add(new LaundryItemToReturnTypeBean(1));
		returntypelist.add(new LaundryItemToReturnTypeBean(3));

		LaundryItemInsertRequest request = new LaundryItemInsertRequest("token", 1, 3, list, washtypelist,
				returntypelist, photoList);

		HttpClient httpclient = HttpClients.createDefault();
		HttpPost httppost = new HttpPost("http://10.60.1.39:7056/blackcore/sits/laundryservice/iteminsert");

		APIRequest enity = new APIRequest(request);
		Gson gson = new Gson();
		String json = gson.toJson(enity);
		System.out.println(json);
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

	private byte[] getByteArray(final String fileName) throws IOException {
		byte[] imageInByte = new byte[1];
		BufferedImage originalImage = ImageIO.read(new File(fileName));

		// convert BufferedImage to byte array
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(originalImage, "jpg", baos);
		baos.flush();

		imageInByte = baos.toByteArray();
		baos.close();
		return imageInByte;
	}
}
