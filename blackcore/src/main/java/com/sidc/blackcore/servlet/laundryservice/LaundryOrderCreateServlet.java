package com.sidc.blackcore.servlet.laundryservice;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sidc.blackcore.api.mobile.laundry.request.LaundryOrderCreateRequest;
import com.sidc.blackcore.api.parser.APIParser;
import com.sidc.blackcore.api.parser.APIServlet;
import com.sidc.sits.logical.laundryservice.LaundryOrderCreateProcess;
import com.sidc.utils.exception.SiDCException;
import com.sidc.utils.log.LogAction;

@WebServlet("/sits/laundryservice/ordercreate")
public class LaundryOrderCreateServlet extends APIServlet {
	private static final long serialVersionUID = 8335329566592634731L;
	private final static Logger logger = LoggerFactory.getLogger(LaundryOrderCreateServlet.class);

	public LaundryOrderCreateServlet() {
		// TODO Auto-generated constructor stub
	}

	@SuppressWarnings("unchecked")
	@Override
	protected Object execute(String apiRequest, HttpServletRequest req) throws SiDCException, Exception {
		// TODO Auto-generated method stub
		final LaundryOrderCreateRequest enity = (LaundryOrderCreateRequest) APIParser.getInstance().parse(apiRequest,
				LaundryOrderCreateRequest.class);

		return new LaundryOrderCreateProcess(enity).execute();
	}

	@Override
	protected void initial() throws SiDCException, Exception {
		// TODO Auto-generated method stub
		LogAction.getInstance().initial(logger, this.getClass().getCanonicalName());
	}
}