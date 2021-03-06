package com.sidc.rcu.hmi.modesetting.bean;

import java.io.Serializable;
import java.util.List;

public class RcuModeCatalogueBean implements Serializable {
	private static final long serialVersionUID = 7301324381965145056L;

	private String catalogue;
	private List<RcuModeDevicesBean> devices;

	public String getCatalogue() {
		return catalogue;
	}

	public List<RcuModeDevicesBean> getDevices() {
		return devices;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RcuModelCatalogueBean [catalogue=");
		builder.append(catalogue);
		builder.append(", devices=");
		builder.append(devices);
		builder.append("]");
		return builder.toString();
	}

	public RcuModeCatalogueBean(String catalogue, List<RcuModeDevicesBean> devices) {
		super();
		this.catalogue = catalogue;
		this.devices = devices;
	}

}
