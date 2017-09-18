package com.sidc.rcu.hmi.conf;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import com.sidc.rcu.hmi.bean.systeminitial.WebsocketInitialBean;
import com.sidc.rcu.hmi.common.CommonDataKey;
import com.sidc.rcu.hmi.common.DataCenter;

public class SystemConfiguration {
	public SystemConfiguration() throws Exception {
		// TODO Auto-generated constructor stub
	}

	public void initial() throws Exception {
		WebsocketInitialBean websocketSetting = readWebSocketFile(
				new File(Env.SYSTEM_DEF_PATH + Env.RMI_CONFIGUATION_PATH));

		if (websocketSetting == null) {
			throw new NullPointerException();
		}

		DataCenter.getInstance().put(CommonDataKey.WEBSOCKET_SETTING, websocketSetting);
	}

	private WebsocketInitialBean readWebSocketFile(File file) throws Exception {

		JAXBContext jaxbContext = JAXBContext.newInstance(WebsocketInitialBean.class);

		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		WebsocketInitialBean config = (WebsocketInitialBean) jaxbUnmarshaller.unmarshal(file);

		return config;
	}
}
