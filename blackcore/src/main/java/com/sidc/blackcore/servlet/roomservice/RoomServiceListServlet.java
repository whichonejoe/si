package com.sidc.blackcore.servlet.roomservice;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sidc.blackcore.api.parser.APIParser;
import com.sidc.blackcore.api.parser.APIServlet;
import com.sidc.blackcore.api.sits.roomservice.request.RoomServiceRequest;
import com.sidc.sits.logical.roomservice.RoomServiceListProcess;
import com.sidc.utils.exception.SiDCException;
import com.sidc.utils.log.LogAction;

@WebServlet("/sits/inroomdininglist")
public class RoomServiceListServlet extends APIServlet {
	private static final long serialVersionUID = 1035845176248966534L;
	private final static Logger logger = LoggerFactory.getLogger(RoomServiceListServlet.class);

	@SuppressWarnings("unchecked")
	@Override
	protected Object execute(String apiRequest, HttpServletRequest req) throws SiDCException, Exception {

		final RoomServiceRequest enity = (RoomServiceRequest) APIParser.getInstance().parse(apiRequest,
				RoomServiceRequest.class);

		return new RoomServiceListProcess(enity).execute();
	}

	@Override
	protected void initial() throws SiDCException, Exception {
		// TODO Auto-generated method stub
		LogAction.getInstance().initial(logger, this.getClass().getCanonicalName());
	}
}
