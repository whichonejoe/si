package com.sidc.sdk.agent;

import com.derex.cm.stb.api.request.APIRequest;
import com.sidc.blackcore.api.agent.request.RoomChangeRequest;
import com.sidc.sdk.abs.AbsHttpClient;

public class RoomChangeClient extends AbsHttpClient {

    private RoomChangeRequest enity;

    public RoomChangeClient(String host, RoomChangeRequest enity) {
	super(host, "/roomchange");
	this.enity = enity;
	// TODO Auto-generated constructor stub
    }

    @Override
    protected String request() throws Exception {
	// TODO Auto-generated method stub
	APIRequest request = new APIRequest(enity);

	return super.getGson().toJson(request);
    }

    @Override
    protected Object response(String json) throws Exception {
	// TODO Auto-generated method stub
	return null;
    }

}
