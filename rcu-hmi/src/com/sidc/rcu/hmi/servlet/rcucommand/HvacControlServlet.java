package com.sidc.rcu.hmi.servlet.rcucommand;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sidc.rcu.hmi.api.parser.APIParser;
import com.sidc.rcu.hmi.api.parser.APIServlet;
import com.sidc.rcu.hmi.logical.rcucommand.HvacControlProcess;
import com.sidc.rcu.hmi.rcucommand.request.HvacControlRequest;
import com.sidc.utils.exception.SiDCException;
import com.sidc.utils.log.LogAction;

@WebServlet("/rcu/hvaccontrol")
public class HvacControlServlet extends APIServlet {
	private static final long serialVersionUID = -4022837555987582601L;
	private final static Logger logger = LoggerFactory.getLogger(HvacControlServlet.class);

	@SuppressWarnings("unchecked")
	@Override
	protected Object execute(String apiRequest) throws SiDCException, Exception {
		// TODO Auto-generated method stub
		final HvacControlRequest entity = (HvacControlRequest) APIParser.getInstance().parse(apiRequest,
				HvacControlRequest.class);

		return new HvacControlProcess(entity).execute();
	}

	@Override
	protected void initial(HttpServletRequest req) throws SiDCException, Exception {
		// TODO Auto-generated method stub
		LogAction.getInstance().initial(logger, this.getClass().getCanonicalName());
	}

}
