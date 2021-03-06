package com.sidc.rcu.hmi.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.sidc.rcu.hmi.abs.AbstractRcuBean;

public class RcuRoomInfoBean extends AbstractRcuBean implements Serializable {
	private static final long serialVersionUID = -8890050681459250032L;

	private String roomNo;
	private String type;
	private String floor;
	private boolean isCheckin = false;
	private List<RcuServiceInfoBean> service = new ArrayList<RcuServiceInfoBean>();
	private List<RcuHvacInfoBean> hvac = new ArrayList<RcuHvacInfoBean>();
	private RcuCardInfoBean card;
	private RcuRoomStatusBean rcu;
	private List<LanguageBean> langs = new ArrayList<LanguageBean>();
	private Date time;

	public RcuRoomStatusBean getRcu() {
		return rcu;
	}

	public String getFloor() {
		return floor;
	}

	public void setFloor(String floor) {
		this.floor = floor;
	}

	public void setRcu(RcuRoomStatusBean rcu) {
		this.rcu = rcu;
	}

	public RcuCardInfoBean getCard() {
		return card;
	}

	public void setCard(RcuCardInfoBean card) {
		this.card = card;
	}

	public List<RcuHvacInfoBean> getHvac() {
		return hvac;
	}

	public void setHvac(List<RcuHvacInfoBean> hvac) {
		this.hvac = hvac;
	}

	public List<RcuServiceInfoBean> getService() {
		return service;
	}

	public void setService(List<RcuServiceInfoBean> service) {
		this.service = service;
	}

	public String getRoomNo() {
		return roomNo;
	}

	public void setRoomNo(String roomNo) {
		this.roomNo = roomNo;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean isCheckin() {
		return isCheckin;
	}

	public void setCheckin(boolean isCheckin) {
		this.isCheckin = isCheckin;
	}

	public List<LanguageBean> getLangs() {
		return langs;
	}

	public void setLangs(List<LanguageBean> langs) {
		this.langs = langs;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public RcuRoomInfoBean(String roomNo, String type) {
		super();
		this.roomNo = roomNo;
		this.type = type;
		this.time = new Date();
	}

	public RcuRoomInfoBean(String roomNo, String type, boolean isCheckin, List<LanguageBean> langs) {
		super();
		this.roomNo = roomNo;
		this.type = type;
		this.isCheckin = isCheckin;
		this.langs = langs;
		this.time = new Date();
	}

	public RcuRoomInfoBean(String roomNo, String floor, boolean isCheckin) {
		super();
		this.roomNo = roomNo;
		this.floor = floor;
	}

}
