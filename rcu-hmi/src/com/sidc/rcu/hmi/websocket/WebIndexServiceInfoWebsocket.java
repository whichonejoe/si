package com.sidc.rcu.hmi.websocket;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import com.sidc.rcu.hmi.api.parser.UDPParser;
import com.sidc.rcu.hmi.bean.RcuRoomStatusBean;
import com.sidc.rcu.hmi.bean.RcuServiceInfoBean;
import com.sidc.rcu.hmi.common.CommonDataKey;
import com.sidc.rcu.hmi.common.DataCenter;
import com.sidc.rcu.hmi.conf.PmsCommonKey;
import com.sidc.rcu.hmi.websocket.bean.IndexServiceWebsoketBean;
import com.sidc.rcu.hmi.websocket.configurator.WebSocketEndPointConfigurator;
import com.sidc.utils.exception.SiDCException;

@ServerEndpoint(value = "/socketindexinfo", configurator = WebSocketEndPointConfigurator.class)
public class WebIndexServiceInfoWebsocket {
	private List<Session> userSessions = Collections.synchronizedList(new ArrayList<Session>());

	@OnOpen
	public void onOpen(Session userSession) {
		synchronized (userSessions) {
			userSessions.add(userSession);
		}
	}

	@OnError
	public void error(Session session, Throwable t) throws IOException {
		session.close();
		synchronized (userSessions) {
			userSessions.remove(session);
		}
	}

	@OnClose
	public void onClose(Session userSession) throws IOException {
		userSession.close();
		synchronized (userSessions) {
			userSessions.remove(userSession);
		}

		// userSession.setMaxIdleTimeout(-1);
	}

	@OnMessage
	public void onMessage(String message, Session userSession) throws SiDCException, IOException {

		final List<IndexServiceWebsoketBean> list = getBroadcastMessage();

		list.addAll(roomInfoProcess(PmsCommonKey.CHECKIN));
		list.addAll(roomInfoProcess(PmsCommonKey.CHECKOUT));

		broadcast(UDPParser.getInstance().toJson(list));

		if (message.equals("logopen")) {
			/*
			 * String result = ""; int index = 0; try { BufferedReader br = new
			 * BufferedReader(new FileReader(Env.SYSTEM_DEF_PATH +
			 * "hmi_websocket_test")); String line; while ((line =
			 * br.readLine()) != null) { ++index; result += line +
			 * System.getProperty("line.separator"); } } catch (Exception e) {
			 * 
			 * }
			 * 
			 * if (index > 5000) { FileUtils.writeStringToFile(new
			 * File(Env.SYSTEM_DEF_PATH + "hmi_websocket_test"),
			 * UDPParser.getInstance().toJson(list)); } else {
			 * FileUtils.writeStringToFile(new File(Env.SYSTEM_DEF_PATH +
			 * "hmi_websocket_test"), result +
			 * UDPParser.getInstance().toJson(list)); }
			 */
		}

	}

	@SuppressWarnings("unchecked")
	private List<IndexServiceWebsoketBean> roomInfoProcess(final String key) {
		final List<String> rooms = (List<String>) DataCenter.getInstance().get(key);

		if (rooms == null || rooms.isEmpty()) {
			return new ArrayList<IndexServiceWebsoketBean>();
		}

		List<IndexServiceWebsoketBean> list = new ArrayList<IndexServiceWebsoketBean>();

		list.add(new IndexServiceWebsoketBean(key, String.valueOf(rooms.size())));

		return list;
	}

	@SuppressWarnings("unchecked")
	private List<IndexServiceWebsoketBean> getBroadcastMessage() {
		final String[] keycode = { "SOS", "MUR", "DND", "PIR" };

		final long intervals = 60000 * 1;// 訊息 遺失時間

		final HashMap<String, List<RcuServiceInfoBean>> map = (HashMap<String, List<RcuServiceInfoBean>>) DataCenter
				.getInstance().get(CommonDataKey.RCU_SERVICE_INFO);

		final HashMap<String, RcuRoomStatusBean> roomMap = (HashMap<String, RcuRoomStatusBean>) DataCenter.getInstance()
				.get(CommonDataKey.RCU_ROOM_STATUS);

		List<IndexServiceWebsoketBean> list = new ArrayList<IndexServiceWebsoketBean>();

		if (map == null || map.isEmpty()) {
			return list;
		}

		if (roomMap == null || roomMap.isEmpty()) {
			return list;
		}

		for (List<RcuServiceInfoBean> servicelist : map.values()) {
			for (RcuServiceInfoBean entity : servicelist) {
				for (String str : keycode) {
					if (entity.getKeycode().equals(str)) {
						if (entity.getStatus().equals("1")) {
							final RcuRoomStatusBean heartbeatEntity = roomMap.get(entity.getRoomNo());

							final long minusTime = (heartbeatEntity.getTime().getTime() - entity.getTime().getTime())
									/ intervals;

							list.add(new IndexServiceWebsoketBean(entity.getRoomNo(), entity.getKeycode(),
									entity.getStatus(), entity.getLangs(), (int) minusTime, entity.getTime()));
						}
						break;
					}
				}
			}
		}

		return list;
	}

	private void broadcast(String msg) throws IOException {
		if (userSessions == null || userSessions.isEmpty()) {
			return;
		}

		synchronized (userSessions) {
			for (Session s : userSessions) {
				if (!s.isOpen())
					continue;
				s.getBasicRemote().sendText(msg);
			}
		}
	}
}
