package com.sidc.rcu.hmi.bean;

import java.io.Serializable;
import java.util.Date;

public class RcuRoomStatusBean implements Serializable {
	private static final long serialVersionUID = -5930399699018236370L;
	private String roomNo;
	private Date time;

	public String getRoomNo() {
		return roomNo;
	}

	public Date getTime() {
		return time;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RcuRoomStatusBean [roomNo=");
		builder.append(roomNo);
		builder.append(", time=");
		builder.append(time);
		builder.append("]");
		return builder.toString();
	}

	public RcuRoomStatusBean(String roomNo) {
		super();
		this.roomNo = roomNo;
		this.time = new Date();
	}

}
