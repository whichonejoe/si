package com.sidc.rcu.hmi.websocket.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RoomControlCatalogueWebsocketBean implements Serializable {
	private static final long serialVersionUID = 4990113725414937987L;

	private String catalogue;
	private List<RoomControlDevicesWebsocketBean> devices = new ArrayList<RoomControlDevicesWebsocketBean>();

	public List<RoomControlDevicesWebsocketBean> getDevices() {
		return devices;
	}

	public RoomControlCatalogueWebsocketBean(String catalogue, List<RoomControlDevicesWebsocketBean> devices) {
		super();
		this.catalogue = catalogue;
		this.devices = devices;
	}

	public String getCatalogue() {
		return catalogue;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RoomControlCatalogueWebsocketBean [catalogue=");
		builder.append(catalogue);
		builder.append(", devices=");
		builder.append(devices);
		builder.append("]");
		return builder.toString();
	}
}
