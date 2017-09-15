package com.sidc.rcu.hmi.websocket.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.sidc.rcu.hmi.bean.LanguageBean;

public class IndexServiceWebsoketBean implements Serializable {
	private static final long serialVersionUID = -7274066531290527887L;
	private String roomNo;
	private String keycode;
	private String status;
	private List<LanguageBean> langs;
	private int lostMinute;
	private Date time;

	public String getRoomNo() {
		return roomNo;
	}

	public String getKeycode() {
		return keycode;
	}

	public String getStatus() {
		return status;
	}

	public List<LanguageBean> getLangs() {
		return langs;
	}

	public int getLostMinute() {
		return lostMinute;
	}

	public Date getTime() {
		return time;
	}

	public IndexServiceWebsoketBean(String roomNo, String keycode, String status, List<LanguageBean> langs,
			int lostMinute, Date time) {
		super();
		this.roomNo = roomNo;
		this.keycode = keycode;
		this.status = status;
		this.langs = langs;
		this.lostMinute = lostMinute;
		this.time = time;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("IndexServiceWebsoketBean [roomNo=");
		builder.append(roomNo);
		builder.append(", keycode=");
		builder.append(keycode);
		builder.append(", status=");
		builder.append(status);
		builder.append(", langs=");
		builder.append(langs);
		builder.append(", lostMinute=");
		builder.append(lostMinute);
		builder.append(", time=");
		builder.append(time);
		builder.append("]");
		return builder.toString();
	}

}