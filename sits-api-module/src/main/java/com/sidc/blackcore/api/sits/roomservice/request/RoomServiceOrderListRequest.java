package com.sidc.blackcore.api.sits.roomservice.request;

import java.io.Serializable;

public class RoomServiceOrderListRequest implements Serializable {
	private static final long serialVersionUID = 1926958967294474638L;
	private String langcode;
	private String deviceid;
	private int orderid;
	private String status;
	private String startime;
	private String endtime;

	public String getLangcode() {
		return langcode;
	}

	public String getDeviceid() {
		return deviceid;
	}

	public int getOrderid() {
		return orderid;
	}

	public String getStatus() {
		return status;
	}

	public String getStartime() {
		return startime;
	}

	public String getEndtime() {
		return endtime;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RoomServiceOrderListRequest [langcode=");
		builder.append(langcode);
		builder.append(", deviceid=");
		builder.append(deviceid);
		builder.append(", orderid=");
		builder.append(orderid);
		builder.append(", status=");
		builder.append(status);
		builder.append(", startime=");
		builder.append(startime);
		builder.append(", endtime=");
		builder.append(endtime);
		builder.append("]");
		return builder.toString();
	}

	public RoomServiceOrderListRequest(String langcode, String deviceid, int orderid, String status, String startime,
			String endtime) {
		super();
		this.langcode = langcode;
		this.deviceid = deviceid;
		this.orderid = orderid;
		this.status = status;
		this.startime = startime;
		this.endtime = endtime;
	}

}
