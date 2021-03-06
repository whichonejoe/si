package test;

import java.io.File;
import java.util.HashMap;

import org.apache.commons.codec.CharEncoding;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

import com.google.gson.Gson;
import com.sidc.rcu.hmi.bean.RcuCardInfoBean;
import com.sidc.rcu.hmi.common.CommonDataKey;
import com.sidc.rcu.hmi.common.DataCenter;
import com.sidc.rcu.hmi.initial.RcuRoomStatusInitial;
import com.sidc.rcu.hmi.receiver.bean.UdpRreceiveBean;
import com.sidc.rcu.hmi.udp.receiver.RcuCardReceiver;
import com.sidc.utils.exception.SiDCException;

public class cardReceiverUdpTest {

	@SuppressWarnings("unused")
	@Test
	public void test() throws SiDCException, Exception {
		new RcuRoomStatusInitial("/listroomrcu").execute();

		HashMap<String, RcuCardInfoBean> map = (HashMap<String, RcuCardInfoBean>) DataCenter.getInstance()
				.get(CommonDataKey.RCU_CARD_INFO);

		File f = new File("D:\\workspace\\sidc-ra-server\\WebContent\\card.json");

		String json = FileUtils.readFileToString(f, CharEncoding.UTF_8);

		Gson gson = new Gson();

		UdpRreceiveBean entity = gson.fromJson(json, UdpRreceiveBean.class);

		RcuCardInfoBean cardInfoEntity = map.get(entity.getRoomNo());

//		for (RcuDeviceBean testEntity : cardInfoEntity.getList()) {
//			if (testEntity.getKeycode().equals("MEMBERCARD")) {
//				System.out.println("============================================");
//				if (testEntity.getCondition() == null) {
//					System.out.println("condition null");
//					continue;
//				}
//				System.out.println(testEntity.getCondition().getStatus());
//			}
//		}

		new RcuCardReceiver(entity.getRoomNo(), entity.getData()).execute();

		map = (HashMap<String, RcuCardInfoBean>) DataCenter.getInstance().get(CommonDataKey.RCU_CARD_INFO);

		cardInfoEntity = map.get(entity.getRoomNo());

//		for (RcuDeviceBean testEntity : cardInfoEntity.getList()) {
//			if (testEntity.getKeycode().equals("MEMBERCARD")) {
//				System.out.println(testEntity.getCondition().getStatus());
//			}
//		}
	}

}
